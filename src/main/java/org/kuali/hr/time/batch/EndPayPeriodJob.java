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
package org.kuali.hr.time.batch;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EndPayPeriodJob implements Job {
	
	private static final Logger LOG = Logger.getLogger(EndPayPeriodJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String batchUserPrincipalId = getBatchUserPrincipalId();
        
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntriesId = jobDataMap.getString("hrCalendarEntriesId");
			String tkClockLogId = jobDataMap.getString("tkClockLogId");
			
	        CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrCalendarEntriesId);
	        Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
	        calendarEntry.setCalendarObj(calendar);
	        
	        Date beginPeriodDateTime = calendarEntry.getBeginPeriodDateTime();
	        Date endPeriodDateTime = calendarEntry.getEndPeriodDateTime();
	        ClockLog openClockLog = TkServiceLocator.getClockLogService().getClockLog(tkClockLogId);
	        String ipAddress = openClockLog.getIpAddress();
	        String principalId = openClockLog.getPrincipalId();
	
	        TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginPeriodDateTime, endPeriodDateTime);
	        if (timesheetDocumentHeader != null) {
	            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
	            String assignmentKey = new AssignmentDescriptionKey(openClockLog.getJobNumber(), openClockLog.getWorkArea(), openClockLog.getTask()).toAssignmentKeyString();
	            Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument, assignmentKey);
	    		
	            TkServiceLocator.getClockLogService().processClockLog(new Timestamp(endPeriodDateTime.getTime()), assignment, calendarEntry, ipAddress, 
	            		new java.sql.Date(endPeriodDateTime.getTime()), timesheetDocument, TkConstants.CLOCK_OUT, principalId, batchUserPrincipalId);
	            TkServiceLocator.getClockLogService().processClockLog(new Timestamp(beginPeriodDateTime.getTime()), assignment, calendarEntry, ipAddress, 
	            		new java.sql.Date(beginPeriodDateTime.getTime()), timesheetDocument, TkConstants.CLOCK_IN, principalId, batchUserPrincipalId);
	        }
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	}
	
    private String getBatchUserPrincipalId() {
    	String principalId = null;
    	
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Person person = KimApiServiceLocator.getPersonService().getPersonByPrincipalName(principalName);
        if (person != null) {
        	principalId = person.getPrincipalId();
        }
        
        return principalId;
    }

}