package redis.clients.tedis;

import java.util.Locale;

public enum Keyword {
    AGGREGATE, ALPHA, ASC, BY, DESC, GET, LIMIT, MESSAGE, NO, NOSORT, PMESSAGE, PSUBSCRIBE,
    PUNSUBSCRIBE, OK, ONE, QUEUED, SET, STORE, SUBSCRIBE, UNSUBSCRIBE, WEIGHTS, WITHSCORES,
    RESETSTAT, REWRITE, RESET, FLUSH, EXISTS, LOAD, KILL, LEN, REFCOUNT, ENCODING, IDLETIME,
    GETNAME, SETNAME, LIST, MATCH, COUNT, PING, PONG, UNLOAD, REPLACE, KEYS, PAUSE;

    public final byte[] raw;

    Keyword() {
        raw = SafeEncoder.encode(this.name().toLowerCase(Locale.ENGLISH));
    }
}
