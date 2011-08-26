package org.kuali.hr.time.paycalendar.dao;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

import java.util.Date;
import java.util.List;

public interface PayCalendarEntriesDao {

	public PayCalendarEntries getPayCalendarEntries(Long hrPyCalendarEntriesId);

    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long hrPyCalendarId, Date endPeriodDate);
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, Date currentDate);
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, PayCalendarEntries payCalendarEntries);
    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, PayCalendarEntries payCalendarEntries);


	public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
}
