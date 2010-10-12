package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.overtime.weekly.rule.dao.WeeklyOvertimeRuleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {
	
	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;
	
	
	@Override
	public void processWeeklyOvertimeRule(List<WeeklyOvertimeRule> lstWeeklyOvertime,
			TimesheetDocument timesheetDocument) {
		//Grab the earnGroup to calculate overtime
		String earnGroup = lstWeeklyOvertime.get(0).getMaxHoursEarnGroup();
		
		//Grab all the earn codes for the convert from max hours group
		List<String> earnCodes = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(earnGroup, (java.sql.Date)timesheetDocument.getDocumentHeader().getPayBeginDate());
		//Calculate all the hours for this and compare against the max hours to determine if ovt occurred for
		//each week which is determined by a 7 day week
		int numOfWeeks = TKUtils.getNumberOfWeeks((java.sql.Date)timesheetDocument.getDocumentHeader().getPayBeginDate(), (java.sql.Date)timesheetDocument.getDocumentHeader().getPayEndDate());
		
		for(int i = 0 ;i< numOfWeeks;i++){
			//find the amount of overtime
			
			//if any overtime do the reverse sort on the week 
		}
		
		//Convert timeblocks as per the rule by reverse sorting and starting at teh end of each week and convert
		//until hours are exhausted, convert to ovt pref if one for this workarea
		
	
		
	}
	
	

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
