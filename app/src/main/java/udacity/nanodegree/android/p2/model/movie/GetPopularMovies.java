package udacity.nanodegree.android.p2.model.movie;

import android.net.Uri;

import udacity.nanodegree.android.p2.network.FetchRules;

/**
 * Created by alexandre on 22/10/2016.
 */

public class GetPopularMovies implements FetchRules {

    private static final String TAG = "GetPopularMovies";

    @Override
    public Uri composeUrl(Uri baseUrl) {
        return baseUrl.buildUpon()
                      .appendPath("popular")
                      .build();
    }
}
