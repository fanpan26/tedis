package redis.clients.tedis;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/1/20 15:31
 * @project tedis
 */
public interface SortedSetCommands {
    int zadd(String key,float score,String member);
    int zadd(String key, List<String> scores, List<String> members);
    long zcount(String key, float min, float max);
    long zcount(String key, int min, int max);
    long zcount(String key, long min, long max);
    long zcount(String key, double min, double max);
    long zcard(String key);
    long zincrby(String key, long increment, String member);
    int zincrby(String key,int increment,String member);
    double zincrby(String key,double increment,String member);
    float zincrby(String key,float increment,String member);
    long zlexcount(String key,long min,long max);
    int zlexcount(String key,int min,int max);
}
