package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.overtime.weekly.rule.dao.WeeklyOvertimeRuleDao;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {
	
	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;

	@Override
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(String fromEarnGroup, Date asOfDate) {
		return weeklyOvertimeRuleDao.findWeeklyOvertimeRules(fromEarnGroup, asOfDate);
	}

	@Override
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule) {
		weeklyOvertimeRuleDao.saveOrUpdate(weeklyOvertimeRule);
	}

	@Override
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules) {
		weeklyOvertimeRuleDao.saveOrUpdate(weeklyOvertimeRules);
	}

	public void setWeeklyOvertimeRuleDao(WeeklyOvertimeRuleDao weeklyOvertimeRuleDao) {
		this.weeklyOvertimeRuleDao = weeklyOvertimeRuleDao;
	}


}
