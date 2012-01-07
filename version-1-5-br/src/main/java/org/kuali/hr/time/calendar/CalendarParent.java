package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.time.service.base.TkServiceLocator;

public abstract class CalendarParent {
    private List<CalendarWeek> weeks = new ArrayList<CalendarWeek>();
    private CalendarEntries calendarEntry;
    private DateTime beginDateTime;
    private DateTime endDateTime;

    public CalendarParent(CalendarEntries calendarEntry) {
        this.calendarEntry = calendarEntry;
        this.beginDateTime = calendarEntry.getBeginLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
        this.endDateTime = calendarEntry.getEndLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
    }


    public DateTime getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(DateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public CalendarEntries getCalendarEntry() {
        return calendarEntry;
    }

    public void setCalendarEntry(CalendarEntries calendarEntry) {
        this.calendarEntry = calendarEntry;
    }

    public List<CalendarWeek> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<CalendarWeek> weeks) {
        this.weeks = weeks;
    }

    /**
     * Provides the calendar title / heading. If the Pay Calendar entry spans
     * multiple months, you get Abbreviated Month name + year of both the
     * beginning month and the ending month.
     *
     * @return String for calendar title use.
     */
    public String getCalendarTitle() {
        StringBuilder sb = new StringBuilder();

        if (getBeginDateTime().getMonthOfYear() == getEndDateTime().getMonthOfYear() ||
                (getBeginDateTime().getMonthOfYear() != getEndDateTime().getMonthOfYear()
                        && getEndDateTime().getDayOfMonth() == 1 && getEndDateTime().getSecondOfDay() == 0)) {
            sb.append(getBeginDateTime().toString("MMMM y"));
        } else {
            sb.append(getBeginDateTime().toString("MMM y"));
            sb.append(" - ");
            sb.append(getEndDateTime().toString("MMM y"));
        }

        return sb.toString();
    }

    /**
     * Assumption of 7 "days" per week, or 7 "blocks" per row.
     *
     * @return A list of string titles for each row block (day)
     */
    public List<String> getCalendarDayHeadings() {
        List<String> dayStrings = new ArrayList<String>(7);
        // always render from Sunday
        int firstDay = 0 - getBeginDateTime().getDayOfWeek();
        int lastDay = firstDay + 7;

        if (getBeginDateTime().getMinuteOfDay() == 0) {
            // "Standard" days.
            for (int i = firstDay; i < lastDay; i++) {
                DateTime currDay = getBeginDateTime().plusDays(i);
                dayStrings.add(currDay.toString("E"));
            }
        } else {
            // Day Split Strings
            StringBuilder builder = new StringBuilder();
            for (int i = firstDay; i < lastDay; i++) {
                DateTime currStart = getBeginDateTime().plusDays(i);
                DateTime currEnd = getBeginDateTime().plusDays(i);

                builder.append(currStart.toString("E HH:mm"));
                builder.append(" - ");
                builder.append(currEnd.toString("E HH:mm"));

                dayStrings.add(builder.toString());
            }
        }

        return dayStrings;
    }

}
