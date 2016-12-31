package udacity.nanodegree.android.p2.model.detail.review;

import android.net.Uri;

import udacity.nanodegree.android.p2.network.fetch.FetchRules;

/**
 * Created by alexandre on 18/12/2016.
 */

public class GetReviews implements FetchRules {

    private final String id;

    public GetReviews(String id) {
        this.id = id;
    }

    @Override
    public Uri composeUrl(Uri baseUrl) {
        return baseUrl.buildUpon()
                .appendPath(id)
                .appendPath("reviews")
                .build();
    }
}
