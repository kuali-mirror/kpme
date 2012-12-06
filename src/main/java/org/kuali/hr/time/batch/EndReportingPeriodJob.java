package org.kuali.hr.time.batch;

import java.text.DateFormat;

import org.joda.time.DateTime;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EndReportingPeriodJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");
		String principalId = jobDataMap.getString("principalId");
		
		CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);
		DateTime endPeriodDateTime = new DateTime(calendarEntry.getEndPeriodDateTime());
		
		String subject = "End of Reporting Period Reminder";
		StringBuilder message = new StringBuilder();
		message.append("Please submit your Leave Calendar for the period ending on ");
		message.append(DateFormat.getDateInstance(DateFormat.LONG).format(endPeriodDateTime.minusDays(1).toDate()));
		message.append(", so it can be reviewed and approved by your supervisor.");
		
		TkServiceLocator.getKPMENotificationService().sendNotification(subject, message.toString(), principalId);
	}
	
}