package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

public abstract class CalendarWeek {

    private List<CalendarDay> days = new ArrayList<CalendarDay>();

    public List<CalendarDay> getDays() {
        return days;
    }

    public void setDays(List<CalendarDay> days) {
        this.days = days;
    }
}
