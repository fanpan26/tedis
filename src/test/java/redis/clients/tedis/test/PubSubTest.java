package redis.clients.tedis.test;


import org.junit.Test;

public class PubSubTest extends TedisTestBase{

    @Test
    public void subscribe(){
        tedis.subscribe(new MyPubSub(),"channel1");
    }
}
