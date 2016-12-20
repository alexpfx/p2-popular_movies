package udacity.nanodegree.android.p2.utils;

import android.content.ContentValues;
import android.database.Cursor;

import junit.framework.Assert;

import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

/**
 * Created by alexandre on 04/12/2016.
 */

public class MovieTestHelper {

    public static final ContentValues getFightClubContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_MOVIE_ID, 550);
        cv.put(MovieEntry.COLUMN_TITLE, "Fight Club");
        cv.put(MovieEntry.COLUMN_SYNOPSIS, "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "1999-10-14");

        cv.put(MovieEntry.COLUMN_POSTER, "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg");
        cv.put(MovieEntry.COLUMN_USER_RATING, 8.1d);

        return cv;
    }

    public static final ContentValues getPulpFictionContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_MOVIE_ID, 680);
        cv.put(MovieEntry.COLUMN_TITLE, "Pulp Fiction");
        cv.put(MovieEntry.COLUMN_SYNOPSIS, "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "1994-10-14");
        cv.put(MovieEntry.COLUMN_RUNTIME, 100);

        cv.put(MovieEntry.COLUMN_POSTER, "/dM2w364MScsjFf8pfMbaWUcWrR.jpg");
        cv.put(MovieEntry.COLUMN_USER_RATING, 6.870153d);


        return cv;

    }

    public static class Asserts {

        public static void assertColumnValueString(ContentValues expected, Cursor cursor, String columnName) {
            Assert.assertEquals(expected.get(columnName), cursor.getString(cursor.getColumnIndex(columnName)));
        }

        public static void assertColumnValueInteger(ContentValues expected, Cursor cursor, String columnName) {
            Assert.assertEquals(expected.get(columnName), cursor.getInt(cursor.getColumnIndex(columnName)));
        }



        public static void assertColumnValueDouble(ContentValues expected, Cursor cursor, String columnName) {
            Assert.assertEquals(expected.get(columnName), cursor.getDouble(cursor.getColumnIndex(columnName)));
        }
    }

}
