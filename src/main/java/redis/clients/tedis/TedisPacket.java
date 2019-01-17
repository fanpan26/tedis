package redis.clients.tedis;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.tio.core.intf.Packet;

import java.util.List;

public class TedisPacket extends Packet {

    public boolean hasListValue() {
        return objects != null;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    private List<Object> objects;

    private byte[] body;

    private boolean longValue = false;

    public boolean hasLongValue() {
        return longValue;
    }

    private long value;

    public void setLongValue(long value) {
        this.value = value;
        longValue = true;
    }

    public long getLongValue() {
        return value;
    }

    private boolean err;
    public void setErr(){
        this.err = true;
    }

    public boolean hasErr(){
        return this.err;
    }
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

    public TedisPacket() {
    }

    public TedisPacket(byte[] body) {
        setBody(body);
    }
}
