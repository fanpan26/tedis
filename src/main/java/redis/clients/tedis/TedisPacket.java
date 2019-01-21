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

    public TedisPacket setObjects(List<Object> objects) {
        this.objects = objects;
        return this;
    }

    private List<Object> objects;

    private byte[] body;

    private boolean longValue = false;

    public boolean hasLongValue() {
        return longValue;
    }

    private long value;

    public TedisPacket setLongValue(long value) {
        this.value = value;
        longValue = true;
        return this;
    }

    public long getLongValue() {
        return value;
    }

    private boolean err;
    public TedisPacket setErr(){
        this.err = true;
        return this;
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
    public TedisPacket setBody(byte[] body) {
        this.body = body;
        return this;
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
