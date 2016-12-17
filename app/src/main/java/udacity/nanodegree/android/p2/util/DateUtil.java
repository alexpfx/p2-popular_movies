package udacity.nanodegree.android.p2.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by alexandre on 14/12/2016.
 */

public class DateUtil {
    public static long normalizaDate(Date startDate) {
        Calendar calDate = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        long time = startDate.getTime();
        calDate.setTimeInMillis(time - (time % 86400000));
        return calDate.getTime()
                .getTime();
    }

}