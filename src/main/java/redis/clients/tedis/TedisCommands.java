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

    boolean exists(final String key);

    boolean del(final String key);

    void quit();

    void flush();
    /**
     * 通过lua实现分布式锁
     * */
    TedisLock getLock(final String lockKey);

    String selectDb(int db);

    boolean expire(final String key,long seconds);
    boolean pexpire(final String key,long milliseconds);
    boolean expireAt(final String key,long timestamp);
    boolean pexpireAt(final String key,long timestamp);
}
