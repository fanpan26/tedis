package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class ListTest extends TedisTestBase {



    private String prepareData(String type) {
        final String key ="tedis:"+ System.currentTimeMillis() + type;
        int res = tedis.lpush(key, "value1", "value2", "value3", "value4");
        Assert.assertEquals(4, res);
        return key;
    }

    @Test
    public void lpush(){
        final String key =System.currentTimeMillis()+"lpushKey";
        int res = tedis.lpush(key,"panzi","xiaoming","zhangsan");
        Assert.assertEquals(3,res);
    }

    @Test
    public void blpop() {
        String key = prepareData("blpop");
        String res = tedis.blpop(2, key);
        Assert.assertEquals("value4", res);
    }

    @Test
    public void brpop(){
        String key = prepareData("brpop");
        String res = tedis.brpop(2, key);
        Assert.assertEquals("value1", res);
    }

    @Test
    public void lindex(){
        String key = prepareData("lindex");
        String res = tedis.lindex(key,2);
        Assert.assertEquals("value2", res);
    }

    @Test
    public void llen(){
        String key = prepareData("llen");
        long len = tedis.llen(key);
        Assert.assertEquals(4,len);
    }

    @Test
    public void lpop(){
        String key = prepareData("lpop");
        String res = tedis.lpop(key);
        Assert.assertEquals("value4", res);
    }
    @Test
    public void lpopNotExists(){
        String res = tedis.lpop("tedis:lpopNotExists");
        Assert.assertNull(res);
    }

    @Test
    public void rpop(){
        String key = prepareData("rpop");
        String res = tedis.rpop(key);
        Assert.assertEquals("value1", res);
    }

    @Test
    public void rpush(){
        final String key =System.currentTimeMillis()+"rpushKey";
        int res = tedis.rpush(key,"panzi","xiaoming","zhangsan");
        Assert.assertEquals(3,res);
    }

    @Test
    public void rpushxNotExists(){
        final String key =System.currentTimeMillis()+"rpushxKey";
        int res = tedis.rpushx(key,"panzi");
        Assert.assertEquals(0,res);
    }

    @Test
    public void rpushxExists() {
        String key = prepareData("rpushx");
        int res = tedis.rpushx(key,"test1");
        Assert.assertEquals(5,res);
    }

    @Test
    public void lpushxNotExists(){
        final String key =System.currentTimeMillis()+"lpushx";
        int res = tedis.lpushx(key,"panzi");
        Assert.assertEquals(0,res);
    }

    @Test
    public void lpushxExists() {
        String key = prepareData("lpushx");
        int res = tedis.lpushx(key,"test1");
        Assert.assertEquals(5,res);
    }

    @Test
    public void ltrim() {
        String key = prepareData("ltrim");
        String res = tedis.ltrim(key, 0, 2);
        long len = tedis.llen(key);
        Assert.assertEquals(3,len);
        Assert.assertEquals("OK", res);
    }

    @Test
    public void lset(){
        String key = prepareData("lset");
        String res = tedis.lset(key,0,"valueset");
        Assert.assertEquals("OK", res);
    }

    @Test
    public void lremExists(){
        String key = prepareData("lrem");
       int res =  tedis.lrem(key,1,"value4");
       Assert.assertEquals(1,res);
    }
    @Test
    public void lremNotExists(){
        String key = prepareData("lrem");
        int res =  tedis.lrem(key,1,"value5");
        Assert.assertEquals(0,res);
    }

    @Test
    public void lrangeExists(){
        String key = prepareData("lrange");
        List<String> res =tedis.lrange(key,0,2);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void lrangeNotExists(){
        List<String> res =tedis.lrange("lrangeNotExists",0,2);
        Assert.assertEquals(0,res.size());
    }

    @Test
    public void linsertExists() {
        String key = prepareData("linsert");
        int res = tedis.linsert(key, true, "value1", "preValue1");
        Assert.assertEquals(5, res);
    }
    @Test
    public void linsertNotExists() {
        int res = tedis.linsert("linsertNotExists", true, "value1", "preValue1");
        Assert.assertEquals(0, res);
    }

    @Test
    public void rpoplpush() {
        String key = prepareData("rpoplpush");
        String res = tedis.rpoplpush(key, key+"des");
        Assert.assertEquals("value1", res);
        long len = tedis.llen(key+"des");
         Assert.assertEquals(1, len);
    }

    @Test
    public void brpoplpushExists() {
        String key = prepareData("brpoplpush");
        String res = tedis.brpoplpush(key, key+"des",2);
        Assert.assertEquals("value1", res);
        long len = tedis.llen(key+"des");
        Assert.assertEquals(1, len);
    }

    @Test
    public void brpoplpushNotExists() {
        String res = tedis.brpoplpush("tedis:brpoplpushNotExists", "tedis:brpoplpushNotExistsdes",2);
        Assert.assertNull(res);
    }


//    String brpoplpush(String source,String destination,long timeout);
}
