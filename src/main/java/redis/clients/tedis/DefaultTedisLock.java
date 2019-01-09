package redis.clients.tedis;

public class DefaultTedisLock implements TedisLock {

    private final Tedis tedis;

    public DefaultTedisLock(final Tedis tedis) {
        this.tedis = tedis;
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }
}
