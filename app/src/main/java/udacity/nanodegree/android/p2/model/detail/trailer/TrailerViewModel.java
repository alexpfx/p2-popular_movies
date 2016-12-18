package udacity.nanodegree.android.p2.model.detail.trailer;

import android.databinding.BaseObservable;

import udacity.nanodegree.android.p2.network.data_transfer.TrailerItem;

/**
 * Created by alexandre on 27/11/2016.
 */

public class TrailerViewModel extends BaseObservable {
    private String id;
    private String key;
    private String title;
    private static final String TAG = "TrailerViewModel";

    public TrailerViewModel(String id, String key, String title) {
        this.id = id;
        this.key = key;
        this.title = title;
    }

    public static TrailerViewModel fromTrailerItem(TrailerItem item) {
        return new TrailerViewModel(item.getId(), item.getKey(), item.getName());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

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
}