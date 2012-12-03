package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InitiateJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");
		String principalId = jobDataMap.getString("principalId");
		
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		
		try {
			if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
				TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, calendarEntry);
			} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
				TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(principalId, calendarEntry);
			}
		} catch (WorkflowException we) {
			throw new JobExecutionException("Failure to execute job due to WorkflowException", we);
		}
	}
	
}