package udacity.nanodegree.android.p2.domain;

/**
 * Created by alexandre on 14/11/2016.
 */

public class TrailerItem {
    private String id;
    private String key;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TrailerItem{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
