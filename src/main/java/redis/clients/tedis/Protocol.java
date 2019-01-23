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

    public static final long DEFAULT_RESPONSE_TIMEOUT = 60000L;

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

        //foreach，format as $[num]\r\n[content]\r\n,this length must be bytes.length
        for (String arg : args) {
            builder.append("$")
                    .append(SafeEncoder.encode(arg).length);
            appendCrLf(builder)
                    .append(arg);
            appendCrLf(builder);
        }
        //最后转换为 byte[],此处使用  Jedis 中的 SafeEncoder
        System.out.println("*  "+cmd.getName());
        System.out.println("```");
        System.out.println(builder.toString().replaceAll("\r\n","\\\\r\\\\n"));
        return SafeEncoder.encode(builder.toString());

    }

    private static TedisOutputStream os = new TedisOutputStream();

    public static byte[] buildCommandBodyWithOutputStream(final ProtocolCommand cmd,String... args) {
        byte[] command = cmd.getRaw();
        byte[][] byteArgs = new byte[args.length][];
        for (int i = 0; i < args.length; i++) {
            byteArgs[i] = SafeEncoder.encode(args[i]);
        }

        os.write(ASTERISK_BYTE);
        os.writeIntCrLf(args.length + 1);
        os.write(DOLLAR_BYTE);
        os.writeIntCrLf(command.length);
        os.write(command);
        os.writeCrLf();

        for (final byte[] arg : byteArgs) {
            os.write(DOLLAR_BYTE);
            os.writeIntCrLf(arg.length);
            os.write(arg);
            os.writeCrLf();
        }

       return os.getByte();

    }

    private static StringBuilder appendCrLf(StringBuilder builder){
        return builder.append('\r')
                .append('\n');
    }



    public static void main(String[] args){
        Protocol.buildCommandBody(Command.SET,"key","value");
    }
}
