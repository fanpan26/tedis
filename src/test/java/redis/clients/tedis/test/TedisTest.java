package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.tedis.Tedis;

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
    public void eval(){
        String res = (String)tedis.eval("return 'hello world'");
        Assert.assertTrue("hello world".equals(res));
    }

    @Test
    public void getLock() {
        tedis.getLock("myLockKey");
    }

    @Test
    public void flush(){
        tedis.flush();
    }

}
