package redis.clients.tedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Tedis  implements TedisCommands,ScriptingCommands {

    private static Logger logger = LoggerFactory.getLogger(Tedis.class);

    private static final int DEFAULT_LIMIT = 0;
    private static final String BRACKETS_CONTAINS = "[";
    private static final String BRACKETS_NOT_CONTAINS = "(";

    private Client client;

    public Tedis() {
        this(null, 0);
    }

    public Tedis(final String host, final int port) {
        if (host == null || port == 0) {
            client = new Client();
        } else {
            client = new Client(host, port);
        }
    }

    @Override
    public String set(String key, String value) {
        client.set(key, value);
        return client.getStatusCodeReply();
    }

    @Override
    public String set(String key, long value) {
        return set(key,String.valueOf(value));
    }

    @Override
    public String set(String key, int value) {
        return set(key,String.valueOf(value));
    }

    @Override
    public String set(String key, double value) {
        return set(key,String.valueOf(value));
    }

    @Override
    public String set(String key, float value) {
        return set(key,String.valueOf(value));
    }

    @Override
    public String get(String key) {
        client.get(key);
        return client.getBulkReply();
    }

    @Override
    public List<String> get(String... keys) {
        client.get(keys);
        return client.getListStringReply();
    }

    @Override
    public String get(String key, int start, int end) {
        client.get(key, start, end);
        return client.getBulkReply();
    }

    @Override
    public String getset(String key, String value) {
        client.getset(key, value);
        return client.getBulkReply();
    }

    @Override
    public String getset(String key, long value) {
        return getset(key,String.valueOf(value));
    }

    @Override
    public String getset(String key, int value) {
        return getset(key,String.valueOf(value));
    }

    @Override
    public String getset(String key, float value) {
        return getset(key,String.valueOf(value));
    }

    @Override
    public String getset(String key, double value) {
        return getset(key,String.valueOf(value));
    }

    @Override
    public int getbit(String key, int offset) {
        client.getbit(key, offset);
        return client.getIntegerReply();
    }

    @Override
    public int setbit(String key, int offset, boolean value) {
        client.setbit(key, offset, value ? 1 : 0);
        return client.getIntegerReply();
    }

    @Override
    public String setex(String key, String value, long time, TimeUnit unit) {
        long expires = 0;
        switch (unit) {
            case DAYS:
                expires = time * 86400000;
                break;
            case HOURS:
                expires = time * 3600000;
                break;
            case MINUTES:
                expires = time * 60000;
                break;
            case SECONDS:
                expires = time * 1000;
                break;
            case MILLISECONDS:
                expires = time;
                break;
            case NANOSECONDS:
            case MICROSECONDS:
                throw new UnsupportedOperationException("NANOSECONDS or MICROSECONDS");
        }
        client.psetex(key, value, expires);
        return client.getBulkReply();
    }

    @Override
    public boolean setnx(String key, String value) {
        client.setnx(key, value);
        return client.getBooleanReply();
    }

    @Override
    public int set(String key, String value, long offset) {
        client.setrange(key, offset, value);
        return client.getIntegerReply();
    }


    private String[] getKeyValues(List<String> keys, List<String> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("keys size must equals values size");
        }
        List<String> kvs = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            kvs.add(keys.get(i));
            kvs.add(values.get(i));
        }
        String[] kvsStr = new String[keys.size() << 1];
        kvs.toArray(kvsStr);
        return kvsStr;
    }

    @Override
    public int msetnx(List<String> keys, List<String> values) {
        client.msetnx(getKeyValues(keys, values));
        return client.getIntegerReply();
    }

    @Override
    public String set(List<String> keys, List<String> values) {
        client.mset(getKeyValues(keys, values));
        return client.getBulkReply();
    }

    @Override
    public int append(String key, String value) {
        client.append(key, value);
        return client.getIntegerReply();
    }

    @Override
    public int hset(String key, String field, String value) {
        client.hset(key, field, value);
        return client.getIntegerReply();
    }

    @Override
    public boolean hexists(String key, String field) {
        client.hexists(key, field);
        return client.getBooleanReply();
    }

    @Override
    public String hget(String key, String field) {
        client.hget(key, field);
        return client.getBulkReply();
    }

    @Override
    public boolean hdel(String key, String... fields) {
        client.hdel(key, fields);
        return client.getBooleanReply();
    }

    @Override
    public Map<String, String> hget(String key) {
        client.hget(key);
        return client.getMapReply();
    }

    @Override
    public long hincrBy(String key, String field, long value) {
        client.hincrBy(key, field, String.valueOf(value));
        return client.getLongReply();
    }

    @Override
    public int hincrBy(String key, String field, int value) {
        client.hincrBy(key, field, String.valueOf(value));
        return client.getIntegerReply();
    }

    @Override
    public float hincrBy(String key, String field, float value) {
        client.hincrBy(key, field, String.valueOf(value));
        return client.getFloatReply();
    }

    @Override
    public double hincrBy(String key, String field, double value) {
        client.hincrBy(key, field, String.valueOf(value));
        return client.getDoubleReply();
    }

    @Override
    public long hincrBy(String key, String field) {
        return hincrBy(key, field, 1L);
    }

    @Override
    public List<String> hkeys(String key) {
        client.hkeys(key);
        return client.getListStringReply();
    }

    @Override
    public int hlen(String key) {
        client.hlen(key);
        return client.getIntegerReply();
    }

    @Override
    public List<String> hget(String key, String... fields) {
        client.hmget(key, fields);
        return client.getListStringReply();
    }

    @Override
    public String hset(String key, List<String> fields, List<String> values) {
        client.hmset(key, getKeyValues(fields, values));
        return client.getStatusCodeReply();
    }

    @Override
    public boolean hsetnx(String key, String field, String value) {
        client.hsetnx(key, field, value);
        return client.getBooleanReply();
    }

    @Override
    public List<String> hvals(String key) {
        client.hvals(key);
        return client.getListStringReply();
    }

    @Override
    public String blpop(long timeout, String... keys) {
        client.blpop(timeout, keys);
        return client.getListStringReply(1);
    }

    @Override
    public String brpop(long timeout, String... keys) {
        client.brpop(timeout, keys);
        return client.getListStringReply(1);
    }

    @Override
    public String brpoplpush(String source, String destination, long timeout) {
        client.brpoplpush(source, destination, timeout);
        return client.getBulkReply();
    }

    @Override
    public String lindex(String key, int index) {
        client.lindex(key, index);
        return client.getBulkReply();
    }

    @Override
    public int linsertBeofore(String key, String pivot, String value) {
        return linsert(key, true, pivot, value);
    }

    @Override
    public int linsertAfter(String key, String pivot, String value) {
        return linsert(key, false, pivot, value);
    }

    @Override
    public int linsert(String key, boolean before, String pivot, String value) {
        client.linsert(key, before, pivot, value);
        return client.getIntegerReply();
    }

    @Override
    public long llen(String key) {
        client.llen(key);
        return client.getLongReply();
    }

    @Override
    public String lpop(String key) {
        client.lpop(key);
        return client.getBulkReply();
    }

    @Override
    public int lpush(String key, String... values) {
        client.lpush(key, values);
        return client.getIntegerReply();
    }

    @Override
    public int lpushx(String key, String value) {
        client.lpushx(key, value);
        return client.getIntegerReply();
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        client.lrange(key, start, end);
        return client.getListStringReply();
    }

    @Override
    public int lrem(String key, int count, String value) {
        client.lrem(key, count, value);
        return client.getIntegerReply();
    }

    @Override
    public String lset(String key, int index, String value) {
        client.lset(key, index, value);
        return client.getStatusCodeReply();
    }

    @Override
    public String ltrim(String key, long start, long end) {
        client.ltrim(key, start, end);
        return client.getStatusCodeReply();
    }

    @Override
    public String rpop(String key) {
        client.rpop(key);
        return client.getBulkReply();
    }

    @Override
    public String rpoplpush(String source, String destination) {
        client.rpoplpush(source, destination);
        return client.getBulkReply();
    }

    @Override
    public int rpush(String key, String... values) {
        client.rpush(key, values);
        return client.getIntegerReply();
    }

    @Override
    public int rpushx(String key, String value) {
        client.rpushx(key, value);
        return client.getIntegerReply();
    }

    @Override
    public int len(String key) {
        client.strlen(key);
        return client.getIntegerReply();
    }

    @Override
    public String ping() {
        client.ping();
        return client.getStatusCodeReply();
    }

    /**
     * 发布
     *
     * @param channel
     * @param message
     */
    @Override
    public int publish(String channel, String message) {
        client.publish(channel, message);
        return client.getIntegerReply();
    }

    /**
     * 订阅
     *
     * @param channels
     */
    @Override
    public void subscribe(TedisPubSub pubSub, final String... channels) {
        pubSub.proceed(client, channels);
    }

    /**
     * 模式订阅
     *
     * @param pubSub
     * @param channelPatterns
     */
    @Override
    public void pSubscribe(TedisPubSub pubSub, String... channelPatterns) {
        pubSub.proceedPatterns(client, channelPatterns);
    }

    @Override
    public long incr(String key) {
        client.incr(key);
        return client.getLongReply();
    }

    @Override
    public long incrby(String key, long value) {
        client.incrby(key, String.valueOf(value));
        return client.getLongReply();
    }

    @Override
    public float incrby(String key, float value) {
        client.incrby(key, String.valueOf(value));
        return client.getFloatReply();
    }

    @Override
    public double incrby(String key, double value) {
        client.incrby(key, String.valueOf(value));
        return client.getDoubleReply();
    }


    @Override
    public long decr(String key) {
        client.decr(key);
        return client.getLongReply();
    }

    @Override
    public long decrby(String key, long value) {
        client.decrby(key, String.valueOf(value));
        return client.getLongReply();
    }

    @Override
    public float decrby(String key, float value) {
        client.decrby(key, String.valueOf(value));
        return client.getFloatReply();
    }

    @Override
    public double decrby(String key, double value) {
        client.decrby(key, String.valueOf(value));
        return client.getDoubleReply();
    }

    @Override
    public int decrby(String key,int value){
        client.decrby(key,String.valueOf(value));
        return client.getIntegerReply();
    }

    @Override
    public int incrby(String key,int value){
        client.incrby(key,String.valueOf(value));
        return client.getIntegerReply();
    }

    @Override
    public boolean del(String key) {
        client.del(key);
        return client.getIntegerReply() > 0;
    }

    @Override
    public String dump(String key) {
       client.dump(key);
       return client.getBulkReply();
    }

    @Override
    public List<String> keys(String pattern) {
        client.keys(pattern);
        return client.getListStringReply();
    }

    @Override
    public boolean move(String key, int db) {
       client.move(key,db);
       return client.getBooleanReply();
    }

    @Override
    public boolean persist(String key) {
       client.persist(key);
       return client.getBooleanReply();
    }

    @Override
    public long pttl(String key) {
       client.pttl(key);
       return client.getLongReply();
    }

    @Override
    public long ttl(String key) {
      client.ttl(key);
      return client.getLongReply();
    }

    @Override
    public String randomkey() {
       client.randomkey();
       return client.getBulkReply();
    }

    @Override
    public String rename(String key, String newKey) {
        client.rename(key,newKey);
        return client.getBulkReply();
    }

    @Override
    public String renamenx(String key, String newKey) {
        client.renamenx(key,newKey);
        return client.getStatusCodeReply();
    }

    @Override
    public String type(String key) {
        client.type(key);
        return client.getStatusCodeReply();
    }

    @Override
    public boolean exists(String key) {
        client.exists(key);
        return client.getBooleanReply();
    }

    @Override
    public void quit() {
        client.quit();
        client.getStatusCodeReply();
        client.disconnect();
    }

    @Override
    public String flush() {
        client.flush();
        return client.getStatusCodeReply();
    }

    @Override
    public String selectDb(int db) {
        client.selectDb(db);
        return client.getStatusCodeReply();
    }

    @Override
    public boolean expire(String key, long seconds) {
        client.expire(key, seconds);
        return client.getIntegerReply() > 0;
    }

    @Override
    public boolean pexpire(String key, long milliseconds) {
        client.pexpire(key, milliseconds);
        return client.getBooleanReply();
    }

    @Override
    public boolean expireAt(String key, long timestamp) {
        client.expireAt(key, timestamp);
        return client.getBooleanReply();
    }

    @Override
    public boolean pexpireAt(String key, long timestamp) {
        client.pexpireAt(key, timestamp);
        return client.getBooleanReply();
    }

    @Override
    public Boolean scriptExists(String sha1) {
        return client.scriptExists(sha1);
    }

    @Override
    public List<Boolean> scriptExists(String... sha1) {
        return client.scriptExists(sha1);
    }

    @Override
    public Object eval(String script) {
        return client.eval(script);
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        return client.eval(script, keys, args);
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        return client.eval(script, keyCount, params);
    }

    @Override
    public Object evalsha(String sha1) {
        return client.evalsha(sha1);
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return client.evalsha(sha1, keys, args);
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        return client.evalsha(sha1, keyCount, params);
    }

    @Override
    public String scriptLoad(String script) {
        return client.scriptLoad(script);
    }

    @Override
    public long sadd(String key, String... members) {
        client.sadd(key, members);
        return client.getLongReply();
    }


    private String[] getParams(Object... members){
        String[] args = new String[members.length];
        for (int i=0;i<members.length;i++){
            args[i] = String.valueOf(members[i]);
        }
        return args;
    }

    @Override
    public long sadd(String key, long... members) {
        return sadd(key, getParams(members));
    }

    @Override
    public long sadd(String key, int... members) {
        return sadd(key, getParams(members));
    }

    @Override
    public long sadd(String key, double... members) {
        return sadd(key, getParams(members));
    }

    @Override
    public long sadd(String key, float... members) {
        return sadd(key, getParams(members));
    }

    @Override
    public long scard(String key) {
        client.scard(key);
        return client.getLongReply();
    }

    @Override
    public List<String> sdiff(String key1, String key2) {
        client.sdiff(key1, key2);
        return client.getListStringReply();
    }

    @Override
    public long sdiffstore(String destination, String key1, String key2) {
        client.sdiffstore(destination, key1, key2);
        return client.getLongReply();
    }

    @Override
    public List<String> sinter(String key1, String key2) {
        client.sinter(key1, key2);
        return client.getListStringReply();
    }

    @Override
    public long sinterstore(String destination, String key1, String key2) {
        client.sinterstore(destination, key1, key2);
        return client.getLongReply();
    }

    @Override
    public boolean sismember(String key, String member) {
        client.sismember(key, member);
        return client.getBooleanReply();
    }

    @Override
    public List<String> smembers(String key) {
        client.smembers(key);
        return client.getListStringReply();
    }

    @Override
    public int smove(String source, String destination, String member) {
        client.smove(source, destination, member);
        return client.getIntegerReply();
    }

    @Override
    public String spop(String key) {
        client.spop(key);
        return client.getBulkReply();
    }

    @Override
    public List<String> srandmember(String key, int count) {
        client.srandmember(key, count);
        return client.getListStringReply();
    }

    @Override
    public int srem(String key, String... members) {
        client.srem(key, members);
        return client.getIntegerReply();
    }

    @Override
    public List<String> sunion(String key1, String key2) {
        client.sunion(key1, key2);
        return client.getListStringReply();
    }

    @Override
    public long sunionstore(String destination, String key1, String key2) {
        client.sunionstore(destination, key1, key2);
        return client.getLongReply();
    }

    @Override
    public ScanResult sscan(String key, long cursor, String match, int count) {
        client.sscan(key, cursor, match, count);
        List<String> result = client.getListStringReply();
        ScanResult scanResult = new ScanResult(result.get(0));
        result.remove(0);
        scanResult.setObjects(result);
        return scanResult;
    }

    @Override
    public int zadd(String key, float score, String member) {
        client.zadd(key, String.valueOf(score), member);
        return client.getIntegerReply();
    }

    @Override
    public int zadd(String key, List<String> scores, List<String> members) {
        String[] args = getKeyValues(scores, members);
        client.zadd(key, args);
        return client.getIntegerReply();
    }

    @Override
    public long zcount(String key, float min, float max) {
        client.zcount(key, String.valueOf(min), String.valueOf(max));
        return client.getLongReply();
    }

    @Override
    public long zcount(String key, int min, int max) {
        client.zcount(key, String.valueOf(min), String.valueOf(max));
        return client.getLongReply();
    }

    @Override
    public long zcount(String key, long min, long max) {
        client.zcount(key, String.valueOf(min), String.valueOf(max));
        return client.getLongReply();
    }

    @Override
    public long zcount(String key, double min, double max) {
        client.zcount(key, String.valueOf(min), String.valueOf(max));
        return client.getLongReply();
    }

    @Override
    public long zcard(String key) {
        client.zcard(key);
        return client.getLongReply();
    }

    @Override
    public long zincrby(String key, long increment, String member) {
        client.zincrby(key, String.valueOf(increment), member);
        return client.getLongReply();
    }

    @Override
    public int zincrby(String key, int increment, String member) {
        client.zincrby(key, String.valueOf(increment), member);
        return client.getIntegerReply();
    }

    @Override
    public double zincrby(String key, double increment, String member) {
        client.zincrby(key, String.valueOf(increment), member);
        return client.getDoubleReply();
    }

    @Override
    public float zincrby(String key, float increment, String member) {
        client.zincrby(key, String.valueOf(increment), member);
        return client.getFloatReply();
    }

    @Override
    public long zlexcount(String key, String min, String max) {
        client.zlexcount(key, BRACKETS_CONTAINS + min, BRACKETS_CONTAINS + max);
        return client.getLongReply();
    }

    @Override
    public List<String> zrange(String key, long start, long end, boolean containsScore) {
        client.zrange(key, String.valueOf(start), String.valueOf(end), containsScore);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrange(String key, int start, int end, boolean containsScore) {
        client.zrange(key, String.valueOf(start), String.valueOf(end), containsScore);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrange(String key, long start, boolean containsScore) {
        client.zrange(key, String.valueOf(start), "-1", containsScore);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrange(String key, int start, boolean containsScore) {
        client.zrange(key, String.valueOf(start), "-1", containsScore);
        return client.getListStringReply();
    }

    @Override
    public long zrank(String key, String member) {
        client.zrank(key, member);
        Long res = client.getLongReply(-1);
        return res == -1 ? res : res + 1;
    }

    @Override
    public long zrevrank(String key, String member) {
        client.zrevrank(key, member);
        Long res = client.getLongReply(-1);
        return res == -1 ? res : res + 1;
    }

    @Override
    public List<String> zrangebyscore(String key, int min, int max, boolean withScore, int limit) {
        client.zrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrangebyscore(String key, int min, int max) {
        return zrangebyscore(key, min, max, false, DEFAULT_LIMIT);
    }

    @Override
    public List<String> zrangebyscore(String key, double min, double max, boolean withScore, int limit) {
        client.zrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrangebyscore(String key, double min, double max) {
        return zrangebyscore(key, min, max, false, DEFAULT_LIMIT);
    }

    @Override
    public List<String> zrangebyscore(String key, float min, float max, boolean withScore, int limit) {
        client.zrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrangebyscore(String key, float min, float max) {
        return zrangebyscore(key, min, max, false, DEFAULT_LIMIT);
    }

    @Override
    public List<String> zrangebyscore(String key, long min, long max, boolean withScore, int limit) {
        client.zrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrangebyscore(String key, long min, long max) {
        return zrangebyscore(key, min, max, false, DEFAULT_LIMIT);
    }

    @Override
    public List<String> zrangebyscore(String key) {
        client.zrangebyscore(key, "-inf", "+inf", false, DEFAULT_LIMIT);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrevrangebyscore(String key) {
        client.zrevrangebyscore(key, "+inf", "-inf", false, DEFAULT_LIMIT);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrevrangebyscore(String key, int min, int max, boolean withScore, int limit) {
        client.zrevrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrevrangebyscore(String key, int min, int max) {
        return zrevrangebyscore(key, min, max, false, 0);
    }

    @Override
    public List<String> zrevrangebyscore(String key, float min, float max, boolean withScore, int limit) {
        client.zrevrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrevrangebyscore(String key, float min, float max) {
        return zrevrangebyscore(key, min, max, false, DEFAULT_LIMIT);
    }

    @Override
    public List<String> zrevrangebyscore(String key, long min, long max, boolean withScore, int limit) {
        client.zrevrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrevrangebyscore(String key, long min, long max) {
        return zrevrangebyscore(key, min, max, false, DEFAULT_LIMIT);
    }

    @Override
    public List<String> zrevrangebyscore(String key, double min, double max, boolean withScore, int limit) {
        client.zrevrangebyscore(key, String.valueOf(min), String.valueOf(max), withScore, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrevrangebyscore(String key, double min, double max) {
        return zrevrangebyscore(key, min, max, false, DEFAULT_LIMIT);
    }

    @Override
    public int zremrangebyscore(String key, int min, int max) {
        client.zremrangebyscore(key, String.valueOf(min), String.valueOf(max));
        return client.getIntegerReply();
    }

    @Override
    public int zremrangebyscore(String key, float min, float max) {
        client.zremrangebyscore(key, String.valueOf(min), String.valueOf(max));
        return client.getIntegerReply();
    }

    @Override
    public int zremrangebyscore(String key, double min, double max) {
        client.zremrangebyscore(key, String.valueOf(min), String.valueOf(max));
        return client.getIntegerReply();
    }

    @Override
    public int zremrangebyscore(String key, long min, long max) {
        client.zremrangebyscore(key, String.valueOf(min), String.valueOf(max));
        return client.getIntegerReply();
    }

    @Override
    public int zrem(String key, String... members) {
        client.zrem(key, members);
        return client.getIntegerReply();
    }

    @Override
    public float zscore(String key, String member) {
        client.zscore(key, member);
        return client.getFloatReply(-1f);
    }

    @Override
    public List<String> zrangebylex(String key, String min, String max, int limit) {
        return zrangebylex(key, min, max, limit, true, true);
    }

    @Override
    public List<String> zrangebylex(String key, String min, String max) {
        return zrangebylex(key, min, max, DEFAULT_LIMIT, true, true);
    }

    @Override
    public List<String> zrangebylex(String key, String min, String max, int limit, boolean containsMin, boolean containsMax) {
        client.zrangebylex(key, (containsMin ?  BRACKETS_CONTAINS : BRACKETS_NOT_CONTAINS) + min, (containsMax ?  BRACKETS_CONTAINS : BRACKETS_NOT_CONTAINS) + max, limit);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrangebylex(String key, String min, String max, boolean containsMin, boolean containsMax) {
        return zrangebylex(key, min, max, DEFAULT_LIMIT, containsMin, containsMax);
    }

    @Override
    public List<String> zrangebylex(String key, String max, boolean containsMax) {
        client.zrangebylex(key, "-", (containsMax ? BRACKETS_CONTAINS : BRACKETS_NOT_CONTAINS) + max, DEFAULT_LIMIT);
        return client.getListStringReply();
    }

    @Override
    public List<String> zrangebylex(String key, String max) {
        return zrangebylex(key, max, true);
    } @Override
    public
    int zremrangebyrank(String key,long start,long stop) {
        client.zremrangebyrank(key, String.valueOf(start), String.valueOf(stop));
        return client.getIntegerReply();
    }
    @Override
    public int zremrangebyrank(String key,int start,int stop){
        client.zremrangebyrank(key, String.valueOf(start), String.valueOf(stop));
        return client.getIntegerReply();
    }
    @Override
    public int zremrangebyrank(String key,int start){
        return zremrangebyrank(key,start,-1);
    }
    @Override
    public int zremrangebyrank(String key,long start) {
        return zremrangebyrank(key, start, -1L);
    }
}
