package udacity.nanodegree.android.p2.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import udacity.nanodegree.android.p2.MainActivity;
import udacity.nanodegree.android.p2.utils.MovieTestHelper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.CONTENT_URI;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_ID;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.TABLE_NAME;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry._ID;

/**
 * Created by alexandre on 04/12/2016.
 */
public class MoviesContentProviderTest {

    private static final String TAG = "MoviesContentProviderTe";
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    private Context context;

    private SQLiteOpenHelper sqLiteOpenHelper;

    @Before
    public void setUp() throws Exception {
        context = mainActivityRule.getActivity();
        this.sqLiteOpenHelper = new MoviesOpenHelper(context);
    }

    @After
    public void tearDown() throws Exception {
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        database.delete(TABLE_NAME, _ID + " != -1", null);
    }

    private ContentValues insertValuesForTest(ContentValues cv) {
        long id = sqLiteOpenHelper.getWritableDatabase()
                .insertOrThrow(TABLE_NAME, null, cv);
        cv.put(_ID, id);
        return cv;
    }

    private Cursor queryAll() {
        return sqLiteOpenHelper.getReadableDatabase()
                .query(TABLE_NAME, MovieEntry.PROJECTION, null, null, null, null, null, null);
    }

    @Test
    public void testQuery() throws Exception {
        ContentValues contentValues = insertValuesForTest(MovieTestHelper.getPulpFictionContentValues());

        Cursor cursor = context.getContentResolver()
                .query(CONTENT_URI, null, null, null, null, null);

        assertTrue(cursor.moveToFirst());

        cursor.close();
    }

    @Test
    public void getType() throws Exception {
        String type = context.getContentResolver()
                .getType(CONTENT_URI);

    }

    @Test
    public void testDelete() throws Exception {
        insertValuesForTest(MovieTestHelper.getFightClubContentValues());
        insertValuesForTest(MovieTestHelper.getPulpFictionContentValues());
        Cursor cursor = queryAll();
        assertTrue(cursor.moveToFirst());
        long id = cursor.getLong(INDEX_ID);

        context.getContentResolver()
                .delete(CONTENT_URI, "_id = ?", new String[]{String.valueOf(id)});

        cursor = queryAll();
        cursor.moveToFirst();

        Assert.assertEquals(1, cursor.getCount());

        do {
            long testid = cursor.getLong(INDEX_ID);
            assertNotSame(id, testid);
        } while (cursor.moveToNext());
        context.getContentResolver()
                .delete(CONTENT_URI, MovieEntry._ID + "!= 1", new String[]{});

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
        assertFalse(Long.valueOf(values.getAsLong(_ID)) == -1L);

        int count = context.getContentResolver()
                .update(CONTENT_URI, MovieTestHelper.getPulpFictionContentValues(), _ID + "=?", new String[]{String.valueOf(values.getAsLong(_ID))});

        assertEquals(1, count);

        Cursor cursor = queryAll();

//        MovieTestHelper.Asserts.assertEqualContentValuesAndCursor(MovieTestHelper.getPulpFictionContentValues(), cursor);

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
                .insert(CONTENT_URI, fightClubContentValues);

        long id = ContentUris.parseId(uri);
        assertFalse(-1L == id);

        uri = context.getContentResolver()
                .insert(CONTENT_URI, pulpFictionContentValues);

        id = ContentUris.parseId(uri);
        assertFalse(-1L == id);

        cursor = queryAll();

    }

}