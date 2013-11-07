/**
 * Copyright 2004-2013 The Kuali Foundation
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

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EndPayPeriodJob implements Job {
	
	private static final Logger LOG = Logger.getLogger(EndPayPeriodJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
LOG.info("Starting of EndPayPeriod Job!!!");
		String batchUserPrincipalId = getBatchUserPrincipalId();
        
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
			String tkClockLogId = jobDataMap.getString("tkClockLogId");
LOG.info("Calendar entry id is " + hrCalendarEntryId + ", Clock log id is " + tkClockLogId);			
	        CalendarEntry calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
	        Calendar calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
	        calendarEntry.setCalendarObj(calendar);
	        
	        DateTime endPeriodDateTime = calendarEntry.getEndPeriodFullDateTime();
            CalendarEntry nextCalendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
            DateTime beginNextPeriodDateTime = nextCalendarEntry.getBeginPeriodFullDateTime();

LOG.info("Current Calendar entry beginDateTime is " + calendarEntry.getBeginPeriodFullDateTime().toString() + ", endDateTime is " + calendarEntry.getEndPeriodFullDateTime().toString());
LOG.info("Next Calendar entry beginDateTime is " + nextCalendarEntry.getBeginPeriodFullDateTime().toString() + ", endDateTime is " + nextCalendarEntry.getEndPeriodFullDateTime().toString());

	        ClockLog openClockLog = TkServiceLocator.getClockLogService().getClockLog(tkClockLogId);
	        String ipAddress = openClockLog.getIpAddress();
	        String principalId = openClockLog.getPrincipalId();
	
	        TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, calendarEntry.getBeginPeriodFullDateTime(), endPeriodDateTime);
	        if (timesheetDocumentHeader != null) {
LOG.info("Current timesheet document id is " + timesheetDocumentHeader.getDocumentId());	        	
	            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
	            AssignmentDescriptionKey assignmentKey = new AssignmentDescriptionKey(openClockLog.getJobNumber(), openClockLog.getWorkArea(), openClockLog.getTask());
	            Assignment assignment = timesheetDocument.getAssignment(assignmentKey);
LOG.info("Before creating clock OUT log!");	    		
	            ClockLog clockOutLog = TkServiceLocator.getClockLogService().processClockLog(endPeriodDateTime, assignment, calendarEntry, ipAddress, 
	            		endPeriodDateTime.toLocalDate(), timesheetDocument, TkConstants.CLOCK_OUT, false, principalId, batchUserPrincipalId);
LOG.info("Clock OUT log created, the id is " + clockOutLog.getTkClockLogId() + ", timestamp is " + clockOutLog.getTimestamp().toString());
 
	            TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService()
	            		.getDocumentHeader(principalId, nextCalendarEntry.getBeginPeriodFullDateTime(), nextCalendarEntry.getEndPeriodFullDateTime());
	            TimesheetDocument nextTimeDoc = null;
	            if(nextTdh != null) {
	            	nextTimeDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(nextTdh.getDocumentId());
LOG.info("Next Time document is not null, the document id is " + nextTdh.getDocumentId());
	            }
LOG.info("Before creating clock IN log!");	 	            
	            ClockLog clockInLog = TkServiceLocator.getClockLogService().processClockLog(beginNextPeriodDateTime, assignment, nextCalendarEntry, ipAddress, 
	            		beginNextPeriodDateTime.toLocalDate(), nextTimeDoc, TkConstants.CLOCK_IN, false, principalId, batchUserPrincipalId);
LOG.info("Clock IN log created, the id is " + clockInLog.getTkClockLogId() + ", timestamp is " + clockInLog.getTimestamp().toString());

	            // add 5 seconds to clock in log's timestamp so it will be found as the latest clock action
	            Timestamp ts= clockInLog.getTimestamp();
	            java.util.Calendar cal = java.util.Calendar.getInstance();
	            cal.setTimeInMillis(ts.getTime());
	            cal.add(java.util.Calendar.SECOND, 5);
	            Timestamp later = new Timestamp(cal.getTime().getTime());
	            clockInLog.setTimestamp(later);
	            TkServiceLocator.getClockLogService().saveClockLog(clockInLog);
LOG.info("After adding 5 seconds to ClockInLog, the timestamp is " + clockInLog.getTimestamp().toString());	     
	        }
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	}
	
    private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }

}
