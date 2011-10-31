package org.kuali.hr.time.calendar.dao;

import org.kuali.hr.time.calendar.CalendarEntries;

import java.util.Date;
import java.util.List;

public interface CalendarEntriesDao {

	public CalendarEntries getPayCalendarEntries(Long hrPyCalendarEntriesId);

    public CalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long hrPyCalendarId, Date endPeriodDate);
	public CalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, Date currentDate);
    public CalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, CalendarEntries payCalendarEntries);
    public CalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, CalendarEntries payCalendarEntries);


	public List<CalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
}
