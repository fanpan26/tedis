package redis.clients.tedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

public class TedisAioHandler implements ClientAioHandler {

    private static final Logger logger = LoggerFactory.getLogger(TedisAioHandler.class);

    private String clientName;

    public TedisAioHandler(final String clientName) {
        this.clientName = clientName;
        QueueFactory.put(this.clientName);
    }

    /**
     * 创建心跳包
     *
     * @return
     * @author tanyaowu
     */
    @Override
    public Packet heartbeatPacket() {
        return null;
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
        //debug(buffer,position,readableLength);
        return BufferReader.decode(buffer, limit, position);
    }

    private void debug(ByteBuffer buffer,int position,int readableLength) {
        byte[] body = new byte[readableLength];
        buffer.get(body);
        System.out.println(new String(body));

        buffer.position(position);
    }

    /**
     * 编码
     *
     * @param packet
     * @param groupContext
     * @param channelContext
     * @return
     * @author: tanyaowu
     */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
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
            if(responsePacket.isSubscribeBody()){
                QueueFactory.getSubscribe(clientName).put(responsePacket);
            }else {
                QueueFactory.get(clientName).put(responsePacket);
            }
        }
    }
}
