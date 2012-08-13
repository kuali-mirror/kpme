package org.kuali.hr.lm.leaveSummary.service;

import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.time.calendar.CalendarEntries;

public interface LeaveSummaryService {
	 public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry) throws Exception;
}
