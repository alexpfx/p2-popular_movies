package udacity.nanodegree.android.p2;

import android.net.Uri;

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
