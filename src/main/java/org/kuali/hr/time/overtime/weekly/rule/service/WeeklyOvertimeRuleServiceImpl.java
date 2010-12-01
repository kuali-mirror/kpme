package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.overtime.weekly.rule.dao.WeeklyOvertimeRuleDao;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
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

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {

	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;

	@Override
	public void processWeeklyOvertimeRule(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {
		java.sql.Date asOfDate = TKUtils.getTimelessDate(timesheetDocument.getDocumentHeader().getPayBeginDate());
		String principalId = timesheetDocument.getDocumentHeader().getPrincipalId();
		List<WeeklyOvertimeRule> weeklyOvertimeRules = this.getWeeklyOvertimeRules(asOfDate);

		// For the given payperiod, this is our time broken into FLSA weeks.
		List<FlsaWeek> flsaWeeks = aggregate.getFlsaWeeks();
		List<FlsaWeek> previousWeeks = null;
		
		if (flsaWeeks.size() == 0) {
			return;
		}
			
		FlsaWeek firstWeek = flsaWeeks.get(0);
		
		// Grab the previous list of FLSA Weeks.
		if (!firstWeek.isFirstWeekFull()) {		
			 List<TimeBlock> prevBlocks = TkServiceLocator.getTimesheetService().getPrevDocumentTimeBlocks(principalId, timesheetDocument.getDocumentHeader().getDocumentId());
			 if (prevBlocks.size() > 0) {
				TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, timesheetDocument.getDocumentHeader().getDocumentId());
				if (prevTdh != null) {
					PayCalendarDates prevPayCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(principalId, TKUtils.getTimelessDate(prevTdh.getPayBeginDate()));
					TkTimeBlockAggregate prevTimeAggregate = new TkTimeBlockAggregate(prevBlocks, prevPayCalendarEntry);
					previousWeeks = prevTimeAggregate.getFlsaWeeks();
					if (previousWeeks.size() == 0) {
						previousWeeks = null;
					}
				}
			 }
		}
		
		// Iterate over each Weekly Overtime Rule (We have to grab them all to see if they apply)
		for (WeeklyOvertimeRule wor : weeklyOvertimeRules) {
			// Grab all the earn codes for the convert from max hours group
			Set<String> maxHoursEarnCodes = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(wor.getMaxHoursEarnGroup(), asOfDate);
			Set<String> convertFromEarnCodes = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(wor.getConvertFromEarnGroup(), asOfDate);

			// Iterate over the weeks for this Pay Period (FLSA)
			//
			// Moving Week By week..
			for (int i = 0; i < flsaWeeks.size(); i++) {			
				BigDecimal maxHoursSum = BigDecimal.ZERO; // To consider whether we've met overtime
				BigDecimal overtimeHours = BigDecimal.ZERO; // The number of hours to apply
				
				// TODO : Step 1 - get maxHours from previous pay period week if available.
				// We have to consider our previous pay period, last week,
				// starting from the flsa begin day to the end.
				if (i == 0 && previousWeeks != null) {
					FlsaWeek previousLastWeek = previousWeeks.get(previousWeeks.size() - 1);
					// We know that this week is all admissible, we have already 
					// filtered by time and days.
					for (FlsaDay day : previousLastWeek.getFlsaDays()) {
						// figure out our reg hours to count towards this week.						
						for (String ec : day.getEarnCodeToHours().keySet()) {
							if (maxHoursEarnCodes.contains(ec)) {
								maxHoursSum = maxHoursSum.add(day.getEarnCodeToHours().get(ec), TkConstants.MATH_CONTEXT);
							}
						}						
					}
				}
				
				// TODO : Step 2 - Continue Computing Max Hours
				FlsaWeek currentWeek = flsaWeeks.get(i);
				for (FlsaDay day : currentWeek.getFlsaDays()) {
					for (String ec : day.getEarnCodeToHours().keySet()) {
						if (maxHoursEarnCodes.contains(ec)) {
							maxHoursSum = maxHoursSum.add(day.getEarnCodeToHours().get(ec), TkConstants.MATH_CONTEXT);
						}
					}					
				}
				
				// TODO : Compute how many hours to apply
				overtimeHours = maxHoursSum.subtract(wor.getMaxHours(), TkConstants.MATH_CONTEXT);				
				if (overtimeHours.compareTo(BigDecimal.ZERO) <= 0) {
					// nothing for this week, move to next week.
					continue;
				}
				
				// TODO : Step 3 - Reverse Sort current Time Blocks for this week.
				List<FlsaDay> daysOfCurrentWeek = currentWeek.getFlsaDays();
				if (daysOfCurrentWeek.size() > 0) {
					for (int j=daysOfCurrentWeek.size()-1; j >= 0; j--) {
						FlsaDay day = daysOfCurrentWeek.get(j);
						
						List<TimeBlock> dayBlocks = day.getAppliedTimeBlocks();
						Collections.sort(dayBlocks, new Comparator<TimeBlock>() { // Sort the Time Blocks
							public int compare(TimeBlock tb1, TimeBlock tb2) {
								if (tb1 != null && tb2 != null)
									return -1*tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
								return 0;
							}	
						});
				
						// Apply OT 
						for (TimeBlock block : dayBlocks) {
							if (overtimeHours.compareTo(BigDecimal.ZERO) > 0) {
								String overtimeEarnCode = getOvertimeEarnCode(principalId, block, wor, asOfDate);
								overtimeHours = applyOvertimeToTimeBlock(block, overtimeEarnCode, convertFromEarnCodes, overtimeHours);
							}
						}
					}
				}
			}		
		}
	}
	
	
	private String getOvertimeEarnCode(String principalId, TimeBlock block, WeeklyOvertimeRule wor, Date asOfDate) {
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

		return otEarnCode;
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
			String thdEarnCode = detail.getEarnCode();
			if (convertFromEarnCodes.contains(thdEarnCode)) {
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
		
		// If we have new time Hour details to add to the time block, add them.
		// We are modifying the data in the list in place, the caller into this service 
		// will handle the diff / persist.
		if (addDetails.size() > 0) {
			details.addAll(addDetails);
			block.setTimeHourDetails(details);
		}
		
		return otHours.subtract(applied);
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