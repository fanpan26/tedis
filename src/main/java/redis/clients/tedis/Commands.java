package redis.clients.tedis;

public interface Commands {
    void set(String key,String value);
    void get(String key);
}
