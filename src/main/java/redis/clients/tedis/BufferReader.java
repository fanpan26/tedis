package redis.clients.tedis;

import org.tio.core.exception.AioDecodeException;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class BufferReader {

    public static boolean isCr(byte b) {
        return b == Protocol.CR;
    }
    public static boolean isLf(byte b) {
        return b == Protocol.LF;
    }

    private static long readLongCrLf(ByteBuffer buffer) throws AioDecodeException {
        long value = 0L;
        boolean isNeg = false;
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            if (b == '-') {
                isNeg = true;
                continue;
            }
            if (isCr(b)) {
                b = buffer.get();
                if (!isLf(b)) {
                    throw new AioDecodeException("unexpected redis server response");
                }
                break;
            } else {
                value = value * 10 + b - '0';
            }
        }
        return isNeg ? -value : value;
    }

    private static int readIntCrLf(ByteBuffer buffer) throws AioDecodeException {
        return (int) readLongCrLf(buffer);
    }

    /**
     * 服务器响应信息解析
     * +号开头，例如 +OK\r\n
     */
    private static Object readSingleLineBody(ByteBuffer buffer, int limit, int position) throws AioDecodeException {
        byte[] body = new byte[limit - position];
        int i = 0;
        //结束标志
        boolean endFlag = false;

        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            //如果是\r
            if (isCr(b)) {
                byte c = buffer.get();
                //如果不是\n抛出异常
                if (!isLf(c)) {
                    throw new AioDecodeException("unexpected redis server response");
                }
                //结束解析
                endFlag = true;
                break;
            } else {
                body[i++] = b;
            }
        }
        //如果此次解析一直没有遇到\r\n，则返回null，等待下次解析
        if (!endFlag) {
            return null;
        }
        byte[] resBody = new byte[i];
        System.arraycopy(body, 0, resBody, 0, i);
        return resBody;
    }

    private static final byte[] EMPTY_BYTES = new byte[0];

    /**
     * 服务器响应信息解析
     * $号开头，例如 $5\r\nredis\r\n
     */
    private static Object readFixedLengthBody(ByteBuffer buffer) throws AioDecodeException {
        int bodyLength = readIntCrLf(buffer);
        if (bodyLength == -1) {
            return EMPTY_BYTES;
        }

        //剩下的长度不够解析body，返回null，等待下次解析
        if (buffer.remaining() < bodyLength) {
            return null;
        }

        byte[] body = new byte[bodyLength];
        if (bodyLength > 0) {
            buffer.get(body, 0, bodyLength);
        }
        //读取crlf
        if (buffer.remaining() >= 2) {
            buffer.get();
            buffer.get();
        } else {
            //CRLF没有传回，等待下一次解码
            return null;
        }
        return body;
    }

    /**
     * 服务器响应信息解析
     * :号开头，例如 :1\r\n
     */
    private static Object readIntegerBody(ByteBuffer buffer) throws AioDecodeException {
        return readIntCrLf(buffer);
    }

    private static final List<Object> EMPTY_LIST = new ArrayList<>(0);

    /**
     * 服务器响应信息解析
     * *号开头，例如 *3\r\n$3\r\nset\r\n$1\r\na\r\n$2\r\nb\r\n
     */
    private static Object readMulityLineBody(ByteBuffer buffer) throws AioDecodeException {
        int count = readIntCrLf(buffer);
        if (count == -1 || count == 0) {
            return EMPTY_LIST;
        }

        List<Object> results = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Object ret = processInternal(buffer, buffer.limit(), buffer.position());
            results.add(ret);
        }
        return results;
    }

    private static Object processInternal(ByteBuffer buffer, int limit, int position) throws AioDecodeException {
        byte first = buffer.get();
        switch (first) {
            case Protocol.PLUS_BYTE:
                return readSingleLineBody(buffer, limit, position);
            case Protocol.DOLLAR_BYTE:
                return readFixedLengthBody(buffer);
            case Protocol.MINUS_BYTE:
                break;
            case Protocol.ASTERISK_BYTE:
                return readMulityLineBody(buffer);
            case Protocol.COLON_BYTE:
                return readIntegerBody(buffer);
        }
        //其他情况直接return null，需要确保每种情况解析正确
        return null;
    }

    public static TedisPacket decode(ByteBuffer buffer, int limit, int position, String clientName) throws AioDecodeException {
        byte first = buffer.get();
        switch (first) {
            //+OK
            case Protocol.PLUS_BYTE:
                return buildPacket((byte[]) readSingleLineBody(buffer, limit, position), clientName);
            case Protocol.DOLLAR_BYTE:
                return buildPacket((byte[]) readFixedLengthBody(buffer), clientName);
            case Protocol.MINUS_BYTE:
                return errorPacket((byte[]) readSingleLineBody(buffer, limit, position), clientName);
            case Protocol.ASTERISK_BYTE:
                return buildPacket((List<Object>) readMulityLineBody(buffer), clientName);
            case Protocol.COLON_BYTE:
                return buildPacket(readLongCrLf(buffer), clientName);
        }
        //其他情况直接return null，需要确保每种情况解析正确
        return null;
    }

    private static TedisPacket errorPacket(byte[] error, String clientName) {
        return ClientFactory.getErrorPacket(clientName, error);
    }

    private static TedisPacket buildPacket(byte[] body, String clientName) {
        return ClientFactory.getBytesPacket(clientName, body);
    }

    private static TedisPacket buildPacket(long value, String clientName) {
        return ClientFactory.getLongPacket(clientName, value);
    }

    private static TedisPacket buildPacket(List<Object> reply, String clientName) {
        return ClientFactory.getListPacket(clientName, reply);
    }
}
