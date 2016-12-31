package udacity.nanodegree.android.p2.model.detail;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by alexandre on 12/12/2016.
 */

public class DetailAdapter extends SimpleCursorAdapter {

    public DetailAdapter(Context context, int layout, Cursor c, String[] from, int[] to,
            int flags) {
        super(context, layout, c, from, to, flags);
    }
}
