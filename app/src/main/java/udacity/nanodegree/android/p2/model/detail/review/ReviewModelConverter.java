package udacity.nanodegree.android.p2.model.detail.review;

import udacity.nanodegree.android.p2.model.comum.ViewModelConverter;
import udacity.nanodegree.android.p2.network.data_transfer.ReviewItem;

/**
 * Created by alexandre on 18/12/2016.
 */

public class ReviewModelConverter implements ViewModelConverter<ReviewViewModel, ReviewItem> {
    @Override
    public ReviewViewModel fromItem(ReviewItem item) {
        return new ReviewViewModel(item);
    }
}
