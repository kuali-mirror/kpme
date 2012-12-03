package org.kuali.hr.time.batch;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EndPayPeriodJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

		String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");
		String tkClockLogId = jobDataMap.getString("tkClockLogId");
		
        CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);
        Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
        calendarEntry.setCalendarObj(calendar);
        
        Date beginDate = calendarEntry.getBeginPeriodDate();
        Date endDate = calendarEntry.getEndPeriodDate();
        ClockLog openClockLog = TkServiceLocator.getClockLogService().getClockLog(tkClockLogId);
        String ipAddress = openClockLog.getIpAddress();
        String principalId = openClockLog.getPrincipalId();
        
        DateTime openClockLogDateTime = new DateTime(openClockLog.getClockTimestamp().getTime(), DateTimeZone.forID(openClockLog.getClockTimestampTimezone()));
        DateTime endPeriodDateTime = new DateTime(calendarEntry.getEndPeriodDateTime().getTime(), DateTimeZone.forID(openClockLog.getClockTimestampTimezone()));
        Interval interval = new Interval(openClockLogDateTime, endPeriodDateTime);

        TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
        if (timesheetDocumentHeader != null) {
            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
            String assignmentKey = new AssignmentDescriptionKey(openClockLog.getJobNumber(), openClockLog.getWorkArea(), openClockLog.getTask()).toAssignmentKeyString();
            Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument, assignmentKey);

            TkServiceLocator.getClockLogService().processClockLog(new Timestamp(interval.getEndMillis()), assignment, calendarEntry, ipAddress, 
            		new java.sql.Date(endPeriodDateTime.getMillis()), timesheetDocument, TkConstants.CLOCK_OUT, principalId, TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);
            TkServiceLocator.getClockLogService().processClockLog(new Timestamp(endPeriodDateTime.getMillis()), assignment, calendarEntry, ipAddress, 
            		new java.sql.Date(endPeriodDateTime.getMillis()), timesheetDocument, TkConstants.CLOCK_IN, principalId, TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);
        }
	}

}