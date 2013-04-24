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
package org.kuali.hr.tklm.time.missedpunch.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.tklm.time.clocklog.ClockLog;
import org.kuali.hr.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.tklm.time.missedpunch.dao.MissedPunchDao;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.util.TKContext;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MissedPunchServiceImpl implements MissedPunchService {
	
	private static final Logger LOG = Logger.getLogger(MissedPunchServiceImpl.class);

    MissedPunchDao missedPunchDao;

    @Override
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId) {
        return missedPunchDao.getMissedPunchByRouteHeader(headerId);
    }

    public void setMissedPunchDao(MissedPunchDao missedPunchDao) {
        this.missedPunchDao = missedPunchDao;
    }

    @Override
    public void updateClockLogAndTimeBlockIfNecessary(MissedPunchDocument missedPunch) {
        Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();

        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime());
        DateTime actionDateTime = new DateTime(actionDate.getTime());

        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());

        ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(missedPunch.getTkClockLogId());
        // in case the missedpunch doc has an valication error but the clockLog has been changed at certain time
        // need to reset the clock log back to the original one
        if(cl == null) {
        	MissedPunchDocument mpd = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(missedPunch.getDocumentNumber());
        	if(mpd != null) {
        		missedPunch.setTkClockLogId(mpd.getTkClockLogId());
        		cl = TkServiceLocator.getClockLogService().getClockLog(missedPunch.getTkClockLogId());
        	}
        }

        if(cl != null && cl.getClockTimestamp().compareTo(new Timestamp(actionDateTime.getMillis())) != 0){
        	//change occurred between the initial save and the approver
        	//inactivate all the previous timeblocks and delete clock logs
        	String logEndId = null;
        	String logBeginId = null;
        	List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocksForClockLogEndId(cl.getTkClockLogId());
        	if(timeBlocks.isEmpty()) {
        		// get timeBlock with the Clock Log as the clock_log_begin_id
        		timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocksForClockLogBeginId(cl.getTkClockLogId());
        		if(!timeBlocks.isEmpty()) {
        			logEndId = timeBlocks.get(0).getClockLogEndId();
        		}
        	} else {
        		logBeginId = timeBlocks.get(0).getClockLogBeginId();	// new time blocks should keep the original clockLogBeginId
        	}
        	
        	//delete existing time blocks
        	for(TimeBlock tb : timeBlocks){
        		TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);
        	}
        	KRADServiceLocator.getBusinessObjectService().delete(cl);
        	// delete the existing clock log and add new time blocks
        	addClockLogForMissedPunch(missedPunch, logEndId, logBeginId);
        }
    }

    @Override
    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch) {
        Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();

        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime());
        DateTime actionDateTime = new DateTime(actionDate.getTime());

        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        missedPunch.setActionDate(new Date(actionDateTime.getMillis()));
        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        Assignment assign = TkServiceLocator.getAssignmentService().getAssignment(tdoc, missedPunch.getAssignment());
        // Need to build a clock log entry.
        //Timestamp clockTimestamp, String selectedAssign, TimesheetDocument timesheetDocument, String clockAction, String ip) {
        Timestamp ts = new Timestamp(missedPunch.getActionDate().getTime());
        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
        Long zoneOffset = TkServiceLocator.getTimezoneService().getTimezoneOffsetFromServerTime(DateTimeZone.forID(lastClockLog.getClockTimestampTimezone()));
        Timestamp clockLogTime = new Timestamp(ts.getTime() - zoneOffset); // convert the action time to the system zone time

        ClockLog clockLog = TkServiceLocator.getClockLogService().buildClockLog(clockLogTime, clockLogTime,
                assign,
                tdoc,
                missedPunch.getClockAction(),
                TKUtils.getIPAddressFromRequest(TKContext.getHttpServletRequest()));

        TkServiceLocator.getClockLogService().saveClockLog(clockLog);
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());

        if (StringUtils.equals(clockLog.getClockAction(), TkConstants.CLOCK_OUT) ||
                StringUtils.equals(clockLog.getClockAction(), TkConstants.LUNCH_OUT)) {
            String earnCode = assign.getJob().getPayTypeObj().getRegEarnCode();
            this.buildTimeBlockRunRules(lastClockLog, clockLog, tdoc, assign, earnCode, lastClockLog.getClockTimestamp(), clockLog.getClockTimestamp());
        }
    }

    @Override
    // is called by updateClockLogAndTimeBlockIfNecessary when approver changes time on approving an existing missed punch doc

    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch, String logEndId, String logBeginId) {
        Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();

        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime());
        DateTime actionDateTime = new DateTime(actionDate.getTime());

        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        missedPunch.setActionDate(new Date(actionDateTime.getMillis()));
        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        Assignment assign = TkServiceLocator.getAssignmentService().getAssignment(tdoc, missedPunch.getAssignment());
        // Need to build a clock log entry.
        Timestamp ts = new Timestamp(missedPunch.getActionDate().getTime());
        ClockLog lastLog = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
        Long zoneOffset = TkServiceLocator.getTimezoneService().getTimezoneOffsetFromServerTime(DateTimeZone.forID(lastLog.getClockTimestampTimezone()));
        Timestamp clockLogTime = new Timestamp(ts.getTime() - zoneOffset); // convert the action time to the system zone time

        ClockLog clockLog = TkServiceLocator.getClockLogService().buildClockLog(clockLogTime, clockLogTime,
                assign,
                tdoc,
                missedPunch.getClockAction(),
                TKUtils.getIPAddressFromRequest(TKContext.getHttpServletRequest()));
        TkServiceLocator.getClockLogService().saveClockLog(clockLog);
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());
//        MissedPunchDocument doc = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(missedPunch.getDocumentNumber());
//        doc.setTkClockLogId(clockLog.getTkClockLogId());
//        KNSServiceLocator.getBusinessObjectService().save(doc);
        
        // if both clock log ids are null, no need to create new time blocks
        if(!(logEndId == null && logBeginId == null)) {
	        ClockLog endLog = null;
	        ClockLog beginLog = null;
	       if(logEndId != null) {
	    	   endLog = TkServiceLocator.getClockLogService().getClockLog(logEndId);
	       } else {
	    	   endLog = clockLog; 
	       }
	       if (logBeginId != null) {
	           beginLog = TkServiceLocator.getClockLogService().getClockLog(logBeginId);
	       } else {
	    	   beginLog = clockLog;
	       }
	        
	       if (beginLog != null && endLog != null && beginLog.getClockTimestamp().before(endLog.getClockTimestamp())) {
	           String earnCode = assign.getJob().getPayTypeObj().getRegEarnCode();
	           this.buildTimeBlockRunRules(beginLog, endLog, tdoc, assign, earnCode, beginLog.getClockTimestamp(), endLog.getClockTimestamp());
	       } else {
	        	// error
	    	   GlobalVariables.getMessageMap().putError("document.actionTime", "clock.mp.invalid.datetime");
	       }
        }
    }

    /**
     * Helper method to build time blocks and fire the rules processing. This
     * should be called only if there was a CLOCK_OUT action.
     */
    private void buildTimeBlockRunRules(ClockLog beginClockLog, ClockLog endClockLog, TimesheetDocument tdoc, Assignment assignment, String earnCode, Timestamp beginTimestamp, Timestamp endTimestamp) {
        // New Time Blocks, pointer reference
        List<TimeBlock> newTimeBlocks = tdoc.getTimeBlocks();
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(newTimeBlocks);
        for (TimeBlock tb : newTimeBlocks) {
            referenceTimeBlocks.add(tb.copy());
        }

        // Add TimeBlocks after we store our reference object!
        List<TimeBlock> blocks = TkServiceLocator.getTimeBlockService().buildTimeBlocks(
                assignment, earnCode, tdoc, beginTimestamp,
                endTimestamp, BigDecimal.ZERO, BigDecimal.ZERO, true, false, TKContext.getPrincipalId());


        // Add the clock log IDs to the time blocks that were just created.
        for (TimeBlock block : blocks) {
            block.setClockLogBeginId(beginClockLog.getTkClockLogId());
            block.setClockLogEndId(endClockLog.getTkClockLogId());
        }

        newTimeBlocks.addAll(blocks);

        //reset time block
        TkServiceLocator.getTimesheetService().resetTimeBlock(newTimeBlocks);
        //apply any rules for this action
        TkServiceLocator.getTkRuleControllerService().applyRules(
                TkConstants.ACTIONS.CLOCK_OUT, newTimeBlocks,
                tdoc.getCalendarEntry(),
                tdoc, tdoc.getPrincipalId()
        );

        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks, TKContext.getPrincipalId());
    }
    public MissedPunchDocument getMissedPunchByClockLogId(String clockLogId){
    	return missedPunchDao.getMissedPunchByClockLogId(clockLogId);

    }

    @Override
    public void approveMissedPunch(MissedPunchDocument document) {
    	String batchUserPrincipalId = getBatchUserPrincipalId();
        
        if (batchUserPrincipalId != null) {
	        String rhid = document.getDocumentNumber();
	        WorkflowDocument wd = WorkflowDocumentFactory.loadDocument(batchUserPrincipalId, rhid);
	        wd.superUserBlanketApprove("Batch job superuser approving missed punch document.");
        } else {
        	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        	LOG.error("Could not approve missed punch document due to missing batch user " + principalName);
        }
    }
    
    private String getBatchUserPrincipalId() {
    	String principalName = ConfigContext.getCurrentContextConfig().getProperty(TkConstants.BATCH_USER_PRINCIPAL_NAME);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        return principal == null ? null : principal.getPrincipalId();
    }
    
    @Override
    public List<MissedPunchDocument> getMissedPunchDocsByTimesheetDocumentId(String timesheetDocumentId) {
        return missedPunchDao.getMissedPunchDocsByTimesheetDocumentId(timesheetDocumentId);
    }

}
