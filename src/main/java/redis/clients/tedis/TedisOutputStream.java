package redis.clients.tedis;

import org.tio.core.Tio;


public final class TedisOutputStream {
    protected final byte[] buf;

    protected int count;

    private final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999,
            999999999, Integer.MAX_VALUE };

    private final static byte[] DigitTens = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4',
            '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6',
            '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8',
            '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9', };

    private final static byte[] DigitOnes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', };

    private final static byte[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z' };

    public TedisOutputStream() {
        this(8192);
    }

    public TedisOutputStream(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        buf = new byte[size];
    }

    public byte[] getByte(){
        if (count > 0) {
            byte[] sendBytes =new byte[count];
            System.arraycopy(buf,0,sendBytes,0,count);
            count = 0;
            return sendBytes;
        }
        return null;
    }
    private void flushBuffer() {
        if (count > 0) {
            byte[] sendBytes =new byte[count];
            System.arraycopy(buf,0,sendBytes,0,count);
            count = 0;
        }
    }


    private void send(byte[] body){
        System.out.println(SafeEncoder.encode(body));
    }

    public void write(final byte b) {
        if (count == buf.length) {
            flushBuffer();
        }
        buf[count++] = b;
    }

    public void write(final byte[] b) {
        write(b, 0, b.length);
    }

    public void write(final byte[] b, final int off, final int len) {
        if (len >= buf.length) {
            flushBuffer();
            write(b, off, len);
        } else {
            if (len >= buf.length - count) {
                flushBuffer();
            }
            System.arraycopy(b, off, buf, count, len);
            count += len;
        }
    }

    public void writeCrLf() {
        if (2 >= buf.length - count) {
            flushBuffer();
        }

        buf[count++] = '\r';
        buf[count++] = '\n';
    }

    public void writeIntCrLf(int value) {
        if (value < 0) {
            write((byte) '-');
            value = -value;
        }

        int size = 0;
        while (value > sizeTable[size])
            size++;

        size++;
        if (size >= buf.length - count) {
            flushBuffer();
        }

        int q, r;
        int charPos = count + size;

        while (value >= 65536) {
            q = value / 100;
            r = value - ((q << 6) + (q << 5) + (q << 2));
            value = q;
            buf[--charPos] = DigitOnes[r];
            buf[--charPos] = DigitTens[r];
        }

        for (;;) {
            q = (value * 52429) >>> (16 + 3);
            r = value - ((q << 3) + (q << 1));
            buf[--charPos] = digits[r];
            value = q;
            if (value == 0) break;
        }
        count += size;

        writeCrLf();
    }
}