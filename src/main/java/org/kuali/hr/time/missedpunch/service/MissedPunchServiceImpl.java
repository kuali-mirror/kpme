package org.kuali.hr.time.missedpunch.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.missedpunch.dao.MissedPunchDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class MissedPunchServiceImpl implements MissedPunchService {

    MissedPunchDao missedPunchDao;

    @Override
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId) {
        return missedPunchDao.getMissedPunchByRouteHeader(headerId);
    }

    public void setMissedPunchDao(MissedPunchDao missedPunchDao) {
        this.missedPunchDao = missedPunchDao;
    }
    
    public void updateClockLogAndTimeBlockIfNecessary(MissedPunchDocument missedPunch){
        java.util.Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();
        
        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime());
        DateTime actionDateTime = new DateTime(actionDate.getTime());

        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        
        ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(missedPunch.getTkClockLogId());
        if(cl.getClockTimestamp().compareTo(new Timestamp(actionDateTime.getMillis())) != 0){
        	//change occurred between the initial save and the approver
        	//inactivate all the previous timeblocks and delete clock logs
        	List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocksForClockLog(cl.getTkClockLogId());
        	for(TimeBlock tb : timeBlocks){
        		TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);
        	}
        	KNSServiceLocator.getBusinessObjectService().delete(cl);
        	addClockLogForMissedPunch(missedPunch);
        }
    }
    
    public void addClockLogForMissedPunch(MissedPunchDocument missedPunch){
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
        ClockLog clockLog = TkServiceLocator.getClockLogService().buildClockLog(new Timestamp(missedPunch.getActionDate().getTime()),
                assign,
                tdoc,
                missedPunch.getClockAction(),
                TKUtils.getIPAddressFromRequest(TKContext.getHttpServletRequest()));

        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
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
    
    /**
     * Helper method to build time blocks and fire the rules processing. This
     * should be called only if there was a CLOCK_OUT action.
     *
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
                assignment,earnCode, tdoc, beginTimestamp,
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
                tdoc.getCalendarEntry(),
                tdoc, tdoc.getPrincipalId()
        );

        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
    }
    public MissedPunchDocument getMissedPunchByClockLogId(Long clockLogId){
    	return missedPunchDao.getMissedPunchByClockLogId(clockLogId);
    }

}
