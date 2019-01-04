package redis.clients.tedis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.tedis.Tedis;
import redis.clients.tedis.TedisPubSub;


public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        Tedis tedis = new Tedis("192.168.1.225", 6379);
        tedis.subscribe(new TedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                logger.debug("channel:{},message:{}", channel, message);
            }

            @Override
            public void onSubscribe(String channel, int result) {
                logger.debug("onSubscribe:channel:{},result:{}", channel, result);
            }
        }, "channel1", "channel2");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
