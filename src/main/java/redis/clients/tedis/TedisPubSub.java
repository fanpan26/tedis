package redis.clients.tedis;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import static redis.clients.tedis.Keyword.*;


public abstract class TedisPubSub {

    public abstract void onMessage(final String channel,final String message);
    public abstract void onPMessage(final String pattern,final String channel,final String message);
    public abstract void onSubscribe(final String channel);
    public abstract void onPSubscribe(final String channelPatterns);
    public  abstract void onUnSubscribe(final String channel);

    public void unSubscribe() {
        client.unsubscribe();
    }

    public void unSubscribe(String... channels) {
        client.unsubscribe(channels);
    }

    public void pUnSubscribe(){
        client.punsubscribe();
    }

    public void pUnSubscribe(String... patterns){
        client.punsubscribe(patterns);
    }

    //订阅的 Channel 个数
    private int subscribedChannels = 0;

    private Client client;

    public void proceed(Client client, final String... channels) {
        prepareSubscribe(client, channels);
        client.subscribe(channels);
        process();
    }
    public void proceedPatterns(Client client, final String... channels) {
        prepareSubscribe(client, channels);
        client.psubscribe(channels);
        process();
    }

    private void  prepareSubscribe(Client client, final String... channels){
        this.client = client;
        if(channels == null || channels.length == 0){
            throw new NullPointerException("channels");
        }
        ClientFactory.put(client.getSubscribeId());
    }

    private void  resetSubscribedChannels(List<Object> reply) {
        subscribedChannels = ((Integer) reply.get(2)).intValue();
    }

    private void process() {
        Executors.newCachedThreadPool().execute(() -> {
            do {
                List<Object> reply = client.getSubscribeReply();
                if (reply != null && !reply.isEmpty()) {
                    final Object firstObj = reply.get(0);
                    final byte[] resp = (byte[]) firstObj;
                    boolean handled = handleSubscribe(resp, reply);

                    if (handled) {
                        continue;
                    }

                    handled = handleMessage(resp, reply);
                    if (handled) {
                        continue;
                    }
                    handleUnSubscribe(resp, reply);
                }
            } while (isSubscribed());
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private boolean handleSubscribe(byte[] resp,List<Object> reply){
        //是否普通订阅
        boolean isSubscribe = Arrays.equals(SUBSCRIBE.raw, resp);
        //是否模式匹配订阅
        boolean isPSubscribe = Arrays.equals(Keyword.PSUBSCRIBE.raw, resp);

        if (isSubscribe || isPSubscribe) {
            resetSubscribedChannels(reply);
            //第二个值为 channel 名称
            final byte[] channelBytes = (byte[]) reply.get(1);
            //转化为 string
            final String channel = getString(channelBytes);

            //调用事件 (onSubscribe,onPSubscribe 子类可以重写)
            if (isSubscribe) {
                onSubscribe(channel);
            } else {
                onPSubscribe(channel);
            }
            return true;
        }
        return false;
    }

    private boolean handleUnSubscribe(byte[] resp,List<Object> reply){
       if( Arrays.equals(UNSUBSCRIBE.raw, resp)||Arrays.equals(PUNSUBSCRIBE.raw, resp)){
           resetSubscribedChannels(reply);
           final byte[] channelBytes = (byte[]) reply.get(1);
           final String channel = getString(channelBytes);
           onUnSubscribe(channel);
           return true;
       }
       return false;
    }

    private boolean handleMessage(byte[] resp, List<Object> reply) {
        boolean isMessage = Arrays.equals(MESSAGE.raw, resp);
        boolean isPMessage = Arrays.equals(PMESSAGE.raw, resp);
        if (isMessage || isPMessage) {
            final byte[] secondBytes = (byte[]) reply.get(1);
            final byte[] thirdBytes = (byte[]) reply.get(2);
            final String second = getString(secondBytes);
            final String third = getString(thirdBytes);
            if (isMessage) {
                onMessage(second, third);
            } else {
                final byte[] messageBytes = (byte[]) reply.get(3);
                final String message = getString(messageBytes);
                onPMessage(second, third, message);
            }
            return true;
        }
        return false;
    }

    private String getString(byte[] bytes) {
        return bytes == null ? null : SafeEncoder.encode(bytes);
    }

    public boolean isSubscribed() {
        return subscribedChannels > 0;
    }
}
