package org.kuali.hr.time.overtime.daily.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.overtime.daily.rule.dao.DailyOvertimeRuleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workschedule.WorkSchedule;

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
	
	public List<TimeBlock> processDailyOvertimeRule(TkTimeBlockAggregate timeBlockAggregate, TimesheetDocument timesheetDocument){
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		Map<String, List<DailyOvertimeRule>> assignKeyToDailyOverTimeRuleList = new HashMap<String, List<DailyOvertimeRule>>();
		Map<String, List<WorkSchedule>> assignKeyToWorkScheduleList = new HashMap<String, List<WorkSchedule>>();
		//iterate over all assignments and place the list of rules if any in map
		for(Assignment assign : timesheetDocument.getAssignments()){
			List<DailyOvertimeRule> dailyOvertimeRules = getDailyOvertimeRules(assign.getJob().getDept(), assign.getWorkArea(), assign.getTask(), timesheetDocument.getAsOfDate());
	
			if(dailyOvertimeRules!=null && !dailyOvertimeRules.isEmpty()){
				String assignKey = assign.getJobNumber()+"_"+assign.getWorkArea()+"_"+assign.getTask();
				assignKeyToDailyOverTimeRuleList.put(assignKey, dailyOvertimeRules);
				List<WorkSchedule> workScheduleList = TkServiceLocator.getWorkScheduleService().getWorkSchedules(timesheetDocument.getPrincipalId(), assign.getJob().getDept(), 
														assign.getWorkArea(), timesheetDocument.getAsOfDate());
				
				if(workScheduleList!=null && !workScheduleList.isEmpty()){
					assignKeyToWorkScheduleList.put(assignKey, workScheduleList);
				}
			}
		}
		//if no daily overtime rules found for this person bail out
		if(assignKeyToDailyOverTimeRuleList.isEmpty()){
			return timeBlockAggregate.getFlattenedTimeBlockList();
		}

		for(List<TimeBlock> lstDayTimeBlocks : timeBlockAggregate.getDayTimeBlockList()){
			Map<String,BigDecimal> assignKeyToDailyTotals = new HashMap<String,BigDecimal>();
			for(TimeBlock timeBlock : lstDayTimeBlocks){
				String assignKey = timeBlock.getJobNumber()+"_"+timeBlock.getWorkArea()+"_"+timeBlock.getTask();
				BigDecimal currVal = assignKeyToDailyTotals.get(assignKey);
				if(currVal == null){
					currVal = new BigDecimal(0);
				}
				currVal = currVal.add(timeBlock.getHours(), TkConstants.MATH_CONTEXT);
				assignKeyToDailyTotals.put(assignKey, currVal);
			}
			//TODO iterate over the daily totals that were just calculated and compare with rules for entry
			//TODO match up assignments to rules based on hours worked
			//TODO if work schedule entry for this day then ignore daily ovt rule as this may need to be impl specific logic 
			//TODO modify lstTimeBlocks accordingly
		}
		
		
		return lstTimeBlocks;
	}

}
