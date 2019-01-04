package redis.clients.tedis.test;

import org.tio.utils.SystemTimer;
import redis.clients.tedis.Tedis;

public class TedisTest {
    public static void main(String[] args) {
        Tedis tedis = new Tedis("192.168.1.225", 6379);
        tedis.set("panzi", "213456");

        long start = SystemTimer.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            tedis.set("panzi", "123456");
        }
        long end = SystemTimer.currentTimeMillis();

        System.out.println("循环200遍共用时："+(end-start)+"ms,平均："+(float)(end-start)/200+"ms");
    }
}
