package org.kuali.hr.time.batch;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.batch.service.BatchJobService;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CalendarEntrySchedulerJob extends QuartzJobBean {

	private static int CALENDAR_ENTRIES_POLLING_WINDOW;
	
	private static BatchJobService batchJobService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Date asOfDate = TKUtils.getCurrentDate();
        List<CalendarEntries> calendarEntries = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntryNeedsScheduled(getCalendarEntriesPollingWindow(), asOfDate);

        try {
	        for (CalendarEntries calendarEntry : calendarEntries) {	
	            if (calendarEntry.getBatchInitiateDateTime() != null) {
	            	if (!getBatchJobService().jobsScheduledForGroup(InitiateJob.class, calendarEntry.getBatchInitiateDateTime())) {
	            		getBatchJobService().scheduleInitiateJobs(calendarEntry);
	            	}
	            }
	            
	            if (calendarEntry.getBatchEndPayPeriodDateTime() != null) {
	            	if (!getBatchJobService().jobsScheduledForGroup(EndPayPeriodJob.class, calendarEntry.getBatchEndPayPeriodDateTime())) {
	            		getBatchJobService().scheduleEndPayPeriodJobs(calendarEntry);
	            	}
	            }
	            
	            if (calendarEntry.getBatchEmployeeApprovalDateTime() != null) {
	            	if (!getBatchJobService().jobsScheduledForGroup(EmployeeApprovalJob.class, calendarEntry.getBatchEmployeeApprovalDateTime())) {
	            		getBatchJobService().scheduleEmployeeApprovalJobs(calendarEntry);
	            	}
	            }
	            
	            if (calendarEntry.getBatchSupervisorApprovalDateTime() != null) {
	            	if (!getBatchJobService().jobsScheduledForGroup(MissedPunchApprovalJob.class, calendarEntry.getBatchSupervisorApprovalDateTime())) {
	            		getBatchJobService().scheduleMissedPunchApprovalJobs(calendarEntry);
	            	}
	            }
	            
	            if (calendarEntry.getBatchSupervisorApprovalDateTime() != null) {
	            	if (!getBatchJobService().jobsScheduledForGroup(SupervisorApprovalJob.class, calendarEntry.getBatchSupervisorApprovalDateTime())) {
	            		getBatchJobService().scheduleSupervisorApprovalJobs(calendarEntry);
	            	}
	            }
	        }
        } catch (SchedulerException se) {
        	throw new JobExecutionException("Exception thrown during job scheduling", se);
        }
	}
	
	public int getCalendarEntriesPollingWindow() {
		return CALENDAR_ENTRIES_POLLING_WINDOW;
	}

	public void setCalendarEntriesPollingWindow(int calendarEntriesPollingWindow) {
		CALENDAR_ENTRIES_POLLING_WINDOW = calendarEntriesPollingWindow;
	}

	public static BatchJobService getBatchJobService() {
		return CalendarEntrySchedulerJob.batchJobService;
	}

	public void setBatchJobService(BatchJobService batchJobService) {
		CalendarEntrySchedulerJob.batchJobService = batchJobService;
	}

}