package org.kuali.hr.time.overtime.daily.rule.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;

public interface DailyOvertimeRuleDao {

	/**
	 * Given a Department, Work Area, Task and Effective Date, provides the
	 * daily overtime rule that applies.
	 * 
	 * @return
	 */
	public DailyOvertimeRule findDailyOvertimeRule(String location, String paytype, String dept, Long workArea, Date asOfDate);
	
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule);
	
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules);
	
	public DailyOvertimeRule getDailyOvertimeRule(String tkDailyOvertimeRuleId);
}
