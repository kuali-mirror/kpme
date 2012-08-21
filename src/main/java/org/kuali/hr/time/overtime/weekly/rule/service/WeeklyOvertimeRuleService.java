package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface WeeklyOvertimeRuleService {
	/**
	 * Save or Update a given WeeklyOvertimeRule
	 * @param weeklyOvertimeRule
	 */
    @CacheEvict(value={WeeklyOvertimeRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule);
	/**
	 * Save or Update a List of WeeklyOvertimeRules
	 * @param weeklyOvertimeRules
	 */
    @CacheEvict(value={WeeklyOvertimeRule.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules);
	/**
	 * Fetch a List of WeeklyOvertimeRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= WeeklyOvertimeRule.CACHE_NAME, key="'asOfDate=' + #p1")
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(Date asOfDate);
	/**
	 * Process weekly overtime rules for a given TkTimeBlockAggregate
	 * @param timesheetDocument
	 * @param aggregate
	 */
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);
	/**
	 * Fetch Weekly overtime rule by id
	 * @param tkWeeklyOvertimeRuleId
	 * @return
	 */
    @Cacheable(value= WeeklyOvertimeRule.CACHE_NAME, key="'tkWeeklyOvertimeRuleId=' + #p0")
	public WeeklyOvertimeRule getWeeklyOvertimeRule(String tkWeeklyOvertimeRuleId);
}
