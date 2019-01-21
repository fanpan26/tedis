package redis.clients.tedis;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientFactory {
    private static final ConcurrentHashMap<String, LinkedBlockingQueue<TedisPacket>> queues = new ConcurrentHashMap<>(1);
    private static final ConcurrentHashMap<String, HashMap<String, TedisPacket>> packets = new ConcurrentHashMap<>(1);

    private static final String LONG_PACKET = "long_packet";
    private static final String ERROR_PACKET = "error_packet";
    private static final String LIST_PACKET = "list_packet";
    private static final String BODY_PACKET = "body_packet";


    /**
     * 新开启一个客户端，就加入一条消息回调队列
     */
    public static void put(final String clientName) {
        queues.putIfAbsent(clientName, new LinkedBlockingQueue<>());
        HashMap<String, TedisPacket> packetHashMap = new HashMap<>();
        packetHashMap.put(LONG_PACKET, new TedisPacket());
        packetHashMap.put(ERROR_PACKET, new TedisPacket().setErr());
        packetHashMap.put(LIST_PACKET, new TedisPacket());
        packetHashMap.put(BODY_PACKET, new TedisPacket());
        packets.put(clientName, packetHashMap);
    }

    public static TedisPacket getLongPacket(final String clientName, long value) {
        return packets.get(clientName).get(LONG_PACKET).setLongValue(value);
    }

    public static TedisPacket getErrorPacket(final String clientName, byte[] error) {
        if (error == null) {
            return null;
        }
        return packets.get(clientName).get(ERROR_PACKET).setBody(error);
    }

    public static TedisPacket getBytesPacket(final String clientName, byte[] body) {
        if (body == null) {
            return null;
        }
        if (body.length == 0) {
            return TedisPacket.Empty();
        }
        return packets.get(clientName).get(BODY_PACKET).setBody(body);
    }

    public static TedisPacket getListPacket(final String clientName, List<Object> objects) {
        if (objects == null) {
            return null;
        }
        return packets.get(clientName).get(LIST_PACKET).setObjects(objects);
    }

    public static LinkedBlockingQueue<TedisPacket> get(final String clientName) {
        return queues.get(clientName);
    }
}
