package redis.clients.tedis;

import java.util.List;

import static redis.clients.tedis.Command.*;

public class Client extends Connection implements Commands,ScriptingCommands {
    public Client() {
        super();
    }

    public Client(final String host, final int port) {
        super(host, port);
    }

    @Override
    public void connect() throws Exception {
        if (!isConnected()) {
            super.connect();
//            if (password != null) {
//                auth(password);
//                getStatusCodeReply();
//            }
//            if (db > 0) {
//                select(db);
//                getStatusCodeReply();
//            }
        }
    }

    @Override
    public void set(final String key, final String value) {
        sendCommand(Command.SET, key, value);
    }

    @Override
    public void get(final String key) {
        sendCommand(GET, key);
    }

    @Override
    public void get(String key, int start, int end) {
        sendCommand(GETRANGE, key, String.valueOf(start), String.valueOf(end));
    }

    @Override
    public void get(String... keys) {
        sendCommand(MGET, keys);
    }

    @Override
    public void setex(String key, String value, long seconds) {
        sendCommand(SETEX, key, String.valueOf(seconds), value);
    }

    @Override
    public void psetex(String key, String value, long milliseconds) {
        sendCommand(PSETEX, key, String.valueOf(milliseconds), value);
    }

    @Override
    public void setnx(String key, String value) {
        sendCommand(SETNX, key, value);
    }

    @Override
    public void msetnx(String... kvs) {
        sendCommand(MSETNX, kvs);
    }

    @Override
    public void setrange(String key, long offset, String value) {
        sendCommand(SETRANGE, key, String.valueOf(offset), value);
    }

    @Override
    public void strlen(String key) {
        sendCommand(STRLEN, key);
    }

    @Override
    public void incrByfloat(String key, float value) {
        sendCommand(INCRBYFLOAT, key, String.valueOf(value));
    }

    @Override
    public void append(String key, String value) {
        sendCommand(APPEND, key, value);
    }

    @Override
    public void hset(String key, String field, String value) {
        sendCommand(HSET, key, field, value);
    }

    @Override
    public void hexists(String key, String field) {
        sendCommand(HEXISTS, key, field);
    }

    @Override
    public void hget(String key, String field) {
        sendCommand(HGET, key, field);
    }

    @Override
    public void hdel(String key, String... fields) {
        sendCommand(HDEL, getParams(key, fields));
    }

    @Override
    public void hget(String key) {
        sendCommand(HGETALL, key);
    }

    @Override
    public void hincrBy(String key, String field, long value) {
        sendCommand(HINCRBY, key, field, String.valueOf(value));
    }

    @Override
    public void hincrByFloat(String key, String field, float value) {
        sendCommand(HINCRBYFLOAT, key, field, String.valueOf(value));
    }

    @Override
    public void hkeys(String key) {
        sendCommand(HKEYS, key);
    }

    @Override
    public void hlen(String key) {
        sendCommand(HLEN, key);
    }

    @Override
    public void hmget(String key, String... fields) {
        sendCommand(HMGET, getParams(key, fields));
    }

    @Override
    public void hmset(String key, String... kvs) {
        sendCommand(HMSET, getParams(key, kvs));
    }

    @Override
    public void hsetnx(String key, String field, String value) {
        sendCommand(HSETNX, key, field, value);
    }

    @Override
    public void hvals(String key) {
        sendCommand(HVALS, key);
    }

    @Override
    public void blpop(long timeout, String... keys) {
        sendCommand(BLPOP, keys, timeout);
    }

    @Override
    public void brpop(long timeout, String... keys) {
        sendCommand(BRPOP, keys, timeout);
    }

    @Override
    public void brpoplpush(String source, String destination, long timeout) {
        sendCommand(BRPOPLPUSH, source, destination, String.valueOf(timeout));
    }

    @Override
    public void lindex(String key, int index) {
        sendCommand(LINDEX, key, index);
    }

    @Override
    public void linsert(String key, boolean before, String pivot, String value) {
        sendCommand(LINSERT, key, before ? "BEFORE" : "AFTER", pivot, value);
    }

    @Override
    public void llen(String key) {
        sendCommand(LLEN, key);
    }

    @Override
    public void lpop(String key) {
        sendCommand(LPOP, key);
    }

    @Override
    public void lpush(String key, String... values) {
        sendCommand(LPUSH, getParams(key, values));
    }

    @Override
    public void lpushx(String key, String value) {
        sendCommand(LPUSHX, key, value);
    }

    @Override
    public void lrange(String key, long start, long end) {
        sendCommand(LRANGE, key, start, end);
    }

    @Override
    public void lrem(String key, int count, String value) {
        sendCommand(LREM, key, String.valueOf(count), value);
    }

    @Override
    public void lset(String key, int index, String value) {
        sendCommand(LSET, key, String.valueOf(index), value);
    }

    @Override
    public void ltrim(String key, long start, long end) {
        sendCommand(LTRIM, key, start, end);
    }

    @Override
    public void rpop(String key) {
        sendCommand(RPOP, key);
    }

    @Override
    public void rpoplpush(String source, String destination) {
        sendCommand(RPOPLPUSH, source, destination);
    }

    @Override
    public void rpush(String key, String... values) {
        sendCommand(RPUSH, getParams(key, values));
    }

    @Override
    public void rpushx(String key, String value) {
        sendCommand(RPUSHX, key, value);
    }

