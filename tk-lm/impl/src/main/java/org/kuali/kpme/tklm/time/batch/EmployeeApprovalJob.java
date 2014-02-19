/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.batch;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.batch.BatchJob;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EmployeeApprovalJob extends BatchJob {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
//		String documentId = jobDataMap.getString("documentId");

		CalendarEntry calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
		Calendar calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		
		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
    	DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
		
		if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
	        List<TimesheetDocumentHeader> timesheetDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	       
	        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
	        	if(timesheetDocumentHeader != null) {
	        		String docId = timesheetDocumentHeader.getDocumentId();
	        		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(docId);
	        		if(timesheetDocument != null) {
						if (TkConstants.EMPLOYEE_APPROVAL_DOC_STATUS.contains(KEWServiceLocator.getRouteHeaderService().getDocumentStatus(docId))) {
							// use the range of the calendar entry to retrieve the correct PrincipalHrAtrribute record for the employee
							// then check if the calendar name/id matches the one from the calendar entry
							String principalId = timesheetDocument.getPrincipalId();
							PrincipalHRAttributesContract phraRecord = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate.toLocalDate());
							if(phraRecord != null && phraRecord.getPayCalendar().equals(calendar.getCalendarName())) {		
								TkServiceLocator.getTimesheetService().routeTimesheet(principalId, timesheetDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
				            }
						}
	        		}
	        	}
	        }
    	} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
	        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
	        	if (leaveCalendarDocumentHeader != null) {
	        		String docId = leaveCalendarDocumentHeader.getDocumentId();
					LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(docId);
					if (TkConstants.EMPLOYEE_APPROVAL_DOC_STATUS.contains(KEWServiceLocator.getRouteHeaderService().getDocumentStatus(docId))) {
						String principalId = leaveCalendarDocument.getPrincipalId();
						PrincipalHRAttributesContract phraRecord = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate.toLocalDate());
						if(phraRecord != null && phraRecord.getLeaveCalendar().equals(calendar.getCalendarName())) {	
							LmServiceLocator.getLeaveCalendarService().routeLeaveCalendar(principalId, leaveCalendarDocument, HrConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
						}
					}
				}
	        }
    	}
	}
	
}
