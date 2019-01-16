package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.tedis.Tedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HashTest {

    private Tedis tedis;

    @Before
    public void before() {
        tedis = new Tedis("192.168.1.225", 6379);
    }

    @Test
    public void hashset() {
        int res = tedis.hset("user", "name1", "panzi1");
        Assert.assertEquals(true, res >= 0);
    }

    @Test
    public void hashExist() {
        tedis.hset("user", "existkey", "value");
        boolean res = tedis.hexists("user", "existkey");
        Assert.assertEquals(true, res);
    }

    @Test
    public void hashGet() {
        tedis.hset("user", "getkey", "value");
        String res = tedis.hget("user", "getkey");
        Assert.assertEquals("value", res);
    }

    @Test
    public void hgetAll() {
        tedis.hset("user1", "f1", "v1");
        tedis.hset("user1", "f2", "v2");
        tedis.hset("user1", "f3", "v3");
        java.util.Map<String, String> map = tedis.hget("user1");
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void hashDel() {
        tedis.hset("user", "delkey", "value");
        boolean res = tedis.hdel("user", "delkey");
        Assert.assertEquals(true, res);
    }

    @Test
    public void hincrBy() {
        long res = tedis.hincrBy("incryKey", "t1" + System.currentTimeMillis());
        Assert.assertEquals(1L, res);
    }

    @Test
    public void hincrByValue() {
        long res = tedis.hincrBy("incryKey", "t1" + System.currentTimeMillis(), 10L);
        Assert.assertEquals(10L, res);
    }

    @Test
    public void hincrByFloat() {
        float res = tedis.hincrByFloat("incryKey", "t2" + System.currentTimeMillis());
        Assert.assertEquals(1.0f, res, 1);
    }

    @Test
    public void hincrByFloatValue() {
        float res = tedis.hincrByFloat("incryKey", "t2" + System.currentTimeMillis(), 10.2f);
        Assert.assertEquals(10.2f, res, 1);
    }

    @Test
    public void hkeys(){
        tedis.hset("user1", "f1", "v1");
        tedis.hset("user1", "f2", "v2");
        tedis.hset("user1", "f3", "v3");

       List<String> res = tedis.hkeys("user1");
       Assert.assertEquals(3,res.size());
    }

    @Test
    public void hlen(){
        tedis.hset("user2", "f1", "v1");
        tedis.hset("user2", "f2", "v2");
        tedis.hset("user2", "f3", "v3");

        int res = tedis.hlen("user2");
        Assert.assertEquals(3,res);
    }

    @Test
    public  void hmget(){
        tedis.hset("user3", "f1", "v1");
        tedis.hset("user3", "f2", "v2");
        tedis.hset("user3", "f3", "v3");

       List<String> res = tedis.hget("user3","f1","f2");
        Assert.assertEquals(2,res.size());
    }

    @Test
    public void hmset(){
        List<String> keys = new ArrayList<>();
        keys.add("key1");
        keys.add("key2");
        keys.add("key3");
        keys.add("key4");
        List<String> values = new ArrayList<>();
        values.add("value1");
        values.add("value2");
        values.add("value3");
        values.add("value4");

       String res = tedis.hset("user4",keys,values);

        Assert.assertEquals("OK",res);

    }

    @Test
    public void hvals(){
        tedis.hset("user5", "f1", "v1");
        tedis.hset("user5", "f2", "v2");
        tedis.hset("user5", "f3", "v3");

        List<String> res = tedis.hvals("user5");
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void hsetnx() {
        String randomKey = "f1" + System.currentTimeMillis();
        boolean res1 = tedis.hsetnx("user6", randomKey, "v1");
        boolean res2 = tedis.hsetnx("user6", randomKey, "v2");

        Assert.assertEquals(true,res1);
        Assert.assertEquals(false,res2);
    }
}
