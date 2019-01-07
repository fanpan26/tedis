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
        //*[num]
        builder.append('*')
                //命令数（1） + 参数的个数
                .append(1 + args.length);
        appendCrLf(builder)
                //命令长度  $[cmd_length]
                .append("$")

                .append(cmd.getName().length());
        appendCrLf(builder)
                //命令内容 cmd
                .append(cmd.getName());
        appendCrLf(builder);

        //遍历参数，按照 $[num]\r\n[content]\r\n的格式拼接
        for (String arg : args) {
            builder.append("$")
                    .append(arg.length());
            appendCrLf(builder)
                    .append(arg);
            appendCrLf(builder);
        }
        //最后转换为 byte[],此处使用  Jedis 中的 SafeEncoder
        return SafeEncoder.encode(builder.toString());

    }

    private static StringBuilder appendCrLf(StringBuilder builder){
        return builder.append('\r')
                .append('\n');
    }



    public static void main(String[] args){
        Protocol.buildCommandBody(Command.SET,"key","value");
    }
}
