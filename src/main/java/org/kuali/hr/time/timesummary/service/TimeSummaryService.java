package org.kuali.hr.time.timesummary.service;

import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.TimeSummary;

public interface TimeSummaryService {
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument);
}
