package xyz.rimon.medicationassistant.commons.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Locale;

import xyz.rimon.medicationassistant.commons.prefs.DroidPrefs_;

/**
 * Created by SAyEM on 8/11/17.
 */

@EBean
public class LocaleHelper {

    @Pref
    DroidPrefs_ prefs;

    @RootContext
    Context context;

    public static String LOCALE_BN = "bn";
    public static String LOCALE_EN = "en";


    public void onCreate() {
        String lang = getPersistedData();
        setLocale(lang);
    }

    public String getLanguage() {
        return getPersistedData();
    }

    public void setLocale(String language) {
        persist(language);
        updateResources(language);
    }

    private String getPersistedData() {
        return prefs.defaultLocale().get();
    }

    private void persist(String language) {
        prefs.defaultLocale().put(language);
    }

    private void updateResources(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public boolean isBanglaCurrentLocale() {
        return getLanguage().equals("bn");
    }

}
