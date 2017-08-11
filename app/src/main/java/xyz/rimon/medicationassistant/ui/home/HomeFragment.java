package xyz.rimon.medicationassistant.ui.home;

import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.components.TimePickersSelector;
import xyz.rimon.medicationassistant.core.CoreFragment;

/**
 * Created by SAyEM on 8/11/17.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends CoreFragment {
    @ViewById
    Button btnAdd;

    @ViewById
    TimePickersSelector tpSelector;

    @AfterViews
    void afterViews() {

    }

    @Click
    void btnAdd() {
        Toaster.showToast(getActivity(), Arrays.toString(this.tpSelector.getText()));
    }

}
