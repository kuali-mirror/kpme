package org.kuali.hr.time.overtime.daily.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public interface DailyOvertimeRuleService {
	/**
	 * Save of update a DailyOvertimeRule
	 * @param dailyOvertimeRule
	 */
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule);
	/**
	 * Save or Update a List of DailyOvertimeRules
	 * @param dailyOvertimeRules
	 */
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules);
	
	/**
	 * Fetch DailyOvertimeRule for the given criteria
	 * @param location
	 * @param paytype
	 * @param dept
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
	public DailyOvertimeRule getDailyOvertimeRule(String location, String paytype, String dept, Long workArea, Date asOfDate);
	/**
	 * Process DailyOvertimeRules for the given TkTimeBlockAggregate
	 * @param timesheetDocument
	 * @param aggregate
	 */
	public void processDailyOvertimeRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);

	public DailyOvertimeRule getDailyOvertimeRule(Long tkDailyOvertimeRuleId);
}
