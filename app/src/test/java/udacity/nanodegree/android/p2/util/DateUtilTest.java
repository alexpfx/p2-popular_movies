package udacity.nanodegree.android.p2.util;

import android.text.format.Time;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by alexandre on 17/12/2016.
 */
public class DateUtilTest {
    @Test
    public void testNormalizeDate_clear_time() throws Exception {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(2001, 2, 15, 14, 25, 46);
        long r = DateUtil.normalizaDate(calendar.getTime());

        calendar.setTimeInMillis(r);

        Calendar exp = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        exp.clear();
        exp.set(2001, 2, 15);

        Assert.assertEquals(exp, calendar);

    }


}