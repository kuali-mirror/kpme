package org.kuali.hr.time.rule;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class TkRuleControllerServiceImpl implements TkRuleControllerService {
	
	public List<TimeBlock> applyRules(String action, List<TimeBlock> timeBlocks, PayCalendarEntries payEntry, TimesheetDocument timesheetDocument){
		//foreach action run the rules that apply
		List<TimeBlock> newTimeBlocks = timeBlocks;
		if(StringUtils.equals(action, TkConstants.ACTIONS.ADD_TIME_BLOCK) || StringUtils.equals(action, TkConstants.ACTIONS.CLOCK_OUT)){
			TkTimeBlockAggregate timeBlockAggregate = new TkTimeBlockAggregate(timeBlocks, payEntry);
			TkServiceLocator.getShiftDifferentialRuleService().processShiftDifferentialRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getDailyOvertimeRuleService().processDailyOvertimeRules(timesheetDocument, timeBlockAggregate);
			TkServiceLocator.getWeeklyOvertimeRuleService().processWeeklyOvertimeRule(timesheetDocument, timeBlockAggregate);
			// dept lunch rule
			newTimeBlocks = TkServiceLocator.getDepartmentLunchRuleService().applyDepartmentLunchRule(timeBlockAggregate.getFlattenedTimeBlockList());
		}
		return newTimeBlocks;
	}


}