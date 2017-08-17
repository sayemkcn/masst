package xyz.rimon.medicationassistant.ui.druglist;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.NativeExpressAdView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Commons;
import xyz.rimon.medicationassistant.commons.Logger;
import xyz.rimon.medicationassistant.core.CoreFragment;
import xyz.rimon.medicationassistant.domains.Drug;
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

    @ViewById
    TextView txtNoItem;

    @ViewById
    NativeExpressAdView adView;

    @AfterViews
    void afterViews() {
        this.setupRecyclerView();
    }

    private void setupRecyclerView() {
        List<Drug> drugList = StorageUtils.readObjects(StorageUtils.ALL_DRUGS_FILE);
        if (drugList != null && !drugList.isEmpty()) {
            this.txtNoItem.setVisibility(View.GONE);
            this.drugListRecyclerView.setVisibility(View.VISIBLE);
        }
        this.drugListRecyclerView.setAdapter(new DrugListAdapter(getActivity(), drugList));
        this.drugListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Load ads
        Commons.Ads.loadNativeAds(getActivity(),this.adView);
    }

    @Subscribe
    public void onDrugUpdated(DrugUpdatedEvent event) {
        Logger.i("onDrugUpdated()", event.toString());
    }
}
