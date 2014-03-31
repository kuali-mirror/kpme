/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.batch.BatchJob;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.util.List;

public class EndPayPeriodJob extends BatchJob {
	
	private static final Logger LOG = Logger.getLogger(EndPayPeriodJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		
		String batchUserPrincipalId = getBatchUserPrincipalId();
		if (batchUserPrincipalId != null) {
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
	
			String hrCalendarEntryId = jobDataMap.getString("hrCalendarEntryId");
			
		    DateTime currentDateTime =  new DateTime();
			LOG.info("EndOfPayPeiodJob is running at " + currentDateTime.toString() + " for hrCalendarEntryId " + hrCalendarEntryId);
		    
	        CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(hrCalendarEntryId);
	        Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
	        //calendarEntry.setCalendarObj(calendar);
	        
	        
	    	String calendarName = calendarEntry.getCalendarName();
	    	DateTime scheduleDate = calendarEntry.getBatchEndPayPeriodFullDateTime();
	    	
	    	List<? extends PrincipalHRAttributesContract> principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getActiveEmployeesForPayCalendar(calendarName, scheduleDate.toLocalDate());
            for (PrincipalHRAttributesContract principalHRAttribute : principalHRAttributes) {
	    		String pId = principalHRAttribute.getPrincipalId();
	    	    
	    		List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAllAssignmentsByCalEntryForTimeCalendar(pId, calendarEntry);
	    		for (Assignment assignment : assignments) {
	    			String jobNumber = String.valueOf(assignment.getJobNumber());
	    			String workArea = String.valueOf(assignment.getWorkArea());
	    			String task = String.valueOf(assignment.getTask());
	    			
	    			ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(pId, jobNumber, workArea, task, calendarEntry);
	    	    	if (lastClockLog != null && TkConstants.ON_THE_CLOCK_CODES.contains(lastClockLog.getClockAction())) {
	    	    		runEndPayPeriodJobForUser(calendarEntry, pId, lastClockLog, batchUserPrincipalId);
	    	    	}
	    		}
	    	}
        } else {
        	String principalName = getBatchUserPrincipalName();
        	LOG.error("Could not run batch jobs due to missing batch user " + principalName);
        }
	}
	
	
	protected void runEndPayPeriodJobForUser(CalendarEntry calendarEntry, String principalId, ClockLog openClockLog, String batchUserPrincipalId) {
        LOG.debug("EndOfPayPeiodJob started for user " + principalId + " and clockLog " + openClockLog.getTkClockLogId());
		DateTime endPeriodDateTime = calendarEntry.getEndPeriodFullDateTime();
	        
        // need to use the user's time zone and the system time zone to figure out the system time of endPeriodDatTime in the user's timezone
        DateTimeZone userTimezone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(principalId));
		DateTimeZone systemTimeZone = TKUtils.getSystemDateTimeZone();
		// time to use to create the CO clock log
        DateTime coLogDateTime = TKUtils.convertTimeForDifferentTimeZone(endPeriodDateTime,systemTimeZone,userTimezone);
	        
        CalendarEntry nextCalendarEntry = HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
        DateTime beginNextPeriodDateTime = nextCalendarEntry.getBeginPeriodFullDateTime();
        // time to use to create the CI clock log
        DateTime ciLogDateTime = TKUtils.convertTimeForDifferentTimeZone(beginNextPeriodDateTime,systemTimeZone,userTimezone);
        
        String ipAddress = openClockLog.getIpAddress();
	    TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, calendarEntry.getBeginPeriodFullDateTime(), endPeriodDateTime);
	    if (timesheetDocumentHeader != null) {
	        AssignmentDescriptionKey assignmentKey = new AssignmentDescriptionKey(openClockLog.getJobNumber(), openClockLog.getWorkArea(), openClockLog.getTask());
            Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(principalId, assignmentKey, coLogDateTime.toLocalDate());
	        ClockLog clockOutLog = TkServiceLocator.getClockLogService().processClockLog(principalId, timesheetDocumentHeader.getDocumentId(), coLogDateTime, assignment, calendarEntry, ipAddress,
	            		endPeriodDateTime.toLocalDate(), TkConstants.CLOCK_OUT, false, batchUserPrincipalId);

            TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService()
            		.getDocumentHeader(principalId, nextCalendarEntry.getBeginPeriodFullDateTime(), nextCalendarEntry.getEndPeriodFullDateTime());
            //TimesheetDocument nextTimeDoc = null;
            //if(nextTdh != null) {
            //	nextTimeDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(nextTdh.getDocumentId());
            //}
            ClockLog clockInLog = TkServiceLocator.getClockLogService().processClockLog(principalId, nextTdh == null ? null : nextTdh.getDocumentId(), ciLogDateTime, assignment, nextCalendarEntry, ipAddress,
            		beginNextPeriodDateTime.toLocalDate(), TkConstants.CLOCK_IN, false, batchUserPrincipalId);

            // add 5 seconds to clock in log's timestamp so it will be found as the latest clock action
            ClockLog.Builder ciBuilder = ClockLog.Builder.create(clockInLog);
            ciBuilder.setCreateTime(clockInLog.getCreateTime().plusSeconds(5));
            clockInLog = TkServiceLocator.getClockLogService().saveClockLog(ciBuilder.build());
            LOG.debug("Clock Out log created is  " + clockOutLog.getTkClockLogId() + ",  Clock In Log created is " + clockInLog.getTkClockLogId());
	    }
        LOG.debug("EndOfPayPeiodJob is finished for user " + principalId + " and clockLog " + openClockLog.getTkClockLogId());
	}
	

}
