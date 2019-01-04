package redis.clients.tedis;

public interface Commands {
    void set(final String key,final String value);
    void get(final String key);
    void ping();
    void publish(final String channel,final String message);
    void subscribe(final String... channels);
    void unSubscribe(final String... channels);
    void pSubscribe(final String... channelPatterns);
    void pUnSubscribe(final String... channelPatterns);
}
