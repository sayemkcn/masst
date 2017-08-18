package xyz.rimon.medicationassistant.components;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Logger;
import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.utils.DateUtils;

/**
 * Created by SAyEM on 8/11/17.
 */

public class TimePickersSelector extends LinearLayout implements AdapterView.OnItemSelectedListener, View.OnClickListener {
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
            et.setHint(getResources().getString(R.string.hint_chooseTime) + " " + (i + 1));
            et.setInputType(InputType.TYPE_NULL);
            et.setOnKeyListener(null);
            et.setOnClickListener(this);
            this.linearLayout.addView(et, i);
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
        switch (id) {
            case 201:
                this.showTimePicker((EditText) findViewById(201));
                break;
            case 202:
                this.showTimePicker((EditText) findViewById(202));
                break;
            case 203:
                this.showTimePicker((EditText) findViewById(203));
                break;
            case 204:
                this.showTimePicker((EditText) findViewById(204));
                break;
            case 205:
                this.showTimePicker((EditText) findViewById(205));
                break;
            default:
                Toaster.showToast(getContext(), id + "");
                break;
        }
    }

    private void showTimePicker(final EditText editText) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                try {
                    DateFormat timeFormat = DateUtils.getTimeFormat24();
                    Date time = timeFormat.parse(hour + ":" + minute);
                    editText.setText(DateUtils.getTimeFormat12().format(time));
                } catch (ParseException e) {
                    Logger.e("showTimePicker()", e.toString());
                }
            }
        }, 12, 0, false);
        timePickerDialog.show();
    }

    public String[] getData() {
        int count = Integer.parseInt(this.spnTimes.getSelectedItem().toString());
        String[] times = new String[count];
        for (int i = 0; i < count; i++) {
            EditText et = findViewById(200 + (i + 1));
            times[i] = et.getText().toString();
            if (times[i] == null || times[i].isEmpty()) {
                et.setError(getResources().getString(R.string.error_fieldEmpty));
                return null;
            }
        }
        return times;
    }

    public void setData(String[] data) {
        String[] spnTimesArray = getResources().getStringArray(R.array.spnTimes);
        for (int i = 0; i < spnTimesArray.length; i++) {
            // init spinner
            if (data.length == Integer.parseInt(spnTimesArray[i]))
                this.spnTimes.setSelection(i);
            // init textviews
//            int id = this.generateId(i);
//            EditText et = this.linearLayout.findViewById(id);
//            et.setText(data[i]);
        }

    }
}
