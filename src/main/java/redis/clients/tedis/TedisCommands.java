package redis.clients.tedis;

public interface TedisCommands {
    String set(String key, String value);

    String get(String key);

    String ping();

    int publish(final String channel, final String message);

    void subscribe(TedisPubSub pubSub, final String... channels);

    void pSubscribe(TedisPubSub pubSub, final String... channelPatterns);

    long incr(final String key);

    long incrBy(final String key, long value);

    long decr(final String key);

    long decrBy(final String key, long value);
}
