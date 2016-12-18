package udacity.nanodegree.android.p2.model.comum;

/**
 * Created by alexandre on 18/12/2016.
 */

public interface ViewModelConverter<M, I> {
    M fromItem (I item);
}


