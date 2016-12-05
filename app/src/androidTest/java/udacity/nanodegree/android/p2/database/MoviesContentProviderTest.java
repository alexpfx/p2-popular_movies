package udacity.nanodegree.android.p2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import udacity.nanodegree.android.p2.MainActivity;
import udacity.nanodegree.android.p2.utils.MovieTestHelper;
import udacity.nanodegree.android.p2.utils.TestHelper;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

/**
 * Created by alexandre on 04/12/2016.
 */
public class MoviesContentProviderTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    private Context context;

    SQLiteDatabase database;

    @Before
    public void setUp() throws Exception {
        context = mainActivityRule.getActivity();
        MoviesOpenHelper dbHelper = new MoviesOpenHelper(context);
        database = dbHelper.getWritableDatabase();

    }

    @After
    public void tearDown() throws Exception {
        database.close();
        TestHelper.deleteDatabase(database);
    }

    private ContentValues insertValuesForTest() {
        ContentValues contentValues = MovieTestHelper.getFightClubContentValues();
        database.insert(MovieEntry.TABLE_NAME, null, contentValues);
        return contentValues;
    }

    private Cursor queryAll() {
        return database.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null, null);
    }

    @Test
    public void query() throws Exception {
        ContentValues contentValues = insertValuesForTest();

        Cursor cursor = context.getContentResolver()
                .query(MovieEntry.CONTENT_URI, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        cursor.moveToNext();
        assertColumnValueString(contentValues, cursor, MovieEntry.COLUMN_POSTER);
        assertColumnValueString(contentValues, cursor, MovieEntry.COLUMN_RELEASE);
        assertColumnValueString(contentValues, cursor, MovieEntry.COLUMN_TITLE);
        assertColumnValueString(contentValues, cursor, MovieEntry.COLUMN_SYNOPSIS);
        assertColumnValueDouble(contentValues, cursor, MovieEntry.COLUMN_USER_RATING);
    }

    public void assertColumnValueString(ContentValues expected, Cursor cursor, String columnName) {
        Assert.assertEquals(expected.get(columnName), cursor.getString(cursor.getColumnIndex(columnName)));
    }

    public void assertColumnValueDouble(ContentValues expected, Cursor cursor, String columnName) {
        Assert.assertEquals(expected.get(columnName), cursor.getDouble(cursor.getColumnIndex(columnName)));
    }

    //    @Test
    public void getType() throws Exception {

    }

    @Test
    public void delete() throws Exception {
        insertValuesForTest();
        insertValuesForTest();
        Cursor cursor = queryAll();
        assertTrue(cursor.moveToFirst());
        long id = cursor.getLong(cursor.getColumnIndex(MovieEntry._ID));

        context.getContentResolver()
                .delete(MovieEntry.CONTENT_URI, "_id = ?", new String[]{String.valueOf(id)});

        cursor = queryAll();
        cursor.moveToFirst();
        do {
            long testid = cursor.getLong(cursor.getColumnIndex(MovieEntry._ID));
            assertNotSame(id, testid);
        } while (cursor.moveToNext());

    }

    //    @Test
    public void update() throws Exception {

    }

}