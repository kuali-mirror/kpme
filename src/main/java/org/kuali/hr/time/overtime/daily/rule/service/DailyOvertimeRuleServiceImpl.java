package org.kuali.hr.time.overtime.daily.rule.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.overtime.daily.rule.dao.DailyOvertimeRuleDao;

public class DailyOvertimeRuleServiceImpl implements DailyOvertimeRuleService {
	
	private DailyOvertimeRuleDao dailyOvertimeRuleDao = null;
	
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule) {
		dailyOvertimeRuleDao.saveOrUpdate(dailyOvertimeRule);
	}
	
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules) {
		dailyOvertimeRuleDao.saveOrUpdate(dailyOvertimeRules);
	}
	
	@Override
	/**
	 * For now this will be implemented with all possible cases - need to improve this, though caching will alleviate 
	 * A lot of this insanity.
	 * 
	 * We have a binary permutation 2^n of our n independent variables, (in this case n=3; dept, work area, task).
	 * 
	 * This SHOULD be the only class that has this wacknuttery. 
	 */
	public List<DailyOvertimeRule> getDailyOvertimeRules(String dept, Long workArea, Long task, Date asOfDate) {
		List<DailyOvertimeRule> dotrls = new ArrayList<DailyOvertimeRule>();
		
		// department, workarea, task
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules(dept, workArea, task, asOfDate);
		
		// department, workarea, -1
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules(dept, workArea, -1L, asOfDate);
		
		// department, -1, task
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules(dept, -1L, task, asOfDate);
		
		// department, -1, -1
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules(dept, -1L, -1L, asOfDate);
		
		// *, workarea, task
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules("*", workArea, task, asOfDate);
		
		// *, workarea, -1
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules("*", workArea, -1L, asOfDate);
		
		// *, -1, task
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules("*", -1L, task, asOfDate);
		
		// *, -1, -1
		if (dotrls.isEmpty())
			dotrls = dailyOvertimeRuleDao.findDailyOvertimeRules("*", -1L, -1L, asOfDate);
		
		// Do anything else we have to do to the list, which is probably nothing.
		// ...
		//
		
		return dotrls;
	}



	public void setDailyOvertimeRuleDao(DailyOvertimeRuleDao dailyOvertimeRuleDao) {
		this.dailyOvertimeRuleDao = dailyOvertimeRuleDao;
	}

}
