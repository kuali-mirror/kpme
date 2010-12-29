package org.kuali.hr.time.overtime.daily.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public interface DailyOvertimeRuleService {

	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule);
	
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules);
	
	public DailyOvertimeRule getDailyOvertimeRule(String location, String paytype, String dept, Long workArea, Date asOfDate);
	
	public void processDailyOvertimeRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);
}
