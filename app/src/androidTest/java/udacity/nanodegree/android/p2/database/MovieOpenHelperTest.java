package udacity.nanodegree.android.p2.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import udacity.nanodegree.android.p2.MainActivity;
import udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

/**
 * Created by alexandre on 03/12/2016.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MovieOpenHelperTest {

    private static final String TAG = "MovieOpenHelperTest";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    SQLiteDatabase database;

    @Before
    public void setUp() {
        database = MoviesOpenHelper.getInstance(mainActivityRule.getActivity())
                .getWritableDatabase();
    }

    @After
    public void tearDown() {
        SQLiteDatabase.deleteDatabase(new File(database.getPath()));
    }

    @Test
    public void testCreateDb() {
        Assert.assertTrue(database.isOpen());
        Cursor cursor = database.rawQuery("select name from sqlite_master where type = 'table'", null);
        Assert.assertTrue(wereTablesCreated(cursor, MovieEntry.TABLE_NAME));

        database.close();
    }

    @Test
    public void insert() {
        long rowid = database.insert(MovieEntry.TABLE_NAME, null, getContentValues());
        Assert.assertFalse(rowid == -1);

        Cursor cursor = database.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null);
        Assert.assertTrue(cursor.moveToFirst());

        String title = cursor.getString(2);

        Assert.assertEquals("title", title);
        cursor.close();
        database.close();

    }

    private boolean wereTablesCreated(Cursor cursor, String... name) {
        if (!cursor.moveToFirst()) {
            return false;
        }

        Set<String> tables = new HashSet<>(Arrays.asList(name));
        do {
            tables.remove(cursor.getString(0));
        } while (cursor.moveToNext());

        return tables.isEmpty();
    }

    public ContentValues getContentValues() {
        ContentValues c = new ContentValues();
        c.put(MovieEntry.COLUMN_MOVIE_ID, "movie_id");
        c.put(MovieEntry.COLUMN_POSTER, "poster");
        c.put(MovieEntry.COLUMN_RELEASE_DATE, "release");
        c.put(MovieEntry.COLUMN_SYNOPSIS, "synopsis");
        c.put(MovieEntry.COLUMN_TITLE, "title");
        c.put(MovieEntry.COLUMN_USER_RATING, 4.5);
        return c;
    }
}
