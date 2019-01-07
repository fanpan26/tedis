package redis.clients.tedis;

public interface TedisCommands {
    /**
     * REQUEST:[SET KEY VALUE]
     * RESPONSE:[+OK]
     * */
    String set(String key, String value);
    /**
     * REQUEST:[GET KEY]
     * RESPONSE:[$5\r\nredis\r\n]
     * */
    String get(String key);
    /**
     * REQUEST:[PING]
     * RESPONSE:[+PONG]
     * */
    String ping();
    /**
     * REQUEST:[PUBLISH CHANNEL MESSAGE]
     * RESPONSE:[]
     * */
    int publish(final String channel, final String message);
    /**
     * REQUEST:[SUBSCRIBE CHANNEL]
     * RESPONSE:[:1/0]
     * */
    void subscribe(TedisPubSub pubSub, final String... channels);
    /**
     * REQUEST:[PSUBSCRIBE CHANNELPATTERN]
     * RESPONSE:[]
     * */
    void pSubscribe(TedisPubSub pubSub, final String... channelPatterns);
    /**
     * REQUEST:[INCR KEY]
     * RESPONSE:[:NUMBER]
     * */
    long incr(final String key);
    /**
     * REQUEST:[INCRBY KEY]
     * RESPONSE:[:NUMBER]
     * */
    long incrBy(final String key, long value);

    /**
     * REQUEST:[DECR KEY]
     * RESPONSE:[:NUMBER]
     * */
    long decr(final String key);

    /**
     * REQUEST:[DECRBY KEY]
     * RESPONSE:[:NUMBER]
     * */
    long decrBy(final String key, long value);

    /**
     * REQUEST:[EXISTS KEY]
     * RESPONSE:[:1/0]
     * */
    boolean exists(final String key);
    /**
     * REQUEST:[DEL KEY...]
     * RESPONSE:[:0]
     * */
    boolean del(final String key);
    /**
     * REQUEST:[QUIT]
     * RESPONSE:[+OK]
     * */
    void quit();
}
