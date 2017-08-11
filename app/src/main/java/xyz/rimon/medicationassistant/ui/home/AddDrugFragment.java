package xyz.rimon.medicationassistant.ui.home;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.commons.Validator;
import xyz.rimon.medicationassistant.components.TimePickersSelector;
import xyz.rimon.medicationassistant.core.CoreActivity;
import xyz.rimon.medicationassistant.core.CoreFragment;
import xyz.rimon.medicationassistant.domains.Drug;
import xyz.rimon.medicationassistant.events.DrugAddedEvent;
import xyz.rimon.medicationassistant.utils.StorageUtils;

/**
 * Created by SAyEM on 8/11/17.
 */

@EFragment(R.layout.fragment_add_drug)
public class AddDrugFragment extends CoreFragment {
    @ViewById
    Button btnAdd;

    @ViewById
    EditText etName;

    @ViewById
    Spinner spnType;

    @ViewById
    EditText etDays;

    @ViewById
    EditText etComment;

    @ViewById
    TimePickersSelector tpSelector;

    @AfterViews
    void afterViews() {

    }

    @Click
    void btnAdd() {
        if (tpSelector.getText() == null ||
                !Validator.isValid(getContext(), etName) ||
                !Validator.isValid(getContext(), etDays) ||
                !Validator.isValid(getContext(), etComment))
            return;

        Drug drug = new Drug(etName.getText().toString(),
                spnType.getSelectedItem().toString(),
                tpSelector.getText(),
                Integer.parseInt(etDays.getText().toString()),
                etComment.getText().toString());
        StorageUtils.writeObject(StorageUtils.ALL_DRUGS_FILE, drug);
        EventBus.getDefault().post(new DrugAddedEvent(drug));
    }


    @Subscribe
    public void onDrugAdded(DrugAddedEvent event) {
        ((CoreActivity) getActivity()).loadFragment(DrugListFragment_.builder().build());
        Toaster.showToast(getContext(), getResources().getString(R.string.msg_drugAdded));
    }

}
