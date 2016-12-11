package udacity.nanodegree.android.p2.model.movie;

import android.net.Uri;

import udacity.nanodegree.android.p2.network.FetchRules;

/**
 * Created by alexandre on 15/11/2016.
 */

public class GetNowPlaying implements FetchRules {
    @Override
    public Uri composeUrl(Uri baseUrl) {
        return baseUrl.buildUpon()
                .appendPath("now_playing")
                .build();
    }
}
