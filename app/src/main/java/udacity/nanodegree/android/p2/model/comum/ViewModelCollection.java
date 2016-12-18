package udacity.nanodegree.android.p2.model.comum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandre on 18/12/2016.
 */

public class ViewModelCollection<M, I> {
    private List<M> models = new ArrayList<>();

    public ViewModelCollection(List<I> items, ViewModelConverter<M, I> converter) {
        for (I item : items) {
            models.add(converter.fromItem(item));
        }
    }

    public M get(int index) {
        return models.get(index);
    }

    public int size() {
        return models.size();
    }

}
