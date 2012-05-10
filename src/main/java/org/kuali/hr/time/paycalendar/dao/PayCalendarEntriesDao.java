package org.kuali.hr.time.paycalendar.dao;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

import java.util.Date;
import java.util.List;

public interface PayCalendarEntriesDao {

	public PayCalendarEntries getPayCalendarEntries(String hrPyCalendarEntriesId);

    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(String hrPyCalendarId, Date endPeriodDate);
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, Date currentDate);
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, PayCalendarEntries payCalendarEntries);
    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(String hrPyCalendarId, PayCalendarEntries payCalendarEntries);


	public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
	
	 public PayCalendarEntries getCalendarEntriesByBeginAndEndDate(Date beginPeriodDate, Date endPeriodDate);
}
