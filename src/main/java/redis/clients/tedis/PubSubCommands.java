package redis.clients.tedis;

/**
 * @author fyp
 * @crate 2019/1/20 10:14
 * @project tedis
 */
public interface PubSubCommands {
    int publish(final String channel, final String message);
    void subscribe(TedisPubSub pubSub, final String... channels);
    void pSubscribe(TedisPubSub pubSub, final String... channelPatterns);
}
