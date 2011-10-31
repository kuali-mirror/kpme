package org.kuali.hr.time.calendar.service;

import org.kuali.hr.time.calendar.CalendarEntries;

import java.util.Date;
import java.util.List;

public interface CalendarEntriesService {

    /**
     * Method to directly access the PayCalendarEntries object by ID.
     *
     * @param hrPyCalendarEntriesId The ID to retrieve.
     * @return a PayCalendarEntries object.
     */
	public CalendarEntries getPayCalendarEntries(Long hrCalendarEntriesId);

    /**
     * Method to obtain the current PayCalendarEntries object based on the
     * indicated calendar and asOfDate.
     * @param hrPyCalendarId The calendar to reference.
     * @param asOfDate The date reference point.
     * @return the current PayCalendarEntries effective by the asOfDate.
     */
	public CalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(Long hrCalendarId, Date asOfDate);
    public CalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long hrCalendarId, Date endPeriodDate);

    public CalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long hrCalendarId, CalendarEntries pce);
    public CalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long hrCalendarId, CalendarEntries pce);

    /**
     * Provides a list of PayCalendarEntries that are in the indicated window
     * of time from the as of date.
     * @param thresholdDays ± days from the asOfDate to form the window of time.
     * @param asOfDate The central date to query from.
     * @return A list of PayCalendarEntries.
     */
	public List<CalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
}
