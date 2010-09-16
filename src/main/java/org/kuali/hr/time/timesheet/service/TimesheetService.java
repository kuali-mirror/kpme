package org.kuali.hr.time.timesheet.service;

import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.exception.WorkflowException;

public interface TimesheetService {

	/**
	 * Opens the timesheet document for the user at the given payEndDate provided.
	 * If the timesheet does not exist, it is created.
	 * @param principalId
	 * @return
	 */
	public TimesheetDocument openTimesheetDocument(String principalId, PayCalendarDates payCalendarDates) throws WorkflowException;
	
	public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument);
	
	/**
	 * For a given document ID, return a fully populated time sheet document.
	 * 
	 * Fully populated means: TimeBlocks, Jobs, Assignments
	 * 
	 * @param documentId
	 * @return
	 */
	public TimesheetDocument getTimesheetDocument(Long documentId);
}
