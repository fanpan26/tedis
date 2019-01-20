package redis.clients.tedis;

import java.util.List;
import java.util.Map;

/**
 * @author fyp
 * @crate 2019/1/20 10:13
 * @project tedis
 */
public interface HashCommands {
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
}
