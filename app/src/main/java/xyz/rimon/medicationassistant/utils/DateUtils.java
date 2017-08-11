package xyz.rimon.medicationassistant.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import xyz.rimon.medicationassistant.commons.Toaster;

/**
 * Created by SAyEM on 8/11/17.
 */

public class DateUtils {

    public static DateFormat getTimeFormat24() {
        return new SimpleDateFormat("HH:mm", Locale.US);
    }

    public static DateFormat getTimeFormat12() {
        return new SimpleDateFormat("hh:mm a", Locale.US);
    }

    public static DateFormat getReadableDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }

    public static DateFormat getDayMonthDateFormat() {
        return new SimpleDateFormat("MMMM dd", Locale.US);
    }

    public static String buildDatePickerStringDate(int year, int month, int dayOfMonth) {
        String m;
        String d;
        if (++month < 10) m = "0" + month;
        else m = String.valueOf(month);

        if (dayOfMonth < 10) d = "0" + dayOfMonth;
        else d = String.valueOf(dayOfMonth);
        return year + "-" + m + "-" + d;
    }

    public static Date toDate(Context context, String dateString) {
        DateFormat sdf = DateUtils.getReadableDateFormat();
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            Toaster.showToast(context, "Birth date format is wrong! Corrent format is: yyyy-MM-dd");
        }
        return date;
    }
}
