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
    public void zcountFloat(){
        String key = prepareData("zcount");
        long count = tedis.zcount(key,1f,5f);
        Assert.assertEquals(5,count);
    }
    @Test
    public void zcountDouble(){
        String key = prepareData("zcount");
        long count = tedis.zcount(key,1f,5f);
        Assert.assertEquals(5,count);
    }
    @Test
    public void zcountInt(){
        String key = prepareData("zcount");
        long count = tedis.zcount(key,1,5);
        Assert.assertEquals(5,count);
    }
    @Test
    public void zcountLong(){
        String key = prepareData("zcount");
        long count = tedis.zcount(key,1L,5L);
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

    @Test
    public void zlexcount(){
        String key=prepareData("zlexcount");
        long res =tedis.zlexcount(key,"value1","value6");
        Assert.assertEquals(6,res);
    }

    @Test
    public void zrangeWithoutScore() {
        String key = prepareData("zrange");
        List<String> res = tedis.zrange(key, 1, false);
        Assert.assertEquals(9, res.size());
    }

    @Test
    public void zrangeWithScore() {
        String key = prepareData("zrange");
        List<String> res = tedis.zrange(key, 1, true);
        Assert.assertEquals(18, res.size());
    }

    @Test
    public void zrangeStartEndWithScore() {
        String key = prepareData("zrange");
        List<String> res = tedis.zrange(key, 1,5, true);
        Assert.assertEquals(10, res.size());
    }

    @Test
    public void zrangeStartEndWithoutScore() {
        String key = prepareData("zrange");
        List<String> res = tedis.zrange(key, 1,5, false);
        Assert.assertEquals(5, res.size());
    }

    @Test
    public void zrankNull(){
        String key = generateKey("zrank");
        long rank = tedis.zrank(key,"value4");
        Assert.assertEquals(-1,rank);
    }

    @Test
    public void zrank(){
        String key = prepareData("zrank");
        long rank = tedis.zrank(key,"value4");
        Assert.assertEquals(4,rank);
    }

    @Test
    public void zrevrankNull(){
        String key = generateKey("zrevank");
        long rank = tedis.zrevrank(key,"value4");
        Assert.assertEquals(-1,rank);
    }

    @Test
    public void zrevrank(){
        String key = prepareData("zrevank");
        long rank = tedis.zrevrank(key,"value4");
        Assert.assertEquals(7,rank);
    }

    @Test
    public void zrangebyscoreWithScoreAndLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1, 3, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrangebyscoreWithScoreAndNoLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1, 3);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zrangebyscoreFloatWithScoreAndLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1f, 3f, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrangebyscoreFloatWithScoreAndNoLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1f, 3f);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zrangebyscoreLongWithScoreAndLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1L, 3L, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrangebyscoreLongWithScoreAndNoLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1L, 3L);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zrangebyscoreDoubleWithScoreAndLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1d, 3d, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrangebyscoreDoubleWithScoreAndNoLimit() {
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrangebyscore(key, 1d, 3d);
        Assert.assertEquals(3,res.size());
    }



    @Test
    public void zremNotExist(){
        String key = generateKey("zrem");
        int res = tedis.zrem(key,"test");
        Assert.assertEquals(0,res);
    }

    @Test
    public void zrem() {
        String key = prepareData("zrem");
        int res = tedis.zrem(key, "value1", "value2");
        Assert.assertEquals(2, res);
    }

    @Test
    public void zscoreNotExist(){
        String key = generateKey("zscore");
        float res = tedis.zscore(key,"value1");
        Assert.assertEquals(-1f,res,0);
    }

    @Test
    public void zscoreExist(){
        String key = prepareData("zscore");
        float res = tedis.zscore(key,"value5");
        Assert.assertEquals(5f,res,0);
    }

    @Test
    public void zrangebyscoreall(){
        String key = prepareData("zrangebyscore");
         List<String> res = tedis.zrangebyscore(key);
         Assert.assertEquals(10,res.size());
    }

    @Test
    public void zrevrangebyscoreall(){
        String key = prepareData("zrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key);
        Assert.assertEquals(10,res.size());
    }

    @Test
    public void zrevrangebyscoreWithScoreAndLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3, 1, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrevrangebyscoreWithScoreAndNoLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3, 1);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zrevrangebyscoreFloatWithScoreAndLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3f, 1f, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrevrangebyscoreFloatWithScoreAndNoLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3f, 1f);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zrevrangebyscoreLongWithScoreAndLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3L, 1L, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrevrangebyscoreLongWithScoreAndNoLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3L, 1L);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zrevrangebyscoreDoubleWithScoreAndLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3d, 1d, true, 2);
        Assert.assertEquals(4,res.size());
    }
    @Test
    public void zrevrangebyscoreDoubleWithScoreAndNoLimit() {
        String key = prepareData("zrevrangebyscore");
        List<String> res = tedis.zrevrangebyscore(key, 3d, 1d);
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zremrangeIntbyscore(){
        String key = prepareData("zremrangebyscore");
        int res = tedis.zremrangebyscore(key,1,3);
        Assert.assertEquals(3,res);
    }
    @Test
    public void zremrangeLongbyscore(){
        String key = prepareData("zremrangebyscore");
        int res = tedis.zremrangebyscore(key,1L,3L);
        Assert.assertEquals(3,res);
    }
    @Test
    public void zremrangeFloatbyscore(){
        String key = prepareData("zremrangebyscore");
        int res = tedis.zremrangebyscore(key,1f,3f);
        Assert.assertEquals(3,res);
    }
    @Test
    public void zremrangeDoublebyscore(){
        String key = prepareData("zremrangebyscore");
        int res = tedis.zremrangebyscore(key,1d,3d);
        Assert.assertEquals(3,res);
    }

    @Test
    public void zrangebylex(){
        String key = prepareData("zrangebylex");
        List<String> res = tedis.zrangebylex(key,"value3");
        Assert.assertEquals(3,res.size());
    }
    @Test
    public void zrangebylexMinMax(){
        String key = prepareData("zrangebylex");
        List<String> res = tedis.zrangebylex(key,"value1","value3");
        Assert.assertEquals(3,res.size());
    }

    @Test
    public void zrangebylexMinMaxContainsMinNotContainsMax(){
        String key = prepareData("zrangebylex");
        List<String> res = tedis.zrangebylex(key,"value1","value3",true,false);
        Assert.assertEquals(2,res.size());
    }

    @Test
    public void zrangebylexMinMaxNotContainsMinContainsMax(){
        String key = prepareData("zrangebylex");
        List<String> res = tedis.zrangebylex(key,"value1","value3",false,true);
        Assert.assertEquals(2,res.size());
    }

    @Test
    public void zrangebylexMinMaxNotContainsMinContainsMaxLimit(){
        String key = prepareData("zrangebylex");
        List<String> res = tedis.zrangebylex(key,"value1","value3",1,false,true);
        Assert.assertEquals("value2",res.get(0));
    }

    @Test
    public void zrangebylexMinMaxLimit(){
        String key = prepareData("zrangebylex");
        List<String> res = tedis.zrangebylex(key,"value1","value3",1);
        Assert.assertEquals("value1",res.get(0));
    }

    @Test
    public void zremrangebyrankInt(){
        String key =prepareData("zremrangebyrank");
        int res = tedis.zremrangebyrank(key,1,5);
        Assert.assertEquals(5,res);
    }

    @Test
    public void zremrangebyrankLong(){
        String key =prepareData("zremrangebyrank");
        int res = tedis.zremrangebyrank(key,1L,5L);
        Assert.assertEquals(5,res);
    }

    @Test
    public void zremrangebyrankLongNoStop(){
        String key =prepareData("zremrangebyrank");
        int res = tedis.zremrangebyrank(key,1L);
        Assert.assertEquals(9,res);
    }
    @Test
    public void zremrangebyrankIntNoStop(){
        String key =prepareData("zremrangebyrank");
        int res = tedis.zremrangebyrank(key,9);
        Assert.assertEquals(1,res);
    }
}
