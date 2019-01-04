package redis.clients.tedis;


import static redis.clients.tedis.Command.*;

public class Client extends Connection implements Commands{
    public Client(){
        super();
    }

    public Client(final String host,final int port){
        super(host,port);
    }

    @Override
    public void connect() throws Exception {
        if (!isConnected()) {
            super.connect();
//            if (password != null) {
//                auth(password);
//                getStatusCodeReply();
//            }
//            if (db > 0) {
//                select(db);
//                getStatusCodeReply();
//            }
        }
    }

    @Override
    public void set(final String key,final String value) {
        sendCommand(Command.SET, key, value);
    }

    @Override
    public void get(final String key) {
        sendCommand(GET, key);
    }

    @Override
    public void  ping(){
        sendCommand(PING);
    }

    @Override
    public void publish(final String channel, final String message) {
        sendCommand(Command.PUBLISH,channel,message);
    }

    @Override
    public void subscribe(final String... channels) {
        sendCommand(SUBSCRIBE,channels);
    }

    @Override
    public void unSubscribe(String... channels) {
        sendCommand(UNSUBSCRIBE,channels);
    }

    @Override
    public void pSubscribe(String... channelPatterns) {
        sendCommand(PSUBSCRIBE, channelPatterns);
    }

    @Override
    public void pUnSubscribe(String... channelPatterns) {
        sendCommand(PUNSUBSCRIBE,channelPatterns);
    }
}
