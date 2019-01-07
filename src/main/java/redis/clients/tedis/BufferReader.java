package redis.clients.tedis;

import org.tio.core.exception.AioDecodeException;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static redis.clients.tedis.Command.*;

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

    /**
     * 服务器响应信息解析
     * $号开头，例如 $5\r\nredis\r\n
     */
    private static Object readFixedLengthBody(ByteBuffer buffer) throws AioDecodeException {
        int bodyLength = readIntCrLf(buffer);
        if (bodyLength == -1) {
            return TedisPacket.Empty();
        }

        //剩下的长度不够解析body，返回null，等待下次解析
        if (buffer.remaining() < bodyLength) {
            return null;
        }

        byte[] body = new byte[bodyLength];
        buffer.get(body, 0, bodyLength);

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
    /**
     * 服务器响应信息解析
     * *号开头，例如 *3\r\n$3\r\nset\r\n$1\r\na\r\n$2\r\nb\r\n
     */
    private static Object readMulityLineBody(ByteBuffer buffer) throws  AioDecodeException{
        int count = readIntCrLf(buffer);
        if(count == -1){
            return null;
        }
        List<Object> results =new ArrayList<>(count);
        for (int i=0;i<count;i++){
            Object ret = processInternal(buffer,buffer.limit(),buffer.position());
            if(ret == null){
                return null;
            }
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

    public static TedisPacket decode(ByteBuffer buffer, int limit, int position) throws AioDecodeException {
        byte first = buffer.get();
        switch (first) {
            case Protocol.PLUS_BYTE:
                return buildPacket((byte[]) readSingleLineBody(buffer, limit, position));
            case Protocol.DOLLAR_BYTE:
                return buildPacket((byte[]) readFixedLengthBody(buffer));
            case Protocol.MINUS_BYTE:
                break;
            case Protocol.ASTERISK_BYTE:
                return buildPacket((List<Object>) readMulityLineBody(buffer));
            case Protocol.COLON_BYTE:
                return buildPacket(readLongCrLf(buffer));
        }
        //其他情况直接return null，需要确保每种情况解析正确
        return null;
    }

    private static TedisPacket buildPacket(byte[] body) {
        if(body == null){
            return null;
        }
        return new TedisPacket(body);
    }

    private static TedisPacket buildPacket(long value) {
        TedisPacket packet = new TedisPacket();
        packet.setLongValue(value);
        return packet;
    }

    private static TedisPacket buildPacket(List<Object> reply) {
        if(reply == null || reply.isEmpty()){
            return null;
        }
        final Object firstObj = reply.get(0);

        final byte[] resp = (byte[]) firstObj;

        if (Arrays.equals(SUBSCRIBE.raw, resp)
                || Arrays.equals(UNSUBSCRIBE.raw, resp)
                || Arrays.equals(MESSAGE.raw, resp)
                || Arrays.equals(PMESSAGE.raw, resp)
                || Arrays.equals(PSUBSCRIBE.raw, resp)
                || Arrays.equals(PUNSUBSCRIBE.raw, resp)
                || Arrays.equals(PONG.raw, resp)) {

            TedisPacket subscribePacket = new TedisPacket();
            subscribePacket.setObjects(reply);
            return subscribePacket;
        }else {
            //待续
            return new TedisPacket();
        }
    }

    public static byte[] int2Bytes(int value) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (value >> 24);
        bytes[2] = (byte) (value >> 16);
        bytes[1] = (byte) (value >> 8);
        bytes[0] = (byte) value;
        return bytes;
    }

    public static int bytes2Int(byte[] bytes ) {
        int int1 = bytes[0] & 0xff;
        int int2 = (bytes[1] & 0xff) << 8;
        int int3 = (bytes[2] & 0xff) << 16;
        int int4 = (bytes[3] & 0xff) << 24;
        return int1 | int2 | int3 | int4;
    }
}
