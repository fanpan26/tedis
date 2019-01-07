package redis.clients.tedis;

import java.util.Locale;

public enum Command implements ProtocolCommand{
    SET,GET,PING,SUBSCRIBE,PUBLISH,MESSAGE,UNSUBSCRIBE,PMESSAGE,PSUBSCRIBE,PUNSUBSCRIBE,PONG,
    DECRBY, DECR, INCRBY, INCR;

    private final String name;
    public final byte[] raw;
    Command() {
        name = this.name();
        raw = SafeEncoder.encode(this.name().toLowerCase(Locale.ENGLISH));
    }

    @Override
    public String getName() {
        return name;
    }

}