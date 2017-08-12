package xyz.rimon.medicationassistant.ui.druglist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Logger;
import xyz.rimon.medicationassistant.core.CoreFragment;
import xyz.rimon.medicationassistant.events.DrugUpdatedEvent;
import xyz.rimon.medicationassistant.ui.druglist.adapter.DrugListAdapter;
import xyz.rimon.medicationassistant.utils.StorageUtils;

/**
 * Created by SAyEM on 8/11/17.
 */

@EFragment(R.layout.fragment_drug_list)
public class DrugListFragment extends CoreFragment {
    @ViewById
    RecyclerView drugListRecyclerView;

    @AfterViews
    void afterViews() {
        this.setupRecyclerView();
    }

    private void setupRecyclerView() {
        this.drugListRecyclerView.setAdapter(new DrugListAdapter(getActivity(), StorageUtils.readObjects(StorageUtils.ALL_DRUGS_FILE)));
        this.drugListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Subscribe
    public void onDrugUpdated(DrugUpdatedEvent event) {
        Logger.i("onDrugUpdated()", event.toString());
    }
}