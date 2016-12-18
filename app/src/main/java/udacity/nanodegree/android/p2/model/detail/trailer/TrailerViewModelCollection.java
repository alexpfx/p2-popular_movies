package udacity.nanodegree.android.p2.model.detail.trailer;

import java.util.ArrayList;
import java.util.List;

import udacity.nanodegree.android.p2.network.data_transfer.TrailerItem;

/**
 * Created by alexandre on 27/11/2016.
 */

public class TrailerViewModelCollection {
    private final List<TrailerViewModel> collection;

    public TrailerViewModelCollection(List<TrailerItem> items) {
        collection = new ArrayList<>();
        for (TrailerItem ti : items) {
            collection.add(TrailerViewModel.fromTrailerItem(ti));
        }
    }

    public TrailerViewModel get(int position) {
        return collection.get(position);
    }

    public int size() {
        return collection.size();
    }

}
