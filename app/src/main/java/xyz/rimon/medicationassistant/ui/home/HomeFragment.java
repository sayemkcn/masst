package xyz.rimon.medicationassistant.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;

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
import xyz.rimon.medicationassistant.ui.home.adapter.HomeAdapter;
import xyz.rimon.medicationassistant.utils.DateUtils;
import xyz.rimon.medicationassistant.utils.NetworkUtils;
import xyz.rimon.medicationassistant.utils.StorageUtils;

/**
 * Created by SAyEM on 8/12/17.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends CoreFragment {
    @ViewById
    RecyclerView upcomingMedRecyclerView;

    @ViewById
    TextView txtNoItem;

    @ViewById
    NativeExpressAdView adView;

    @AfterViews
    void afterViews() {
        List<Drug> drugList = this.getUpcomingMedications(StorageUtils.readObjects(StorageUtils.ALL_DRUGS_FILE));
        if (drugList != null && !drugList.isEmpty()) {
            this.txtNoItem.setVisibility(View.GONE);
            this.upcomingMedRecyclerView.setVisibility(View.VISIBLE);
        }
        upcomingMedRecyclerView.setAdapter(new HomeAdapter(getActivity(), drugList));
        upcomingMedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // load ads
        this.loadAds();
    }

    private void loadAds() {
        // Set its video options.
        this.adView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());

        // The VideoController can be used to get lifecycle events and info about an ad's video
        // asset. One will always be returned by getVideoController, even if the ad has no video
        // asset.
        final VideoController vc = this.adView.getVideoController();
        vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoEnd() {
                Logger.d("ADMOB_VIDEO_CONTROLLER", "Video playback is finished.");
                super.onVideoEnd();
            }
        });

        // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
        // loading.
        this.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (vc.hasVideoContent()) {
                    Logger.d("ADMOB", "Received an ad that contains a video asset.");
                } else {
                    Logger.d("ADMOB", "Received an ad that does not contain a video asset.");
                }
            }
        });
        this.adView.loadAd(new AdRequest.Builder().addTestDevice(getResources().getString(R.string.test_device_id_1)).build());
        // set adview visibility
        if (NetworkUtils.isConnected(getActivity()))
            this.adView.setVisibility(View.VISIBLE);
        else this.adView.setVisibility(View.GONE);
    }

    private List<Drug> getUpcomingMedications(List<Drug> drugList) {
        List<Drug> newList = new ArrayList<>();
        for (int i = 0; i < drugList.size(); i++) {
            Drug drug = drugList.get(i);
            for (int j = 0; j < drug.getTimes().length; j++) {
                try {
                    Date time = DateUtils.getTimeFormat12().parse(drug.getTimes()[j]);
                    String currDateTimeString = DateUtils.getTimeFormat12().format(new Date());
                    Date currTime = DateUtils.getTimeFormat12().parse(currDateTimeString);
                    // check if medication time is after current time & medication period is not over
                    // medication period = drug created date + days to take medication
                    if (time.after(currTime) && !drug.isMedicationOver())
                        if (!newList.contains(drug)) // if not already added
                            newList.add(drug);
                } catch (ParseException e) {
                    Logger.e("getUpcomingMedications()", e.toString());
                }
            }
        }
        return newList;
    }
}
