package udacity.nanodegree.android.p2.utils;

import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.File;

/**
 * Created by alexandre on 05/12/2016.
 */

public class TestHelper {

    private static class TestContentObserver extends ContentObserver{
        private TestContentObserver(Handler handler) {
            super(handler);
        }

        static TestContentObserver getContentObserver (){
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(new Handler(ht.getLooper()));
        }
    }

    public static TestContentObserver getTestContentObserver (){
        return TestContentObserver.getContentObserver();
    }

    public static void deleteDatabase (SQLiteDatabase database){
        SQLiteDatabase.deleteDatabase(new File(database.getPath()));
    }

}
