package redis.clients.tedis;

public interface TedisCommands {
    /**
     * 设置缓存
     * */
    String set(String key,String value);
    /**
     * 获取缓存
     * */
    String get(String key);

    String ping();
}
