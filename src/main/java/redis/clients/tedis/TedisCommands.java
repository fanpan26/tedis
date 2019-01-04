package redis.clients.tedis;

public interface TedisCommands {
    /**
     * 设置缓存
     * */
    String set(String key,String value);
    /**
     * 获取缓存
     * */
    String get(String key);

    /**
     * ping
     * */
    String ping();

    /**
     * 发布
     * */
    int publish(final String channel,final String message);
    /**
     * 订阅
     * */
    void subscribe(TedisPubSub pubSub, final String... channels);
}
