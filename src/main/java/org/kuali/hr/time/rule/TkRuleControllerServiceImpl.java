package org.kuali.hr.time.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.util.List;

public class TkRuleControllerServiceImpl implements TkRuleControllerService {

    // TODO : Filter actions to reduce computation for rule runs: Clock IN for example does not need to execute rule running.
	public void applyRules(String action, List<TimeBlock> timeBlocks, PayCalendarEntries payEntry, TimesheetDocument timesheetDocument, String principalId){
		//foreach action run the rules that apply
		if(StringUtils.equals(action, TkConstants.ACTIONS.ADD_TIME_BLOCK) || StringUtils.equals(action, TkConstants.ACTIONS.CLOCK_OUT)){
			TkTimeBlockAggregate timeBlockAggregate = new TkTimeBlockAggregate(timeBlocks, payEntry, payEntry.getPayCalendarObj(), true);
            //
            // Need to run LunchRule first - It will reduce hours in some instances

            // anyone who records time through the time entry form (async users) should not be affected by the lunch rule
            // Noted that the date field in the buildTkUser method is not currently used.
            TKUser user = TkServiceLocator.getUserService().buildTkUser(principalId, new java.sql.Date(System.currentTimeMillis()));
            if (user.getCurrentTargetRoles().isSynchronous()) {
			    TkServiceLocator.getDepartmentLunchRuleService().applyDepartmentLunchRule(timeBlockAggregate.getFlattenedTimeBlockList());
            }
			TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getDailyOvertimeRuleService().processDailyOvertimeRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, timeBlockAggregate);
		}
	}


}