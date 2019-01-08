package redis.clients.tedis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.tedis.Tedis;
import redis.clients.tedis.TedisPubSub;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        TedisPubSub pb =new MyPubSub();
        Tedis tedis = new Tedis("192.168.1.225", 6379);
        tedis.pSubscribe(pb,"news.*");
        System.out.println("已经订阅news.*");
        tedis.subscribe(pb,"news.sports");
        System.out.println("已经订阅news.sports");
    }
}
