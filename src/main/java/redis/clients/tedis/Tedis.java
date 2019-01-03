package redis.clients.tedis;

import org.tio.utils.SystemTimer;

public class Tedis  implements TedisCommands  {

    private Client client;

    public Tedis(){
        client = new Client();
    }

    public  Tedis(final String host,final int port) {
        client = new Client(host, port);
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    @Override
    public String set(String key, String value) {
       client.set(key,value);
       return client.getStatusCodeReply();
    }

    /**
     * 获取缓存
     *
     * @param key
     */
    @Override
    public String get(String key) {
       client.get(key);
       return client.getBulkReply();
    }

    @Override
    public String ping() {
        client.ping();
        return client.getStatusCodeReply();
    }

    public static void main(String[] args) {
        Tedis tedis = new Tedis("192.168.1.225", 6379);
        tedis.ping();
        long start = SystemTimer.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            tedis.set("tedis", "tedis");
        }
        tedis.get("tedis");
        long end = SystemTimer.currentTimeMillis();
        System.out.println("总共用时：" + (end - start) + "ms,平均用时：" + ((end - start) / 100) + "ms");
    }
}
