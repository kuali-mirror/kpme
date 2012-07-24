package org.kuali.hr.time.rule;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

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
            if (TKContext.getUser().getCurrentTargetRoles().isSynchronous()) {
			    TkServiceLocator.getDepartmentLunchRuleService().applyDepartmentLunchRule(timeBlockAggregate.getFlattenedTimeBlockList());
            }
			TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getDailyOvertimeRuleService().processDailyOvertimeRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, timeBlockAggregate);
		}
	}


}