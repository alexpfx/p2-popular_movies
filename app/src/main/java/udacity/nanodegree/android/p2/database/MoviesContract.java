package udacity.nanodegree.android.p2.database;

import android.provider.BaseColumns;

/**
 * Created by alexandre on 30/11/2016.
 */

public class MoviesContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "MOVIES";

        public static final String COLUMN_TITLE = "TITLE";

        public static final String COLUMN_POSTER = "POSTER";

        public static final String COLUMN_SYNOPSIS = "SYNOPSIS";

        public static final String COLUMN_USER_RATING = "RATING";

        public static final String COLUMN_RELEASE = "RELEASE_DATE";

    }

}
