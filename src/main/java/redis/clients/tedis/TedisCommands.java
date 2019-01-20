package redis.clients.tedis;


public interface TedisCommands extends StringCommands,SetCommands,HashCommands,ListCommands,PubSubCommands{
    boolean exists(final String key);
    boolean del(final String key);
    String ping();
    void quit();
    String flush();
    String selectDb(int db);
    boolean expire(final String key,long seconds);
    boolean pexpire(final String key,long milliseconds);
    boolean expireAt(final String key,long timestamp);
    boolean pexpireAt(final String key,long timestamp);
}
