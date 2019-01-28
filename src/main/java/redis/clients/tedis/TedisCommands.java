package redis.clients.tedis;


public interface TedisCommands extends KeyCommands,
        StringCommands,
        SetCommands,
        HashCommands,
        ListCommands,
        SortedSetCommands,
        PubSubCommands{

    String ping();
    void quit();
    String flush();
    String selectDb(int db);
}
