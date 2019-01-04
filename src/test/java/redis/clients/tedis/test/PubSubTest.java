package redis.clients.tedis.test;

import redis.clients.tedis.Tedis;

public class PubSubTest {
    public static void main(String[] args){
        MyPubSub pubSub = new MyPubSub();

        Tedis tedis = new Tedis("192.168.1.225", 6379);

        tedis.subscribe(pubSub,"test_channel");
    }
}
