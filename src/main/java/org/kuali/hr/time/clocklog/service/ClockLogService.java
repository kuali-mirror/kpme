package org.kuali.hr.time.clocklog.service;

import java.sql.Timestamp;
import java.util.List;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface ClockLogService {

    public void saveClockLog(ClockLog clockLog);
    public ClockLog getLastClockLog(String principalId);
    public ClockLog getLastClockLog(String principalId, String clockAction);
    /**
     * Build ClockLog based on criteria passed in
     * @param clockTimestamp
     * @param selectedAssign
     * @param timesheetDocument
     * @param clockAction
     * @param ip
     * @return
     */
	public ClockLog buildClockLog(Timestamp clockTimestamp, String selectedAssign, TimesheetDocument timesheetDocument, String clockAction, String ip);
	
	public List<ClockLog> getOpenClockLogs(PayCalendarEntries payCalendarEntry);
}
