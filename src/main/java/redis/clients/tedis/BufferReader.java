package redis.clients.tedis;

import org.tio.core.exception.AioDecodeException;

import java.nio.ByteBuffer;

public class BufferReader {

    public static boolean isCr(byte b){
        return b=='\r';
    }

    public static boolean isLf(byte b){
        return b=='\n';
    }

    public static long readLongCrLf(ByteBuffer buffer, int limit, int position) throws AioDecodeException {
        long value = 0L;
        boolean isNeg = false;
        while (position <= limit) {
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

    public static int readIntCrLf(ByteBuffer buffer, int limit, int position) throws AioDecodeException {
        return (int) readLongCrLf(buffer, limit, position);
    }
}
