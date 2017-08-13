package xyz.rimon.medicationassistant.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xyz.rimon.medicationassistant.domains.Drug;
import xyz.rimon.medicationassistant.utils.DateUtils;
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

}
