package xyz.rimon.medicationassistant.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Logger;
import xyz.rimon.medicationassistant.core.CoreFragment;
import xyz.rimon.medicationassistant.domains.Drug;
import xyz.rimon.medicationassistant.ui.druglist.adapter.DrugListAdapter;
import xyz.rimon.medicationassistant.ui.home.adapter.HomeAdapter;
import xyz.rimon.medicationassistant.utils.DateUtils;
import xyz.rimon.medicationassistant.utils.StorageUtils;

/**
 * Created by SAyEM on 8/12/17.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends CoreFragment {
    @ViewById
    RecyclerView upcomingMedRecyclerView;

    @AfterViews
    void afterViews() {
        List<Drug> drugList = this.getUpcomingMedications(StorageUtils.readObjects(StorageUtils.ALL_DRUGS_FILE));
        upcomingMedRecyclerView.setAdapter(new HomeAdapter(getActivity(), drugList));
        upcomingMedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private List<Drug> getUpcomingMedications(List<Drug> drugList) {
        List<Drug> newList = new ArrayList<>();
        for (int i = 0; i < drugList.size(); i++) {
            Drug drug = drugList.get(i);
            for (int j = 0; j < drug.getTimes().length; j++) {
                try {
                    Date date = DateUtils.getTimeFormat12().parse(drug.getTimes()[j]);
                    String currDateString = DateUtils.getTimeFormat12().format(new Date());
                    Date currDate = DateUtils.getTimeFormat12().parse(currDateString);
                    if (date.after(currDate))
                        newList.add(drug);
                } catch (ParseException e) {
                    Logger.e("getUpcomingMedications()", e.toString());
                }
            }
        }
        return newList;
    }
}
