package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import redis.clients.tedis.Tedis;
import redis.clients.tedis.TedisLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TedisTest {

    private Tedis tedis;
    private Tedis tedis1;

    @Before
    public void before() {
        tedis = new Tedis("192.168.1.225", 6379);
        tedis1=new Tedis("192.168.1.225", 6379);
    }

    @Test
    public void set() {
        String result = tedis.set("test", "test");
        Assert.assertEquals("OK", result);
    }

    @Test
    public void get() {
        String result = tedis.get("test");
        Assert.assertEquals("test", result);
    }

    @Test
    public void incr() {
        long res = tedis.incr("incr_key");
        Assert.assertEquals(true, res > 0);
    }

    @Test
    public void incrBy() {
        long res = tedis.incrBy("incr_key_by", 10);
        Assert.assertEquals(true, res >= 10);
    }

    @Test
    public void decr() {
        long res = tedis.decr("decr_key");
        Assert.assertEquals(true, res < 0);
    }

    @Test
    public void decrBy() {
        long res = tedis.decrBy("decr_key_by", 10);
        Assert.assertEquals(true, res <= -10);
    }

    @Test
    public void exists() {
        tedis.set("exist_key", "1");
        boolean res = tedis.exists("exist_key");
        Assert.assertEquals(true, res);
    }

    @Test
    public void del() {
        tedis.set("delKey","1");
        boolean res = tedis.del("delKey");
        Assert.assertEquals(true, res);
    }

    @Test
    public void ping(){
        tedis.ping();
    }

//    @Test
//    public void quit(){
//        tedis.ping();
//        tedis.quit();
//    }

    @Test
    public void selectDb(){
       String result = tedis.selectDb(1);
       Assert.assertTrue("OK".equals(result));
    }

    @Test
    public void eval(){
        String res = (String)tedis.eval("return 'hello world'");
        Assert.assertTrue("hello world".equals(res));
    }

//    @Test
//    public void getLock() {
//        TedisLock lock = tedis.getLock("myLockKey");
//        if (lock != null) {
//            System.out.println("获取锁成功");
//        }
//
//        boolean success = false;
//        while (!success) {
//            TedisLock lock1 = tedis1.getLock("myLockKey");
//            if (lock1 != null) {
//                System.out.println("获取锁成功");
//                success = true;
//            } else {
//                System.out.println("获取锁失败");
//            }
//        }
//    }

//    @Test
//    public void flush(){
//        tedis.flush();
//    }

    @Test
    public void expire() {
        String key = "test_expire";
        tedis.set(key, "value");
        boolean expired = tedis.expire(key, 3);
        Assert.assertTrue(expired);

        try {
            Thread.sleep(3000);
        }catch (InterruptedException ex){

        }
        String value = tedis.get(key);
        Assert.assertEquals(null, value);
    }

    @Test
    public void pexpire() {
        String key = "test_pexpire";
        tedis.set(key, "value");
        boolean expired = tedis.pexpire(key, 3000);
        Assert.assertTrue(expired);

        try {
            Thread.sleep(3000);
        }catch (InterruptedException ex){

        }
        String value = tedis.get(key);
        Assert.assertEquals(null, value);
    }

    @Test
    public void expireAt(){
        String key = "test_expireAt";
        tedis.set(key, "value");
        boolean expired = tedis.expireAt(key, System.currentTimeMillis()/1000+3);
        Assert.assertTrue(expired);

        try {
            Thread.sleep(3000);
        }catch (InterruptedException ex){

        }
        String value = tedis.get(key);
        Assert.assertEquals(null, value);
    }

    @Test
    public void pexpireAt(){
        String key = "test_expireAt";
        tedis.set(key, "value");
        boolean expired = tedis.pexpireAt(key, System.currentTimeMillis()/1000+3000);
        Assert.assertTrue(expired);

        try {
            Thread.sleep(3000);
        }catch (InterruptedException ex){

        }
        String value = tedis.get(key);
        Assert.assertEquals(null, value);
    }

    @Test
    public void getRange() {
        tedis.set("getRangeTest", "test123456789");
        String res = tedis.get("getRangeTest", 0, 5);
        Assert.assertEquals("test12", res);
    }

    @Test
    public void getset() {
        tedis.set("getsetTest", "test");
        String res = tedis.getset("getsetTest", "newtest");
        Assert.assertEquals("test", res);
    }

    @Test
    public void getbit(){
      int res =  tedis.getbit("getsetTest",5);
      Assert.assertEquals(1,res);
    }

    @Test
    public void setbit() {
        int res1 = tedis.setbit("setbitTest1", 6, true);
        Assert.assertEquals(true, res1 >= 0);
    }

    @Test
    public void getMany() {
        tedis.set("key1", "value1");
        tedis.set("key2", "value2");
        tedis.set("key3", "value3");

        List<String> results = tedis.get("key1", "key2", "key3");
        Assert.assertEquals(3,results.size());
    }

    @Test
    public void setex() {
        tedis.setex("setExKey", "test", 2, TimeUnit.SECONDS);
        try {
            Thread.sleep(2100);
        }catch (InterruptedException e){

        }
        boolean res = tedis.exists("setExKey");
        Assert.assertEquals(false,res);
    }

    @Test
    public void setnx(){
        tedis.setnx("setNxKey","value");
        tedis.setnx("setNxKey","value1");
        String res = tedis.get("setNxKey");
        Assert.assertEquals("value",res);
    }

    @Test
    public void msetnx(){
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

       int res = tedis.msetnx(keys,values);
       Assert.assertEquals(true,res>=0);
    }

    @Test
    public void strlen() {
        tedis.set("lenKey", "123456");
        int len = tedis.len("lenKey");
        Assert.assertEquals(6, len);
    }

    @Test
    public void setrange() {
        tedis.set("rangeKey", "123456");
        int res = tedis.set("rangeKey", "123456", 3);
        Assert.assertEquals(9, res);
    }

    @Test
    public void setMany(){
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

       String res = tedis.set(keys,values);
       Assert.assertEquals("OK",res);
    }

    @Test
    public void incrByfloat(){
      float value =  tedis.incrByfloat("floatKey"+System.currentTimeMillis(),1.265f);
      Assert.assertEquals(1.265f,value,3);
    }

    @Test
    public void append(){
        tedis.set("appendKey","123456");
        tedis.append("appendKey","789");
        Assert.assertEquals("123456789",tedis.get("appendKey"));
    }
}
