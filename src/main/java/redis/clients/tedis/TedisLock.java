package redis.clients.tedis;

public interface TedisLock{
    void lock();
    void unlock();
}
