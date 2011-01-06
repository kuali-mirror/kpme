package org.kuali.hr.time.overtime.daily.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.overtime.daily.rule.dao.DailyOvertimeRuleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
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
	@CacheResult
	/**
	 * Search for the valid Daily Overtime Rule, wild cards are allowed on
	 * location 
	 * paytype
	 * department
	 * workArea
	 * 
	 * asOfDate is required.
	 */
	public DailyOvertimeRule getDailyOvertimeRule(String location, String paytype, String dept, Long workArea, Date asOfDate) {
		DailyOvertimeRule dailyOvertimeRule = null;
		
		//		l, p, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, paytype, dept, workArea, asOfDate);

		//		l, p, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, paytype, dept, -1L, asOfDate);
		
		//		l, p, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, paytype, "%", workArea, asOfDate);
		
		//		l, p, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, paytype, "%", -1L, asOfDate);
		
		//		l, *, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, "%", dept, workArea, asOfDate);
		
		//		l, *, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, "%", dept, -1L, asOfDate);
		
		//		l, *, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, "%", "%", workArea, asOfDate);
		
		//		l, *, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(location, "%", "%", -1L, asOfDate);
		
		//		*, p, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, dept, workArea, asOfDate);
		
		//		*, p, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, dept, -1L, asOfDate);
		
		//		*, p, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, "%", workArea, asOfDate);
		
		//		*, p, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, "%", -1L, asOfDate);
		
		//		*, *, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", dept, workArea, asOfDate);
		
		//		*, *, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", dept, -1L, asOfDate);
		
		//		*, *, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", "%", workArea, asOfDate);
		
		//		*, *, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", "%", -1L, asOfDate);		
							
		return dailyOvertimeRule;
	}



	public void setDailyOvertimeRuleDao(DailyOvertimeRuleDao dailyOvertimeRuleDao) {
		this.dailyOvertimeRuleDao = dailyOvertimeRuleDao;
	}
	
	/**
	 * Provides a key used to store and look up Daily Overtime Rules.
	 * @param job
	 * @param assignment
	 * @return
	 */
	private String getIdentifyingKey(Assignment assignment) {
		StringBuffer keybuf = new StringBuffer();
		Job job = assignment.getJob();
		
		keybuf.append(job.getLocation()).append('_');
		keybuf.append(job.getHrPayType()).append('_');
		keybuf.append(job.getDept()).append('_');
		keybuf.append(assignment.getWorkArea());
		
		return keybuf.toString();
	}

	/** Duplicated method:: 'Expression Problem' problem. :) */
	private String getIdentifyingKey(TimeBlock block, Date asOfDate, String principalId) {
		StringBuffer keybuf = new StringBuffer();
		Job job = TkServiceLocator.getJobSerivce().getJob(principalId, block.getJobNumber(), asOfDate);
		
		keybuf.append(job.getLocation()).append('_');
		keybuf.append(job.getHrPayType()).append('_');
		keybuf.append(job.getDept()).append('_');
		keybuf.append(block.getWorkArea());
		
		return keybuf.toString();
	}

	
	public void processDailyOvertimeRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate timeBlockAggregate){
		Map<String, DailyOvertimeRule> idKeyToDailyOvertimeRules = new HashMap<String, DailyOvertimeRule>();
		Map<String, List<WorkSchedule>> idKeyToWorkSchedules = new HashMap<String, List<WorkSchedule>>();
		
		//iterate over all assignments and place the list of rules if any in map
		// TODO : Verify the required rules here - What are we using to retrieve the rules
		for(Assignment assignment : timesheetDocument.getAssignments()) {
			Job job = assignment.getJob();
			DailyOvertimeRule dailyOvertimeRule = getDailyOvertimeRule(job.getLocation(), job.getHrPayType(), job.getDept(), assignment.getWorkArea(), timesheetDocument.getAsOfDate());
	
			if(dailyOvertimeRule !=null) {
				String idKey = this.getIdentifyingKey(assignment);
				idKeyToDailyOvertimeRules.put(idKey, dailyOvertimeRule);
				List<WorkSchedule> workSchedules = TkServiceLocator.getWorkScheduleService().getWorkSchedules(timesheetDocument.getPrincipalId(), assignment.getJob().getDept(), assignment.getWorkArea(), timesheetDocument.getAsOfDate());
				
				if(workSchedules!=null && !workSchedules.isEmpty()){
					idKeyToWorkSchedules.put(idKey, workSchedules);
				}
			}
		}
				
		// Quick Bail
		if(idKeyToDailyOvertimeRules.isEmpty()){
			return;
		}

		// TODO: We iterate Day by Day
		for(List<TimeBlock> dayTimeBlocks : timeBlockAggregate.getDayTimeBlockList()){
			
			if (dayTimeBlocks.size() == 0)
				continue;
			
			// 1: ... bucketing by (idKey -> List<TimeBlock>)
			Map<String,List<TimeBlock>> idKeyToDayTotals = new HashMap<String,List<TimeBlock>>();
			for(TimeBlock timeBlock : dayTimeBlocks) {
				String idKey = this.getIdentifyingKey(timeBlock, timesheetDocument.getAsOfDate(), timesheetDocument.getPrincipalId());
				List<TimeBlock> blocks = idKeyToDayTotals.get(idKey);
				if (blocks == null) {
					blocks = new LinkedList<TimeBlock>();
					idKeyToDayTotals.put(idKey, blocks);
				}
				blocks.add(timeBlock);
			}
			
			// 2: Iterate over ruleKeys, then over blockBucket
			for (String key : idKeyToDailyOvertimeRules.keySet()) {
				DailyOvertimeRule rule = idKeyToDailyOvertimeRules.get(key); // should be able to assume not null here.
				Set<String> fromEarnGroup = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(rule.getFromEarnGroup(), TKUtils.getTimelessDate(timesheetDocument.getPayCalendarEntry().getBeginPeriodDateTime()));
				List<TimeBlock> blocksForRule = idKeyToDayTotals.get(key);
				if (blocksForRule == null || blocksForRule.size() == 0)
					continue; // skip to next rule and check for valid blocks.
				sortTimeBlocksNatural(blocksForRule);
				
				// 3: Iterate over the timeblocks, apply the rule when necessary.
				BigDecimal hours = BigDecimal.ZERO;
				List<TimeBlock> applicationList = new LinkedList<TimeBlock>();
				TimeBlock previous = null;
				for (TimeBlock block : blocksForRule) {
					if (exceedsMaxGap(previous, block, rule.getMaxGap())) {
						apply(hours, applicationList, rule, fromEarnGroup);
						applicationList.clear();
						hours = BigDecimal.ZERO;
						previous = null; // reset our chain
					} else {
						applicationList.add(block);
						previous = block; // build up our chain
					}
					for (TimeHourDetail thd : block.getTimeHourDetails())
						if (fromEarnGroup.contains(thd.getEarnCode()))
							hours = hours.add(thd.getHours(), TkConstants.MATH_CONTEXT);						
				}
				// when we run out of blocks, we may have more to apply.
				apply(hours, applicationList, rule, fromEarnGroup);
			}
			
		}
	}
	
	/**
	 * Reverse sorts blocks and applies hours to matching earn codes in the 
	 * time hour detail entries.
	 * 
	 * @param hours Total number of Daily Overtime Hours to apply.
	 * @param blocks Time blocks found to need rule application.
	 * @param rule The rule we are applying.
	 * @param earnGroup Earn group we've already loaded for this rule.
	 */
	private void apply(BigDecimal hours, List<TimeBlock> blocks, DailyOvertimeRule rule, Set<String> earnGroup) {
		sortTimeBlocksInverse(blocks);
		if (blocks != null && blocks.size() > 0)
			if (hours.compareTo(rule.getMinHours()) >= 0)
				for (TimeBlock block : blocks)
					hours = applyOvertimeToTimeBlock(block, rule.getEarnCode(), earnGroup, hours.subtract(rule.getMinHours(), TkConstants.MATH_CONTEXT));
	}
	
	
	/**
	 * Method to apply (if applicable) overtime additions to the indiciated TimeBlock.  TimeBlock 
	 * earn code is checked against the convertFromEarnCodes Set.
	 * 
	 * @param block
	 * @param otEarnCode
	 * @param convertFromEarnCodes
	 * @param otHours
	 * 
	 * @return The amount of overtime hours remaining to be applied.  (BigDecimal is immutable)
	 */
	private BigDecimal applyOvertimeToTimeBlock(TimeBlock block, String otEarnCode, Set<String> convertFromEarnCodes, BigDecimal otHours) {
		BigDecimal applied = BigDecimal.ZERO;
		
		if (otHours.compareTo(BigDecimal.ZERO) <= 0)
			return BigDecimal.ZERO;
		
		List<TimeHourDetail> details = block.getTimeHourDetails();
		List<TimeHourDetail> addDetails = new LinkedList<TimeHourDetail>();
		for (TimeHourDetail detail : details) {
			if (convertFromEarnCodes.contains(detail.getEarnCode())) {
				// n = detailHours - otHours
				BigDecimal n = detail.getHours().subtract(otHours, TkConstants.MATH_CONTEXT);
				// n >= 0 (meaning there are greater than or equal amount of Detail hours vs. OT hours, so apply all OT hours here)
				// n < = (meaning there were more OT hours than Detail hours, so apply only the # of hours in detail and update applied.
				if (n.compareTo(BigDecimal.ZERO) >= 0) {
					// if 
					applied = otHours;
				} else {
					applied = detail.getHours();
				}
				
				// Make a new TimeHourDetail with the otEarnCode with "applied" hours
				TimeHourDetail timeHourDetail = new TimeHourDetail();
				timeHourDetail.setHours(applied);
				timeHourDetail.setEarnCode(otEarnCode);
				timeHourDetail.setTkTimeBlockId(block.getTkTimeBlockId());
				
				// Decrement existing matched FROM earn code.
				detail.setHours(detail.getHours().subtract(applied, TkConstants.MATH_CONTEXT));				
				addDetails.add(timeHourDetail);
			}
		}
		
		if (addDetails.size() > 0) {
			details.addAll(addDetails);
			block.setTimeHourDetails(details);
		}
		
		return otHours.subtract(applied);
	}

	
	// TODO : Refactor this Copy-Pasta mess to util/comparator classes.

	private void sortTimeBlocksInverse(List<TimeBlock> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlock>() { // Sort the Time Blocks
			public int compare(TimeBlock tb1, TimeBlock tb2) {
				if (tb1 != null && tb2 != null)
					return -1 * tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
				return 0;
			}	
		});		
	}

	
	private void sortTimeBlocksNatural(List<TimeBlock> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlock>() { // Sort the Time Blocks
			public int compare(TimeBlock tb1, TimeBlock tb2) {
				if (tb1 != null && tb2 != null)
					return tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
				return 0;
			}	
		});		
	}
	
	/**
	 * Does the difference between the previous time blocks clock out time and the 
	 * current time blocks clock in time exceed the max gap?
	 * 
	 * @param previous If null, false is returned.
	 * @param current
	 * @param maxGap
	 * @return
	 */
	boolean exceedsMaxGap(TimeBlock previous, TimeBlock current, BigDecimal maxGap) {
		if (previous == null) 
			return false;
		
		long difference = current.getBeginTimestamp().getTime() - previous.getEndTimestamp().getTime();
		BigDecimal gapHours = TKUtils.convertMillisToHours(difference);
		
		return (gapHours.compareTo(maxGap) > 0);
	}
}
