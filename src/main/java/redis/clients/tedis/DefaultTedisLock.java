package redis.clients.tedis;

public class DefaultTedisLock implements TedisLock {

    private static final String lockScript = "if(redis.call('exists',KEYS[1])==0) then "+
            "redis.call('hset',KEYS[1],ARGV[2],1); "+
            "redis.call('pexpire',KEYS[1],ARGV[1]); "+
            "return nil; "+
            "end; "+
            "if(redis.call('hexists',KEYS[1],ARGV[2])==1) then "+
            "redis.call('hincrby',KEYS[1],ARGV[2],1); "+
            "redis.call('pexpire',KEYS[1],ARGV[1]);"+
            "return nil;"+
            "end; "+
            "return redis.call('pttl',KEYS[1]);";

    private static final long DEFAULT_LOCK_TIME = 30000;

    private final Client client;

    public DefaultTedisLock(final Client client) {
        this.client = client;
    }


    @Override
    public TedisLock getLock(final String lockKey){
       Object result = client.eval(lockScript, 1, lockKey, String.valueOf(DEFAULT_LOCK_TIME), client.getClientName());
       if(result == null){
           return this;
       }
       return null;
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }
}
