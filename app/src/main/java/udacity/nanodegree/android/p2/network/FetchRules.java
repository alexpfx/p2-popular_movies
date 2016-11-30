package udacity.nanodegree.android.p2.network;

import android.net.Uri;

/**
 * Created by alexandre on 13/11/2016.
 */

public interface FetchRules {
    Uri composeUrl(Uri baseUrl);
}
