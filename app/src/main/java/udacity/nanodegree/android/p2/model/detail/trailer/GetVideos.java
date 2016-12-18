package udacity.nanodegree.android.p2.model.detail.trailer;

import android.net.Uri;

import udacity.nanodegree.android.p2.network.FetchRules;

/**
 * Created by alexandre on 13/11/2016.
 */

public class GetVideos implements FetchRules {

    private String id;

    public GetVideos(String id) {
        this.id = id;
    }

    @Override
    public Uri composeUrl(Uri baseUrl) {
        return baseUrl.buildUpon()
                .appendPath(id)
                .appendPath("videos")
                .build();
    }
}
