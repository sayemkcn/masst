package xyz.rimon.medicationassistant.domains;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by SAyEM on 8/11/17.
 */

public class Drug implements Serializable {
    private String name;
    private String type;
    private String[] times;
    private int daysCount;
    private String comment;

    public Drug() {
    }

    public Drug(String name, String type, String[] times, int daysCount, String comment) {
        this.name = name;
        this.type = type;
        this.times = times;
        this.daysCount = daysCount;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", times=" + Arrays.toString(times) +
                ", daysCount=" + daysCount +
                ", comment='" + comment + '\'' +
                '}';
    }
}
