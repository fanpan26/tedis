package redis.clients.tedis.test;

import redis.clients.tedis.TedisPubSub;

public class MyPubSub extends TedisPubSub {


    @Override
    public void onMessage(String channel, String message) {
        System.out.println(channel + "收到了消息："+message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println( channel + "("+pattern+")收到了消息："+message);
    }

    @Override
    public void onSubscribe(String channel) {
        System.out.println("订阅了："+channel);
    }

    @Override
    public void onPSubscribe(String channelPatterns) {
        System.out.println("订阅了："+channelPatterns);
    }

    @Override
    public void onUnSubscribe(String channel) {
        System.out.println("取消订阅了："+channel);
    }
}
