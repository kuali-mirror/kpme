package org.kuali.hr.time.clocklog.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.clocklog.dao.ClockLogDao;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClockLogServiceImpl implements ClockLogService {

    private ClockLogDao clockLogDao;

    public ClockLogServiceImpl() {
    }

    public void saveClockLog(ClockLog clockLog) {
        clockLogDao.saveOrUpdate(clockLog);
    }

    @Override
    public ClockLog processClockLog(Timestamp clockTimeStamp, Assignment assignment, PayCalendarEntries pe, String ip, java.sql.Date asOfDate, TimesheetDocument td, String clockAction, String principalId) {

        // process rules
        Timestamp clockTimestamp = TkServiceLocator.getGracePeriodService().processGracePeriodRule(clockTimeStamp, new java.sql.Date(pe.getBeginPeriodDateTime().getTime()));

        ClockLog clockLog = TkServiceLocator.getClockLogService().buildClockLog(clockTimestamp, assignment, td, clockAction, ip);
        TkServiceLocator.getClockLocationRuleService().processClockLocationRule(clockLog, asOfDate);

        // If the clock action is clock out or lunch out, create a time block besides the clock log
        if (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT) || StringUtils.equals(clockAction, TkConstants.LUNCH_OUT)) {
            processTimeBlock(clockLog, assignment, pe, td, clockAction, principalId);
        }

        //Save current clock log to get id for timeblock building
        TkServiceLocator.getClockLogService().saveClockLog(clockLog);

        return clockLog;
    }

    private void processTimeBlock(ClockLog clockLog, Assignment assignment, PayCalendarEntries pe, TimesheetDocument td, String clockAction, String principalId) {
        ClockLog lastLog = null;
        Timestamp lastClockTimestamp = null;
        Long beginClockLogId = null;
        Long endClockLogId = null;

        if (StringUtils.equals(clockAction, TkConstants.LUNCH_OUT)) {
            lastLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId, TkConstants.CLOCK_IN);
        } else if (StringUtils.equals(clockAction, TkConstants.CLOCK_OUT)) {
            lastLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
        }
        if (lastLog != null) {
            lastClockTimestamp = lastLog.getClockTimestamp();
            beginClockLogId = lastLog.getTkClockLogId();
        } else {

            //TODO: need to handle the exception
            //caf.setErrorMessage("No Clock action found for " + principalId);
            //return mapping.findForward("basic");

        }
        // TODO: Do we have to set clockLog on the form?
        //caf.setClockLog(clockLog);
        endClockLogId = clockLog.getTkClockLogId();

        long beginTime = lastClockTimestamp.getTime();
        Timestamp beginTimestamp = new Timestamp(beginTime);
        Timestamp endTimestamp = clockLog.getClockTimestamp();

//        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(td, selectedAssignment);

        // TODO: Is there any case where the user is not a sync employee and still needs to clock in/out?
        // String earnCode = TKContext.getUser().getCurrentTargetRoles().isSynchronous() ? assignment.getJob().getPayTypeObj().getRegEarnCode() : caf.getSelectedEarnCode();

        // New Time Blocks, pointer reference
        List<TimeBlock> newTimeBlocks = td.getTimeBlocks();
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(td.getTimeBlocks().size());
        for (TimeBlock tb : td.getTimeBlocks()) {
            referenceTimeBlocks.add(tb.copy());
        }

        // Add TimeBlocks after we store our reference object!
        List<TimeBlock> aList = TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment, assignment.getJob().getPayTypeObj().getRegEarnCode(), td, beginTimestamp, endTimestamp, BigDecimal.ZERO, BigDecimal.ZERO, true);
        for (TimeBlock tb : aList) {
            tb.setClockLogBeginId(beginClockLogId);
            tb.setClockLogEndId(endClockLogId);
        }
        newTimeBlocks.addAll(aList);

        //reset time hour details
        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);
        //apply any rules for this action
        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.CLOCK_OUT, newTimeBlocks, pe, td, principalId);

        //call persist method that only saves added/deleted/changed timeblocks
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
        //TkServiceLocator.getTimeHourDetailService().saveTimeHourDetail(tb);
        //TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(caf);
    }

    @Override
    public ClockLog buildClockLog(Timestamp clockTimestamp, Assignment assignment, TimesheetDocument timesheetDocument, String clockAction, String ip) {
        String principalId = timesheetDocument.getPrincipalId();

        ClockLog clockLog = new ClockLog();
        clockLog.setPrincipalId(principalId);
//        AssignmentDescriptionKey assignmentDesc = TkServiceLocator.getAssignmentService().getAssignmentDescriptionKey(selectedAssign);
//        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument, selectedAssign);
        clockLog.setJob(timesheetDocument.getJob(assignment.getJobNumber()));
        clockLog.setJobNumber(assignment.getJobNumber());
        clockLog.setWorkArea(assignment.getWorkArea());
        clockLog.setTkWorkAreaId(assignment.getWorkAreaObj().getTkWorkAreaId());

        Long tkTaskId = null;
        for (Task task : assignment.getWorkAreaObj().getTasks()) {
            if (task.getTask().compareTo(assignment.getTask()) == 0) {
                tkTaskId = task.getTkTaskId();
                break;
            }
        }
        clockLog.setTask(assignment.getTask());
        clockLog.setTkTaskId(tkTaskId);
        clockLog.setClockTimestampTimezone(TkServiceLocator.getTimezoneService().getUserTimezone());
        clockLog.setClockTimestamp(clockTimestamp);
        clockLog.setClockAction(clockAction);
        clockLog.setIpAddress(ip);
        clockLog.setHrJobId(assignment.getJob().getHrJobId());
        clockLog.setUserPrincipalId(principalId);
        clockLog.setTimestamp(new Timestamp(System.currentTimeMillis()));

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

    public List<ClockLog> getOpenClockLogs(PayCalendarEntries payCalendarEntry) {
        return clockLogDao.getOpenClockLogs(payCalendarEntry);
    }

	@Override
	public ClockLog getClockLog(Long tkClockLogId) {
		return clockLogDao.getClockLog(tkClockLogId);
	}

}
