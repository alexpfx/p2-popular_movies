package udacity.nanodegree.android.p2;

import android.database.sqlite.SQLiteOpenHelper;

import com.clough.android.androiddbviewer.ADBVApplication;

import udacity.nanodegree.android.p2.database.MoviesOpenHelper;

/**
 * Created by alexandre on 11/12/2016.
 */

public class CustomApp extends ADBVApplication {
    @Override
    public SQLiteOpenHelper getDataBase() {
        return new MoviesOpenHelper(getApplicationContext());
    }
}
