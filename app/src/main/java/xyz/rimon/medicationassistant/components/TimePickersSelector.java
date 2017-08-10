package xyz.rimon.medicationassistant.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Toaster;

/**
 * Created by SAyEM on 8/11/17.
 */

public class TimePickersSelector extends LinearLayout implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    private Spinner spnTimes;
    private LinearLayout linearLayout;

    public TimePickersSelector(Context context) {
        super(context);
        this.init();
    }

    public TimePickersSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.timepickerselector, this);

        this.spnTimes = findViewById(R.id.spnTimes);
        this.linearLayout = findViewById(R.id.timePickerLayout);

        this.spnTimes.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        int value = Integer.parseInt(this.spnTimes.getSelectedItem().toString());
        this.linearLayout.removeAllViews();
        this.drawViews(value);
    }

    private void drawViews(int counter) {
        for (int i = 0; i < counter; i++) {
            EditText et = new EditText(getContext());
            et.setId(this.generateId(i));
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(layoutParams);
            et.setHint(getResources().getString(R.string.hint_chooseTime)+" "+(i+1));
            et.setOnClickListener(this);
            this.linearLayout.addView(et,i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private int generateId(int position) {
        return ++position + 200;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case 201:
                ((EditText)findViewById(201)).setText("Selected");
                break;
            default:
                Toaster.showToast(getContext(),id+"");
                break;
        }
    }
}
