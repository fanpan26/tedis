package redis.clients.tedis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.tedis.Tedis;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        Tedis tedis = new Tedis("192.168.1.225", 6379);

        tedis.subscribe(new MyPubSub(),"channel1","channel2");
    }
}
