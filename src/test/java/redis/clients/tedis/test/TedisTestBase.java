package redis.clients.tedis.test;

import redis.clients.tedis.Tedis;


public class TedisTestBase {
    //protected Tedis tedis= new Tedis("192.168.187.129", 6379);

    protected Tedis tedis= new Tedis("192.168.1.225", 6379);
    protected String generateKey(String prefix) {
        long ran = tedis.incrby("ranKey",1);
        return prefix+"_"+ ran;
    }

}
