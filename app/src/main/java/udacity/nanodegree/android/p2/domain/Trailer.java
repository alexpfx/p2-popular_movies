package udacity.nanodegree.android.p2.domain;

import java.util.List;

/**
 * Created by alexandre on 14/11/2016.
 */

public class Trailer {

    private String id;

    private List<TrailerItem> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TrailerItem> getResults() {
        return results;
    }

    public void setResults(List<TrailerItem> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "id='" + id + '\'' +
                ", results=" + results +
                '}';
    }
}
