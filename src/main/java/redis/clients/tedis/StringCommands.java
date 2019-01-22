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
    long incrby(final String key, long value);
    int incrby(final String key,int value);
    float incrby(final String key, float value);
    double incrby(final String key, double value);
    long decr(final String key);
    long decrby(final String key, long value);
    int decrby(final String key,int value);
    float decrby(final String key, float value);
    double decrby(final String key, double value);
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
    int append(String key,String value);
}
