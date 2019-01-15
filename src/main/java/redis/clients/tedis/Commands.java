package redis.clients.tedis;

public interface Commands {
    void set(final String key,final String value);
    void get(final String key);
    void get(final String key,int start,int end);
    void getset(final String key,final String value);
    void getbit(final String key,int offset);
    void setbit(final String key,int offset,int value);
    void get(final String... keys);
    void setex(final String key,final String value,long seconds);
    void psetex(final String key,final String value,long milliseconds);
    void setnx(final String key,final String value);
    void msetnx(final String... kvs);
    void setrange(final String key,long offset,final String value);
    void strlen(final String key);
    void mset(String... kvs);
    void append(final String key,final String value);
    void ping();
    void publish(final String channel,final String message);
    void subscribe(final String... channels);
    void unSubscribe(final String... channels);
    void pSubscribe(final String... channelPatterns);
    void pUnSubscribe(final String... channelPatterns);
    void incr(final String key);
    void decr(final String key);
    void incrBy(final String key,long value);
    void incrByfloat(final String key,float value);
    void decrBy(final String key,long value);
    void exists(final String key);
    void del(final String key);
    void quit();
    void flush();
    void selectDb(int db);
    void expire(final String key,long seconds);
    void expireAt(final String key,long timestamp);
    void pexpire(final String key,long milliseconds);
    void pexpireAt(final String key,long timestamp);

}
