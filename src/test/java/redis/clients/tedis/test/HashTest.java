package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.tedis.Tedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HashTest extends TedisTestBase{

    @Test
    public void hashset() {
        String key = generateKey("hashset");
        int res = tedis.hset(key, "name1", "panzi1");
        Assert.assertEquals(true, res >= 0);
    }

    @Test
    public void hashExist() {
        String key = generateKey("hashexist");
        tedis.hset(key, "existkey", "value");
        boolean res = tedis.hexists(key, "existkey");
        Assert.assertEquals(true, res);
    }

    @Test
    public void hashGet() {
        String key = generateKey("hashget");
        tedis.hset(key, "getkey", "value");
        String res = tedis.hget(key, "getkey");
        Assert.assertEquals("value", res);
    }

    @Test
    public void hgetAll() {
        String key = generateKey("hgetall");
        tedis.hset(key, "f1", "v1");
        tedis.hset(key, "f2", "v2");
        tedis.hset(key, "f3", "v3");
        java.util.Map<String, String> map = tedis.hget(key);
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void hashDel() {
        String key = generateKey("hdel");
        tedis.hset(key, "delkey", "value");
        boolean res = tedis.hdel(key, "delkey");
        Assert.assertEquals(true, res);
    }

    @Test
    public void hincrBy() {
        String key = generateKey("hincrby");
        long res = tedis.hincrBy(key, "t1" );
        Assert.assertEquals(1L, res);
    }

    @Test
    public void hincrByValue() {
        String key = generateKey("hincrby");
        long res = tedis.hincrBy(key, "t1", 10L);
        Assert.assertEquals(10L, res);
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
        String key = generateKey("hsetnx");
        String key1 = generateKey("hsetnx");
        boolean res1 = tedis.hsetnx(key1, key, "v1");
        boolean res2 = tedis.hsetnx(key1, key, "v2");

        Assert.assertEquals(true,res1);
        Assert.assertEquals(false,res2);
    }
}
