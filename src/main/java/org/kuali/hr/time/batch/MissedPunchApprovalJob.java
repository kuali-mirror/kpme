package org.kuali.hr.time.batch;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MissedPunchApprovalJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");

		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);

		Date beginDate = calendarEntry.getBeginPeriodDateTime();
		Date endDate = calendarEntry.getEndPeriodDateTime();
   	
		List<TimesheetDocumentHeader> timesheetDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
		for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
			List<MissedPunchDocument> missedPunchDocuments = TkServiceLocator.getMissedPunchService().getMissedPunchDocsByTimesheetDocumentId(timesheetDocumentHeader.getDocumentId());
			for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
				TkServiceLocator.getMissedPunchService().approveMissedPunch(missedPunchDocument);
			}
		}
	}
	
}