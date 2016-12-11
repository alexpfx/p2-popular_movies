package udacity.nanodegree.android.p2.model.movie;

import android.net.Uri;

import udacity.nanodegree.android.p2.network.FetchRules;

/**
 * Created by alexandre on 23/10/2016.
 */
public class GetTopMovies implements FetchRules {
    @Override
    public Uri composeUrl(Uri baseUrl) {
        return baseUrl.buildUpon()
                .appendPath("top_rated")
                .build();
    }
}
