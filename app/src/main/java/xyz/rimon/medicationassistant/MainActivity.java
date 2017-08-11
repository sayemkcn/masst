package xyz.rimon.medicationassistant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.components.TimePickersSelector;
import xyz.rimon.medicationassistant.core.CoreActivity;
import xyz.rimon.medicationassistant.ui.home.HomeFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends CoreActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @ViewById
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        this.navigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(HomeFragment_.builder().build());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                loadFragment(HomeFragment_.builder().build());
                Toaster.showToast(getApplicationContext(), "Home");
                return true;
            case R.id.navigation_dashboard:
                Toaster.showToast(getApplicationContext(), "Dashboard");
                return true;
            case R.id.navigation_notifications:
                Toaster.showToast(getApplicationContext(), "Notifications");
                return true;
        }
        return false;
    }

}
