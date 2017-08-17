package xyz.rimon.medicationassistant.commons;

import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.domains.Drug;
import xyz.rimon.medicationassistant.utils.DateUtils;
import xyz.rimon.medicationassistant.utils.NetworkUtils;
import xyz.rimon.medicationassistant.utils.StorageUtils;

/**
 * Created by SAyEM on 8/12/17.
 */

public class Commons {
    private Commons() {
    }

    public static List<Drug> getMatchedDrugs() {
        List<Drug> drugList = StorageUtils.readObjects(StorageUtils.ALL_DRUGS_FILE);
        List<Drug> newList = new ArrayList<>();
        for (int i = 0; i < drugList.size(); i++) {
            Drug drug = drugList.get(i);
            for (int j = 0; j < drug.getTimes().length; j++) {
                if (drug.getTimes()[j].equals(DateUtils.getTimeFormat12().format(new Date()))) {
                    // check if alert enabled
                    if (drug.isAlert() && !drug.isMedicationOver())
                        newList.add(drug);
                }
            }
        }
        return newList;
    }

    public static String getDrugNamesString(List<Drug> drugList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < drugList.size(); i++) {
            stringBuilder.append(drugList.get(i).getName());
            if (i != drugList.size() - 1)
                stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    public static final class Ads {
        private Ads() {
        }

        public static void loadNativeAds(Context context, NativeExpressAdView adView) {
            // Set its video options.
            adView.setVideoOptions(new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build());

            // The VideoController can be used to get lifecycle events and info about an ad's video
            // asset. One will always be returned by getVideoController, even if the ad has no video
            // asset.
            final VideoController vc = adView.getVideoController();
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    Logger.d("ADMOB_VIDEO_CONTROLLER", "Video playback is finished.");
                    super.onVideoEnd();
                }
            });

            // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
            // loading.
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (vc.hasVideoContent()) {
                        Logger.d("ADMOB", "Received an ad that contains a video asset.");
                    } else {
                        Logger.d("ADMOB", "Received an ad that does not contain a video asset.");
                    }
                }
            });
            adView.loadAd(new AdRequest.Builder().addTestDevice(context.getString(R.string.test_device_id_1)).build());
            // set adview visibility
            if (NetworkUtils.isConnected(context))
                adView.setVisibility(View.VISIBLE);
            else adView.setVisibility(View.GONE);
        }
    }

}
