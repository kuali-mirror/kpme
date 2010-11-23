package org.kuali.hr.time.rule;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TkRuleControllerService {
	public List<TimeBlock> applyRules(String action, List<TimeBlock> timeBlocks, PayCalendarDates payEntry, TimesheetDocument timesheetDocument);
}
