package redis.clients.tedis;

import javafx.concurrent.Task;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static redis.clients.tedis.Command.*;

public abstract class TedisPubSub {

    /**
     * 接收到消息回调
     * */
    public abstract void onMessage(final String channel,final String message);

    /**
     * 订阅频道成功之后
     * */
    public abstract void onSubscribe(final String channel,int result);

    //订阅的 Channel 个数
    private int subscribedChannels = 0;

    private Client client;

    public void proceed(Client client, final String... channels) {
        this.client = client;
        if(channels == null || channels.length == 0){
            throw new NullPointerException("channels");
        }
        QueueFactory.putSubscribe(client.getClientName());
        client.subscribe(channels);
        process();
    }

    private void process() {
        do {
            List<Object> reply = client.getSubscribeReply();
            if (reply != null && !reply.isEmpty()) {
                final Object firstObj = reply.get(0);
                final byte[] resp = (byte[]) firstObj;
                if (Arrays.equals(SUBSCRIBE.raw, resp)) {
                    subscribedChannels = ((Integer) reply.get(2)).intValue();
                    final byte[] channelBytes = (byte[]) reply.get(1);
                    final String channel = (channelBytes == null) ? null : SafeEncoder.encode(channelBytes);
                    onSubscribe(channel, subscribedChannels);
                } else if (Arrays.equals(UNSUBSCRIBE.raw, resp)) {

                } else if (Arrays.equals(MESSAGE.raw, resp)) {
                    final byte[] channelBytes = (byte[]) reply.get(1);
                    final byte[] messageBytes = (byte[]) reply.get(2);
                    final String channel = (channelBytes == null) ? null : SafeEncoder.encode(channelBytes);
                    final String message = (messageBytes == null) ? null : SafeEncoder.encode(messageBytes);
                    onMessage(channel, message);
                } else if (Arrays.equals(PMESSAGE.raw, resp)) {

                } else if (Arrays.equals(PSUBSCRIBE.raw, resp)) {

                } else if (Arrays.equals(PUNSUBSCRIBE.raw, resp)) {

                } else if (Arrays.equals(PONG.raw, resp)) {

                }
            }
        } while (isSubscribed());
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isSubscribed() {
        return subscribedChannels > 0;
    }
}
