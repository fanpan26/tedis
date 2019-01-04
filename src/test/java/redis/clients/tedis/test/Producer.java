package redis.clients.tedis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.utils.SystemTimer;
import redis.clients.tedis.Tedis;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        Tedis tedis = new Tedis("192.168.1.225", 6379);
//        Executors.newScheduledThreadPool(2).scheduleWithFixedDelay(() -> {
//            tedis.publish("channel1", "this message is to channel1");
//            tedis.publish("channel2", "this message is to channel2");
//        }, 2000, 2000L, TimeUnit.MILLISECONDS);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入消息");
            String message = scanner.nextLine();
            int result = tedis.publish("channel1",message);
            System.out.println("发送消息结果："+result);
        }
    }
}
