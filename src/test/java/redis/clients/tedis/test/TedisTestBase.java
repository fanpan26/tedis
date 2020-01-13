package redis.clients.tedis.test;

import redis.clients.tedis.Tedis;


public class TedisTestBase {
    protected Tedis tedis= new Tedis("10.179.132.197", 6379);

    //protected Tedis tedis= new Tedis("127.0.0.1", 6379);
    protected String generateKey(String prefix) {
        long ran = tedis.incrby("ranKey",1);
        return prefix+"_"+ ran;
    }

}
