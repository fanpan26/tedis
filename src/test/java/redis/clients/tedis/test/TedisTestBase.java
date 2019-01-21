package redis.clients.tedis.test;

import redis.clients.tedis.Tedis;

public class TedisTestBase {
    protected Tedis tedis= new Tedis("192.168.1.225", 6379);

    protected String generateKey(String prefix) {
        return "tedis:" + prefix + ":" + System.currentTimeMillis();
    }

}
