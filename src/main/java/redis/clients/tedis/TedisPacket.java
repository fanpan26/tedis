package redis.clients.tedis;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.tio.core.intf.Packet;

import java.util.List;

public class TedisPacket extends Packet {

    public boolean isSubscribeBody() {
        return objects != null && !objects.isEmpty();
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    private List<Object> objects;

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

    public TedisPacket(){}
    public TedisPacket(byte[] body){
        setBody(body);
    }
}
