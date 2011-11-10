package org.kuali.hr.time.clocklog.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.sql.Timestamp;
import java.util.List;

public interface ClockLogService {
	/**
	 * Save clock log 
	 * @param clockLog
	 */
    public void saveClockLog(ClockLog clockLog);
    /**
     * Fetch last clock log for principal id
     * @param principalId
     * @return
     */
    public ClockLog getLastClockLog(String principalId);
    
    /**
     * Fetch last clock log for principal id and clock action
     * @param principalId
     * @param clockAction
     * @return
     */
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
	
	/**
	 * Fetch open clock logs by pay calendar entry
	 * @param calendarEntry
	 * @return
	 */
	public List<ClockLog> getOpenClockLogs(CalendarEntries calendarEntry);

	/**
	 * Process clock log created
	 * @param clockTimestamp
	 * @param assignment
	 * @param pe
	 * @param ip
	 * @param asOfDate
	 * @param td
	 * @param clockAction
	 * @param principalId
	 * @return
	 */
    ClockLog processClockLog(Timestamp clockTimestamp, Assignment assignment, CalendarEntries pe, String ip, java.sql.Date asOfDate, TimesheetDocument td, String clockAction, String principalId);
    
    /**
     * Fetch clock log by id
     * @param tkClockLogId
     * @return
     */
    public ClockLog getClockLog(Long tkClockLogId);
}
