package xyz.rimon.medicationassistant.events;

import xyz.rimon.medicationassistant.domains.Drug;

/**
 * Created by SAyEM on 8/11/17.
 */

public class DrugAddedEvent {
    private Drug drug;

    public DrugAddedEvent(Drug drug) {
        this.drug = drug;
    }

    public Drug getDrug() {
        return drug;
    }

    @Override
    public String toString() {
        return "DrugAddedEvent{" +
                "drug=" + drug +
                '}';
    }
}
