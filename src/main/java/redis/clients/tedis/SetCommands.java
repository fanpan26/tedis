package redis.clients.tedis;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/1/20 8:44
 * @project tedis
 */
public interface SetCommands {
    long sadd(final String key,String... members);
    long scard(final String key);
    List<String> sdiff(final String key1, final String key2);
    long sdiffstore(final String destination,final String key1,final String key2);
    List<String> sinter(final String key1,final String key2);
    long sinterstore(final String destination,final String key1,final String key2);
    boolean sismember(final String key,final String member);
    List<String> smembers(final String key);
    int smove(final String source,final String destination,final String member);
    String spop(final String key);
    List<String> srandmember(final String key,int count);
    int srem(final String key,String... members);
    List<String> sunion(final String key1,final String key2);
    long sunionstore(final String destination,final String key1,final String key2);
    ScanResult sscan(final String key,long cursor,final String match,int count);
}
