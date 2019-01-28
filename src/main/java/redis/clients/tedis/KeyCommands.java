package redis.clients.tedis;

import java.util.List;

public interface KeyCommands {
    boolean expire(final String key,long seconds);
    boolean pexpire(final String key,long milliseconds);
    boolean expireAt(final String key,long timestamp);
    boolean pexpireAt(final String key,long timestamp);
    boolean exists(final String key);
    boolean del(final String key);
    String dump(String key);
    List<String> keys(String pattern);
    boolean move(String key,int db);
    boolean persist(String key);
    long pttl(String key);
    long ttl(String key);
    String randomkey();
    String rename(String key,String newKey);
    String renamenx(String key,String newKey);
    String type(String key);
}
