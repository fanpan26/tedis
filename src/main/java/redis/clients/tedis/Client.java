package redis.clients.tedis;


import java.util.List;

import static redis.clients.tedis.Command.*;

public class Client extends Connection implements Commands,ScriptingCommands {
    public Client() {
        super();
    }

    public Client(final String host, final int port) {
        super(host, port);
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
    public void set(final String key, final String value) {
        sendCommand(Command.SET, key, value);
    }

    @Override
    public void get(final String key) {
        sendCommand(GET, key);
    }

    @Override
    public void ping() {
        sendCommand(PING);
    }

    @Override
    public void publish(final String channel, final String message) {
        sendCommand(Command.PUBLISH, channel, message);
    }

    @Override
    public void subscribe(final String... channels) {
        sendCommand(SUBSCRIBE, channels);
    }

    @Override
    public void unSubscribe(String... channels) {
        sendCommand(UNSUBSCRIBE, channels);
    }

    @Override
    public void pSubscribe(String... channelPatterns) {
        sendCommand(PSUBSCRIBE, channelPatterns);
    }

    @Override
    public void pUnSubscribe(String... channelPatterns) {
        sendCommand(PUNSUBSCRIBE, channelPatterns);
    }

    @Override
    public void incr(final String key) {
        sendCommand(INCR, key);
    }

    @Override
    public void decr(final String key) {
        sendCommand(DECR, key);
    }

    @Override
    public void incrBy(final String key, long value) {
        sendCommand(INCRBY, key, String.valueOf(value));
    }

    @Override
    public void decrBy(final String key, long value) {
        sendCommand(DECRBY, key, String.valueOf(value));
    }

    @Override
    public void exists(final String key) {
        sendCommand(EXISTS, key);
    }

    @Override
    public void del(final String key) {
        sendCommand(DEL, key);
    }

    @Override
    public void quit() {
        sendCommand(QUIT);
    }

    @Override
    public void flush() {
        sendCommand(FLUSHDB);
    }

    @Override
    public Boolean scriptExists(String sha1) {
        return null;
    }

    @Override
    public List<Boolean> scriptExists(String... sha1) {
        return null;
    }

    @Override
    public Object eval(String script) {
        return eval(script, 0);
    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        return eval(script, keys.size(), getParams(keys, args));
    }

    @Override
    public Object eval(String script, int keyCount, String... params) {
        sendCommand(EVAL, getParams(script,keyCount, params));
        return getEvalReply();
    }

    @Override
    public Object evalsha(String sha1) {
        return null;
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return null;
    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        return null;
    }

    @Override
    public String scriptLoad(String script) {
        return null;
    }


    private String[] getParams(String script, int keyCount, String... args) {
        String[] res = new String[2 + args.length];
        res[0] = script;
        res[1] = String.valueOf(keyCount);
        if (args.length > 0) {
            System.arraycopy(args, 0, res, 2, args.length);
        }
        return res;
    }

    private String[] getParams(List<String> keys, List<String> args) {
        int keyCount = keys.size();
        int argCount = args.size();

        String[] params = new String[keyCount + argCount];


        for (int i = 0; i < keyCount; i++)
            params[i] = keys.get(i);

        for (int i = 0; i < argCount; i++)
            params[keyCount] = args.get(i);

        return params;
    }
}
