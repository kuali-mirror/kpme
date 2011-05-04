package org.kuali.hr.time.missedpunch;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.bo.DocumentHeader;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MissedPunchMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -1505817190754176279L;

    @Override
    public void doRouteStatusChange(DocumentHeader documentHeader) {
        MissedPunch missedPunch = (MissedPunch)super.getBusinessObject();
        java.util.Date actionDate = missedPunch.getActionDate();
        java.sql.Time actionTime = missedPunch.getActionTime();

        LocalTime actionTimeLocal = new LocalTime(actionTime.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime actionDateTime = new DateTime(actionDate.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        actionDateTime = actionDateTime.plus(actionTimeLocal.getMillisOfDay());
        missedPunch.setActionDate(new java.util.Date(actionDateTime.getMillis()));

        missedPunch.setDocumentId(this.documentNumber);
        missedPunch.setTimestamp(new Timestamp(System.currentTimeMillis()));

        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(missedPunch.getTimesheetDocumentId());
        // Need to build a clock log entry.
        //Timestamp clockTimestamp, String selectedAssign, TimesheetDocument timesheetDocument, String clockAction, String ip) {
        ClockLog clockLog = TkServiceLocator.getClockLogService().buildClockLog(new Timestamp(actionDateTime.getMillis()),
                missedPunch.getAssignment(),
                tdoc,
                missedPunch.getClockAction(),
                TKUtils.getIPAddressFromRequest(TKContext.getHttpServletRequest()));

        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(missedPunch.getPrincipalId());
        TkServiceLocator.getClockLogService().saveClockLog(clockLog);
        missedPunch.setTkClockLogId(clockLog.getTkClockLogId());

        if (StringUtils.equals(clockLog.getClockAction(), TkConstants.CLOCK_OUT) ||
                StringUtils.equals(clockLog.getClockAction(), TkConstants.LUNCH_OUT)) {

            Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(
                    tdoc,
                    missedPunch.getAssignment());

            String earnCode = assignment.getJob().getPayTypeObj().getRegEarnCode();

            this.buildTimeBlockRunRules(lastClockLog, clockLog, tdoc, assignment, earnCode, lastClockLog.getClockTimestamp(), clockLog.getClockTimestamp());
        }
    }

    @Override
	public void saveBusinessObject() {
        super.saveBusinessObject();
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

        //reset time hour details
        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);
        //apply any rules for this action
        TkServiceLocator.getTkRuleControllerService().applyRules(
                TkConstants.ACTIONS.CLOCK_OUT, newTimeBlocks,
                tdoc.getPayCalendarEntry(),
                tdoc
        );

        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
    }
}
