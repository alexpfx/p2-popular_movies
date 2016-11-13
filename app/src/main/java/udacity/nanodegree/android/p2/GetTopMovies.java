package udacity.nanodegree.android.p2;

import android.net.Uri;

/**
 * Created by alexandre on 23/10/2016.
 */
public class GetTopMovies implements FetchRules {
    @Override
    public Uri composeUrl(Uri baseUrl) {
        return baseUrl.buildUpon().appendPath("top_rated").build();
    }
}
