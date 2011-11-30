package org.kuali.hr.time.calendar;

import org.joda.time.DateTime;
import org.kuali.hr.time.util.TkConstants;

public class LeaveCalendar extends CalendarParent {

    public LeaveCalendar(CalendarEntries calendarEntry) {
        super(calendarEntry);

        DateTime currDateTime = getBeginDateTime();
        DateTime endDateTime = getEndDateTime();
        DateTime firstDay = getBeginDateTime();

        // Fill in the days if the first day or end day is in the middle of the week
        // Monday = 1; Sunday = 7
        if (currDateTime.getDayOfWeek() > 0) {
            currDateTime = currDateTime.minusDays(currDateTime.getDayOfWeek());
            firstDay = currDateTime;
        }
        if (endDateTime.getDayOfWeek() < 7) {
            endDateTime = endDateTime.plusDays(7 - endDateTime.getDayOfWeek());
        }

        LeaveCalendarWeek leaveCalendarWeek = new LeaveCalendarWeek();

        while (currDateTime.isBefore(endDateTime)) {
            //Create weeks
            LeaveCalendarDay leaveCalendarDay = new LeaveCalendarDay();

            // If the day is not within the current pay period, mark them as read only (setGray)
            if (currDateTime.isBefore(getBeginDateTime()) || currDateTime.isAfter(getEndDateTime())) {
                leaveCalendarDay.setGray(true);
            } else {
                // This is for the div id of the days on the calendar.
                // It creates a day id like day_11/01/2011 which will make day parsing easier in the javascript.
                leaveCalendarDay.setDayNumberDelta(currDateTime.toString(TkConstants.DT_BASIC_DATE_FORMAT));
//                leaveCalendarDay.setLedgers()
            }
            leaveCalendarWeek.getDays().add(leaveCalendarDay);

            // cut a week on Sat.
            if (currDateTime.getDayOfWeek() == 6 && currDateTime != firstDay) {
                getWeeks().add(leaveCalendarWeek);
                leaveCalendarWeek = new LeaveCalendarWeek();
            }

            leaveCalendarDay.setDayNumberString(currDateTime.dayOfMonth().getAsShortText());

            currDateTime = currDateTime.plusDays(1);
        }

        if (!leaveCalendarWeek.getDays().isEmpty()) {
            getWeeks().add(leaveCalendarWeek);
        }
    }
}
