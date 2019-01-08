package redis.clients.tedis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.tedis.Tedis;

import java.io.UnsupportedEncodingException;

public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        Tedis tedisPublish =  new Tedis("192.168.1.225", 6379);
        //tedisPublish.publish("channel1","welcome to NBA");
        tedisPublish.publish("news.sports","中文不支持吗？");

    }
}
