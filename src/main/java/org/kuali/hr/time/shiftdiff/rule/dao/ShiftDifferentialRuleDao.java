package org.kuali.hr.time.shiftdiff.rule.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;

public interface ShiftDifferentialRuleDao {

	public List<ShiftDifferentialRule> findShiftDifferentialRules(String location, String tkSalGroup, String payGrade, Date asOfDate);
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule);
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules);
}
