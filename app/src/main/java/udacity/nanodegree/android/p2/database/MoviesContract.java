package udacity.nanodegree.android.p2.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by alexandre on 30/11/2016.
 */

public class MoviesContract {
    public static final String CONTENT_AUTHORITY = "udacity.nanodegree.android.p2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + PATH_MOVIE;

        public static final String TABLE_NAME = "MOVIES";

        public static final String COLUMN_TITLE = "TITLE";

        public static final String COLUMN_POSTER = "POSTER";

        public static final String COLUMN_SYNOPSIS = "SYNOPSIS";

        public static final String COLUMN_USER_RATING = "RATING";

        public static final String COLUMN_RELEASE = "RELEASE_DATE";

        public static final Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
