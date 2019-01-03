package redis.clients.tedis;

public class TedisException extends RuntimeException {
    private static final long serialVersionUID = -2946266495682282677L;

    public TedisException(String message) {
        super(message);
    }

    public TedisException(Throwable e) {
        super(e);
    }

    public TedisException(String message, Throwable cause) {
        super(message, cause);
    }
}
