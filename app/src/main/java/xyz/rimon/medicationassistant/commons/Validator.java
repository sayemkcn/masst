package xyz.rimon.medicationassistant.commons;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import xyz.rimon.medicationassistant.R;

/**
 * Created by SAyEM on 8/11/17.
 */

public class Validator {
    private Validator() {
    }

    public static boolean isValid(Context context, EditText et) {
        if (et == null) return false;
        if (et.getText().toString().isEmpty()) {
            et.setError(context.getResources().getString(R.string.error_firldEmpty));
            return false;
        }
        return true;
    }

}
