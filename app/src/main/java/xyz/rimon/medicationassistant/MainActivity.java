package xyz.rimon.medicationassistant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.components.TimePickersSelector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    Button btnAdd;

    private TimePickersSelector tpSelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        this.tpSelector = (TimePickersSelector) findViewById(R.id.timePickerSelector);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:

                return true;
            case R.id.navigation_dashboard:

                return true;
            case R.id.navigation_notifications:

                return true;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnAdd) {
            Toaster.showToast(getApplicationContext(), Arrays.toString(this.tpSelector.getText()));
        }
    }
}
