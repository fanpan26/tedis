package redis.clients.tedis;

public interface TedisLock{

    TedisLock getLock(final String lockKey);
    void lock();
    void unlock();
}
