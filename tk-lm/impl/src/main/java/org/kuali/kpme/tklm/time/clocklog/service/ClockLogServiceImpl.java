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
package org.kuali.kpme.tklm.time.clocklog.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.clocklog.dao.ClockLogDao;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class ClockLogServiceImpl implements ClockLogService {

    private ClockLogDao clockLogDao;

    public ClockLogServiceImpl() {
    }

    public void saveClockLog(ClockLog clockLog) {
        clockLogDao.saveOrUpdate(clockLog);
    }

    @Override
    public ClockLog processClockLog(DateTime clockDateTime, Assignment assignment,CalendarEntry pe, String ip, LocalDate asOfDate, TimesheetDocument td, String clockAction, String principalId) {
        return processClockLog(clockDateTime, assignment, pe, ip, asOfDate, td, clockAction, principalId, HrContext.getPrincipalId());
    }

    @Override
    public ClockLog processClockLog(DateTime clockDateTime, Assignment assignment,CalendarEntry pe, String ip, LocalDate asOfDate, TimesheetDocument td, String clockAction, String principalId, String userPrincipalId) {
        // process rules
        DateTime roundedClockDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(clockDateTime, pe.getBeginPeriodFullDateTime().toLocalDate());

        ClockLog clockLog = buildClockLog(roundedClockDateTime, new Timestamp(System.currentTimeMillis()), assignment, td, clockAction, ip, userPrincipalId);
        TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, asOfDate);

        // If the clock action is clock out or lunch out, create a time block besides the clock log
        if (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT) || StringUtils.equals(clockAction, TkConstants.LUNCH_OUT)) {
            processTimeBlock(clockLog, assignment, pe, td, clockAction, principalId, userPrincipalId);
        } else {
            //Save current clock log to get id for timeblock building
            KRADServiceLocator.getBusinessObjectService().save(clockLog);
        }

        return clockLog;
    }

    private void processTimeBlock(ClockLog clockLog, Assignment assignment, CalendarEntry pe, TimesheetDocument td, String clockAction, String principalId, String userPrincipalId) {
        ClockLog lastLog = null;
        DateTime lastClockDateTime = null;
        String beginClockLogId = null;
        String endClockLogId = null;

        if (StringUtils.equals(clockAction, TkConstants.LUNCH_OUT)) {
            lastLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId, TkConstants.CLOCK_IN);
        } else if (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT)) {
            lastLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
        }
        if (lastLog != null) {
        	lastClockDateTime = lastLog.getClockDateTime();
            beginClockLogId = lastLog.getTkClockLogId();
        }
        //Save current clock log to get id for timeblock building
        KRADServiceLocator.getBusinessObjectService().save(clockLog);
        endClockLogId = clockLog.getTkClockLogId();

        DateTime beginDateTime = lastClockDateTime;
        DateTime endDateTime = clockLog.getClockDateTime();

        // New Time Blocks, pointer reference
        List<TimeBlock> newTimeBlocks = td.getTimeBlocks();
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(td.getTimeBlocks().size());
        for (TimeBlock tb : td.getTimeBlocks()) {
            referenceTimeBlocks.add(tb.copy());
        }

        // Add TimeBlocks after we store our reference object!
        List<TimeBlock> aList = TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment, assignment.getJob().getPayTypeObj().getRegEarnCode(), td, beginDateTime, endDateTime, BigDecimal.ZERO, BigDecimal.ZERO, true, false, userPrincipalId);
        for (TimeBlock tb : aList) {
            tb.setClockLogBeginId(beginClockLogId);
            tb.setClockLogEndId(endClockLogId);
        }
        newTimeBlocks.addAll(aList);

        //reset time block
        TkServiceLocator.getTimesheetService().resetTimeBlock(newTimeBlocks);

        //apply any rules for this action
        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.CLOCK_OUT, newTimeBlocks, pe, td, principalId);

        //call persist method that only saves added/deleted/changed timeblocks
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks, userPrincipalId);
    }

    @Override
    public ClockLog buildClockLog(DateTime clockDateTime, Timestamp originalTimestamp, Assignment assignment, TimesheetDocument timesheetDocument, String clockAction, String ip) {
        return buildClockLog(clockDateTime, originalTimestamp, assignment, timesheetDocument, clockAction, ip, HrContext.getPrincipalId());
    }

    @Override
    public ClockLog buildClockLog(DateTime clockDateTime, Timestamp originalTimestamp, Assignment assignment, TimesheetDocument timesheetDocument, String clockAction, String ip, String userPrincipalId) {
        String principalId = timesheetDocument.getPrincipalId();

        ClockLog clockLog = new ClockLog();
        clockLog.setPrincipalId(principalId);
        //        AssignmentDescriptionKey assignmentDesc = HrServiceLocator.getAssignmentService().getAssignmentDescriptionKey(selectedAssign);
        //        Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(timesheetDocument, selectedAssign);
        clockLog.setJob(timesheetDocument.getJob(assignment.getJobNumber()));
        clockLog.setJobNumber(assignment.getJobNumber());
        clockLog.setWorkArea(assignment.getWorkArea());
        clockLog.setTask(assignment.getTask());
        clockLog.setClockTimestampTimezone(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback().getID());
        clockLog.setClockDateTime(clockDateTime);
        clockLog.setClockAction(clockAction);
        clockLog.setIpAddress(ip);
        clockLog.setUserPrincipalId(userPrincipalId);
        // timestamp should be the original time without Grace Period rule applied
        clockLog.setTimestamp(originalTimestamp);

        return clockLog;
    }

    public void setClockLogDao(ClockLogDao clockLogDao) {
        this.clockLogDao = clockLogDao;
    }

    public ClockLog getLastClockLog(String principalId) {
        return clockLogDao.getLastClockLog(principalId);
    }

    public ClockLog getLastClockLog(String principalId, String clockAction) {
        return clockLogDao.getLastClockLog(principalId, clockAction);
    }

    public ClockLog getLastClockLog(String principalId, String jobNumber, String workArea, String task, CalendarEntry calendarEntry) {
        return clockLogDao.getLastClockLog(principalId, jobNumber, workArea, task, calendarEntry);
    }

    @Override
    public ClockLog getClockLog(String tkClockLogId) {
        return clockLogDao.getClockLog(tkClockLogId);
    }

    public List<String> getUnapprovedIPWarning(List<TimeBlock> timeBlocks) {
		 List<String> warningMessages = new ArrayList<String>();
		 if (CollectionUtils.isNotEmpty(timeBlocks)) {
		     Set<String> aSet = new HashSet<String>();
		     for(TimeBlock tb : timeBlocks) {
		    	 if(tb.getClockLogCreated()) {
		    		 if(StringUtils.isNotEmpty(tb.getClockLogBeginId())){
		    			 ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(tb.getClockLogBeginId());
		    			 if(cl.getUnapprovedIP()) {
		    				 aSet.add(buildUnapprovedIPWarning(cl));
		    			 }
		    		 }
		    		 if(StringUtils.isNotEmpty(tb.getClockLogEndId())){
		    			 ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(tb.getClockLogEndId());
		    			 if(cl.getUnapprovedIP()) {
		    				 aSet.add(buildUnapprovedIPWarning(cl));
		    			 }
		    		 }		
		    	 }
		     }
		     warningMessages.addAll(aSet);
		}
		
		return warningMessages;
    }

	public String buildUnapprovedIPWarning(ClockLog cl) {
		String warning = "Warning: Action '" + TkConstants.CLOCK_ACTION_STRINGS.get(cl.getClockAction()) + "' taken at " 
			+ cl.getClockTimestamp() + " was from an unapproved IP address - " + cl.getIpAddress();
		return warning;
	}

}
