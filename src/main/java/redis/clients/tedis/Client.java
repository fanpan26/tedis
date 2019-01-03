package redis.clients.tedis;


public class Client extends Connection {
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

    public void set(final String key,final String value) {
        sendCommand(Protocol.Command.SET, key, value);
    }

    public void get(final String key) {
        sendCommand(Protocol.Command.GET, key);
    }

    public void  ping(){
        sendCommand(Protocol.Command.PING);
    }
}
