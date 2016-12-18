package udacity.nanodegree.android.p2.model.detail.review;

import android.databinding.BaseObservable;

import udacity.nanodegree.android.p2.network.data_transfer.ReviewItem;

/**
 * Created by alexandre on 18/12/2016.
 */

public class ReviewViewModel extends BaseObservable {
    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewViewModel(ReviewItem item) {
        this.id = item.getId();
        this.author = item.getAuthor();
        this.content = item.getContent();
        this.url = item.getUrl();
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

}
