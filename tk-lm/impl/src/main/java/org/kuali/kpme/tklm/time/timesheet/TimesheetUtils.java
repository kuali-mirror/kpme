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
