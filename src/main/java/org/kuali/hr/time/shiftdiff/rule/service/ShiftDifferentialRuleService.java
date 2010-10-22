package org.kuali.hr.time.shiftdiff.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public interface ShiftDifferentialRuleService {

	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules);
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule);
	
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location, String tkSalGroup, String payGrade, Date asOfDate);
	
	public void processShiftDifferentialRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate);

}
