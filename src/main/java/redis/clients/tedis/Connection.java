package redis.clients.tedis;

import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.utils.SystemTimer;

import java.io.Closeable;
import java.io.IOException;
/**
 * 核心业务，包含TioClient的连接，命令的发送
 * */
public class Connection implements Closeable {

    private static int CLIENT_INDEX = 0;

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
        clientName ="tio-redis-client-"+(CLIENT_INDEX++);
        handler = new TedisAioHandler(clientName);
        clientGroupContext = new ClientGroupContext(handler, listener, reconnConf);
        clientGroupContext.setName(clientName);
    }

    public void connect() throws Exception {
        if (!isConnected()) {
            clientGroupContext.setHeartbeatTimeout(0);
            tioClient = new TioClient(clientGroupContext);
            clientChannelContext = tioClient.connect(serverNode);
            isConnected = true;
        }
    }

    public void sendCommand(final ProtocolCommand cmd, final String... args) {
        byte[] body = Protocol.buildCommandBody(cmd, args);
        send(body);
    }

    public String getStatusCodeReply(){
        return getReponse();
    }

    public String getBulkReply(){
        return getReponse();
    }

    private String getReponse() {
        for (; ; ) {
            try {
                TedisPacket packet = QueueFactory.get(clientName).take();
                return packet.hasBody() ? SafeEncoder.encode(packet.getBody()) : null;
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
        if (isConnected()){

        }
    }
}
