package xyz.rimon.medicationassistant;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.commons.helper.LocaleHelper;
import xyz.rimon.medicationassistant.commons.helper.LocaleHelper_;
import xyz.rimon.medicationassistant.commons.prefs.DroidPrefs_;
import xyz.rimon.medicationassistant.core.CoreActivity;
import xyz.rimon.medicationassistant.ui.home.AddDrugFragment_;
import xyz.rimon.medicationassistant.ui.home.DrugListFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends CoreActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {


    @ViewById
    BottomNavigationView navigationView;

    @ViewById
    Toolbar toolbar;

    @ViewById
    Spinner spnLanguage;

    @Pref
    DroidPrefs_ prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        // setup toolbar
        setSupportActionBar(toolbar);
        // set selected
        if (prefs.defaultLocale().get().equals(LocaleHelper.LOCALE_EN))
            spnLanguage.setSelection(0);
        else if (prefs.defaultLocale().get().equals(LocaleHelper.LOCALE_BN))
            spnLanguage.setSelection(1);

        spnLanguage.setOnItemSelectedListener(this);

        this.navigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(DrugListFragment_.builder().build());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                loadFragment(DrugListFragment_.builder().build());
                return true;
            case R.id.navigation_dashboard:
                loadChildFragment(AddDrugFragment_.builder().build());
                return true;
            case R.id.navigation_notifications:
                return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String[] locales = getResources().getStringArray(R.array.locales);
        // check if default locale isn't already selected
        if (!locales[i].equals(prefs.defaultLocale().get())) {
            LocaleHelper_.getInstance_(getApplicationContext()).setLocale(locales[i]);
            MainActivity.this.recreate();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.menu_language) {
//            new AlertDialog.Builder(this)
//                    .setItems(R.array.languages, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            String[] locales = getResources().getStringArray(R.array.locales);
//                            LocaleHelper_.getInstance_(getApplicationContext()).setLocale(locales[i]);
//                            MainActivity.this.recreate();
//                        }
//                    })
//                    .show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
