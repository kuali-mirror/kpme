package org.kuali.hr.time.shiftdiff.rule.service;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.shiftdiff.rule.dao.ShiftDifferentialRuleDao;
import org.kuali.hr.time.timesheet.TimesheetDocument;


public class ShiftDifferentialRuleServiceImpl implements ShiftDifferentialRuleService {
	
	private ShiftDifferentialRuleDao shiftDifferentialRuleDao = null;
	
	public void setShiftDifferentialRuleDao(ShiftDifferentialRuleDao shiftDifferentialRuleDao) {
		this.shiftDifferentialRuleDao = shiftDifferentialRuleDao;
	}

	@Override
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location, String tkSalGroup, String payGrade, Date asOfDate) {
		List<ShiftDifferentialRule> sdrs = shiftDifferentialRuleDao.findShiftDifferentialRules(location, tkSalGroup, payGrade, asOfDate);
		
		if (sdrs == null)
			sdrs = Collections.emptyList();
		
		return sdrs;
	}

	@Override
	public void processShiftDifferentialRules(List<ShiftDifferentialRule> shiftDifferentalRules, TimesheetDocument timesheetDocument) {
		/*
		 // TODO : Paste Business Logic Description Here
		 */
		
		
		// TODO : Implement me!
	}

	@Override
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRules);
	}

	@Override
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRule);
	}

}
