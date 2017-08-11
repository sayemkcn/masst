package xyz.rimon.medicationassistant.commons.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by SAyEM on 8/11/17.
 */

@SharedPref(SharedPref.Scope.UNIQUE)
public interface DroidPrefs {
    @DefaultString("bn")
    String defaultLocale();
}
