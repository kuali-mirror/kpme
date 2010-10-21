package org.kuali.hr.time.clocklog.service;

import java.sql.Timestamp;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface ClockLogService {

    public void saveClockLog(ClockLog clockLog);
    public ClockLog getLastClockLog(String principalId);
	public ClockLog saveClockAction(Timestamp clockTimestamp, String selectedAssign, TimesheetDocument timesheetDocument, String clockAction);
    
}
