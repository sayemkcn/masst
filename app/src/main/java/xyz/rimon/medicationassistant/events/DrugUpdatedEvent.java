package xyz.rimon.medicationassistant.events;

import xyz.rimon.medicationassistant.domains.Drug;

/**
 * Created by SAyEM on 8/12/17.
 */

public class DrugUpdatedEvent {
    private Drug drug;

    public DrugUpdatedEvent(Drug drug) {
        this.drug = drug;
    }

    public Drug getDrug() {
        return drug;
    }

    @Override
    public String toString() {
        return "DrugUpdatedEvent{" +
                "drug=" + drug +
                '}';
    }
}
