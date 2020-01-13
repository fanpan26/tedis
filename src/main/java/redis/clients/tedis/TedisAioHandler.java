package redis.clients.tedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

public class TedisAioHandler implements ClientAioHandler {

    private static final Logger logger = LoggerFactory.getLogger(TedisAioHandler.class);

    private String clientName;

    public TedisAioHandler(final String clientName) {
        this.clientName = clientName;
        ClientFactory.put(this.clientName);
    }


    /**
     * 根据ByteBuffer解码成业务需要的Packet对象.
     * 如果收到的数据不全，导致解码失败，请返回null，在下次消息来时框架层会自动续上前面的收到的数据
     *
     * @param buffer         参与本次希望解码的ByteBuffer
     * @param limit          ByteBuffer的limit
     * @param position       ByteBuffer的position，不一定是0哦
     * @param readableLength ByteBuffer参与本次解码的有效数据（= limit - position）
     * @param channelContext
     * @return
     * @throws AioDecodeException
     */
    @Override
    public Packet decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {
        debug(buffer,position,readableLength);
        return BufferReader.decode(buffer, limit, position,clientName);
    }

    private void debug(ByteBuffer buffer, int position, int readableLength) {
        byte[] body = new byte[readableLength - 2];
        buffer.get(body);
        buffer.position(position);
        System.out.println(new String(body));
    }

    /**
     * 编码
     *
     * @param packet
     * @param tioConfig
     * @param channelContext
     * @return
     * @author: tanyaowu
     */
    @Override
    public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext) {
        TedisPacket tedisPacket = (TedisPacket) packet;
        byte[] body = tedisPacket.getBody();
        int bodyLen = 0;
        if (body != null) {
            bodyLen = body.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bodyLen);
        buffer.put(body);
        return buffer;
    }

    /**
     * 处理消息包
     *
     * @param packet
     * @param channelContext
     * @throws Exception
     * @author: tanyaowu
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        TedisPacket responsePacket = (TedisPacket) packet;
        if (responsePacket != null) {
            ClientFactory.get(clientName).put(responsePacket);
        }
    }

    /**
     * 创建心跳包
     *
     * @param channelContext
     * @return
     * @author tanyaowu
     */
    @Override
    public Packet heartbeatPacket(ChannelContext channelContext) {
        return null;
    }
}
