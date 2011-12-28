package org.kuali.hr.time.shiftdiff.rule.dao;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;

import java.sql.Date;
import java.util.List;

public interface ShiftDifferentialRuleDao {

	public List<ShiftDifferentialRule> findShiftDifferentialRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, Date asOfDate);
	public ShiftDifferentialRule findShiftDifferentialRule(String id);
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule);
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules);
}
