package org.kuali.hr.time.calendar;

import org.kuali.hr.time.timeblock.TimeHourDetail;

public class TimeHourDetailRenderer {
    private TimeHourDetail timeHourDetail;

    public TimeHourDetailRenderer(TimeHourDetail d) {
        this.timeHourDetail = d;
    }

    public TimeHourDetail getTimeHourDetail() {
        return timeHourDetail;
    }

    public String getTitle() {
        return timeHourDetail.getEarnCode();
    }

    public String getHours() {
        return timeHourDetail.getHours().toString();
    }
}
