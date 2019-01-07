package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.tedis.Tedis;

public class TedisTest {

    private Tedis tedis;

    @Before
    public void before() {
        tedis = new Tedis("192.168.1.225", 6379);
    }

    @Test
    public void set() {
        String result = tedis.set("test", "test");
        Assert.assertEquals("OK", result);
    }

    @Test
    public void get(){
        String result = tedis.get("test");
        Assert.assertEquals("test", result);
    }

    @Test
    public void incr(){
        long res = tedis.incr("incr_key");
        Assert.assertEquals(true,res>0);
    }

    @Test
    public void incrBy(){
        long res = tedis.incrBy("incr_key_by",10);
        Assert.assertEquals(true,res>=10);
    }

    @Test
    public void decr(){
        long res = tedis.decr("decr_key");
        Assert.assertEquals(true,res<0);
    }

    @Test
    public void decrBy(){
        long res = tedis.decrBy("decr_key_by",10);
        Assert.assertEquals(true,res<=-10);
    }
}
