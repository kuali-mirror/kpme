package org.kuali.hr.time.calendar.dao;

import org.kuali.hr.time.calendar.CalendarEntries;

import java.util.Date;
import java.util.List;

public interface CalendarEntriesDao {

	public void saveOrUpdate(CalendarEntries calendarEntries);
	public CalendarEntries getCalendarEntries(String hrPyCalendarEntriesId);

    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(String hrPyCalendarId, Date endPeriodDate);
	public CalendarEntries getCurrentCalendarEntriesByCalendarId(String hrPyCalendarId, Date currentDate);
    public CalendarEntries getNextCalendarEntriesByCalendarId(String hrPyCalendarId, CalendarEntries calendarEntries);
    public CalendarEntries getPreviousCalendarEntriesByCalendarId(String hrPyCalendarId, CalendarEntries calendarEntries);


	public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
	public List<CalendarEntries> getFutureCalendarEntries(String hrCalendarId, Date currentDate, int numberOfEntries);

    public CalendarEntries getCalendarEntriesByBeginAndEndDate(Date beginPeriodDate, Date endPeriodDate);
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarId(String hrCalendarId);
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year);
}
