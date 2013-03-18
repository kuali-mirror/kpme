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
package org.kuali.hr.time.rule;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.rice.krad.util.GlobalVariables;

public class TkRuleControllerServiceImpl implements TkRuleControllerService {

    // TODO : Filter actions to reduce computation for rule runs: Clock IN for example does not need to execute rule running.
	public void applyRules(String action, List<TimeBlock> timeBlocks, CalendarEntries payEntry, TimesheetDocument timesheetDocument, String principalId){
		//foreach action run the rules that apply
		if(StringUtils.equals(action, TkConstants.ACTIONS.ADD_TIME_BLOCK) || StringUtils.equals(action, TkConstants.ACTIONS.CLOCK_OUT)){
			TkTimeBlockAggregate timeBlockAggregate = new TkTimeBlockAggregate(timeBlocks, payEntry, payEntry.getCalendarObj(), true);
            //
            // Need to run LunchRule first - It will reduce hours in some instances

            // anyone who records time through the time entry form (async users) should not be affected by the lunch rule
            // Noted that the date field in the buildTkUser method is not currently used.
            if (GlobalVariables.getUserSession() != null && TKUser.getCurrentTargetRoles().isSynchronous()) {
			    TkServiceLocator.getDepartmentLunchRuleService().applyDepartmentLunchRule(timeBlockAggregate.getFlattenedTimeBlockList());
            }
			TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getDailyOvertimeRuleService().processDailyOvertimeRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, timeBlockAggregate);
		}
	}


}