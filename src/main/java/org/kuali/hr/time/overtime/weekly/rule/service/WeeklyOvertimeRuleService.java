package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public interface WeeklyOvertimeRuleService {

	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule);
	
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules);

	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(Date asOfDate);
	
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);
}
