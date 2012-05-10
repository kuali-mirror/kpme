package org.kuali.hr.time.paycalendar.service;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

import java.util.Date;
import java.util.List;

public interface PayCalendarEntriesService {

    /**
     * Method to directly access the PayCalendarEntries object by ID.
     *
     * @param hrPyCalendarEntriesId The ID to retrieve.
     * @return a PayCalendarEntries object.
     */
	public PayCalendarEntries getPayCalendarEntries(String hrPyCalendarEntriesId);

    /**
     * Method to obtain the current PayCalendarEntries object based on the
     * indicated calendar and asOfDate.
     * @param hrPyCalendarId The calendar to reference.
     * @param asOfDate The date reference point.
     * @return the current PayCalendarEntries effective by the asOfDate.
     */
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, Date asOfDate);
    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(String hrPyCalendarId, Date endPeriodDate);

    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, PayCalendarEntries pce);
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, PayCalendarEntries pce);

    /**
     * Provides a list of PayCalendarEntries that are in the indicated window
     * of time from the as of date.
     * @param thresholdDays ± days from the asOfDate to form the window of time.
     * @param asOfDate The central date to query from.
     * @return A list of PayCalendarEntries.
     */
	public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
    
    public PayCalendarEntries getCalendarEntriesByBeginAndEndDate(Date beginPeriodDate, Date endPeriodDate);
}
