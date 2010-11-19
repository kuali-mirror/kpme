package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.overtime.weekly.rule.dao.WeeklyOvertimeRuleDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaOvertimePref;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import edu.emory.mathcs.backport.java.util.Collections;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {

	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;

	@Override
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {
		java.sql.Date asOfDate = TKUtils.getTimelessDate(timesheetDocument.getDocumentHeader().getPayBeginDate());
		String principalId = timesheetDocument.getDocumentHeader().getPrincipalId();
		List<WeeklyOvertimeRule> weeklyOvertimeRules = this.getWeeklyOvertimeRules(asOfDate);
		
		for (WeeklyOvertimeRule wor : weeklyOvertimeRules) {
			// Grab all the earn codes for the convert from max hours group
			Set<String> maxHoursEarnCodes = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(wor.getMaxHoursEarnGroup(), asOfDate);
			Set<String> convertFromEarnCodes = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(wor.getConvertFromEarnGroup(), asOfDate);
			List<Interval> weekIntervals = TKUtils.getWeekIntervals(timesheetDocument.getDocumentHeader().getPayBeginDate(), timesheetDocument.getDocumentHeader().getPayEndDate());
					
			// using new data structure.
			for (int weekCounter = 0; weekCounter < weekIntervals.size(); weekCounter++) {
				
				// At Week 0, we need to look back and figure out the number of 
				// hours that are counting towards Max Hours, no retroactive
				// application is necessary.
				if (weekCounter == 0) {
					 List<TimeBlock> prevBlocks = TkServiceLocator.getTimesheetService().getPrevDocumentTimeBlocks(principalId, timesheetDocument.getDocumentHeader().getDocumentId());
					 TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, timesheetDocument.getDocumentHeader().getDocumentId());
				
				}
				
				List<List<TimeBlock>> weekDaysBlock = aggregate.getFlsaWeekTimeBlocks(weekCounter);
				BigDecimal weekHours = this.getWeekHourSum(weekDaysBlock, maxHoursEarnCodes);
				BigDecimal ovtHours  = weekHours.subtract(wor.getMaxHours(), TkConstants.MATH_CONTEXT);
				
				// Start at the End of the week, and move to Day 0.
				for (int backwardsDayCounter = weekDaysBlock.size()-1; backwardsDayCounter >= 0; backwardsDayCounter--) {
					List<TimeBlock> dayBlock = weekDaysBlock.get(backwardsDayCounter);
					// This is an overtime condition. (ovtHours is decremented as we go)
					if (ovtHours.compareTo(BigDecimal.ZERO) > 0) {
						// We will reverse sort the list of Time Blocks for this day
						Collections.sort(dayBlock, new Comparator<TimeBlock>() {
							public int compare(TimeBlock tb1, TimeBlock tb2) {
								if (tb1 != null && tb2 != null)
									return -1 * tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
								return 0;
							}	
						});
						
						// Iterate and apply hours, only if we have time blocks and overtime hours > 0.
						for (int pos=0; (pos<dayBlock.size() && ovtHours.compareTo(BigDecimal.ZERO) > 0); pos++) {
							TimeBlock block = dayBlock.get(pos);
							
							// Check for Work Area Overtime Preference
							WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(block.getWorkArea(), asOfDate);
							Job job = TkServiceLocator.getJobSerivce().getJob(principalId, block.getJobNumber(), asOfDate);
							List<WorkAreaOvertimePref> otPrefs = workArea.getOverTimePrefs();
							String otEarnCode = null;
							for (WorkAreaOvertimePref waop : otPrefs) {
								if (waop.getPayType().equals(job.getPayTypeObj().getPayType())) {
									otEarnCode = waop.getOvertimePreference();
								}
							}
							
							// If no Work Area Overtime Preference, set to the Weekly Overtime Rule Earn Code
							if (otEarnCode == null)	
								otEarnCode = wor.getConvertToEarnCode();
							
							// Apply OT Hours to otEarnCode by looking for earn codes in the convert_from_earngroup
							//
							ovtHours = applyOvertimeToTimeBlock(block, otEarnCode, convertFromEarnCodes, ovtHours);	
						}
					}					
				}
			}
			
		}
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
		
		List<TimeHourDetail> details = block.getTimeHourDetails();
		List<TimeHourDetail> addDetails = new LinkedList<TimeHourDetail>();
		for (TimeHourDetail detail : details) {
			// Apply
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
				addDetails.add(timeHourDetail);
			}
		}
		
		// If we have new time Hour details to add to the time block, add them.
		// We are modifying the data in the list in place, the caller into this service 
		// will handle the diff / persist.
		if (addDetails.size() > 0) {
			details.addAll(addDetails);
			block.setTimeHourDetails(details);
		}
		
		return otHours.subtract(applied);
	}
	
	/**
	 * Iterates over each day of week list, time block and time block details to sum the hours.
	 * 
	 * @param weekDaysBlock
	 * @param maxHoursEarnCodes
	 * @return A sum of the hours for the provided week based on hours contained within the maxHoursEarnCodes.
	 */
	protected BigDecimal getWeekHourSum(List<List<TimeBlock>> weekDaysBlock, Set<String> maxHoursEarnCodes) {
		BigDecimal sum = BigDecimal.ZERO;
		
		for (List<TimeBlock> days : weekDaysBlock) {
			for (TimeBlock block : days) {
				List<TimeHourDetail> details = block.getTimeHourDetails();
				for (TimeHourDetail detail : details) {
					if (maxHoursEarnCodes.contains(detail.getEarnCode())) {
						sum = sum.add(detail.getHours());
					}
				}				
			}
		}
		
		return sum;
	}

	@Override
	public List<WeeklyOvertimeRule> getWeeklyOvertimeRules(Date asOfDate) {
		return weeklyOvertimeRuleDao.findWeeklyOvertimeRules(asOfDate);
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