package redis.clients.tedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public List<String> get(String... keys){
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
        client.getset(key,value);
        return client.getBulkReply();
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
   public  String setex(String key, String value, long time, TimeUnit unit) {
        long expires = 0;
        switch (unit) {
            case DAYS:
                expires = time * 24 * 3600 * 1000;
                break;
            case HOURS:
                expires = time * 3600 * 1000;
                break;
            case MINUTES:
                expires = time * 60 * 1000;
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
        client.setnx(key,value);
        return client.getIntegerReply() > 0;
    }

    @Override
    public int set(String key, String value, long offset) {
        client.setrange(key, offset, value);
        return client.getIntegerReply();
    }


    private String[] getKeyValues(List<String> keys,List<String> values){
        if(keys.size() != values.size()) {
            throw new IllegalArgumentException("keys size must equals values size");
        }
        List<String> kvs = new ArrayList<>();
        for(int i=0;i<keys.size();i++){
            kvs.add(keys.get(i));
            kvs.add(values.get(i));
        }
        String[] kvsStr = new String[keys.size()<<1];
        kvs.toArray(kvsStr);
        return kvsStr;
    }

    @Override
   public int msetnx(List<String> keys,List<String> values) {
        client.msetnx(getKeyValues(keys,values));
        return client.getIntegerReply();
    }

    @Override
    public String set(List<String> keys, List<String> values) {
        client.mset(getKeyValues(keys, values));
        return client.getBulkReply();
    }

    @Override
    public float incrByfloat(String key,float value){
        client.incrByfloat(key,value);
        return client.getFloatReply();
    }

    @Override
    public int append(String key,String value){
        client.append(key,value);
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
