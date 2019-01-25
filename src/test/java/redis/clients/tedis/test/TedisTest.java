package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Test;

public class TedisTest extends TedisTestBase{

    @Test
    public void ping() {
        tedis.ping();
    }

//    @Test
//    public void quit(){
//        tedis.ping();
//        tedis.quit();
//    }

    @Test
    public void selectDb() {
        String result = tedis.selectDb(1);
        Assert.assertTrue("OK".equals(result));
    }

    @Test
    public void eval() {
        String res = (String) tedis.eval("return 'hello world'");
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
       String res = tedis.flush();
       Assert.assertEquals("OK",res);
    }

    @Test
    public void expire() {
        String key = "test_expire";
        tedis.set(key, "value");
        boolean expired = tedis.expire(key, 3);
        Assert.assertTrue(expired);
    }

    @Test
    public void pexpire() {
        String key = "test_pexpire";
        tedis.set(key, "value");
        boolean expired = tedis.pexpire(key, 3000);
        Assert.assertTrue(expired);
    }

    @Test
    public void expireAt() {
        String key = "test_expireAt";
        tedis.set(key, "value");
        boolean expired = tedis.expireAt(key, System.currentTimeMillis() / 1000 + 3);
        Assert.assertTrue(expired);
    }

    @Test
    public void pexpireAt() {
        String key = "test_expireAt";
        tedis.set(key, "value");
        boolean expired = tedis.pexpireAt(key, System.currentTimeMillis() / 1000 + 3000);
        Assert.assertTrue(expired);
    }


}
