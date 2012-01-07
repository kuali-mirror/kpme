package org.kuali.hr.time.overtime.weekly.rule.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;

public interface WeeklyOvertimeRuleDao {
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule);
	
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules);
	
	public List<WeeklyOvertimeRule> findWeeklyOvertimeRules(Date asOfDate);
	
	public WeeklyOvertimeRule getWeeklyOvertimeRule(String tkWeeklyOvertimeRuleId);
}
