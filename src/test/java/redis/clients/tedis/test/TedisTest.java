package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.tedis.Tedis;
import redis.clients.tedis.TedisLock;

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
        boolean res = tedis.del("decr_key");
        Assert.assertEquals(true, res);
    }

    @Test
    public void ping(){
        tedis.ping();
    }

    @Test
    public void quit(){
        tedis.ping();
        tedis.quit();
    }

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

    @Test
    public void flush(){
        tedis.flush();
    }

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
        boolean expired = tedis.expireAt(key, System.currentTimeMillis()/1000+3000);
        Assert.assertTrue(expired);

        try {
            Thread.sleep(3000);
        }catch (InterruptedException ex){

        }
        String value = tedis.get(key);
        Assert.assertEquals(null, value);
    }

}
