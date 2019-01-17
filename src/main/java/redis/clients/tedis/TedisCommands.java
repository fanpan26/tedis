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
    String blpop(long timeout, String... keys);
    String brpop(long timeout,String... keys);
    String brpoplpush(String source,String destination,long timeout);
    String lindex(String key,int index);
    int linsertBeofore(String key,String pivot,String value);
    int linsertAfter(String key,String pivot,String value);
    int linsert(String key,boolean before,String pivot,String value);
    long llen(String key);
    String lpop(String key);
    int lpush(String key,String... values);
    int lpushx(String key,String value);
    List<String> lrange(String key,long start,long end);
    int lrem(String key,int count,String value);
    String lset(String key,int index,String value);
    String ltrim(String key,long start,long end);
    String rpop(String key);
    String rpoplpush(String source,String destination);
    int rpush(String key,String... values);
    int rpushx(String key,String value);

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
    String flush();
    String selectDb(int db);
    boolean expire(final String key,long seconds);
    boolean pexpire(final String key,long milliseconds);
    boolean expireAt(final String key,long timestamp);
    boolean pexpireAt(final String key,long timestamp);
}
