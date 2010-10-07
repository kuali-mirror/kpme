package org.kuali.hr.time.shiftdiff.rule.dao;

import java.util.List;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;

public interface ShiftDifferentialRuleDao {

	public List<ShiftDifferentialRule> findShiftDifferentialRules();
}
