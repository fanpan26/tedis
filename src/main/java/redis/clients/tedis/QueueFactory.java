package redis.clients.tedis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueFactory {
    private static final ConcurrentHashMap<String, LinkedBlockingQueue<TedisPacket>> queues =new ConcurrentHashMap<>();

    /**
     * 新开启一个客户端，就加入一条消息回调队列
     * */
    public static void put(final String clientName) {
        queues.put(clientName, new LinkedBlockingQueue<>());
    }

    public static LinkedBlockingQueue<TedisPacket> get(final String clientName){
        return queues.get(clientName);
    }
}
