package org.kuali.hr.time.overtime.daily.rule.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;

public interface DailyOvertimeRuleDao {

	/**
	 * Given a Department, Work Area, Task and Effective Date, provides a list of Daily Overtime Rules.
	 * 
	 * @param dept 
	 * @param workArea 
	 * @param task 
	 * @param asOfDate Required.
	 * @return
	 */
	public List<DailyOvertimeRule> findDailyOvertimeRules(String dept, Long workArea, Long task, Date asOfDate);
	
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule);
	
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules);
}
