package redis.clients.tedis;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/1/20 15:31
 * @project tedis
 */
public interface SortedSetCommands {
    int zadd(String key, float score, String member);
    int zadd(String key, List<String> scores, List<String> members);
    long zcount(String key, float min, float max);
    long zcount(String key, int min, int max);
    long zcount(String key, long min, long max);
    long zcount(String key, double min, double max);
    long zcard(String key);
    long zincrby(String key, long increment, String member);
    int zincrby(String key, int increment, String member);
    double zincrby(String key, double increment, String member);
    float zincrby(String key, float increment, String member);
    long zlexcount(String key, String min, String max);
    List<String> zrange(String key, long start, long end, boolean containsScore);
    List<String> zrange(String key, int start, int end, boolean containsScore);
    List<String> zrange(String key, long start, boolean containsScore);
    List<String> zrange(String key, int start, boolean containsScore);
    List<String> zrangebylex(String key,String min,String max,int limit);
    List<String> zrangebylex(String key,String min,String max);
    List<String> zrangebylex(String key,String min,String max,int limit,boolean containsMin,boolean containsMax);
    List<String> zrangebylex(String key,String min,String max,boolean containsMin,boolean containsMax);
    List<String> zrangebylex(String key,String max,boolean containsMax);
    List<String> zrangebylex(String key,String max);
    long zrank(String key, String member);
    long zrevrank(String key, String member);
    List<String> zrangebyscore(String key);
    List<String> zrevrangebyscore(String key);
    List<String> zrangebyscore(String key, int min, int max, boolean withScore, int limit);
    List<String> zrangebyscore(String key, int min, int max);
    List<String> zrangebyscore(String key, float min, float max, boolean withScore, int limit);
    List<String> zrangebyscore(String key, float min, float max);
    List<String> zrangebyscore(String key, long min, long max, boolean withScore, int limit);
    List<String> zrangebyscore(String key, long min, long max);
    List<String> zrangebyscore(String key, double min, double max, boolean withScore, int limit);
    List<String> zrangebyscore(String key, double min, double max);
    List<String> zrevrangebyscore(String key, int min, int max, boolean withScore, int limit);
    List<String> zrevrangebyscore(String key, int min, int max);
    List<String> zrevrangebyscore(String key, float min, float max, boolean withScore, int limit);
    List<String> zrevrangebyscore(String key, float min, float max);
    List<String> zrevrangebyscore(String key, long min, long max, boolean withScore, int limit);
    List<String> zrevrangebyscore(String key, long min, long max);
    List<String> zrevrangebyscore(String key, double min, double max, boolean withScore, int limit);
    List<String> zrevrangebyscore(String key, double min, double max);
    int zremrangebyscore(String key,int min,int max);
    int zremrangebyscore(String key,float min,float max);
    int zremrangebyscore(String key,double min,double max);
    int zremrangebyscore(String key,long min,long max);
    int zremrangebyrank(String key,long start,long stop);
    int zremrangebyrank(String key,int start,int stop);
    int zremrangebyrank(String key,int start);
    int zremrangebyrank(String key,long start);
    int zrem(String key, String... members);
    float zscore(String key, String member);
}
