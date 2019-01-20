package redis.clients.tedis;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/1/20 10:13
 * @project tedis
 */
public interface ListCommands {
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
    List<String> lrange(String key, long start, long end);
    int lrem(String key,int count,String value);
    String lset(String key,int index,String value);
    String ltrim(String key,long start,long end);
    String rpop(String key);
    String rpoplpush(String source,String destination);
    int rpush(String key,String... values);
    int rpushx(String key,String value);
}
