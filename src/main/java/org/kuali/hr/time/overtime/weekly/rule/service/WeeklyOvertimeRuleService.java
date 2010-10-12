package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface WeeklyOvertimeRuleService {

	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule);
	
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules);

	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(String fromEarnGroup, Date asOfDate);
	
	public void processWeeklyOvertimeRule(List<WeeklyOvertimeRule> lstWeeklyOvertime, TimesheetDocument timesheetDocument);
}
