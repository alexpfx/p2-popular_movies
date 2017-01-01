package udacity.nanodegree.android.p2.util;

import android.content.res.Resources;
import android.util.Log;

/**
 * Created by alexandre on 01/01/2017.
 */

public class ResourcesUtil {
    private static final String TAG = "ResourcesUtil";

    public static String getString(int res_id, Object... formatArgs) {
        Log.d(TAG, "getString: "+res_id);
        return Resources.getSystem().getString(res_id, formatArgs);
    }
}
