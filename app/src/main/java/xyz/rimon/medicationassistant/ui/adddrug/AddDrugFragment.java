package xyz.rimon.medicationassistant.ui.adddrug;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Toaster;
import xyz.rimon.medicationassistant.commons.Validator;
import xyz.rimon.medicationassistant.components.TimePickersSelector;
import xyz.rimon.medicationassistant.core.CoreActivity;
import xyz.rimon.medicationassistant.core.CoreFragment;
import xyz.rimon.medicationassistant.domains.Drug;
import xyz.rimon.medicationassistant.events.DrugAddedEvent;
import xyz.rimon.medicationassistant.ui.druglist.DrugListFragment_;
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
    CheckBox ckAlert;

    @ViewById
    TimePickersSelector tpSelector;

    @FragmentArg
    boolean isEdit;

    @FragmentArg
    int position;

    @AfterViews
    void afterViews() {
        if (isEdit)
            initData();
    }

    private void initData() {
        List<Drug> drugList = StorageUtils.readObjects(StorageUtils.ALL_DRUGS_FILE);
        Drug drug = drugList.get(position);

        this.etName.setText(drug.getName());
        this.etDays.setText(String.valueOf(drug.getDaysCount()));
        this.etComment.setText(drug.getComment());
        this.ckAlert.setChecked(drug.isAlert());
        // init drug type spinner
        String[] drugType = getResources().getStringArray(R.array.spnDrugTypes);
        for (int i = 0; i < drugType.length; i++)
            if (drug.getType().equals(drugType[i]))
                this.spnType.setSelection(i);
        // init timpickerselector custom component
        this.tpSelector.setData(drug.getTimes());
    }

    @Click
    void btnAdd() {
        if (tpSelector.getData() == null ||
                !Validator.isValid(getContext(), etName) ||
                !Validator.isValid(getContext(), etDays) ||
                !Validator.isValid(getContext(), etComment))
            return;

        Drug drug = new Drug(etName.getText().toString(),
                spnType.getSelectedItem().toString(),
                tpSelector.getData(),
                Integer.parseInt(etDays.getText().toString()),
                etComment.getText().toString(), new Date());
        // set alert
        drug.setAlert(this.ckAlert.isChecked());

        if (isEdit)
            StorageUtils.writeObject(StorageUtils.ALL_DRUGS_FILE, drug, position);
        else
            StorageUtils.writeObject(StorageUtils.ALL_DRUGS_FILE, drug);
        EventBus.getDefault().post(new DrugAddedEvent(drug));
    }


    @Subscribe
    public void onDrugAdded(DrugAddedEvent event) {
        ((CoreActivity) getActivity()).loadFragment(DrugListFragment_.builder().build());
        Toaster.showToast(getContext(), getResources().getString(R.string.msg_drugAdded));
    }

}
