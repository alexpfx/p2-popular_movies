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
        public static final String COLUMN_MOVIE_ID = "MOVIE_ID";
        public static final String COLUMN_POSTER = "POSTER";
        public static final String COLUMN_SYNOPSIS = "SYNOPSIS";
        public static final String COLUMN_USER_RATING = "RATING";
        public static final String COLUMN_RELEASE_DATE = "RELEASE_DATE";
        public static final String COLUMN_IS_FAVORITE = "IS_FAVORITE";
        public static final String COLUMN_UPDATE_DATE = "UPDATE_DATE";
        public static final String COLUMN_RUNTIME = "RUNTIME";

        public static final int INDEX_ID = 0;
        public static final int INDEX_MOVIE_ID = 1;
        public static final int INDEX_TITLE = 2;
        public static final int INDEX_POSTER = 3;
        public static final int INDEX_SYNOPSIS = 4;
        public static final int INDEX_USER_RATING = 5;
        public static final int INDEX_RELEASE_DATE = 6;
        public static final int INDEX_RUNTIME = 7;
        public static final int INDEX_IS_FAVORITE = 8;
        public static final int INDEX_UPDATE_DATE = 9;

        public static final String[] PROJECTION = new String[]{
                MovieEntry._ID, COLUMN_MOVIE_ID, COLUMN_TITLE, COLUMN_POSTER, COLUMN_SYNOPSIS, COLUMN_USER_RATING, COLUMN_RELEASE_DATE, COLUMN_RUNTIME, COLUMN_IS_FAVORITE, COLUMN_UPDATE_DATE
        };

        public static final Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
