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
package org.kuali.kpme.tklm.time.rules;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

public class TkRuleControllerServiceImpl implements TkRuleControllerService {

    // TODO : Filter actions to reduce computation for rule runs: Clock IN for example does not need to execute rule running.
	public List<TimeBlock> applyRules(String action, List<TimeBlock> timeBlocks, List<LeaveBlock> leaveBlocks, CalendarEntry payEntry, TimesheetDocument timesheetDocument, String principalId){
		//foreach action run the rules that apply
		if(StringUtils.equals(action, TkConstants.ACTIONS.ADD_TIME_BLOCK) || StringUtils.equals(action, TkConstants.ACTIONS.CLOCK_OUT)){
            List<TimeBlock> updatedTimeBlocks = new ArrayList<TimeBlock>();
            CalendarContract calendar = HrServiceLocator.getCalendarService().getCalendar(payEntry.getHrCalendarId());
            TkTimeBlockAggregate timeBlockAggregate = new TkTimeBlockAggregate(timeBlocks, leaveBlocks, payEntry, calendar, true);
            //
            // Need to run LunchRule first - It will reduce hours in some instances

            // anyone who records time through the time entry form (async users) should not be affected by the lunch rule
            // Noted that the date field in the buildTkUser method is not currently used.
            List<TimeBlock> lunchBlocks = new ArrayList<TimeBlock>();
            if (GlobalVariables.getUserSession() != null && TkContext.isTargetSynchronous()) {
			    lunchBlocks = TkServiceLocator.getDepartmentLunchRuleService().applyDepartmentLunchRule(timeBlockAggregate.getFlattenedTimeBlockList());
                //update aggregate with lunch blocks :(   ---- HACK!!! until we can stop passing everything through this aggregate!!!!
                TkTimeBlockAggregate tempAggregate = new TkTimeBlockAggregate(lunchBlocks, timeBlockAggregate.getPayCalendarEntry());
                timeBlockAggregate.setDayTimeBlockList(tempAggregate.getDayTimeBlockList());
            }

			TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getDailyOvertimeRuleService().processDailyOvertimeRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, timeBlockAggregate);
			// check for Premium holiday Earncode 
			return TkServiceLocator.getTimeBlockService().applyHolidayPremiumEarnCode(timesheetDocument.getPrincipalId(), timesheetDocument.getAssignments(), timeBlockAggregate.getFlattenedTimeBlockList());
		}
        return timeBlocks;
	}


}
