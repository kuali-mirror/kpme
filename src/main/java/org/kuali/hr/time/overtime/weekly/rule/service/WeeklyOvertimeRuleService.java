package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;

public interface WeeklyOvertimeRuleService {

	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule);
	
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules);
	
	// TODO : Refactor signature of this method to reflect typical use scenarios
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(String fromEarnGroup, Date asOfDate);
}
