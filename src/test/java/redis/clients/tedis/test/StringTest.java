package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StringTest extends TedisTestBase{

    @Test
    public void getRange() {
        String key = generateKey("getrange");
        tedis.set(key, "test123456789");
        String res = tedis.get(key, 0, 5);
        Assert.assertEquals("test12", res);
    }

    @Test
    public void getset() {
        String key = generateKey("getset");
        tedis.set(key, "test");
        String res = tedis.getset(key, "newtest");
        Assert.assertEquals("test", res);
    }

    @Test
    public void getbit() {
        String key = generateKey("getbit");
        tedis.setbit(key,5,true);
        int res = tedis.getbit(key, 5);
        Assert.assertEquals(1, res);
    }

    @Test
    public void setbit() {
        String key = generateKey("setbit");
        int res1 = tedis.setbit(key, 6, true);
        Assert.assertEquals(true, res1 >= 0);
    }

    @Test
    public void getMany() {
        tedis.set("key1", "value1");
        tedis.set("key2", "value2");
        tedis.set("key3", "value3");

        List<String> results = tedis.get("key1", "key2", "key3");
        Assert.assertEquals(3, results.size());
    }

    @Test
    public void setex() {
        String key = generateKey("setex");
        String res = tedis.setex(key, "test", 2, TimeUnit.SECONDS);
        Assert.assertEquals("OK", res);
    }

    @Test
    public void setnx() {
        tedis.setnx("setNxKey", "value");
        tedis.setnx("setNxKey", "value1");
        String res = tedis.get("setNxKey");
        Assert.assertEquals("value", res);
    }

    @Test
    public void msetnx() {
        List<String> keys = new ArrayList<>();
        keys.add("key1111");
        keys.add("key2222");
        keys.add("key3333");
        keys.add("key4444");
        List<String> values = new ArrayList<>();
        values.add("value1");
        values.add("value2");
        values.add("value3");
        values.add("value4");

        int res = tedis.msetnx(keys, values);
        Assert.assertEquals(true, res >= 0);
    }

    @Test
    public void strlen() {
        String key  =generateKey("strlen");
        tedis.set(key, "123456");
        int len = tedis.len(key);
        Assert.assertEquals(6, len);
    }

    @Test
    public void setrange() {
        String key = generateKey("setrange");
        tedis.set(key, "123456");
        int res = tedis.set(key, "123456", 3);
        Assert.assertEquals(9, res);
    }

    @Test
    public void setMany() {
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

        String res = tedis.set(keys, values);
        Assert.assertEquals("OK", res);
    }

    @Test
    public void append() {
        String key = generateKey("append");
        tedis.set(key, "123456");
        tedis.append(key, "789");
        Assert.assertEquals("123456789", tedis.get(key));
    }

    @Test
    public void set() {
        String key = generateKey("set");
        String result = tedis.set(key, "test");
        Assert.assertEquals("OK", result);
    }

    @Test
    public void get() {
        String key = generateKey("get");
        tedis.set(key,"testValue");
        String res = tedis.get(key);
        Assert.assertEquals("testValue", res);
    }

    @Test
    public void incr() {
        String key =generateKey("incr");
        long res = tedis.incr(key);
        Assert.assertEquals(true, res > 0);
    }

    @Test
    public void incrBy() {
        String key = generateKey("incrby");
        long res = tedis.incrby(key, 10);
        Assert.assertEquals(true, res >= 10);
    }

    @Test
    public void decr() {
        String key = generateKey("decr");
        long res = tedis.decr(key);
        Assert.assertEquals(true, res < 0);
    }

    @Test
    public void decrBy() {
        String key = generateKey("decrby");
        long res = tedis.decrby(key, 1);
        Assert.assertEquals(-1, res);
    }

    @Test
    public void exists() {
        String key = generateKey("exists");
        tedis.set(key, "1");
        boolean res = tedis.exists(key);
        Assert.assertEquals(true, res);
    }

    @Test
    public void del() {
        String key = generateKey("del");
        tedis.set(key, "1");
        boolean res = tedis.del(key);
        Assert.assertEquals(true, res);
    }

}
