package org.kuali.hr.time.timesummary.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.TimeSummary;

public interface TimeSummaryService {
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument);
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument, List<TimeBlock> timeBlocks);
}
