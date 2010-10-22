package org.kuali.hr.time.overtime.daily.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;

public interface DailyOvertimeRuleService {

	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule);
	
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules);
	
	public DailyOvertimeRule getDailyOvertimeRule(String dept, Long workArea, Long task, Date asOfDate);
}
