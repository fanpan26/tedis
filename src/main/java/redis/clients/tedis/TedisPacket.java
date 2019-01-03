package redis.clients.tedis;

import org.tio.core.intf.Packet;

public class TedisPacket extends Packet {
    private byte[] body;

    /**
     * @return the body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean hasBody() {
        return body != null;
    }

    private static final TedisPacket emptyPacket = new TedisPacket();

    public static TedisPacket Empty() {
        return emptyPacket;
    }
}
