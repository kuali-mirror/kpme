package org.kuali.hr.lm.accrual.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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
		
		PrincipalHRAttributes phra = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes(principalId);
		if(phra == null) {
			throw new RuntimeException("Cannot find principal hr attributes for " + principalId);
		}
// if the date range is before the service date of this employee, do not calculate accrual
		if(startDate.before(phra.getServiceDate()) && endDate.before(phra.getServiceDate())) {
			return;
		}
		
		//Fetch Leave Plan for user from hr principal attributes
		LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(phra.getLeavePlan(), phra.getEffectiveDate());
		if(lp == null) {
			throw new RuntimeException("Cannot find Leave Plan for principalId " + principalId);
		}
		
		//Fetch List of AccrualCategories for leave plan
		List<AccrualCategory> accrCatList = TkServiceLocator.getAccrualCategoryService()
					.getActiveAccrualCategoriesForLeavePlan(lp.getLeavePlan(), lp.getEffectiveDate());
		//Build a rate range aggregate with appropriate information for this period of time detailing Rate Ranges for job
		//entries for this range of time
		RateRangeAggregate rrAggregate = this.buildRateRangeAggregate(principalId, startDate, endDate);	
		
		//Iterate over every day in span 
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(startDate);
		while (!aCal.getTime().after(endDate)) {
			java.util.Date currentDate = aCal.getTime();
// if the currentDate is before the start service date of this employee, do nothing
			if(currentDate.before(phra.getServiceDate())) {
				aCal.add(Calendar.DATE, 1);
				continue;
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
				if(currentDate.after(ac.getEffectiveDate())) {
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

		LeaveCode lc = TkServiceLocator.getLeaveCodeService().getLeaveCode(anAC.getLeaveCode(), anAC.getEffectiveDate());
		if(lc == null) {
			throw new RuntimeException("Cannot find Leave Code for Accrual category " + anAC.getAccrualCategory());
		}
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setAccrualCategoryId(anAC.getLmAccrualCategoryId());
		aLeaveBlock.setLeaveDate(new java.sql.Date(currentDate.getTime()));
		aLeaveBlock.setPrincipalId(principalId);
		aLeaveBlock.setLeaveCode(anAC.getLeaveCode());
		aLeaveBlock.setLeaveCodeId(lc.getLmLeaveCodeId());
		aLeaveBlock.setDateAndTime(new Timestamp(currentDate.getTime()));
		aLeaveBlock.setAccrualGenerated(true);
		aLeaveBlock.setBlockId(0L);
		aLeaveBlock.setScheduleTimeOffId(sysSchTimeOffId);
		// use rounding option and fract time allowed of Leave Code to round the leave block hours
		BigDecimal roundedHours = TkServiceLocator.getLeaveCodeService().roundHrsWithLeaveCode(hrs, lc);
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
	public void calculateFutureAccrualUsingPlanningMonth(String principalId) {
		Date currentDate = TKUtils.getCurrentDate();
		PrincipalHRAttributes phra = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes(principalId);
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
}
