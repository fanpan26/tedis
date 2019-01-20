package redis.clients.tedis;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/1/20 9:49
 * @project tedis
 */
public class ScanResult {

    public ScanResult(String cursor){
        this.cursor = cursor;
    }

    public boolean hasRemaining() {
        return !cursor.equals("0");
    }

    private String cursor;

    public long getCursor(){
        return Long.valueOf(cursor);
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

    private List<String> objects;


}
