package udacity.nanodegree.android.p2.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import udacity.nanodegree.android.p2.MainActivity;
import udacity.nanodegree.android.p2.utils.MovieTestHelper;
import udacity.nanodegree.android.p2.utils.TestHelper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

/**
 * Created by alexandre on 04/12/2016.
 */
public class MoviesContentProviderTest {

    private static final String TAG = "MoviesContentProviderTe";
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

    private ContentValues insertValuesForTest(ContentValues cv) {
        long id = database.insert(MovieEntry.TABLE_NAME, null, cv);
        cv.put(MovieEntry._ID, id);
        return cv;
    }

    private Cursor queryAll() {
        return database.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null, null);
    }

    @Test
    public void query() throws Exception {
        ContentValues contentValues = insertValuesForTest(MovieTestHelper.getPulpFictionContentValues());

        Cursor cursor = context.getContentResolver()
                .query(MovieEntry.CONTENT_URI, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        MovieTestHelper.Asserts.assertEqualContentValuesAndCursor(contentValues, cursor);
    }

    @Test
    public void getType() throws Exception {
        String type = context.getContentResolver()
                .getType(MovieEntry.CONTENT_URI);

        Log.d(TAG, "getType: " + type);

    }

    @Test
    public void testDelete() throws Exception {
        insertValuesForTest(MovieTestHelper.getFightClubContentValues());
        insertValuesForTest(MovieTestHelper.getPulpFictionContentValues());
        Cursor cursor = queryAll();
        assertTrue(cursor.moveToFirst());
        long id = cursor.getLong(cursor.getColumnIndex(MovieEntry._ID));

        context.getContentResolver()
                .delete(MovieEntry.CONTENT_URI, "_id = ?", new String[]{String.valueOf(id)});

        cursor = queryAll();
        cursor.moveToFirst();

        Assert.assertEquals(1, cursor.getCount());

        do {
            long testid = cursor.getLong(cursor.getColumnIndex(MovieEntry._ID));
            assertNotSame(id, testid);
        } while (cursor.moveToNext());
        context.getContentResolver()
                .delete(MovieEntry.CONTENT_URI, "_id != -1", new String[]{});

        cursor = queryAll();

        Assert.assertEquals(0, cursor.getCount());

    }

    /**
     * Insert.
     * Query.
     * Update values.
     * Assert.
     *
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        ContentValues values = insertValuesForTest(MovieTestHelper.getFightClubContentValues());
        assertFalse(Long.valueOf(values.getAsLong(MovieEntry._ID)) == -1L);

        int count = context.getContentResolver()
                .update(MovieEntry.CONTENT_URI, MovieTestHelper.getPulpFictionContentValues(), MovieEntry._ID + "=?", new String[]{String.valueOf(values.getAsLong(MovieEntry._ID))});

        assertEquals(1, count);

        Cursor cursor = queryAll();

        MovieTestHelper.Asserts.assertEqualContentValuesAndCursor(MovieTestHelper.getPulpFictionContentValues(), cursor);

    }

    /**
     * @throws Exception
     */
    @Test
    public void testInsert() throws Exception {
        Cursor cursor = queryAll();
        assertFalse(cursor.moveToFirst());
        cursor.close();

        ContentValues fightClubContentValues = MovieTestHelper.getFightClubContentValues();
        ContentValues pulpFictionContentValues = MovieTestHelper.getPulpFictionContentValues();

        Uri uri = context.getContentResolver()
                .insert(MovieEntry.CONTENT_URI, fightClubContentValues);

        long id = ContentUris.parseId(uri);
        assertFalse(-1L == id);

        uri = context.getContentResolver()
                .insert(MovieEntry.CONTENT_URI, pulpFictionContentValues);

        id = ContentUris.parseId(uri);
        assertFalse(-1L == id);

        cursor = queryAll();

    }

}