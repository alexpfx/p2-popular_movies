package udacity.nanodegree.android.p2.utils;

import android.content.ContentValues;

import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

/**
 * Created by alexandre on 04/12/2016.
 */

public class MovieTestHelper {

    public static final ContentValues getFightClubContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_TITLE, "Fight Club");
        cv.put(MovieEntry.COLUMN_SYNOPSIS, "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.");
        cv.put(MovieEntry.COLUMN_RELEASE, "1999-10-14");

        cv.put(MovieEntry.COLUMN_POSTER, "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg");
        cv.put(MovieEntry.COLUMN_USER_RATING, 8.1d);

        return cv;
    }

}
