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
import org.kuali.kpme.core.batch.BatchJob;
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

public class EndPayPeriodJob extends BatchJob {
	
	private static final Logger LOG = Logger.getLogger(EndPayPeriodJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String batchUserPrincipalId = getBatchUserPrincipalId();
        
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
			String tkClockLogId = jobDataMap.getString("tkClockLogId");
	        CalendarEntry calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
	        Calendar calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
	        calendarEntry.setCalendarObj(calendar);
	        
	        DateTime endPeriodDateTime = calendarEntry.getEndPeriodFullDateTime();
            CalendarEntry nextCalendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
            DateTime beginNextPeriodDateTime = nextCalendarEntry.getBeginPeriodFullDateTime();

	        ClockLog openClockLog = TkServiceLocator.getClockLogService().getClockLog(tkClockLogId);
	        String ipAddress = openClockLog.getIpAddress();
	        String principalId = openClockLog.getPrincipalId();
	
	        TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, calendarEntry.getBeginPeriodFullDateTime(), endPeriodDateTime);
	        if (timesheetDocumentHeader != null) {
	            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
	            AssignmentDescriptionKey assignmentKey = new AssignmentDescriptionKey(openClockLog.getJobNumber(), openClockLog.getWorkArea(), openClockLog.getTask());
	            Assignment assignment = timesheetDocument.getAssignment(assignmentKey);
	            ClockLog clockOutLog = TkServiceLocator.getClockLogService().processClockLog(endPeriodDateTime, assignment, calendarEntry, ipAddress,
	            		endPeriodDateTime.toLocalDate(), timesheetDocument, TkConstants.CLOCK_OUT, false, principalId, batchUserPrincipalId);

	            TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService()
	            		.getDocumentHeader(principalId, nextCalendarEntry.getBeginPeriodFullDateTime(), nextCalendarEntry.getEndPeriodFullDateTime());
	            TimesheetDocument nextTimeDoc = null;
	            if(nextTdh != null) {
	            	nextTimeDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(nextTdh.getDocumentId());
	            }
	            ClockLog clockInLog = TkServiceLocator.getClockLogService().processClockLog(beginNextPeriodDateTime, assignment, nextCalendarEntry, ipAddress,
	            		beginNextPeriodDateTime.toLocalDate(), nextTimeDoc, TkConstants.CLOCK_IN, false, principalId, batchUserPrincipalId);

	            // add 5 seconds to clock in log's timestamp so it will be found as the latest clock action
	            Timestamp ts= clockInLog.getTimestamp();
	            java.util.Calendar cal = java.util.Calendar.getInstance();
	            cal.setTimeInMillis(ts.getTime());
	            cal.add(java.util.Calendar.SECOND, 5);
	            Timestamp later = new Timestamp(cal.getTime().getTime());
	            clockInLog.setTimestamp(later);
	            TkServiceLocator.getClockLogService().saveClockLog(clockInLog);
	        }
        } else {
        	String principalName = getBatchUserPrincipalName();
        	LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	}

}
