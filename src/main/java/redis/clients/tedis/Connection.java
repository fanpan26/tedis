package redis.clients.tedis;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 核心业务，包含TioClient的连接，命令的发送
 * */
public class Connection implements Closeable {

    private static int CLIENT_INDEX = 0;

    public String getClientName() {
        return clientName;
    }

    public String getSubscribeId() {
        return getClientName() + "_subscribe";
    }

    private String clientName;
    private String host;
    private int port;
    private boolean isConnected = false;
    private Node serverNode;
    //handler, 包括编码、解码、消息处理
    private ClientAioHandler handler;
    private ClientGroupContext clientGroupContext;
    //事件监听器
    private ClientAioListener listener = new TedisAioListener() ;
    //断链后自动连接的，不想自动连接请设为null
    private ReconnConf reconnConf = new ReconnConf(5000L);

    private TioClient tioClient = null;
    private ClientChannelContext clientChannelContext = null;

    public Connection(){
        this(Protocol.DEFAULT_HOST,Protocol.DEFAULT_PORT);
    }

    public Connection(final String host,final int port) {
        this.host = host;
        this.port = port;
        serverNode = new Node(this.host, this.port);
        clientName = buildClientName();
        handler = new TedisAioHandler(clientName);
        clientGroupContext = new ClientGroupContext(handler, listener, reconnConf);
        clientGroupContext.setName(clientName);
    }

    private String buildClientName() {
        return Protocol.CLIENT_NAME_PREFIX + (CLIENT_INDEX++);
    }

    public void connect() throws Exception {
        if (!isConnected()) {
            clientGroupContext.setHeartbeatTimeout(0);
            tioClient = new TioClient(clientGroupContext);
            clientChannelContext = tioClient.connect(serverNode);
            isConnected = true;
        }
    }

    public void sendCommand(final ProtocolCommand cmd,int arg) {
        sendCommand(cmd, String.valueOf(arg));
    }

    public void sendCommand(final ProtocolCommand cmd,String key,long arg) {
        sendCommand(cmd,key,String.valueOf(arg));
    }

    public void sendCommand(final ProtocolCommand cmd,String key,long arg1,long arg2) {
        sendCommand(cmd,key,String.valueOf(arg1),String.valueOf(arg2));
    }

    public void sendCommand(final ProtocolCommand cmd, final String... args) {
        send(Protocol.buildCommandBody(cmd, args));
        //send( Protocol.buildCommandBodyWithOutputStream(cmd,args));
    }

    public String getStatusCodeReply() {
        return getBulkReply();
    }

    public Integer getIntegerReply() {
       Long value = getLongReply();
       return value == null ? null : value.intValue();
    }

    public boolean getBooleanReply() {
        return getIntegerReply() > 0;
    }

    public Float getFloatReply(){
        String result = getBulkReply();
        if(result != null){
            return Float.valueOf(result);
        }
        return 0.0f;
    }

    public Long getLongReply() {
        TedisPacket packet = getReponse();
        if (packet == null) {
            return null;
        }
        return packet.hasLongValue() ? packet.getLongValue() : null;
    }

    public String getBulkReply() {
        TedisPacket packet = getReponse();
        if (packet == null) {
            return null;
        }
        return packet.hasBody() ? SafeEncoder.encode(packet.getBody()) : null;
    }

    public java.util.Map<String,String> getMapReply() {
        List<String> res = getListStringReply();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < res.size(); i += 2) {
            map.put(res.get(i), res.get(i + 1));
        }
        return map;
    }

    public List<String> getListStringReply(){
        List<Object> replies = getListReply();
        if(replies == null){
            return null;
        }
        List<String> strReplies = new ArrayList<>();
        for (Object obj : replies){
            if(obj instanceof byte[]){
                strReplies.add(SafeEncoder.encode((byte[])obj));
            }else {
                strReplies.add(String.valueOf(obj));
            }
        }
        return strReplies;
    }

    public List<Object> getListReply(){
        TedisPacket packet = getReponse();
        if (packet == null) {
            return null;
        }
        if (packet.hasListValue()) {
            return packet.getObjects();
        }
        return null;
    }

    public Object getEvalReply() {
        TedisPacket packet = getReponse();
        if (packet == null) {
            return null;
        }
        if (packet.hasBody()) {
            if (packet.getBody().length == 0) {
                return null;
            }
            return SafeEncoder.encode(packet.getBody());
        }
        if (packet.hasLongValue()) {
            return packet.getLongValue();
        }
        if (packet.hasListValue()) {
            return packet.getObjects();
        }
        return null;
    }

    private TedisPacket getReponse() {
        for (; ; ) {
            try {
                TedisPacket result = QueueFactory.get(clientName).poll(Protocol.DEFAULT_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS);
                if (result == null) {
                    throw new TedisException("get response time out");
                }
                if(result.hasErr()) {
                    throw new TedisException(SafeEncoder.encode(result.getBody()));
                }
                return result;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<Object> getSubscribeReply() {
        for (; ; ) {
            try {
                TedisPacket packet = QueueFactory.get(clientName).take();
                if (packet == null) {
                    return null;
                }
                return packet.getObjects();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private void send(byte[] body) {
        TedisPacket packet = new TedisPacket();
        packet.setBody(body);
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Tio.send(clientChannelContext, packet);
    }



    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        disconnect();
    }

    public boolean isConnected() {
        return tioClient != null
                && clientChannelContext != null
                &&clientChannelContext.isClosed == false
                && clientChannelContext.isRemoved == false
                && isConnected == true;
    }
    public void disconnect(){
        if (isConnected()) {
            tioClient.stop();
            clientChannelContext = null;
            isConnected = false;
            tioClient = null;
        }
    }
}
