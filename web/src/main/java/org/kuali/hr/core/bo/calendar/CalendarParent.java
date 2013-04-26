/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.core.bo.calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.tklm.time.service.TkServiceLocator;

public abstract class CalendarParent implements Serializable {
    private List<CalendarWeek> weeks = new ArrayList<CalendarWeek>();
    private CalendarEntry calendarEntry;
    private DateTime beginDateTime;
    private DateTime endDateTime;

    public CalendarParent(CalendarEntry calendarEntry) {
        this.calendarEntry = calendarEntry;
        if (calendarEntry != null) {
            this.beginDateTime = calendarEntry.getBeginPeriodLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
            this.endDateTime = calendarEntry.getEndPeriodLocalDateTime().toDateTime(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
        }
    }

    protected CalendarParent() {
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

    public CalendarEntry getCalendarEntry() {
        return calendarEntry;
    }

    public void setCalendarEntry(CalendarEntry calendarEntry) {
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
            for (int i = firstDay; i < lastDay; i++) {
                StringBuilder builder = new StringBuilder("");
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
