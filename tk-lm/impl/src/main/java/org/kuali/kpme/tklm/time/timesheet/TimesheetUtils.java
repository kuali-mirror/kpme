package org.kuali.kpme.tklm.time.timesheet;

import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TimesheetUtils {
    public static void processTimeBlocksWithRuleChange(List<TimeBlock> timeBlocks, List<TimeBlock> referenceTimeBlocks,
                                                       List<LeaveBlock> leaveBlocks, CalendarEntry calendarEntry,
                                                       TimesheetDocument td, String userPrincipalId) {
        timeBlocks = TkServiceLocator.getTimesheetService().resetTimeBlock(timeBlocks, td.getAsOfDate());
        timeBlocks = TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, timeBlocks, leaveBlocks, calendarEntry, td, userPrincipalId);
        TkServiceLocator.getTimeBlockService().saveOrUpdateTimeBlocks(referenceTimeBlocks, timeBlocks, userPrincipalId);
    }

    public static List<TimeBlock> getTimesheetTimeblocksForProcessing(TimesheetDocument doc, boolean includeOverlap) {
        List<TimeBlock> newTimeBlocks = doc.getTimeBlocks();

        if (includeOverlap) {
            List<TimeBlock> extraBlocks = TkServiceLocator.getShiftDifferentialRuleService().getTimeblocksOverlappingTimesheetShift(doc);
            for (TimeBlock extraTimeBlock : extraBlocks) {
                if (!newTimeBlocks.contains(extraTimeBlock)) {
                    newTimeBlocks.add(extraTimeBlock);
                }
            }
        }
        return newTimeBlocks;
    }

    public static List<TimeBlock> getReferenceTimeBlocks(List<TimeBlock> existingBlocks) {
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(existingBlocks.size());
        for (TimeBlock tb : existingBlocks) {
            referenceTimeBlocks.add(TimeBlock.copy(tb));
        }
        return Collections.unmodifiableList(referenceTimeBlocks);
    }

    public static List<LeaveBlock> getLeaveBlocksForTimesheet(TimesheetDocument doc) {
        CalendarEntry ce = doc.getCalendarEntry();
        List<Assignment> assignments = doc.getAllAssignments();
        Set<String> assignmentKeys = new HashSet<String>();
        for (Assignment assignment : assignments) {
            assignmentKeys.add(assignment.getAssignmentKey());
        }
        List<String> uniqueKeys = new ArrayList<String>(assignmentKeys);
        return LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(doc.getPrincipalId(), ce.getBeginPeriodFullDateTime().toLocalDate(), ce.getEndPeriodFullDateTime().toLocalDate(), uniqueKeys);
    }
}
