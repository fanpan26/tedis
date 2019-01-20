package redis.clients.tedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author fyp
 * @crate 2019/1/20 10:11
 * @project tedis
 */
public interface StringCommands {
    long incr(final String key);
    long incrBy(final String key, long value);
    long decr(final String key);
    long decrBy(final String key, long value);
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
}
