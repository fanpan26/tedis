package redis.clients.tedis.test;

import org.junit.Before;
import redis.clients.tedis.Tedis;

public class TedisTestBase {
    protected Tedis tedis;

//    @Before
//    public void before() {
//        tedis = new Tedis("192.168.1.225", 6379);
//    }
    @Before
    public void before() {
        tedis = new Tedis("192.168.187.129", 6379);
        tedis.ping();
    }

    protected String generateKey(String prefix) {
        return "tedis:" + prefix + ":" + System.currentTimeMillis();
    }
}
