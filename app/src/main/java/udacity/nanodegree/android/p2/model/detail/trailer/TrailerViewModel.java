package udacity.nanodegree.android.p2.model.detail.trailer;

import android.databinding.BaseObservable;

import udacity.nanodegree.android.p2.network.data_transfer.TrailerItem;

/**
 * Created by alexandre on 27/11/2016.
 */

public class TrailerViewModel extends BaseObservable {
    private static final String TAG = "TrailerViewModel";
    private String id;
    private String key;
    private String title;

    public TrailerViewModel(String id, String key, String title) {
        this.id = id;
        this.key = key;
        this.title = title;
    }

    public static TrailerViewModel fromTrailerItem(TrailerItem item) {
        return new TrailerViewModel(item.getId(), item.getKey(), item.getName());
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

}
