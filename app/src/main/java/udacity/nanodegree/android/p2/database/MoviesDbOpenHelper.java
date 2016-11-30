package udacity.nanodegree.android.p2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import udacity.nanodegree.android.p2.database.utils.SqliteDmlBuilder;

/**
 * Created by alexandre on 22/11/2016.
 */

public class MoviesDbOpenHelper extends SQLiteOpenHelper {
    public static final String dbName = "data";
    public static final int dbVersion = 4;

    private static MoviesDbOpenHelper instance;

    public static MoviesDbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MoviesDbOpenHelper(context);
        }
        return instance;
    }

    public MoviesDbOpenHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = new SqliteDmlBuilder(MovieDataModel.class).createSql();
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists movies");
        onCreate(db);
    }
}
