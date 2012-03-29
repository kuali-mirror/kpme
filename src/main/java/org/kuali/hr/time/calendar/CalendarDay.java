package org.kuali.hr.time.calendar;

public abstract class CalendarDay {

    private String dayNumberString;
    private int dayNumberDelta;
    private Boolean gray;
    private String dateString;

    public int getDayNumberDelta() {
        return dayNumberDelta;
    }

    public void setDayNumberDelta(int dayNumberDelta) {
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

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
