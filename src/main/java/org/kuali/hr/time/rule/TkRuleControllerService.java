package org.kuali.hr.time.rule;

import java.util.List;

import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TkRuleControllerService {

    /**
     * This method mutates the List<TimeBlock> that is passed in. If you need
     * to reference old vs. new changes, be sure to copy/clone the list before
     * passing it to this method.
     */
	public void applyRules(String action, List<TimeBlock> timeBlocks, CalendarEntries payEntry, TimesheetDocument timesheetDocument, String principalId);
}
