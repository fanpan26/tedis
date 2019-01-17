package redis.clients.tedis.test;

import org.junit.Before;
import redis.clients.tedis.Tedis;

public class TedisTestBase {
    protected Tedis tedis;

    @Before
    public void before() {
        tedis = new Tedis("192.168.1.225", 6379);
    }
}
