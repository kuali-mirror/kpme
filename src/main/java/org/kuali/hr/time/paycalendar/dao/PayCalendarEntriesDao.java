package org.kuali.hr.time.paycalendar.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarEntriesDao {

	public PayCalendarEntries getPayCalendarEntries(Long payCalendarEntriesId);

    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long payCalendarId, Date endPeriodDate);
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(Long payCalendarId, Date currentDate);
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long payCalendarId, PayCalendarEntries payCalendarEntries);
    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long payCalendarId, PayCalendarEntries payCalendarEntries);


	public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
}
