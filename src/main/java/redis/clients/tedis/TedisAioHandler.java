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
        byte first = buffer.get();
        switch (first) {
            case Protocol.PLUS_BYTE:
                return readSingleLinePacket(buffer, limit, position);
            case Protocol.DOLLAR_BYTE:
                return readFixedLengthPacket(buffer, limit, position);
            case Protocol.MINUS_BYTE:
                break;
            case Protocol.ASTERISK_BYTE:
                //return buildAsteriskPacket(buffer, limit, position);
                break;
            case Protocol.COLON_BYTE:
                break;
        }
        //其他情况直接return null，需要确保每种情况解析正确
        return null;
    }

    private TedisPacket readSingleLinePacket(ByteBuffer buffer,int limit,int position) throws AioDecodeException {
        byte[] body = new byte[limit - position];
        int i = 0;
        //结束标志
        boolean endFlag = false;

        while (buffer.position() <= limit) {
            byte b = buffer.get();
            //如果是\r
            if (BufferReader.isCr(b)) {
                byte c = buffer.get();
                //如果不是\n抛出异常
                if (!BufferReader.isLf(c)) {
                    throw new AioDecodeException("unexpected redis server response");
                }
                //结束解析
                endFlag = true;
                break;
            } else {
                body[i++] = b;
            }
        }
        //如果此次解析一直没有遇到\r\n，则返回null，等待下次解析
        if (!endFlag) {
            return null;
        }
        TedisPacket packet = new TedisPacket();
        packet.setBody(body);
        return packet;
    }
    private TedisPacket readFixedLengthPacket(ByteBuffer buffer,int limit,int position) throws AioDecodeException{
        int bodyLength = BufferReader.readIntCrLf(buffer,limit,position);
        if(bodyLength == -1){
            return TedisPacket.Empty();
        }

        //剩下的长度不够解析body，返回null，等待下次解析
        if(buffer.remaining()<bodyLength){
            return null;
        }

        byte[] body = new byte[bodyLength];
        buffer.get(body,0,bodyLength);

        //读取crlf
        if(buffer.remaining()>=2){
            buffer.get();
            buffer.get();
        }else{
            //CRLF没有传回，等待下一次解码
            return null;
        }
        TedisPacket packet = new TedisPacket();
        packet.setBody(body);
        return packet;
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
            QueueFactory.get(clientName).put(responsePacket);
        }
    }
}
