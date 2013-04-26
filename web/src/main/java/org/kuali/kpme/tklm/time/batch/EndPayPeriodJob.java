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
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
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
		String batchUserPrincipalId = getBatchUserPrincipalId();
        
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
			String tkClockLogId = jobDataMap.getString("tkClockLogId");
			
	        CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
	        Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
	        calendarEntry.setCalendarObj(calendar);
	        
	        DateTime beginPeriodDateTime = calendarEntry.getBeginPeriodFullDateTime();
	        DateTime endPeriodDateTime = calendarEntry.getEndPeriodFullDateTime();
	        ClockLog openClockLog = TkServiceLocator.getClockLogService().getClockLog(tkClockLogId);
	        String ipAddress = openClockLog.getIpAddress();
	        String principalId = openClockLog.getPrincipalId();
	
	        TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginPeriodDateTime, endPeriodDateTime);
	        if (timesheetDocumentHeader != null) {
	            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
	            String assignmentKey = new AssignmentDescriptionKey(openClockLog.getJobNumber(), openClockLog.getWorkArea(), openClockLog.getTask()).toAssignmentKeyString();
	            Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(timesheetDocument, assignmentKey);
	    		
	            TkServiceLocator.getClockLogService().processClockLog(new Timestamp(endPeriodDateTime.toDate().getTime()), assignment, calendarEntry, ipAddress, 
	            		endPeriodDateTime.toLocalDate(), timesheetDocument, TkConstants.CLOCK_OUT, principalId, batchUserPrincipalId);
	            TkServiceLocator.getClockLogService().processClockLog(new Timestamp(beginPeriodDateTime.toDate().getTime()), assignment, calendarEntry, ipAddress, 
	            		beginPeriodDateTime.toLocalDate(), timesheetDocument, TkConstants.CLOCK_IN, principalId, batchUserPrincipalId);
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