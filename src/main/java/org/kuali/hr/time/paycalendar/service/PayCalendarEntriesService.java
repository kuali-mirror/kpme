package org.kuali.hr.time.paycalendar.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarEntriesService {

    /**
     * Method to directly access the PayCalendarEntries object by ID.
     *
     * @param payCalendarEntriesId The ID to retrieve.
     * @return a PayCalendarEntries object.
     */
	public PayCalendarEntries getPayCalendarEntries(Long payCalendarEntriesId);

    /**
     * Method to obtain the current PayCalendarEntries object based on the
     * indicated calendar and asOfDate.
     * @param payCalendarId The calendar to reference.
     * @param asOfDate The date reference point.
     * @return the current PayCalendarEntries effective by the asOfDate.
     */
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(Long payCalendarId, Date asOfDate);
    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long payCalendarId, Date endPeriodDate);

    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long payCalendarId, PayCalendarEntries pce);
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long payCalendarId, PayCalendarEntries pce);

    /**
     * Provides a list of PayCalendarEntries that are in the indicated window
     * of time from the as of date.
     * @param thresholdDays ± days from the asOfDate to form the window of time.
     * @param asOfDate The central date to query from.
     * @return A list of PayCalendarEntries.
     */
	public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
}
