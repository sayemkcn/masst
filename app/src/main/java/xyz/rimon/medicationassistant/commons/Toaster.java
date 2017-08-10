package xyz.rimon.medicationassistant.commons;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SAyEM on 8/11/17.
 */

public final class Toaster {
    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
