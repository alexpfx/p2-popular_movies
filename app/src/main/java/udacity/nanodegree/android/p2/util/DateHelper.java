package udacity.nanodegree.android.p2.util;

import android.text.format.Time;

/**
 * Created by alexandre on 14/12/2016.
 */

public class DateHelper {
    public static long normalizeDate(long startDate) {
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

}
