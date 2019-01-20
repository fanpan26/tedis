package redis.clients.tedis.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fyp
 * @crate 2019/1/20 15:34
 * @project tedis
 */
public class SortedSetTest extends TedisTestBase {

    private String prepareData(String prefix){
        String key = generateKey(prefix);
        tedis.zadd(key, 1f, "value1");
        tedis.zadd(key, 2f, "value2");
        tedis.zadd(key, 3f, "value3");
        tedis.zadd(key, 4f, "value4");
        tedis.zadd(key, 5f, "value5");
        tedis.zadd(key, 6f, "value6");
        tedis.zadd(key, 7f, "value7");
        tedis.zadd(key, 8f, "value8");
        tedis.zadd(key, 9f, "value9");
        tedis.zadd(key, 10f, "value10");
        return key;
    }

    @Test
    public void zadd() {
        String key = generateKey("zadd");
        int res = tedis.zadd(key, 1.56f, "value1");
        Assert.assertEquals(1, res);
    }

    @Test
    public void zaddMany(){
        String key = generateKey("zaddMany");
        List<String> scores = new ArrayList<>();
        scores.add("1");
        scores.add("2");
        scores.add("3");
        scores.add("4");
        scores.add("5");
        List<String> members = new ArrayList<>();
        members.add("value1");
        members.add("value2");
        members.add("value3");
        members.add("value4");
        members.add("value5");
       int res =  tedis.zadd(key,scores,members);
        Assert.assertEquals(5,res);
    }

    @Test
    public void zcount(){
        String key = prepareData("zcount");
        long count = tedis.zcount(key,1f,5f);
        Assert.assertEquals(5,count);
    }
    @Test
    public void zincrby(){
        String key = prepareData("zcount");
        long res  = tedis.zincrby(key,1L,"value1");
        Assert.assertEquals(2,res);
    }

    @Test
    public void zincrbyDouble(){
        String key = prepareData("zcount");
        double res  = tedis.zincrby(key,1.5d,"value1");
        Assert.assertEquals(2.5,res,1);
    }
    @Test
    public void zincrbyInt(){
        String key = prepareData("zcount");
        int res  = tedis.zincrby(key,1,"value1");
        Assert.assertEquals(2,res);
    }
    @Test
    public void zincrbyFloat(){
        String key = prepareData("zcount");
        float res  = tedis.zincrby(key,1.5f,"value1");
        Assert.assertEquals(2.5f,res,1);
    }

    @Test
    public void zcard(){
        String key = prepareData("zcard");
        long res = tedis.zcard(key);
        Assert.assertEquals(10,res);
    }
//
//    @Test
//    public void zlexcount(){
//        String key=prepareData("zlexcount");
//        long res
//    }

}
