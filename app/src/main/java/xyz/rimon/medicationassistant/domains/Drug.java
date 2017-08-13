package xyz.rimon.medicationassistant.domains;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import xyz.rimon.medicationassistant.utils.DateUtils;

/**
 * Created by SAyEM on 8/11/17.
 */

public class Drug implements Serializable {
    private String name;
    private String type;
    private String[] times;
    private int daysCount;
    private String comment;
    private boolean alert;
    private Date created;

    public Drug() {
    }

    public Drug(String name, String type, String[] times, int daysCount, String comment, Date created) {
        this.name = name;
        this.type = type;
        this.times = times;
        this.daysCount = daysCount;
        this.comment = comment;
        this.created = created;
    }

    public static final class Type {
        private Type() {
        }

        public static final String TYPE_TABLET = "Tablet";
        public static final String TYPE_CAPSULE = "Capsule";
        public static final String TYPE_SYRUP = "Syrup";
    }

    public String getNextTime() throws ParseException {
        DateFormat dateFormat = DateUtils.getTimeFormat12();
        Date date;
        Date currDate;
        String currDateString;
        for (String time : this.times) {
            date = dateFormat.parse(time);
            currDateString = DateUtils.getTimeFormat12().format(new Date());
            currDate = DateUtils.getTimeFormat12().parse(currDateString);
            if (date.after(currDate))
                return time;
        }
        return null;
    }

    public Date getMedicationEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(created);
        calendar.add(Calendar.DAY_OF_MONTH, this.daysCount);
        return calendar.getTime();
    }

    public boolean isMedicationOver() {
        return new Date().after(this.getMedicationEndDate());
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

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", times=" + Arrays.toString(times) +
                ", daysCount=" + daysCount +
                ", comment='" + comment + '\'' +
                ", alert=" + alert +
                ", created=" + created +
                '}';
    }
}
