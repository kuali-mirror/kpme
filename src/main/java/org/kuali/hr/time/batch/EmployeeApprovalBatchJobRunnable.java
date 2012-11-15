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
package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.document.DocumentStatus;

public class EmployeeApprovalBatchJobRunnable extends BatchJobEntryRunnable {

    public EmployeeApprovalBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

	@Override
	public void doWork() throws Exception {
		BatchJobEntry employeeApprovalBatchJobEntry = TkServiceLocator.getBatchJobEntryService().getBatchJobEntry(getTkBatchJobEntryId());
		String principalId = employeeApprovalBatchJobEntry.getPrincipalId();
		String documentId = employeeApprovalBatchJobEntry.getDocumentId();
		String hrPyCalendarId = employeeApprovalBatchJobEntry.getHrPyCalendarEntryId();
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrPyCalendarId);
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		
		if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
			TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
			if (timesheetDocumentHeader != null) {
				TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
				if (DocumentStatus.INITIATED.getCode().equals(timesheetDocument.getDocumentHeader().getDocumentStatus())
	                    || DocumentStatus.SAVED.getCode().equals(timesheetDocument.getDocumentHeader().getDocumentStatus())) {
					TkServiceLocator.getTimesheetService().routeTimesheet(principalId, timesheetDocument, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
	            }
			}
		} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
			LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
			if (leaveCalendarDocumentHeader != null) {
				LeaveCalendarDocument leaveCalendarDocument = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
				if (DocumentStatus.INITIATED.getCode().equals(leaveCalendarDocument.getDocumentHeader().getDocumentStatus())
	                    || DocumentStatus.SAVED.getCode().equals(leaveCalendarDocument.getDocumentHeader().getDocumentStatus())) {
					TkServiceLocator.getLeaveCalendarService().routeLeaveCalendar(principalId, leaveCalendarDocument, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
				}
			}
		}
	}

}
