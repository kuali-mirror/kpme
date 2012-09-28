/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.timesheet.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;

public interface TimesheetService {

	/**
	 * Opens the timesheet document for the user at the given payEndDate provided.
	 * If the timesheet does not exist, it is created.
	 * @param principalId
	 * @return
	 */
	public TimesheetDocument openTimesheetDocument(String principalId, CalendarEntries payCalendarDates) throws WorkflowException;
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

    void approveTimesheet(String principalId, TimesheetDocument timesheetDocument, String action);

    void routeTimesheet(String action, String principalId, TimesheetDocument timesheetDocument);
}
