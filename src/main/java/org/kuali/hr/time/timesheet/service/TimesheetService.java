package org.kuali.hr.time.timesheet.service;

import java.util.Date;

import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TimesheetService {

	/**
	 * This method essentially:
	 * 
	 * gets pay calendar (provides pay end date)
	 * gets assignments for principal id
	 * 
	 * @param principalId
	 * @return
	 */
	public TimesheetDocument openTimesheetDocument(String principalId, Date payEndDate);
	
	public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument);
}
