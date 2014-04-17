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
package org.kuali.kpme.tklm.time.clocklog.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLogService;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.clocklog.dao.ClockLogDao;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.TimesheetUtils;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClockLogServiceImpl implements ClockLogService {
	
	private static final Logger LOG = Logger.getLogger(ClockLogServiceImpl.class);
	
    private ClockLogDao clockLogDao;

    public ClockLog saveClockLog(ClockLog clockLog) {
        if (clockLog == null) {
            return null;
        }
        ClockLogBo bo = ClockLogBo.from(clockLog);
    	bo = KRADServiceLocator.getBusinessObjectService().save(bo);
        return ClockLogBo.to(bo);
    }

    @Override
    public ClockLog processClockLog(String principalId, String documentId, DateTime clockDateTime, Assignment assignment, CalendarEntry pe, String ip, LocalDate asOfDate, String clockAction, boolean runRules) {
        return processClockLog(principalId, documentId, clockDateTime, assignment, pe, ip, asOfDate, clockAction, runRules, HrContext.getPrincipalId());
    }

    @Override
    public synchronized ClockLog processClockLog(String principalId, String documentId, DateTime clockDateTime, Assignment assignment, CalendarEntry pe, String ip, LocalDate asOfDate, String clockAction, boolean runRules, String userPrincipalId) {
        // process rules
        DateTime roundedClockDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(clockDateTime, pe.getBeginPeriodFullDateTime().toLocalDate());

        ClockLog lastClockLog = null;
        if (StringUtils.equals(clockAction, TkConstants.LUNCH_OUT)) {
            lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(assignment.getPrincipalId(), TkConstants.CLOCK_IN);
        } else if (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT)
                   || StringUtils.equals(clockAction, TkConstants.CLOCK_IN)) {
            lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(assignment.getPrincipalId());
        }

        DateTime lastClockLogTime = lastClockLog != null ? lastClockLog.getClockDateTime() : null;
        if (lastClockLog != null
                && lastClockLogTime.withMillisOfSecond(0).equals(roundedClockDateTime.withMillisOfSecond(0))) {
            roundedClockDateTime = roundedClockDateTime.withMillisOfSecond(lastClockLogTime.getMillisOfSecond() + 1);
        }

        //if span timesheets, we need to build some co/ci clock logs to close out the old period
        if (lastClockLog != null
              && !StringUtils.equals(lastClockLog.getDocumentId(), documentId)
              && (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT) || StringUtils.equals(clockAction, TkConstants.LUNCH_OUT))) {

        }
        ClockLogBo clockLog = buildClockLog(principalId, documentId, roundedClockDateTime, new Timestamp(System.currentTimeMillis()), assignment, clockAction, ip, userPrincipalId);

        if (runRules) {
        	TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, asOfDate);
        }
        
        // If the clock action is clock out or lunch out, create a time block besides the clock log
        if (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT) || StringUtils.equals(clockAction, TkConstants.LUNCH_OUT)) {
            processTimeBlock(principalId, documentId, clockLog, assignment, pe, clockAction, userPrincipalId);
        } else {
            //Save current clock log to get id for timeblock building
            clockLog = KRADServiceLocator.getBusinessObjectService().save(clockLog);
        }
        return ClockLogBo.to(clockLog);
    }

    private void processTimeBlock(String principalId, String documentId, ClockLogBo clockLog, Assignment currentAssignment, CalendarEntry pe, String clockAction, String userPrincipalId) {
        ClockLog lastLog = null;
        DateTime lastClockDateTime = null;
        String beginClockLogId = null;
        String endClockLogId = null;
        TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
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

        // KPME-2680 : chk if hours is not zero then and then create TimeBlock
    	BigDecimal hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
    	if(hours.compareTo(BigDecimal.ZERO) > 0) {
	        // New Time Blocks, pointer reference
	        List<TimeBlock> newTimeBlocks = TimesheetUtils.getTimesheetTimeblocksForProcessing(td, true);
	        List<TimeBlock> referenceTimeBlocks = TimesheetUtils.getReferenceTimeBlocks(newTimeBlocks);
	
	        // Add TimeBlocks after we store our reference object!
	        List<TimeBlock> aList = TkServiceLocator.getTimeBlockService().buildTimeBlocks(principalId, pe, currentAssignment,
                    currentAssignment.getJob().getPayTypeObj().getRegEarnCode(), documentId, beginDateTime, endDateTime,
                    BigDecimal.ZERO, BigDecimal.ZERO, true, false, userPrincipalId, beginClockLogId, endClockLogId);

	        newTimeBlocks.addAll(aList);
	        
	        List<LeaveBlock> leaveBlocks = TimesheetUtils.getLeaveBlocksForTimesheet(td);

            TimesheetUtils.processTimeBlocksWithRuleChange(newTimeBlocks, referenceTimeBlocks, leaveBlocks, pe, td, principalId);
    	}
    }

    private ClockLogBo buildClockLog(String principalId, String documentId, DateTime clockDateTime, Timestamp originalTimestamp, Assignment assignment, String clockAction, String ip, String userPrincipalId) {
        ClockLogBo clockLog = new ClockLogBo();
        clockLog.setDocumentId(documentId);
        clockLog.setPrincipalId(principalId);
        clockLog.setGroupKeyCode(assignment.getGroupKeyCode());
        clockLog.setGroupKey(assignment.getGroupKey());
        clockLog.setJobNumber(assignment.getJobNumber());
        clockLog.setWorkArea(assignment.getWorkArea());
        clockLog.setTask(assignment.getTask());
//        clockLog.setClockTimestampTimezone(HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback().getID());
        clockLog.setClockTimestampTimezone(HrServiceLocator.getTimezoneService().getUserTimezone(principalId));
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
        return ClockLogBo.to(clockLogDao.getLastClockLog(principalId));
    }

    public ClockLog getLastClockLog(String principalId, String clockAction) {
        return ClockLogBo.to(clockLogDao.getLastClockLog(principalId, clockAction));
    }

    @Override
    public ClockLog getLastClockLog(String principalId, String jobNumber, String workArea, String task, CalendarEntry calendarEntry) {
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, calendarEntry.getBeginPeriodFullDateTime(), calendarEntry.getEndPeriodFullDateTime());
        if(tdh == null)
        	return null;
        return ClockLogBo.to(clockLogDao.getLastClockLog(principalId, jobNumber, workArea, task, tdh.getDocumentId()));
    }

    @Override
    public ClockLog getClockLog(String tkClockLogId) {
        return ClockLogBo.to(clockLogDao.getClockLog(tkClockLogId));
    }
    
    @Override
	public void deleteClockLogsForDocumentId(String documentId) {
    	clockLogDao.deleteClockLogsForDocumentId(documentId);
	}

    public List<String> getUnapprovedIPWarning(List<TimeBlock> timeBlocks) {
		 List<String> warningMessages = new ArrayList<String>();
		 if (CollectionUtils.isNotEmpty(timeBlocks)) {
		     Set<String> aSet = new HashSet<String>();
		     for(TimeBlock tb : timeBlocks) {
		    	 if(tb.isClockLogCreated()) {
		    		 if(StringUtils.isNotEmpty(tb.getClockLogBeginId())){
		    			 ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(tb.getClockLogBeginId());
		    			 if(cl != null && cl.isUnapprovedIP()) {
		    				 aSet.add(buildUnapprovedIPWarning(cl));
		    			 }
		    		 }
		    		 if(StringUtils.isNotEmpty(tb.getClockLogEndId())){
		    			 ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(tb.getClockLogEndId());
		    			 if(cl != null && cl.isUnapprovedIP()) {
		    				 aSet.add(buildUnapprovedIPWarning(cl));
		    			 }
		    		 }		
		    	 }
		     }
		     warningMessages.addAll(aSet);
		}
		
		return warningMessages;
    }

    @Override
	public String buildUnapprovedIPWarning(ClockLog cl) {
		return "Warning: Action '" + TkConstants.CLOCK_ACTION_STRINGS.get(cl.getClockAction()) + "' taken at "
			+ HrConstants.DateTimeFormats.FULL_DATE_TIME_FORMAT.print(cl.getClockDateTime()) + " was from an unapproved IP address - " + cl.getIpAddress();
	}

}
