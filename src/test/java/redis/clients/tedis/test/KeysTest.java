package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class KeysTest extends TedisTestBase {


    @Test
    public  void dump(){
        String key = generateKey("dump");
        tedis.set(key,"value");
        tedis.dump(key);
    }

    @Test
    public  void moveNotExists(){
        String key = generateKey("move");
        boolean res =  tedis.move(key,1);
        Assert.assertTrue(!res);
    }
    @Test
    public  void moveExists(){
        String key = generateKey("move");
        tedis.set(key,"value1");
        boolean res =  tedis.move(key,1);
        Assert.assertTrue(res);
    }

    @Test
    public  void persistNotExists(){
        String key =generateKey("persist");
        boolean res = tedis.persist(key);
        Assert.assertTrue(!res);
    }
    @Test
    public  void persistExists() {
        String key = generateKey("persist");
        tedis.setex(key, "value",1000, TimeUnit.SECONDS);
        boolean res = tedis.persist(key);
        Assert.assertTrue(res);
    }

    @Test
    public  void pttl(){
        String key = generateKey("pttl");
        tedis.setex(key, "value",1000, TimeUnit.SECONDS);
        long res = tedis.pttl(key);
        Assert.assertTrue(res>999000);
    }

    @Test
    public  void pttlNotExists(){
        String key = generateKey("pttl");
        long res = tedis.pttl(key);
        Assert.assertEquals(-2,res);
    }

    @Test
    public  void ttl(){
        String key = generateKey("ttl");
        tedis.setex(key, "value",1000, TimeUnit.SECONDS);
        long res = tedis.ttl(key);
        Assert.assertTrue(res>990);
    }

    @Test
    public  void ttlNotExists(){
        String key = generateKey("ttl");
        long res = tedis.ttl(key);
        Assert.assertEquals(-2,res);
    }

    @Test
    public  void randomkey(){
        String res = tedis.randomkey();
        Assert.assertNotNull(res);
    }
    @Test
    public  void rename() {
        String key = generateKey("rename");
        tedis.set(key, "value");
        String res = tedis.rename(key, "newkey");
        Assert.assertEquals("OK", res);
    }
    @Test
    public  void renamenx(){
        String key = generateKey("renamenx");
        String keyNew = generateKey("renamenx_new");
        tedis.set(key, "value");
        tedis.set(keyNew, "value");
        String res = tedis.renamenx(key, keyNew);
        Assert.assertNull(res);
    }
    @Test
    public  void typeSet(){
        String key = generateKey("type");
        tedis.sadd(key,"value");
        String type = tedis.type(key);
        Assert.assertEquals("set",type);
    }

    @Test
    public  void typeString(){
        String key = generateKey("type");
        tedis.set(key,"value");
        String type = tedis.type(key);
        Assert.assertEquals("string",type);
    }

    @Test
    public  void typeNotExists(){
        String key = generateKey("type");
        String type = tedis.type(key);
        Assert.assertEquals("none",type);
    }

    @Test
    public  void typeHash(){
        String key = generateKey("type");
        tedis.hset(key,"field","value");
        String type = tedis.type(key);
        Assert.assertEquals("hash",type);
    }

    @Test
    public  void typeList(){
        String key = generateKey("type");
        tedis.lpush(key,"value1","value2");
        String type = tedis.type(key);
        Assert.assertEquals("list",type);
    }

    @Test
    public  void typeSortedSet(){
        String key = generateKey("type");
        tedis.zadd(key,10,"value1");
        String type = tedis.type(key);
        Assert.assertEquals("zset",type);
    }

    @Test
    public  void keys() {
       List<String> keys = tedis.keys("zrem*");
       Assert.assertNotNull(keys);
    }


}
