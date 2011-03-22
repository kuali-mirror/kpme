package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public interface WeeklyOvertimeRuleService {
	/**
	 * Save or Update a given WeeklyOvertimeRule
	 * @param weeklyOvertimeRule
	 */
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule);
	/**
	 * Save or Update a List of WeeklyOvertimeRules
	 * @param weeklyOvertimeRules
	 */
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules);
	/**
	 * Fetch a List of WeeklyOvertimeRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(Date asOfDate);
	/**
	 * Process weekly overtime rules for a given TkTimeBlockAggregate
	 * @param timesheetDocument
	 * @param aggregate
	 */
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);
}
