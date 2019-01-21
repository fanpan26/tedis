package redis.clients.tedis;

public interface Commands {
    void set( String key, String value);
    void get( String key);
    void get( String key,int start,int end);
    void getset( String key, String value);
    void getbit( String key,int offset);
    void setbit( String key,int offset,int value);
    void get( String... keys);
    void setex( String key, String value,long seconds);
    void psetex( String key, String value,long milliseconds);
    void setnx( String key, String value);
    void msetnx( String... kvs);
    void setrange( String key,long offset, String value);
    void strlen( String key);
    void mset(String... kvs);
    void append( String key, String value);
    void hset( String key, String field, String value);
    void hexists( String key, String field);
    void hget( String key, String field);
    void hdel( String key, String... fields);
    void hget( String key);
    void hincrBy( String key, String field,String value);
    void hkeys( String key);
    void hlen( String key);
    void hmget( String key,String... fields);
    void hmset( String key,String... kvs);
    void hsetnx( String key, String field, String value);
    void hvals( String key);
    void blpop(long timeout, String... keys);
    void brpop(long timeout,String... keys);
    void brpoplpush( String source, String destination,long timeout);
    void lindex( String key,int index);
    void linsert( String key,boolean before, String pivot, String value);
    void llen( String key);
    void lpop( String key);
    void lpush( String key,String... values);
    void lpushx( String key, String value);
    void lrange( String key,long start,long end);
    void lrem( String key,int count, String value);
    void lset( String key,int index, String value);
    void ltrim( String key,long start,long end);
    void rpop( String key);
    void rpoplpush( String source, String destination);
    void rpush( String key,String... values);
    void rpushx( String key, String value);
    void sadd( String key,String... members);
    void scard( String key);
    void sdiff( String key1, String key2);
    void sdiffstore( String destination, String key1, String key2);
    void sinter( String key1, String key2);
    void sinterstore( String destination, String key1, String key2);
    void sismember( String key, String member);
    void smembers( String key);
    void smove( String source, String destination, String member);
    void spop( String key);
    void srandmember( String key,int count);
    void srem( String key,String... members);
    void sunion( String key1, String key2);;
    void sunionstore( String destination, String key1, String key2);
    void sscan( String key,long cursor, String match,int count);

    void zadd( String key,String... args);
    void zcard( String key);
    void zcount( String key,String min,String max);
    void zincrby( String key,String increment,String member);
    void zlexcount(String key,String min,String max);

    void ping();
    void publish( String channel, String message);
    void subscribe( String... channels);
    void unsubscribe( String... channels);
    void psubscribe( String... channelPatterns);
    void punsubscribe( String... channelPatterns);
    void incr( String key);
    void decr( String key);
    void incrby( String key,String value);
    void decrby( String key,String value);
    void exists( String key);
    void del( String key);
    void quit();
    void flush();
    void selectDb(int db);
    void expire( String key,long seconds);
    void expireAt( String key,long timestamp);
    void pexpire( String key,long milliseconds);
    void pexpireAt( String key,long timestamp);

}
