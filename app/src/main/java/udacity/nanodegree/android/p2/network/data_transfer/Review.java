package udacity.nanodegree.android.p2.network.data_transfer;

import java.util.List;

/**
 * Created by alexandre on 18/12/2016.
 */

public class Review {
    private String id;
    private List<ReviewItem> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ReviewItem> getResults() {
        return results;
    }

    public void setResults(List<ReviewItem> results) {
        this.results = results;
    }
}
