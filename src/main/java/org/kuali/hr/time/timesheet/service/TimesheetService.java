package org.kuali.hr.time.timesheet.service;

import java.util.Date;

import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.exception.WorkflowException;

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
	public TimesheetDocument openTimesheetDocument(String principalId, Date payEndDate) throws WorkflowException;
	
	public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument);
	
	/**
	 * For a given document ID, return a fully populated time sheet document.
	 * @param documentId
	 * @return
	 */
	public TimesheetDocument getTimesheetDocument(Long documentId);
}
