package udacity.nanodegree.android.p2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

/**
 * Created by alexandre on 22/11/2016.
 */
public class MoviesOpenHelper extends SQLiteOpenHelper {
    public static final String dbName = "movies.db";
    public static final int dbVersion = 11;
    private static final String TAG = "MoviesOpenHelper";

    private static MoviesOpenHelper instance;

    public static MoviesOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MoviesOpenHelper(context);
        }
        return instance;
    }

    public MoviesOpenHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dml = SqliteDMLBuilder.createInstance()
                .table(MovieEntry.TABLE_NAME)
                .column(MovieEntry._ID, "integer primary key")

                .column(MovieEntry.COLUMN_TITLE, "text not null")
                .column(MovieEntry.COLUMN_POSTER, "text not null")
                .column(MovieEntry.COLUMN_SYNOPSIS, "text not null")

                .column(MovieEntry.COLUMN_MOVIE_ID, "integer not null")
                .column(MovieEntry.COLUMN_RELEASE_DATE, "integer not null")
                .column(MovieEntry.COLUMN_UPDATE_DATE, "integer not null")
                .column(MovieEntry.COLUMN_IS_FAVORITE, "integer not null")
                .column(MovieEntry.COLUMN_RUNTIME, "integer not null")

                .column(MovieEntry.COLUMN_USER_RATING, "real not null")
                .build();
        db.execSQL(dml);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
