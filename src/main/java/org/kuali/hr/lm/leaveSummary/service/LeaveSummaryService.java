package org.kuali.hr.lm.leaveSummary.service;

import java.util.List;

import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.time.calendar.CalendarEntries;

public interface LeaveSummaryService {
	 public LeaveSummary getLeaveSummary(String principalId, CalendarEntries calendarEntry) throws Exception;
 
	 /*
	  * returns the header to be displayed on approval tab for given calendarEntry
	  */
	 public List<String> getHeaderForSummary(CalendarEntries cal);
}
