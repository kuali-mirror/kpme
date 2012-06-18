package org.kuali.hr.lm.accrual.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.accrual.RateRange;
import org.kuali.hr.lm.accrual.RateRangeAggregate;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class AccrualServiceImpl implements AccrualService {

	@Override
	public void runAccrual(String principalId) {
		Date startDate = getStartAccrualDate(principalId);
		Date endDate = getEndAccrualDate(principalId);

		System.out.println("AccrualServiceImpl.runAccrual() STARTED with Principal: "+principalId);
		
		runAccrual(principalId,startDate,endDate);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void runAccrual(String principalId, Date startDate, Date endDate) {
		List<LeaveBlock> accrualLeaveBlocks = new ArrayList<LeaveBlock>();
		Map<String, BigDecimal> accumulatedAccrualCatToAccrualAmounts = new HashMap<String,BigDecimal>();
		if (startDate != null && endDate != null) {
			System.out.println("AccrualServiceImpl.runAccrual() STARTED with Principal: "+principalId+" Start: "+startDate.toString()+" End: "+endDate.toString());
		}
		if(startDate.after(endDate)) {
			throw new RuntimeException("Start Date " + startDate.toString() + " should not be later than End Date " + endDate.toString());
		}
		//Inactivate all previous accrual entries for this span of time
		List<LeaveBlock> previousLB = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, startDate, endDate);
		for(LeaveBlock lb : previousLB) {
			TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(new Long(lb.getLmLeaveBlockId()));
		}
		
		//Build a rate range aggregate with appropriate information for this period of time detailing Rate Ranges for job
		//entries for this range of time
		RateRangeAggregate rrAggregate = this.buildRateRangeAggregate(principalId, startDate, endDate);	
		PrincipalHRAttributes phra = null;
		PrincipalHRAttributes endPhra = null;
		LeavePlan lp = null;
		List<AccrualCategory> accrCatList = null;
		
		//Iterate over every day in span 
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(startDate);
		while (!aCal.getTime().after(endDate)) {
			java.util.Date currentDate = aCal.getTime();
			if(phra == null) {
				phra = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, currentDate);
			}
			if(phra == null || TKUtils.removeTime(currentDate).before(TKUtils.removeTime(phra.getServiceDate()))) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}			
			
	// use the effectiveDate of this principalHRAttribute to search for inactive entries for this principalId
	// If there's an inactive entry, it means the job is going to end on the effectiveDate of the inactive entry
	// used for minimumPercentage and proration
			endPhra = TkServiceLocator.getPrincipalHRAttributeService().getInactivePrincipalHRAttributes(principalId, phra.getEffectiveDate());
			
			if(endPhra != null && TKUtils.removeTime(currentDate).after(TKUtils.removeTime(endPhra.getEffectiveDate()))) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}
			
	// if the date range is before the service date of this employee, do not calculate accrual
			if(endDate.before(phra.getServiceDate())) {
				return;
			}
			//Fetch Leave Plan for user from hr principal attributes
			if(lp == null) {
				lp = TkServiceLocator.getLeavePlanService().getLeavePlan(phra.getLeavePlan(), phra.getEffectiveDate());
			}
			if(lp == null) {
				throw new RuntimeException("Cannot find Leave Plan for principalId " + principalId);
			}
			
			//Fetch List of AccrualCategories for leave plan
			if(accrCatList == null) {
				accrCatList = TkServiceLocator.getAccrualCategoryService()
							.getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), lp.getEffectiveDate());
			}
			//Fetch the accural rate based on rate range for today(Rate range is the accumulated list of jobs and accrual rate for today)
			RateRange currentRange = rrAggregate.getRateOnDate(currentDate);
			if(currentRange == null) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}
			// if the employee status is changed, create an empty leave block on the currentDate
			if(currentRange.isStatusChanged()) {
				this.createEmptyLeaveBlockForStatusChange(principalId, accrualLeaveBlocks, currentDate);
			}
			// if no job found for the employee on the currentDate, do nothing
			if(currentRange.getJobs().isEmpty()) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}
			
			BigDecimal ftePercentage = currentRange.getAccrualRatePercentageModifier();
			
			for(AccrualCategory ac : accrCatList) {
				if(!currentDate.before(phra.getEffectiveDate()) && !ac.getAccrualEarnInterval().equals("N")) {   	// "N" means no accrual
// check if the accrual category has minimum percentage, if it does, use the percentage to calculate accrual for the first and last accrual interval of this employee
					// first accrual interval
					java.util.Date firstIntervalDate = getNextAccrualIntervalDate(ac.getAccrualEarnInterval(),  phra.getEffectiveDate());
					if(!TKUtils.removeTime(currentDate).before(TKUtils.removeTime(phra.getEffectiveDate())) 
							&& !TKUtils.removeTime(currentDate).after(TKUtils.removeTime(firstIntervalDate))) {
						if(!minimumPercentageReachedForPayPeriod(ac.getMinPercentWorked(), ac.getAccrualEarnInterval(), phra.getEffectiveDate(), firstIntervalDate)) {
							continue;
						}
					}
					// last accrual interval
					if(endPhra != null) {	// the employment is going to end on the effectiveDate of enPhra
						java.util.Date lastIntervalDate = getPreviousAccrualIntervalDate(ac.getAccrualEarnInterval(), endPhra.getEffectiveDate());
						if(!TKUtils.removeTime(currentDate).after(TKUtils.removeTime(endPhra.getEffectiveDate())) 
								&& TKUtils.removeTime(currentDate).after(TKUtils.removeTime(lastIntervalDate))) {
							if(!minimumPercentageReachedForPayPeriod(ac.getMinPercentWorked(), ac.getAccrualEarnInterval(), endPhra.getEffectiveDate(), lastIntervalDate)) {
								continue;
							}
						}
					}
					
// check if the accrual category has proration, if it does, only calculate accrual from the next pay intereval
					if(shouldProrationApply(ac, phra, currentDate)) {
						continue;
					}
					
					
					//Determine which accrual rules apply for today and iterate over those
// get the accrual rule 
					AccrualCategoryRule acRule = TkServiceLocator.getAccrualCategoryRuleService()
						.getAccrualCategoryRuleForDate(ac, currentDate, phra.getServiceDate());
					if(acRule == null) {
						continue;
					}
					//Fetch the accural rate based on rate range for today(Rate range is the accumulated list of jobs and accrual rate for today)
					//Add to total accumulatedAccrualCatToAccrualAmounts
//use rule and ftePercentage to calculate the hours
					BigDecimal numberOfDays = new BigDecimal(aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
					BigDecimal dayRate = acRule.getAccrualRate().divide(numberOfDays, 6, BigDecimal.ROUND_HALF_UP);
					this.calculateHours(ac.getLmAccrualCategoryId(), ftePercentage, dayRate, accumulatedAccrualCatToAccrualAmounts);
										
					//Determine if we are at the accrual earn interval in the span, if so add leave block for accumulated accrual amount to list
					//and reset accumulatedAccrualCatToAccrualAmounts for this accrual category
					String earnIntervalKey = ac.getAccrualEarnInterval(); 
					BigDecimal acHours = accumulatedAccrualCatToAccrualAmounts.get(ac.getLmAccrualCategoryId());
					if(this.isDateAtEarnInterval(currentDate, earnIntervalKey) && acHours != null) {
						createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, acHours, ac, null);
						accumulatedAccrualCatToAccrualAmounts.remove(ac.getLmAccrualCategoryId());	// reset the Map
					}
					// create leave block on the last day of the employment
					if(endPhra != null && TKUtils.removeTime(currentDate).equals(TKUtils.removeTime(endPhra.getEffectiveDate()))){
						if(acHours != null) {
							createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, acHours, ac, null);
							accumulatedAccrualCatToAccrualAmounts.remove(ac.getLmAccrualCategoryId());	// reset the Map
						}
					}
				}
			}
			//Determine if today is a system scheduled time off and accrue holiday if so.
			SystemScheduledTimeOff ssto = TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffByDate(lp.getLeavePlan(), currentDate);
			if(ssto != null) {
				AccrualCategory anAC = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(ssto.getAccrualCategory(), ssto.getEffectiveDate());
				if(anAC == null) {
					throw new RuntimeException("Cannot find Accrual Category for system scheduled time off " + ssto.getLmSystemScheduledTimeOffId());
				}
				BigDecimal hrs = new BigDecimal(ssto.getAmountofTime()).multiply(ftePercentage);
				createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, hrs, anAC, ssto.getLmSystemScheduledTimeOffId());
			}
			aCal.add(Calendar.DATE, 1);
		}
		
		//Save accrual leave blocks at the very end
		TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(accrualLeaveBlocks);
		
		
		
	}
	

	private void createLeaveBlock(String principalId, List<LeaveBlock> accrualLeaveBlocks, 
			java.util.Date currentDate, BigDecimal hrs, AccrualCategory anAC, String sysSchTimeOffId) {

//		LeaveCode lc = TkServiceLocator.getLeaveCodeService().getLeaveCode(anAC.getLeaveCode(), anAC.getEffectiveDate());
		// Replacing Leave Code to earn code - KPME 1634
		EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(anAC.getEarnCode(), anAC.getEffectiveDate());
		if(ec == null) {
			throw new RuntimeException("Cannot find Earn Code for Accrual category " + anAC.getAccrualCategory());
		}
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setAccrualCategoryId(anAC.getLmAccrualCategoryId());
		aLeaveBlock.setLeaveDate(new java.sql.Date(currentDate.getTime()));
		aLeaveBlock.setPrincipalId(principalId);
		// TODO : Change it it Earn code
		aLeaveBlock.setLeaveCode(anAC.getEarnCode());
		aLeaveBlock.setLeaveCodeId(ec.getHrEarnCodeId());
		aLeaveBlock.setDateAndTime(new Timestamp(currentDate.getTime()));
		aLeaveBlock.setAccrualGenerated(true);
		aLeaveBlock.setBlockId(0L);
		aLeaveBlock.setScheduleTimeOffId(sysSchTimeOffId);
		// use rounding option and fract time allowed of Leave Code to round the leave block hours
		BigDecimal roundedHours = TkServiceLocator.getEarnCodeService().roundHrsWithEarnCode(hrs, ec);
		aLeaveBlock.setLeaveAmount(roundedHours);
		accrualLeaveBlocks.add(aLeaveBlock);
		
	}
	
	private void createEmptyLeaveBlockForStatusChange(String principalId, List<LeaveBlock> accrualLeaveBlocks, java.util.Date currentDate) {
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setAccrualCategoryId(null);
		aLeaveBlock.setLeaveDate(new java.sql.Date(currentDate.getTime()));
		aLeaveBlock.setPrincipalId(principalId);
		aLeaveBlock.setLeaveCode("MSG");	// fake leave code
		aLeaveBlock.setLeaveCodeId("000");	// fake leave code id
		aLeaveBlock.setDateAndTime(new Timestamp(currentDate.getTime()));
		aLeaveBlock.setAccrualGenerated(true);
		aLeaveBlock.setBlockId(0L);
		aLeaveBlock.setScheduleTimeOffId(null);
		aLeaveBlock.setLeaveAmount(BigDecimal.ZERO);
		
		accrualLeaveBlocks.add(aLeaveBlock);
		
	}

	public void calculateHours(String accrualCategoryId, BigDecimal fte, BigDecimal rate, Map<String, BigDecimal> accumulatedAccrualCatToAccrualAmounts ) {
		BigDecimal hours = rate.multiply(fte);
		BigDecimal oldHours = accumulatedAccrualCatToAccrualAmounts.get(accrualCategoryId);
		BigDecimal newHours = oldHours == null ? hours : hours.add(oldHours);
		accumulatedAccrualCatToAccrualAmounts.put(accrualCategoryId, newHours);
	}
	
	public Date getStartAccrualDate(String principalId){
		return null;
	}
	
	public Date getEndAccrualDate(String principalId){
		//KPME-1246  Fetch planning months
		
		return null;
	}

	@Override
	public void runAccrual(List<String> principalIds) {
		for(String principalId : principalIds){
			runAccrual(principalId);
		}
	}
	
	@Override
	public boolean isDateAtEarnInterval(java.util.Date aDate, String earnInterval) {
		boolean atEarnInterval = false;
		if(TkConstants.ACCRUAL_EARN_INTERVAL.containsKey(earnInterval)) {
			String intervalValue = TkConstants.ACCRUAL_EARN_INTERVAL.get(earnInterval);
			Calendar aCal = Calendar.getInstance();
			aCal.setTime(aDate);
			
			if(intervalValue == "Daily") {
				atEarnInterval = true;
			} else if(intervalValue == "Weekly") {
				// figure out if the day is a Saturday
				if(aCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					atEarnInterval = true;
				}
			} else if (intervalValue == "Semi-Monthly") {
				// either the 15th or the last day of the month
				if(aCal.get(Calendar.DAY_OF_MONTH) == 15 || aCal.get(Calendar.DAY_OF_MONTH) == aCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					atEarnInterval = true;
				}
			} else if (intervalValue == "Monthly") {
				// the last day of the month
				if(aCal.get(Calendar.DAY_OF_MONTH) == aCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					atEarnInterval = true;
				}
			} else if (intervalValue == "Yearly") {
				// the last day of the year
				if(aCal.get(Calendar.DAY_OF_YEAR) == aCal.getActualMaximum(Calendar.DAY_OF_YEAR)) {
					atEarnInterval = true;
				}
			}else if (intervalValue == "No Accrual") {
				// no calculation
			}
		}
		return atEarnInterval;
	}
	
	@Override
	public RateRangeAggregate buildRateRangeAggregate(String principalId, Date startDate, Date endDate) {
		RateRangeAggregate rrAggregate = new RateRangeAggregate();
		List<RateRange> rateRangeList = new ArrayList<RateRange>();	
		Calendar gc = new GregorianCalendar();
		gc.setTime(startDate);

		BigDecimal previousFte = null;
	    while (!gc.getTime().after(endDate)) {
	    	RateRange rateRange = new RateRange();
			List<Job> jobs = TkServiceLocator.getJobSerivce().getActiveLeaveJobs(principalId, gc.getTime());
			rateRange.setJobs(jobs);
			// detect if there's a status change
			if(!jobs.isEmpty()) {
				BigDecimal fteSum = TkServiceLocator.getJobSerivce().getFteSumForJobs(jobs);
				rateRange.setAccrualRatePercentageModifier(fteSum);
				if(previousFte != null && !previousFte.equals(fteSum)) {
					rateRange.setStatusChanged(true);
					rrAggregate.setRateRangeChanged(true);
				}
				previousFte = fteSum;
			}
			DateTime beginInterval = new DateTime(gc.getTime());
			gc.add(Calendar.DATE, 1);
			DateTime endInterval = new DateTime(gc.getTime());
			Interval range = new Interval(beginInterval, endInterval);
			rateRange.setRange(range);
			rateRangeList.add(rateRange);	       
	    }
		rrAggregate.setRateRanges(rateRangeList);
		rrAggregate.setCurrentRate(null);
		return rrAggregate;
	}
	
	@Override
	public boolean isEmpoyeementFutureStatusChanged(String principalId, Date startDate, Date endDate) {
		Date currentDate = TKUtils.getCurrentDate();
		if(endDate.after(currentDate)) {
			RateRangeAggregate rrAggregate = this.buildRateRangeAggregate(principalId, startDate, endDate);
			if(rrAggregate.isRateRangeChanged()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void calculateFutureAccrualUsingPlanningMonth(String principalId, Date asOfDate) {
		Date currentDate = TKUtils.getCurrentDate();
		PrincipalHRAttributes phra = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
		if(phra != null) {
			LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(phra.getLeavePlan(), phra.getEffectiveDate());
			if(lp != null && StringUtils.isNotEmpty(lp.getPlanningMonths())) {
				Calendar aCal = Calendar.getInstance();
				aCal.setTime(currentDate);
				aCal.add(Calendar.MONTH, Integer.parseInt(lp.getPlanningMonths()));
				// max days in months differ, if the date is bigger than the max day, set it to the max day of the month
				if(aCal.getActualMaximum(Calendar.DAY_OF_MONTH) < aCal.get(Calendar.DATE)) {
					aCal.set(Calendar.DATE, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				}
				Date endDate = new java.sql.Date(aCal.getTime().getTime());
				TkServiceLocator.getLeaveAccrualService().runAccrual(principalId, currentDate, endDate);
			}
		}
	}
	
	@Override
	public boolean minimumPercentageReachedForPayPeriod(BigDecimal min, String earnInterval, Date jobDate, java.util.Date intervalDate) {
		if(min == null || min.compareTo(BigDecimal.ZERO) == 0) {
			return true;
		}
		int daysInFirstInterval = getDaysInAccrualInterval(earnInterval, jobDate);
		if(daysInFirstInterval == 0) {
			return true;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(jobDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(intervalDate);
		Long daysDiff = 1L; // for the same dates, the diff should be 1. same logic for all days
		if(cal1.getTime().before(cal2.getTime())) {
			daysDiff += TKUtils.getDaysBetween(cal1, cal2);
		} else {
			daysDiff += TKUtils.getDaysBetween(cal2, cal1);
		}		
		BigDecimal actualPercentage =  new BigDecimal(daysDiff).divide(new BigDecimal(daysInFirstInterval), 2, BigDecimal.ROUND_HALF_EVEN);
		if(actualPercentage.compareTo(min) >= 0) {
			return true;
		}
		
		return false;	
	}
	
	
/*	public boolean minimumPercentageApplyToFirstWeek(BigDecimal min, String earnInterval, Date jobStartDate) {
		if(min == null || min.equals(BigDecimal.ZERO)) {
			return false;
		}
		java.util.Date firstIntervalDate = getNextAccrualIntervalDate(earnInterval, jobStartDate);
		int daysInFirstInterval = getDaysInAccrualInterval(earnInterval, jobStartDate);
		
		if(daysInFirstInterval != 0) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(jobStartDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(firstIntervalDate);
			Long daysDiff = TKUtils.getDaysBetween(cal1, cal2) + 1;   // for the same days, the diff should be 1. same logic for all days
			
			BigDecimal actualPercentage = new BigDecimal (daysDiff / daysInFirstInterval);
			if(actualPercentage.compareTo(min) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean minimumPercentageApplyToLastWeek(BigDecimal min, String earnInterval, Date jobEndDate) {
		if(min == null || min.equals(BigDecimal.ZERO)) {
			return false;
		}
		java.util.Date previousIntervalDate = getPreviousAccrualIntervalDate(earnInterval, jobEndDate);
		int daysInLastInterval = getDaysInAccrualInterval(earnInterval, jobEndDate);
		if(daysInLastInterval != 0) {
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(previousIntervalDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(jobEndDate);
			Long daysDiff = TKUtils.getDaysBetween(cal1, cal2);
			
			BigDecimal actualPercentage = new BigDecimal (daysDiff / daysInLastInterval);
			if(actualPercentage.equals(BigDecimal.ZERO)) {
				return false;
			}
			if(actualPercentage.compareTo(min) >= 0) {
				return true;
			}
		}
		return false;
	}
*/	
	public java.util.Date getPreviousAccrualIntervalDate(String earnInterval, Date aDate) {
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(aDate);
		String intervalValue = TkConstants.ACCRUAL_EARN_INTERVAL.get(earnInterval);
		
		if(intervalValue == "Daily") {
			// no change to calendar
		} else if(intervalValue == "Weekly") {
			aCal.add(Calendar.WEEK_OF_YEAR, -1);
			aCal.set(Calendar.DAY_OF_WEEK, 7);	// set to the Saturday of previous week
		} else if (intervalValue == "Semi-Monthly") {
			aCal.add(Calendar.DAY_OF_YEAR, -15);
			if(aCal.get(Calendar.DAY_OF_MONTH) <=15) {
				aCal.set(Calendar.DAY_OF_MONTH, 15);
			} else {
				aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
		} else if (intervalValue == "Monthly") {
			aCal.add(Calendar.MONTH, -1);
			aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (intervalValue == "Yearly") {
			aCal.add(Calendar.YEAR, -1);
			aCal.set(Calendar.DAY_OF_YEAR, aCal.getActualMaximum(Calendar.DAY_OF_YEAR));
		}else if (intervalValue == "No Accrual") {
			// no change to calendar
		}
		return aCal.getTime();
	}
	
	public java.util.Date getNextAccrualIntervalDate(String earnInterval, Date aDate) {
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(aDate);
		String intervalValue = TkConstants.ACCRUAL_EARN_INTERVAL.get(earnInterval);
		
		if(intervalValue == "Daily") {
			// no change to calendar
		} else if(intervalValue == "Weekly") {
			aCal.set(Calendar.DAY_OF_WEEK, 7);
		} else if (intervalValue == "Semi-Monthly") {
			if(aCal.get(Calendar.DAY_OF_MONTH) <=15) {
				aCal.set(Calendar.DAY_OF_MONTH, 15);
			} else {
				aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
		} else if (intervalValue == "Monthly") {
			aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (intervalValue == "Yearly") {
			aCal.set(Calendar.DAY_OF_YEAR, aCal.getActualMaximum(Calendar.DAY_OF_YEAR));
		}else if (intervalValue == "No Accrual") {
			// no change to calendar
		}
		return aCal.getTime();
	}
	
	public int getDaysInAccrualInterval(String earnInterval, Date aDate) {
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(aDate);
		String intervalValue = TkConstants.ACCRUAL_EARN_INTERVAL.get(earnInterval);
		
		if(intervalValue == "Daily") {
			return 1;
		} else if(intervalValue == "Weekly") {
			return 7;	// saturday
		} else if (intervalValue == "Semi-Monthly") {
			return 15;		// 15th of the month
		} else if (intervalValue == "Monthly") {
			return aCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		} else if (intervalValue == "Yearly") {
			return aCal.getActualMaximum(Calendar.DAY_OF_YEAR);
		}else if (intervalValue == "No Accrual") {
			return 0;
		}
		return 0;
	}
	

	public boolean shouldProrationApply(AccrualCategory ac, PrincipalHRAttributes phra, java.util.Date currentDate) {
		boolean applyFlag = false;
		
		return applyFlag;
	}
}
