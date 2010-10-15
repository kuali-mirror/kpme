package org.kuali.hr.time.overtime.weekly.rule.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
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
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.WorkAreaOvertimePref;

import edu.emory.mathcs.backport.java.util.Collections;

public class WeeklyOvertimeRuleServiceImpl implements WeeklyOvertimeRuleService {

	private WeeklyOvertimeRuleDao weeklyOvertimeRuleDao;

	@Override
	public void processWeeklyOvertimeRule(List<WeeklyOvertimeRule> weeklyOvertimeRules, TimesheetDocument timesheetDocument) {
		java.sql.Date asOfDate = TKUtils.getTimelessDate(timesheetDocument.getDocumentHeader().getPayBeginDate());
		String principalId = timesheetDocument.getDocumentHeader().getPrincipalId();
		// Iterate over Weekly Overtime Rules
		for (WeeklyOvertimeRule wor : weeklyOvertimeRules) {
			// Grab all the earn codes for the convert from max hours group
			Set<String> maxHoursEarnCodes = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(wor.getMaxHoursEarnGroup(), asOfDate);
			Set<String> convertFromEarnCodes = TkServiceLocator.getEarnGroupService().getEarnCodeListForEarnGroup(wor.getConvertFromEarnGroup(), asOfDate);
			List<Interval> weekIntervals = TKUtils.getWeekIntervals(timesheetDocument.getDocumentHeader().getPayBeginDate(), timesheetDocument.getDocumentHeader().getPayEndDate());
			List<List<TimeBlock>> timeBlockWeekList = getTimeBlockWeekArrayList(timesheetDocument.getTimeBlocks(), weekIntervals);

			// Hit each week, one at a time.
			for (int i = 0; i < weekIntervals.size(); i++) {
				List<TimeBlock> weekBlocks = timeBlockWeekList.get(i);
				BigDecimal weekHours = getWeekHourSum(weekBlocks, maxHoursEarnCodes);
				BigDecimal ovtHours  = weekHours.subtract(wor.getMaxHours(), TkConstants.MATH_CONTEXT);
				// This is an overtime condition.
				if (ovtHours.compareTo(BigDecimal.ZERO) > 0) {
					Collections.sort(weekBlocks, new Comparator<TimeBlock>() {
						public int compare(TimeBlock tb1, TimeBlock tb2) {
							if (tb1 != null && tb2 != null)
								return -1 * tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
							return 0;
						}	
					});
					
					// Iterate and apply hours, only if we have time blocks and overtime hours > 0.
					for (int pos=0; (pos<weekBlocks.size() && ovtHours.compareTo(BigDecimal.ZERO) > 0); pos++) {
						TimeBlock block = weekBlocks.get(pos);
						
						// Check for Work Area Overtime Preference
						WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(block.getWorkArea(), asOfDate);
						Job job = TkServiceLocator.getJobSerivce().getJob(principalId, block.getJobNumber(), asOfDate);
						List<WorkAreaOvertimePref> otPrefs = workArea.getOverTimePrefs();
						String otEarnCode = null;
						for (WorkAreaOvertimePref waop : otPrefs) {
							if (waop.getPayType().equals(job.getPayType().getPayType())) {
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
		
		// If we have new time Hour details to add to the time block, add them; persist
		if (addDetails.size() > 0) {
			details.addAll(addDetails);
			block.setTimeHourDetails(details);
			TkServiceLocator.getTimeHourDetailService().saveTimeHourDetail(block);
		}
		
		return otHours.subtract(applied);
	}

	/**
	 * @return A sum of the hours for the provided week based on hours contained within the maxHoursEarnCodes.
	 */
	protected BigDecimal getWeekHourSum(List<TimeBlock> weekBlock, Set<String> maxHoursEarnCodes) {
		BigDecimal sum = BigDecimal.ZERO;

		// Check against earn code list, include in sum if present.
		for (TimeBlock t : weekBlock) {
			List<TimeHourDetail> details = t.getTimeHourDetails();
			for (TimeHourDetail detail : details) {
				if (maxHoursEarnCodes.contains(detail.getEarnCode())) {
					sum = sum.add(detail.getHours());
				}
			}
		}

		return sum;
	}

	/**
	 * @return A list of a list of TimeBlocks. Inner list represents 7
	 *         consecutive 24 hour spans worth of time blocks.  There should be 
	 *         no time blocks that span multiple "day" or "week" boundaries.
	 */
	protected List<List<TimeBlock>> getTimeBlockWeekArrayList(List<TimeBlock> timeBlocks, List<Interval> endDates) {
		List<List<TimeBlock>> weekList = new ArrayList<List<TimeBlock>>(endDates.size());

		for (int i = 0; i < endDates.size(); i++) {
			Interval first = endDates.get(i);

			// Add a LinkedList for the first Week
			weekList.add(new LinkedList<TimeBlock>());

			for (TimeBlock block : timeBlocks) {
				DateTime blockStartDateTime = new DateTime(block.getBeginTimestamp());
				DateTime blockEndDateTime = new DateTime(block.getEndTimestamp());

				if (first.contains(blockStartDateTime)) {
					if (first.contains(blockEndDateTime)) {
						// contained completely in a single week
						weekList.get(i).add(block);
					} else {
						throw new RuntimeException("Time Block out of Bounds.");
					}
				} else {
					if (i >= endDates.size()) {
						throw new RuntimeException("Time Block out of Bounds.");
					}
				}
			}
		}

		return weekList;
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
