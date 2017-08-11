package xyz.rimon.medicationassistant.commons;

import android.util.Log;

/**
 * Created by SAyEM on 8/11/17.
 */

public class Logger {

    public static void d(String tag, String message) {
        if (Config.DEBUG)
            Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        if (Config.DEBUG)
            Log.i(tag, message);
    }

    public static void e(String tag, String message) {
        if (Config.DEBUG)
            Log.e(tag, message);
    }

    public static void wtf(String tag, String message) {
        if (Config.DEBUG)
            Log.wtf(tag, message);
    }


}
