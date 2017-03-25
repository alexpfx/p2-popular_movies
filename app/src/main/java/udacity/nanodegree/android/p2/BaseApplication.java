package udacity.nanodegree.android.p2;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by alexandre on 24/03/2017.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate();
    }
}
