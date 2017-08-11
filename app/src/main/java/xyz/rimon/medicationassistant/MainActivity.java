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

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @ViewById
    Button btnAdd;

    @ViewById
    TimePickersSelector tpSelector;

    @ViewById
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews() {
        this.navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
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


    @Click
    void btnAdd() {
        Toaster.showToast(getApplicationContext(), Arrays.toString(this.tpSelector.getText()));
    }
}
