package org.kuali.hr.time.timesummary.service;

import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.TimeSummary;

import java.util.List;

public interface TimeSummaryService {
	/**
	 * Fetch TimeSummary
	 * @param timesheetDocument
	 * @return
	 */
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument);

    List<String> getHeaderForSummary(CalendarEntries cal, List<Boolean> dayArrangements);
}