    @Override
    public void sadd(String key, String... members) {
        sendCommand(SADD, getParams(key, members));
    }

    @Override
    public void scard(String key) {
        sendCommand(SCARD, key);
    }

    @Override
    public void sdiff(String key1, String key2) {
        sendCommand(SDIFF, key1, key2);
    }

    @Override
    public void sdiffstore(String destination, String key1, String key2) {
        sendCommand(SDIFFSTORE, destination, key1, key2);
    }

    @Override
    public void sinter(String key1, String key2) {
        sendCommand(SINTER, key1, key2);
    }

    @Override
    public void sinterstore(String destination, String key1, String key2) {
        sendCommand(SINTERSTORE, destination, key1, key2);
    }

    @Override
    public void sismember(String key, String member) {
        sendCommand(SISMEMBER, key, member);
    }

    @Override
    public void smembers(String key) {
        sendCommand(SMEMBERS, key);
    }

    @Override
    public void smove(String source, String destination, String member) {
        sendCommand(SMOVE, source, destination, member);
    }

    @Override
    public void spop(String key) {
        sendCommand(SPOP, key);
    }

    @Override
    public void srandmember(String key, int count) {
        sendCommand(SRANDMEMBER, key, count);
    }

    @Override
    public void srem(String key, String... members) {
        sendCommand(SREM, getParams(key, members));
    }

    @Override
    public void sunion(String key1, String key2) {
        sendCommand(SUNION, key1, key2);
    }

    @Override
    public void sunionstore(String destination, String key1, String key2) {
        sendCommand(SUNIONSTORE, destination, key1, key2);
    }

    @Override
    public void sscan(String key, long cursor, String match, int count) {
        boolean hasMatch = match != null && match.length() > 0;
        if (count > 0) {
            if (hasMatch) {
                sendCommand(SSCAN, key, String.valueOf(cursor), "MATCH", match, "COUNT", String.valueOf(count));
            } else {
                sendCommand(SSCAN, key, String.valueOf(cursor), "COUNT", String.valueOf(count));
            }
        } else {
            if (hasMatch) {
                sendCommand(SSCAN, key, String.valueOf(cursor), "match", match);
            } else {
                sendCommand(SSCAN, key, String.valueOf(cursor));
            }
        }
    }

    @Override
    public void mset(String... kvs) {
        sendCommand(MSET, kvs);
    }

    @Override
    public void getset(String key, String value) {
        sendCommand(GETSET, key, value);
    }

    @Override
    public void getbit(String key, int offset) {
        sendCommand(GETBIT, key, offset);
    }

    @Override
    public void setbit(String key, int offset, int value) {
        sendCommand(SETBIT, key, offset, value);
    }

    @Override
    public void ping() {
        sendCommand(PING);
    }

    @Override
    public void publish(final String channel, final String message) {
        sendCommand(Command.PUBLISH, channel, message);
    }

    @Override
    public void subscribe(final String... channels) {
        sendCommand(SUBSCRIBE, channels);
    }

    @Override
    public void unsubscribe(String... channels) {
        sendCommand(UNSUBSCRIBE, channels);
    }

    @Override
    public void psubscribe(String... channelPatterns) {
        sendCommand(PSUBSCRIBE, channelPatterns);
    }

    @Override
    public void punsubscribe(String... channelPatterns) {
        sendCommand(PUNSUBSCRIBE, channelPatterns);
    }

    @Override
    public void incr(final String key) {
        sendCommand(INCR, key);
    }

    @Override
    public void decr(final String key) {
        sendCommand(DECR, key);
    }

    @Override
    public void incrBy(final String key, long value) {
        sendCommand(INCRBY, key, String.valueOf(value));
    }

    @Override
    public void decrBy(final String key, long value) {
        sendCommand(DECRBY, key, String.valueOf(value));
    }

    @Override
    public void exists(final String key) {
        sendCommand(EXISTS, key);
    }

    @Override
    public void del(final String key) {
        sendCommand(DEL, key);
    }

    @Override
    public void quit() {
        sendCommand(QUIT);
    }

    @Override
    public void flush() {
        sendCommand(FLUSHDB);
    }

    @Override
    public void selectDb(int db) {
        sendCommand(SELECT, db);
    }

    @Override
    public void expire(String key, long seconds) {
        sendCommand(EXPIRE, key, seconds);
    }

    @Override
    public void expireAt(String key, long timestamp) {
        sendCommand(EXPIREAT, key, timestamp);
    }

    @Override
    public void pexpire(String key, long milliseconds) {
        sendCommand(PEXPIRE, key, milliseconds);
    }

    @Override
    public void pexpireAt(String key, long timestamp) {
        sendCommand(PEXPIREAT, key, timestamp);
    }

    @Override
    public Boolean scriptExists(String sha1) {
        return null;
    }

    @Override
    public List<Boolean> scriptExists(String... sha1) {
        return null;
    }

    @Override
    public Object eval(String script) {
        return eval(script, 0);
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        return eval(script, keys.size(), getParams(keys, args));
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        sendCommand(EVAL, getParams(script, keyCount, params));
        return getEvalReply();
    }

    @Override
    public Object evalsha(String sha1) {
        return null;
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return null;
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        return null;
    }

    @Override
    public String scriptLoad(String script) {
        return null;
    }
}
