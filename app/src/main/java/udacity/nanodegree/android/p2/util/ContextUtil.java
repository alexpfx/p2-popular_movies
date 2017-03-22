package udacity.nanodegree.android.p2.util;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import udacity.nanodegree.android.p2.R;

/**
 * Created by alexandre on 01/01/2017.
 */

public class ContextUtil {
    private static final String TAG = "ContextUtil";

    public static String getString(int res_id, Object... formatArgs) {
        Log.d(TAG, "getString: "+res_id);
        return Resources.getSystem().getString(res_id, formatArgs);
    }

    public static void setupToolbar(AppCompatActivity activity) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
