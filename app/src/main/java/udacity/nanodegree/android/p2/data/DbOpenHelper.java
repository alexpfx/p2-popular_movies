package udacity.nanodegree.android.p2.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexandre on 22/11/2016.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String dbName = "data";
    public static final int dbVersion = 1;

    public static final String movieTable = "Movies";

    public static final String colId = "id";
    public static final String colTitle = "title";
    public static final String colPoster = "poster";
    public static final String colSynopsis = "synopsys";
    public static final String colUserRating = "userRating";
    public static final String colReleaseDate = "releaseDate";

    public static final String databaseCreate = "create table "+movieTable+ "(" +  colId;


    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
