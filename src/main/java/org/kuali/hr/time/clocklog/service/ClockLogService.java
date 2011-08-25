package org.kuali.hr.time.clocklog.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.sql.Timestamp;
import java.util.List;

public interface ClockLogService {

    public void saveClockLog(ClockLog clockLog);
    public ClockLog getLastClockLog(String principalId);
    public ClockLog getLastClockLog(String principalId, String clockAction);
    /**
     * Build ClockLog based on criteria passed in
     * @param clockTimestamp
     * @param assignment
     * @param timesheetDocument
     * @param clockAction
     * @param ip
     * @return
     */
	public ClockLog buildClockLog(Timestamp clockTimestamp, Assignment assignment, TimesheetDocument timesheetDocument, String clockAction, String ip);
	
	public List<ClockLog> getOpenClockLogs(PayCalendarEntries payCalendarEntry);

    ClockLog processClockLog(Timestamp clockTimestamp, Assignment assignment, PayCalendarEntries pe, String ip, java.sql.Date asOfDate, TimesheetDocument td, String clockAction, String principalId);
    
    public ClockLog getClockLog(Long tkClockLogId);
}
