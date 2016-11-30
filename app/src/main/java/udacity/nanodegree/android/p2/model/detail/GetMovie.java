package udacity.nanodegree.android.p2.model.detail;

import android.net.Uri;

import udacity.nanodegree.android.p2.network.FetchRules;

/**
 * Created by alexandre on 30/10/2016.
 */
public class GetMovie implements FetchRules {
    private final String id;

    public GetMovie(String id) {
        this.id = id;
    }

    @Override
    public Uri composeUrl(Uri baseUrl) {
        return baseUrl.buildUpon().appendPath(id).build();
    }
}
