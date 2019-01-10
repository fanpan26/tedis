package redis.clients.tedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Tedis  implements TedisCommands,ScriptingCommands {

    private static Logger logger = LoggerFactory.getLogger(Tedis.class);

    private TedisLock tedisLock;

    private Client client;

    public Tedis() {
       this(null,0);
    }

    public Tedis(final String host, final int port) {
        if (host == null || port == 0) {
            client = new Client();
        } else {
            client = new Client(host, port);
        }
        tedisLock = new DefaultTedisLock(client);
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    @Override
    public String set(String key, String value) {
        client.set(key, value);
        return client.getStatusCodeReply();
    }

    /**
     * 获取缓存
     *
     * @param key
     */
    @Override
    public String get(String key) {
        client.get(key);
        return client.getBulkReply();
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
    public long incrBy(String key, long value) {
        client.incrBy(key, value);
        return client.getLongReply();
    }

    @Override
    public long decr(String key) {
        client.decr(key);
        return client.getLongReply();
    }

    @Override
    public long decrBy(String key, long value) {
        client.decrBy(key, value);
        return client.getLongReply();
    }

    @Override
    public boolean del(String key) {
        client.del(key);
        return client.getIntegerReply() > 0;
    }

    @Override
    public boolean exists(String key) {
        client.exists(key);
        return client.getIntegerReply() > 0;
    }
    @Override
    public void quit() {
        client.quit();
        client.getStatusCodeReply();
        client.disconnect();
    }

    @Override
    public void flush(){
        client.flush();
        client.getStatusCodeReply();
    }

    /**
     * 通过lua实现分布式锁
     *
     * @param lockKey
     */
    @Override
    public TedisLock getLock(String lockKey) {
        return tedisLock.getLock(lockKey);
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
        return client.getIntegerReply() > 0;
    }

    @Override
    public boolean expireAt(String key, long timestamp) {
        client.expireAt(key, timestamp);
        return client.getIntegerReply() > 0;
    }

    @Override
    public boolean pexpireAt(String key, long timestamp) {
        client.pexpireAt(key, timestamp);
        return client.getIntegerReply() > 0;
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
        return client.eval(script,keys,args);
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        return client.eval(script,keyCount,params);
    }

    @Override
    public Object evalsha(String sha1) {
        return client.evalsha(sha1);
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return client.evalsha(sha1,keys,args);
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        return client.evalsha(sha1,keyCount,params);
    }

    @Override
    public String scriptLoad(String script) {
        return client.scriptLoad(script);
    }
}
