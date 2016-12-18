package udacity.nanodegree.android.p2.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import static udacity.nanodegree.android.p2.database.MoviesContract.CONTENT_AUTHORITY;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;
import static udacity.nanodegree.android.p2.database.MoviesContract.PATH_MOVIE;

/**
 * Created by alexandre on 25/11/2016.
 */

public class MoviesContentProvider extends ContentProvider {
    private static final String TAG = "MoviesContentProvider";
    public static final int MOVIE = 300;
    public static final int MOVIE_BY_ID = 301;
    public static final String UNKNOW_URI = "Unknow uri";

    private MoviesOpenHelper moviesOpenHelper;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        matcher.addURI(authority, PATH_MOVIE, MOVIE);
        matcher.addURI(authority, PATH_MOVIE + "/#", MOVIE_BY_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        moviesOpenHelper = MoviesOpenHelper.getInstance(getContext());
        return true;
    }

    public static final SQLiteQueryBuilder queryBuilder;

    static {
        queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MovieEntry.TABLE_NAME);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = moviesOpenHelper.getReadableDatabase()
                        .query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("url doesn't match: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = moviesOpenHelper.getWritableDatabase();
        Log.d(TAG, "insert: "+contentValues);

        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case MOVIE: {
                long id = database.insert(MovieEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = MovieEntry.buildMovieUri(id);
                } else {
                    throw new SQLException("failed to insert row into " + uri);
                }
            }
            break;
            default:
                throw new UnsupportedOperationException("uri doesn't match: " + uri);
        }

        getContext().getContentResolver()
                .notifyChange(uri, null);
        return returnUri;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException(UNKNOW_URI + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = moviesOpenHelper.getWritableDatabase();
        int rows_deleted = 0;
        if (selection == null) {
            selection = "1";
        }
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                return database.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException(UNKNOW_URI + uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase database = moviesOpenHelper.getWritableDatabase();
        int rows = database.update(MovieEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        if (uriMatcher.match(uri) != MOVIE) {
            throw new UnsupportedOperationException(UNKNOW_URI + uri);
        }

        if (rows != 0) {
            getContext().getContentResolver()
                    .notifyChange(uri, null);
        }

        return rows;

    }

}

