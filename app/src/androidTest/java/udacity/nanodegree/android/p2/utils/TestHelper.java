package udacity.nanodegree.android.p2.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.io.File;

/**
 * Created by alexandre on 05/12/2016.
 */

public class TestHelper {


    public static void deleteDatabase (SQLiteDatabase database){
        SQLiteDatabase.deleteDatabase(new File(database.getPath()));
    }

}
