package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.tedis.ScanResult;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/1/20 8:46
 * @project tedis
 */
public class SetTest extends TedisTestBase {
    @Test
    public void sadd() {
        String key = generateKey("sadd");
        long res = tedis.sadd(key, "1", "2", "3", "4");
        Assert.assertEquals(4, res);
    }

    @Test
    public void scard() {
        String key = generateKey("scard");
        tedis.sadd(key, "1", "2", "3", "4");
        long res = tedis.scard(key);
        Assert.assertEquals(4, res);
    }

    @Test
    public void sdiff() {
        String key1 = generateKey("sdiff1");
        String key2 = generateKey("sdiff2");
        tedis.sadd(key1, "1", "2", "3", "4");
        tedis.sadd(key2, "1", "2", "3", "4", "5", "6");

        List<String> res = tedis.sdiff(key2, key1);
        Assert.assertEquals(2, res.size());
    }

    @Test
    public void sdiffstore() {
        String key1 = generateKey("sdiff1");
        String key2 = generateKey("sdiff2");
        String key3 = generateKey("sdiff3");
        tedis.sadd(key1, "1", "2", "3", "4");
        tedis.sadd(key2, "1", "2", "3", "4", "5", "6");
        long res = tedis.sdiffstore(key3, key2, key1);
        Assert.assertEquals(2, res);
    }

    @Test
    public void sinter() {
        String key1 = generateKey("sinter1");
        String key2 = generateKey("sinter2");
        tedis.sadd(key1, "1", "2", "3", "4");
        tedis.sadd(key2, "1", "2", "3", "4", "5", "6");

        List<String> res = tedis.sinter(key2, key1);
        Assert.assertEquals(4, res.size());
    }

    @Test
    public void sinterstore() {
        String key1 = generateKey("sinter1");
        String key2 = generateKey("sinter2");
        String key3 = generateKey("sinter3");
        tedis.sadd(key1, "1", "2", "3", "4");
        tedis.sadd(key2, "1", "2", "3", "4", "5", "6");
        long res = tedis.sinterstore(key3, key2, key1);
        Assert.assertEquals(4, res);
    }

    @Test
    public void sismember() {
        String key = generateKey("sismember");
        tedis.sadd(key, "1", "2");
        boolean exist1 = tedis.sismember(key, "1");
        boolean exist2 = tedis.sismember(key, "5");
        Assert.assertTrue(exist1);
        Assert.assertTrue(!exist2);
    }

    @Test
    public void smembers() {
        String key = generateKey("smembers");
        tedis.sadd(key, "1", "2", "3");
        List<String> res = tedis.smembers(key);
        Assert.assertEquals(3, res.size());
    }

    @Test
    public void smove() {
        String key = generateKey("smove");
        String destination = generateKey("smove1");
        tedis.sadd(key, "1", "2", "3");
        tedis.smove(key, destination, "1");

        long res = tedis.scard(key);
        long res1 = tedis.scard(destination);
        Assert.assertEquals(2, res);
        Assert.assertEquals(1, res1);
    }

    @Test
    public void spopExists() {
        String key = generateKey("spop");
        tedis.sadd(key, "1", "2", "3", "4");
        String res = tedis.spop(key);
        Assert.assertTrue(res != null);
    }

    @Test
    public void spopNotExists() {
        String key = generateKey("spop");
        String res = tedis.spop(key);
        Assert.assertTrue(res == null);
    }

    @Test
    public void srandmembers() {
        String key = generateKey("srandmembers");
        tedis.sadd(key, "1", "2", "3", "4", "5");
        List<String> res = tedis.srandmember(key, 6);
        Assert.assertEquals(5, res.size());
    }

    @Test
    public void sremExists() {
        String key = generateKey("srem");
        tedis.sadd(key, "1", "2", "3", "4");
        int res = tedis.srem(key, "1");
        Assert.assertEquals(1, res);
    }

    @Test
    public void sremNotExists() {
        String key = generateKey("srem");
        tedis.sadd(key, "1", "2", "3", "4");
        int res = tedis.srem(key, "9");
        Assert.assertEquals(0, res);
    }

    @Test
    public void sunion() {
        String key1 = generateKey("sunion");
        String key2 = generateKey("sunion1");

        tedis.sadd(key1, "1", "2", "3", "4");
        tedis.sadd(key2, "5", "6", "7", "8");

        List<String> res = tedis.sunion(key1, key2);
        Assert.assertEquals(8, res.size());
    }

    @Test
    public void sunionstore() {
        String key1 = generateKey("sunion");
        String key2 = generateKey("sunion1");
        String key3 = generateKey("sunion2");

        tedis.sadd(key1, "1", "2", "3", "4");
        tedis.sadd(key2, "5", "6", "7", "8");

        long res = tedis.sunionstore(key3, key1, key2);
        Assert.assertEquals(8, res);
    }

    @Test
    public void sscan() {
        String key = generateKey("sscan");
        tedis.sadd(key, "hello", "hi", "no", "stop");

        ScanResult res = tedis.sscan(key, 0, "h*", 0);
        Assert.assertEquals(2, res.getObjects().size());
    }

    @Test
    public void sscanLength() {
        String key = generateKey("sscan");
        tedis.sadd(key, "hello", "hi", "no", "stop");

        ScanResult result = null;
        do {
            result = tedis.sscan(key, result == null ? 0 : result.getCursor(), "h*", 1);
        } while (result.hasRemaining());
    }
}
