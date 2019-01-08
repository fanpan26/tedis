package redis.clients.tedis;

public interface ProtocolCommand {
    String getName();
    byte[] getRaw();
}
