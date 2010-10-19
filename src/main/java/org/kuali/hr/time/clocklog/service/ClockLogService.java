package org.kuali.hr.time.clocklog.service;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface ClockLogService {

    public void saveClockLog(ClockLog clockLog);
    public ClockLog getLastClockLog(String principalId);
	public ClockLog saveClockAction(String selectedAssign, TimesheetDocument timesheetDocument, String clockAction);
    
}
