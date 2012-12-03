package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EmployeeApprovalJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");
		String documentId = jobDataMap.getString("documentId");

		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		
		if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
			TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
			if (timesheetDocumentHeader != null) {
				TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
				String documentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(timesheetDocument.getDocumentId());
				String principalId = timesheetDocument.getPrincipalId();
				
				if (DocumentStatus.INITIATED.getCode().equals(documentStatus) || DocumentStatus.SAVED.getCode().equals(documentStatus)) {
					TkServiceLocator.getTimesheetService().routeTimesheet(principalId, timesheetDocument, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
	            }
			}
		} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
			LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
			if (leaveCalendarDocumentHeader != null) {
				LeaveCalendarDocument leaveCalendarDocument = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
				String documentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(leaveCalendarDocument.getDocumentId());
				String principalId = leaveCalendarDocument.getPrincipalId();
				
				if (DocumentStatus.INITIATED.getCode().equals(documentStatus) || DocumentStatus.SAVED.getCode().equals(documentStatus)) {
					TkServiceLocator.getLeaveCalendarService().routeLeaveCalendar(principalId, leaveCalendarDocument, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_ROUTE);
				}
			}
		}
	}
	
}