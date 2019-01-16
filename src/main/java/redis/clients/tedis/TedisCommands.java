package redis.clients.tedis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface TedisCommands {

    String set(String key, String value);
    String get(String key);
    String get(String key,int start,int end);
    String getset(String key,String value);
    int getbit(String key,int offset);
    int setbit(String key,int offset,boolean value);
    List<String> get(String...keys);
    String setex(String key, String value, long time, TimeUnit unit);
    boolean setnx(String key,String value);
    int set(String key,String value,long offset);
    String set(List<String> keys,List<String> values);
    int msetnx(List<String> keys,List<String> values);
    int len(String key);
    float incrByfloat(String key,float value);
    int append(String key,String value);
    int hset(String key,String field,String value);
    boolean hexists(String key,String field);
    String hget(String key,String field);
    boolean hdel(String key,String... fields);
    Map<String,String> hget(String key);
    long hincrBy(String key,String field,long value);
    long hincrBy(String key,String field);
    float hincrByFloat(String key,String field,float value);
    float hincrByFloat(String key,String field);
    List<String> hkeys(String key);
    int hlen(String key);
    List<String> hget(String key,String...fields);
    String hset(String key,List<String> fields,List<String> values);
    boolean hsetnx(String key,String field,String value);
    List<String> hvals(String key);

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
    String selectDb(int db);
    boolean expire(final String key,long seconds);
    boolean pexpire(final String key,long milliseconds);
    boolean expireAt(final String key,long timestamp);
    boolean pexpireAt(final String key,long timestamp);
}
