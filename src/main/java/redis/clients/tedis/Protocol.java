package redis.clients.tedis;

public final class Protocol {

    public static final byte DOLLAR_BYTE = '$';
    public static final byte ASTERISK_BYTE = '*';
    public static final byte PLUS_BYTE = '+';
    public static final byte MINUS_BYTE = '-';
    public static final byte COLON_BYTE = ':';

    public static final String CHARSET = "utf-8";
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 6379;

    public static final byte CR = '\r';
    public static final byte LF = '\n';

    public static final long DEFAULT_RESPONSE_TIMEOUT = 5000L;

    public static final String CLIENT_NAME_PREFIX = "tio-redis-client-";

    public static byte[] buildCommandBody(final ProtocolCommand cmd,String... args) {
        StringBuilder builder = new StringBuilder();
        builder.append('*')
                .append(1 + args.length);
        appendCrLf(builder)
                .append("$")
                .append(cmd.getName().length());
        appendCrLf(builder)
                .append(cmd.getName());
        appendCrLf(builder);
        for (String arg : args) {
            builder.append("$")
                    .append(SafeEncoder.encode(arg).length);
            appendCrLf(builder)
                    .append(arg);
            appendCrLf(builder);
        }
        return SafeEncoder.encode(builder.toString());
    }

    private static TedisOutputStream os = new TedisOutputStream();

    private static StringBuilder appendCrLf(StringBuilder builder){
        return builder.append('\r')
                .append('\n');
    }
}
