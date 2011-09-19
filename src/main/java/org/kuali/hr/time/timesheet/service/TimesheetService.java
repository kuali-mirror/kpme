package org.kuali.hr.time.timesheet.service;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.exception.WorkflowException;

import java.util.Date;
import java.util.List;

public interface TimesheetService {

	/**
	 * Opens the timesheet document for the user at the given payEndDate provided.
	 * If the timesheet does not exist, it is created.
	 * @param principalId
	 * @return
	 */
	public TimesheetDocument openTimesheetDocument(String principalId, PayCalendarEntries payCalendarDates) throws WorkflowException;
	/**
	 * Route the given timesheet
	 * @param principalId
	 * @param timesheetDocument
	 */
	public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument);

    public void approveTimesheet(String principalId, TimesheetDocument timesheetDocument);

    public void disapproveTimesheet(String principalId, TimesheetDocument timesheetDocument);

	/**
	 * For a given document ID, return a fully populated time sheet document.
	 *
	 * Fully populated means: TimeBlocks, Jobs, Assignments
	 *
	 * @param documentId
	 * @return
	 */
	public TimesheetDocument getTimesheetDocument(String documentId);
	/**
	 * Is user a Clock in/out person or do they manually enter TimeBlocks
	 * @return
	 */
	public boolean isSynchronousUser();
	/**
	 * Fetch TimeBlocks for previous pay periods
	 * @param principalId
	 * @param payBeginDate
	 * @return
	 */
	public List<TimeBlock> getPrevDocumentTimeBlocks(String principalId, Date payBeginDate);
	/**
	 * Load holidays on given timesheet
	 * @param timesheetDocument
	 * @param principalId
	 * @param beginDate
	 * @param endDate
	 */
	public void loadHolidaysOnTimesheet(TimesheetDocument timesheetDocument, String principalId, Date beginDate, Date endDate);
	/**
	 * Delete a timesheet(used for testing only)
	 * @param documentId
	 */
	public void deleteTimesheet(String documentId);
	
	public void resetTimeBlock(List<TimeBlock> timeBlock);
}
