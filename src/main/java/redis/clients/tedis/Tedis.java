package redis.clients.tedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tedis  implements TedisCommands {

    private static Logger logger = LoggerFactory.getLogger(Tedis.class);

    private Client client;

    public Tedis() {
        client = new Client();
    }

    public Tedis(final String host, final int port) {
        client = new Client(host, port);
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    @Override
    public String set(String key, String value) {
        client.set(key, value);
        return client.getStatusCodeReply();
    }

    /**
     * 获取缓存
     *
     * @param key
     */
    @Override
    public String get(String key) {
        client.get(key);
        return client.getBulkReply();
    }

    @Override
    public String ping() {
        client.ping();
        return client.getStatusCodeReply();
    }

    /**
     * 发布
     *
     * @param channel
     * @param message
     */
    @Override
    public int publish(String channel, String message) {
        client.publish(channel, message);
        return client.getIntegerReply();
    }

    /**
     * 订阅
     *
     * @param channels
     */
    @Override
    public void subscribe(TedisPubSub pubSub, final String... channels) {
        pubSub.proceed(client, channels);
    }

    /**
     * 模式订阅
     *
     * @param pubSub
     * @param channelPatterns
     */
    @Override
    public void pSubscribe(TedisPubSub pubSub, String... channelPatterns) {
        pubSub.proceedPatterns(client, channelPatterns);
    }
}
