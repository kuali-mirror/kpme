package org.kuali.hr.time.missedpunch.service;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.missedpunch.dao.MissedPunchDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MissedPunchServiceImpl implements MissedPunchService {

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
        java.util.Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();

        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime());
        DateTime actionDateTime = new DateTime(actionDate.getTime());

        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());

        ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(missedPunch.getTkClockLogId());

        if(cl.getClockTimestamp().compareTo(new Timestamp(actionDateTime.getMillis())) != 0){
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
        		logBeginId = timeBlocks.get(0).getClockLogBeginId();
        	}
        	
        	//delete existing time blocks
        	for(TimeBlock tb : timeBlocks){
        		TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);
        	}
        	KNSServiceLocator.getBusinessObjectService().delete(cl);
        	// delete the existing clock log and add new time blocks
        	addClockLogForMissedPunch(missedPunch, logEndId, logBeginId);
        }
    }

    @Override
    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch) {
        java.util.Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();

        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime());
        DateTime actionDateTime = new DateTime(actionDate.getTime());

        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        missedPunch.setActionDate(new java.util.Date(actionDateTime.getMillis()));
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

        MissedPunchDocument doc = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(missedPunch.getDocumentNumber());
        doc.setTkClockLogId(clockLog.getTkClockLogId());
        KNSServiceLocator.getBusinessObjectService().save(doc);
    }

    @Override
    // is called by updateClockLogAndTimeBlockIfNecessary when approver changes time on approving an existing missed punch doc

    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch, String logEndId, String logBeginId) {
        java.util.Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();

        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime());
        DateTime actionDateTime = new DateTime(actionDate.getTime());

        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        missedPunch.setActionDate(new java.util.Date(actionDateTime.getMillis()));
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
        ClockLog endLog = null;
        ClockLog beginLog = null;
        if (logEndId != null) {
            endLog = TkServiceLocator.getClockLogService().getClockLog(logEndId);
            beginLog = clockLog;
        } else if (logBeginId != null) {
            beginLog = TkServiceLocator.getClockLogService().getClockLog(logBeginId);
            endLog = clockLog;
        } else {
            ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
            endLog = lastClockLog;
            beginLog = clockLog;
        }
        TkServiceLocator.getClockLogService().saveClockLog(clockLog);
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());

        String earnCode = assign.getJob().getPayTypeObj().getRegEarnCode();
        this.buildTimeBlockRunRules(beginLog, endLog, tdoc, assign, earnCode, beginLog.getClockTimestamp(), endLog.getClockTimestamp());


        MissedPunchDocument doc = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(missedPunch.getDocumentNumber());
        doc.setTkClockLogId(clockLog.getTkClockLogId());
        KNSServiceLocator.getBusinessObjectService().save(doc);
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
                endTimestamp, BigDecimal.ZERO, BigDecimal.ZERO, true);


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
                tdoc.getPayCalendarEntry(),
                tdoc, tdoc.getPrincipalId()
        );

        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
    }
    public MissedPunchDocument getMissedPunchByClockLogId(String clockLogId){
    	return missedPunchDao.getMissedPunchByClockLogId(clockLogId);

    }

    @Override
    public void approveMissedPunch(MissedPunchDocument document) {
        try {
            String rhid = document.getDocumentNumber();
            WorkflowDocument wd = new WorkflowDocument(TkConstants.BATCH_JOB_USER_PRINCIPAL_ID, Long.parseLong(rhid));
            wd.superUserApprove("Batch job superuser approving missed punch document.");

            document.setDocumentStatus(TkConstants.ROUTE_STATUS.FINAL);
            KNSServiceLocator.getBusinessObjectService().save(document);

        } catch (WorkflowException e) {
            throw new RuntimeException("Exception during route", e);
        }
    }

    @Override
    public List<MissedPunchDocument> getMissedPunchDocsByBatchJobEntry(BatchJobEntry batchJobEntry) {
        return missedPunchDao.getMissedPunchDocsByBatchJobEntry(batchJobEntry);
    }

}
