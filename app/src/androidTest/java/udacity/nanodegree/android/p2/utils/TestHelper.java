package udacity.nanodegree.android.p2.utils;

import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.File;

/**
 * Created by alexandre on 05/12/2016.
 */

public class TestHelper {

    private static class TestContentObserver extends ContentObserver {
        private static final String TAG = "TestContentObserver";

        private TestContentObserver(Handler handler) {
            super(handler);
        }

        static TestContentObserver getContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(new Handler(ht.getLooper()));
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "onChange: " + selfChange);
        }
    }

    public static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getContentObserver();
    }

    public static void deleteDatabase(SQLiteDatabase database) {
        SQLiteDatabase.deleteDatabase(new File(database.getPath()));
    }

}
