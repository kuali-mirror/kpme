package org.kuali.hr.time.calendar;

public abstract class CalendarDay {

    private String dayNumberString;
    private String dayNumberDelta;
    private Boolean gray;

    public String getDayNumberDelta() {
        return dayNumberDelta;
    }

    public void setDayNumberDelta(String dayNumberDelta) {
        this.dayNumberDelta = dayNumberDelta;
    }

    public String getDayNumberString() {
        return dayNumberString;
    }

    public void setDayNumberString(String dayNumberString) {
        this.dayNumberString = dayNumberString;
    }

    public Boolean getGray() {
        return gray;
    }

    public void setGray(Boolean gray) {
        this.gray = gray;
    }
}
