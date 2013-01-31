/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.lm.accrual.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.accrual.PrincipalAccrualRan;
import org.kuali.hr.lm.accrual.RateRange;
import org.kuali.hr.lm.accrual.RateRangeAggregate;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import edu.emory.mathcs.backport.java.util.Collections;

public class AccrualServiceImpl implements AccrualService {

	@Override
	public void runAccrual(String principalId) {
		Date startDate = getStartAccrualDate(principalId);
		Date endDate = getEndAccrualDate(principalId);

		System.out.println("AccrualServiceImpl.runAccrual() STARTED with Principal: "+principalId);
		
		runAccrual(principalId,startDate,endDate, true);
		
	}
	
	@Override
	public void runAccrual(String principalId, Date startDate, Date endDate, boolean recordRanData) {
		runAccrual(principalId, startDate, endDate, recordRanData, TKContext.getPrincipalId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void runAccrual(String principalId, Date startDate, Date endDate, boolean recordRanData, String runAsPrincipalId) {
		List<LeaveBlock> accrualLeaveBlocks = new ArrayList<LeaveBlock>();
		Map<String, BigDecimal> accumulatedAccrualCatToAccrualAmounts = new HashMap<String,BigDecimal>();
		Map<String, BigDecimal> accumulatedAccrualCatToNegativeAccrualAmounts = new HashMap<String,BigDecimal>();
		
		if (startDate != null && endDate != null) {
			System.out.println("AccrualServiceImpl.runAccrual() STARTED with Principal: "+principalId+" Start: "+startDate.toString()+" End: "+endDate.toString());
		}
		if(startDate.after(endDate)) {
			throw new RuntimeException("Start Date " + startDate.toString() + " should not be later than End Date " + endDate.toString());
		}
		//Inactivate all previous accrual-generated entries for this span of time
		inactivateOldAccruals(principalId, startDate, endDate, runAsPrincipalId);
		
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
			RateRange currentRange = rrAggregate.getRateOnDate(currentDate);
			if(currentRange == null) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}
			
			phra = currentRange.getPrincipalHRAttributes();
			if(phra == null || TKUtils.removeTime(currentDate).before(TKUtils.removeTime(phra.getServiceDate()))) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}
			
			
	// use the effectiveDate of this principalHRAttribute to search for inactive entries for this principalId
	// If there's an inactive entry, it means the job is going to end on the effectiveDate of the inactive entry
	// used for minimumPercentage and proration
			endPhra = currentRange.getEndPrincipalHRAttributes();
			if(endPhra != null && TKUtils.removeTime(currentDate).after(TKUtils.removeTime(endPhra.getEffectiveDate()))) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}
			
	// if the date range is before the service date of this employee, do not calculate accrual
			if(endDate.before(phra.getServiceDate())) {
				return;
			}
			lp = currentRange.getLeavePlan();
			accrCatList = currentRange.getAcList();
			// if the employee status is changed, create an empty leave block on the currentDate
			if(currentRange.isStatusChanged()) {
				this.createEmptyLeaveBlockForStatusChange(principalId, accrualLeaveBlocks, currentDate);
			}
			// if no job found for the employee on the currentDate, do nothing
			if(CollectionUtils.isEmpty(currentRange.getJobs())) {
				aCal.add(Calendar.DATE, 1);
				continue;
			}
			
			BigDecimal ftePercentage = currentRange.getAccrualRatePercentageModifier();
			BigDecimal totalOfStandardHours = currentRange.getStandardHours();
			boolean fullFteGranted = false;
			for(AccrualCategory anAC : accrCatList) {
				fullFteGranted = false;
				if(!currentDate.before(phra.getEffectiveDate()) && !anAC.getAccrualEarnInterval().equals("N")) {   	// "N" means no accrual
					boolean prorationFlag = this.getProrationFlag(anAC.getProration());
					// get the accrual rule 
					AccrualCategoryRule currentAcRule = this.getRuleForAccrualCategory(currentRange.getAcRuleList(), anAC);
				
					// check if accrual category rule changed
					if(currentAcRule != null) {
						java.util.Date ruleStartDate = getRuleStartDate(currentAcRule.getServiceUnitOfTime(), phra.getServiceDate(), currentAcRule.getStart());
						Date ruleStartSqlDate = new java.sql.Date(ruleStartDate.getTime());
						java.util.Date previousIntervalDay = this.getPrevIntervalDate(ruleStartSqlDate, anAC.getAccrualEarnInterval(), phra.getPayCalendar(), rrAggregate.getCalEntryMap());
						java.util.Date nextIntervalDay = this.getNextIntervalDate(ruleStartSqlDate, anAC.getAccrualEarnInterval(), phra.getPayCalendar(), rrAggregate.getCalEntryMap());
						
						RateRange previousRange = rrAggregate.getRateOnDate(previousIntervalDay);
						AccrualCategoryRule previousAcRule = null;
						if(previousRange != null) {
							previousAcRule = this.getRuleForAccrualCategory(previousRange.getAcRuleList(), anAC);
						}
						// rule changed
						if(previousAcRule != null && !previousAcRule.getLmAccrualCategoryRuleId().equals(currentAcRule.getLmAccrualCategoryRuleId())) {
							if(TKUtils.removeTime(currentDate).compareTo(TKUtils.removeTime(previousIntervalDay)) >= 0 
									&& TKUtils.removeTime(currentDate).compareTo(TKUtils.removeTime(nextIntervalDay)) <= 0) {
								int workDaysInBetween = TKUtils.getWorkDays(ruleStartSqlDate, nextIntervalDay);
								boolean minReachedFlag = minimumPercentageReachedForPayPeriod(anAC.getMinPercentWorked(), 
												anAC.getAccrualEarnInterval(), workDaysInBetween, new java.sql.Date(nextIntervalDay.getTime()),
												phra.getPayCalendar(), rrAggregate.getCalEntryMap());
								if(prorationFlag) {
									if(minReachedFlag) {
										// min reached, proration=true, rule changed, then use actual work days of currentRule for calculation
										// so nothing special needs to be done here								
									} else {
										//minimum percentage NOT reached, proration = true, rule changed, then use previousRule for the whole pay period
										currentAcRule = previousAcRule;
									}
								} else {
									if(minReachedFlag) {
										// min reached, proration = false, rule changed, then accrual the whole fte of the new rule for this pay interval
										accumulatedAccrualCatToAccrualAmounts.put(anAC.getLmAccrualCategoryId(), currentAcRule.getAccrualRate());
										fullFteGranted = true;
									} else {
										//min NOT reached, proration = false, rule changed, then accrual the whole fte of the previous rule for this pay interval
										accumulatedAccrualCatToAccrualAmounts.put(anAC.getLmAccrualCategoryId(), previousAcRule.getAccrualRate());
										fullFteGranted = true;
									}
								}
							}
						}
					}
					
					// check for first pay period of principal attributes considering minimum percentage and proration	
					java.util.Date firstIntervalDate = this.getNextIntervalDate(phra.getEffectiveDate(), anAC.getAccrualEarnInterval(), phra.getPayCalendar(), rrAggregate.getCalEntryMap());
					if(!TKUtils.removeTime(currentDate).before(TKUtils.removeTime(phra.getEffectiveDate())) 
							&& !TKUtils.removeTime(currentDate).after(TKUtils.removeTime(firstIntervalDate))) {
						int workDaysInBetween = TKUtils.getWorkDays(phra.getEffectiveDate(), firstIntervalDate);
						boolean minReachedFlag = minimumPercentageReachedForPayPeriod(anAC.getMinPercentWorked(),  anAC.getAccrualEarnInterval(), 
									workDaysInBetween, new java.sql.Date(firstIntervalDate.getTime()),
									phra.getPayCalendar(), rrAggregate.getCalEntryMap());
						
						if(prorationFlag) {
							if(minReachedFlag) {
								// minimum reached, proration = true, first pay period, then use actual work days of currentRule for calculation
								// so nothing special needs to be done here
							} else {
								// min NOT reached, proration = true, first pay period, then no accrual for this pay period
								accumulatedAccrualCatToAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
								accumulatedAccrualCatToNegativeAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
								continue;
							}
						} else {
							if(minReachedFlag) {
								//  minimum reached, proration = false, first pay period, then accrual the whole fte of current AC rule for this pay interval
								accumulatedAccrualCatToAccrualAmounts.put(anAC.getLmAccrualCategoryId(), currentAcRule.getAccrualRate());
								fullFteGranted = true;
							} else {
								// min NOT reached, proration = false, first pay period, then no accrual for this pay period
								accumulatedAccrualCatToAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
								accumulatedAccrualCatToNegativeAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
								continue;
							}
						}
					}
					// last accrual interval
					if(endPhra != null) {	// the employment is going to end on the effectiveDate of enPhra
						java.util.Date previousIntervalDate = this.getPrevIntervalDate(endPhra.getEffectiveDate(), anAC.getAccrualEarnInterval(), phra.getPayCalendar(), rrAggregate.getCalEntryMap());
						// currentDate is between the end date and the last interval date, so we are in the last interval
						if(!TKUtils.removeTime(currentDate).after(TKUtils.removeTime(endPhra.getEffectiveDate())) 
								&& TKUtils.removeTime(currentDate).after(TKUtils.removeTime(previousIntervalDate))) {
							java.util.Date lastIntervalDate = this.getNextIntervalDate(endPhra.getEffectiveDate(), anAC.getAccrualEarnInterval(),  phra.getPayCalendar(), rrAggregate.getCalEntryMap());
							int workDaysInBetween = TKUtils.getWorkDays(previousIntervalDate, endPhra.getEffectiveDate());
							boolean minReachedFlag = minimumPercentageReachedForPayPeriod(anAC.getMinPercentWorked(),  anAC.getAccrualEarnInterval(), 
										workDaysInBetween, new java.sql.Date(lastIntervalDate.getTime()),
										phra.getPayCalendar(), rrAggregate.getCalEntryMap());
							if(prorationFlag) {
								if(minReachedFlag) {
									// minimum reached, proration = true, first pay period, then use actual work days of currentRule for calculation
									// so nothing special needs to be done here
								} else {
									// min NOT reached, proration = true, first pay period, then no accrual for this pay period
									accumulatedAccrualCatToAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
									accumulatedAccrualCatToNegativeAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
									continue;
								}
							} else {
								if(minReachedFlag) {
									//  minimum reached, proration = false, first pay period, then accrual the whole fte of current AC rule for this pay interval
									accumulatedAccrualCatToAccrualAmounts.put(anAC.getLmAccrualCategoryId(), currentAcRule.getAccrualRate());
									fullFteGranted = true;
								} else {
									// min NOT reached, proration = false, first pay period, then no accrual for this pay period
									accumulatedAccrualCatToAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
									accumulatedAccrualCatToNegativeAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
									continue;
								}
							}
						}
					}
										
					if(currentAcRule == null) {
						accumulatedAccrualCatToAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
						accumulatedAccrualCatToNegativeAccrualAmounts.remove(anAC.getLmAccrualCategoryId());
						continue;
					}
					
					// only accrual on work days
					if(!TKUtils.isWeekend(currentDate) && !fullFteGranted) {
						BigDecimal accrualRate = currentAcRule.getAccrualRate();
						int numberOfWorkDays = this.getWorkDaysInInterval(new java.sql.Date(currentDate.getTime()), anAC.getAccrualEarnInterval(), phra.getPayCalendar(), rrAggregate.getCalEntryMap());
						BigDecimal dayRate = numberOfWorkDays > 0 ? accrualRate.divide(new BigDecimal(numberOfWorkDays), 6, BigDecimal.ROUND_HALF_UP) : new BigDecimal(0);
						//Fetch the accural rate based on rate range for today(Rate range is the accumulated list of jobs and accrual rate for today)
						//Add to total accumulatedAccrualCatToAccrualAmounts
						//use rule and ftePercentage to calculate the hours						
						this.calculateHours(anAC.getLmAccrualCategoryId(), ftePercentage, dayRate, accumulatedAccrualCatToAccrualAmounts);
						
						//get not eligible for accrual hours based on leave block on this day
						BigDecimal noAccrualHours = getNotEligibleForAccrualHours(principalId, new java.sql.Date(currentDate.getTime()));
						
						if(noAccrualHours.compareTo(BigDecimal.ZERO) != 0 && totalOfStandardHours.compareTo(BigDecimal.ZERO) != 0) {
							BigDecimal dayHours = totalOfStandardHours.divide(new BigDecimal(5), 6, BigDecimal.ROUND_HALF_UP);
							BigDecimal noAccrualRate = dayRate.multiply(noAccrualHours.divide(dayHours));
							this.calculateHours(anAC.getLmAccrualCategoryId(), ftePercentage, noAccrualRate, accumulatedAccrualCatToNegativeAccrualAmounts);
						}
					}					
					//Determine if we are at the accrual earn interval in the span, if so add leave block for accumulated accrual amount to list
					//and reset accumulatedAccrualCatToAccrualAmounts and accumulatedAccrualCatToNegativeAccrualAmounts for this accrual category
					if(this.isDateAnIntervalDate(currentDate, anAC.getAccrualEarnInterval(), phra.getPayCalendar(), rrAggregate.getCalEntryMap())) {
						BigDecimal acHours = accumulatedAccrualCatToAccrualAmounts.get(anAC.getLmAccrualCategoryId());
						
						if(acHours != null) {
							createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, acHours, anAC, null, true, currentRange.getLeaveCalendarDocumentId());
							accumulatedAccrualCatToAccrualAmounts.remove(anAC.getLmAccrualCategoryId());	// reset accumulatedAccrualCatToAccrualAmounts
							fullFteGranted = false;
						}
						
						BigDecimal adjustmentHours = accumulatedAccrualCatToNegativeAccrualAmounts.get(anAC.getLmAccrualCategoryId());
						if(adjustmentHours != null && adjustmentHours.compareTo(BigDecimal.ZERO) != 0) {
							// do not create leave block if the ajustment amount is 0
							createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, adjustmentHours, anAC, null, false, currentRange.getLeaveCalendarDocumentId());
							accumulatedAccrualCatToNegativeAccrualAmounts.remove(anAC.getLmAccrualCategoryId());	// reset accumulatedAccrualCatToNegativeAccrualAmounts
						}
					}			
				}
			}
			//Determine if today is a system scheduled time off and accrue holiday if so.
			SystemScheduledTimeOff ssto = currentRange.getSysScheTimeOff();
			if(ssto != null) {
				AccrualCategory anAC = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(ssto.getAccrualCategory(), ssto.getEffectiveDate());
				if(anAC == null) {
					throw new RuntimeException("Cannot find Accrual Category for system scheduled time off " + ssto.getLmSystemScheduledTimeOffId());
				}
				BigDecimal hrs = ssto.getAmountofTime().multiply(ftePercentage);
				// system scheduled time off leave block
				createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, hrs, anAC, ssto.getLmSystemScheduledTimeOffId(), true, currentRange.getLeaveCalendarDocumentId());
				// usage leave block with negative amount
				createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, hrs.negate(), anAC, ssto.getLmSystemScheduledTimeOffId(), true, currentRange.getLeaveCalendarDocumentId());
			}
			// if today is the last day of the employment, create leave blocks if there's any hours available
			if(endPhra != null && TKUtils.removeTime(currentDate).equals(TKUtils.removeTime(endPhra.getEffectiveDate()))){
				// accumulated accrual amount
				if(!accumulatedAccrualCatToAccrualAmounts.isEmpty()) {
					for(Map.Entry<String, BigDecimal> entry : accumulatedAccrualCatToAccrualAmounts.entrySet()) {
						if(entry.getValue() != null && entry.getValue().compareTo(BigDecimal.ZERO) != 0) {
							AccrualCategory anAC = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(entry.getKey());
							createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, entry.getValue(), anAC, null, true, currentRange.getLeaveCalendarDocumentId());
						}
					}
					accumulatedAccrualCatToAccrualAmounts = new HashMap<String,BigDecimal>();	// reset accumulatedAccrualCatToAccrualAmounts
				}
				// negative/adjustment accrual amount
				if(!accumulatedAccrualCatToNegativeAccrualAmounts.isEmpty()) {
					for(Map.Entry<String, BigDecimal> entry : accumulatedAccrualCatToNegativeAccrualAmounts.entrySet()) {
						if(entry.getValue() != null && entry.getValue().compareTo(BigDecimal.ZERO) != 0) {
							AccrualCategory anAC = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(entry.getKey());
							createLeaveBlock(principalId, accrualLeaveBlocks, currentDate, entry.getValue(), anAC, null, true, currentRange.getLeaveCalendarDocumentId());
						}
					}
					accumulatedAccrualCatToNegativeAccrualAmounts = new HashMap<String,BigDecimal>();	// reset accumulatedAccrualCatToNegativeAccrualAmounts
				}
				phra = null;	// reset principal attribute so new value will be retrieved
				endPhra = null;	// reset end principal attribute so new value will be retrieved
			}
			
			aCal.add(Calendar.DATE, 1);
		}
		
		//Save accrual leave blocks at the very end
		TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(accrualLeaveBlocks);
		
		// record timestamp of this accrual run in database
		if(recordRanData) {
			TkServiceLocator.getPrincipalAccrualRanService().updatePrincipalAccrualRanInfo(principalId);
		}
		
	}
	
	private void inactivateOldAccruals(String principalId, Date startDate, Date endDate, String runAsPrincipalId) {
		List<LeaveBlock> previousLB = TkServiceLocator.getLeaveBlockService().getAccrualGeneratedLeaveBlocks(principalId, startDate, endDate);
		List<LeaveBlock> sstoAccrualList = new ArrayList<LeaveBlock>();
		List<LeaveBlock> sstoUsageList = new ArrayList<LeaveBlock>();
		
		for(LeaveBlock lb : previousLB) {
			if(StringUtils.isNotEmpty(lb.getScheduleTimeOffId())) {
				if(lb.getLeaveAmount().compareTo(BigDecimal.ZERO) > 0) {
					sstoAccrualList.add(lb);
				} else if(lb.getLeaveAmount().compareTo(BigDecimal.ZERO) < 0) {
					sstoUsageList.add(lb);
				}
			} else {
				TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), runAsPrincipalId);
			}
		}
		
		for(LeaveBlock accrualLb : sstoAccrualList) {
			for(LeaveBlock usageLb : sstoUsageList) {
				// both usage and accrual ssto leave blocks are there, so the ssto accural is not banked, removed both leave blocks
				// if this is no ssto usage leave block, it means the user has banked this ssto hours. Don't delete this ssto accrual leave block
				if(accrualLb.getScheduleTimeOffId().equals(usageLb.getScheduleTimeOffId())) {	
					TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(accrualLb.getLmLeaveBlockId(), runAsPrincipalId);
					TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(usageLb.getLmLeaveBlockId(), runAsPrincipalId);
				}
			}
		}
		
	}
	
	private BigDecimal getNotEligibleForAccrualHours(String principalId, Date currentDate) {
		BigDecimal hours = BigDecimal.ZERO;
		// check if there's any manual not-eligible-for-accrual leave blocks, use the hours of the leave block to adjust accrual calculation 
		List<LeaveBlock> lbs = TkServiceLocator.getLeaveBlockService().getNotAccrualGeneratedLeaveBlocksForDate(principalId, currentDate);
		for(LeaveBlock lb : lbs) {
			EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(lb.getEarnCode(), currentDate);
			if(ec == null) {
				throw new RuntimeException("Cannot find Earn Code for Leave block " + lb.getLmLeaveBlockId());
			}
			if(ec.getEligibleForAccrual().equals("N") && lb.getLeaveAmount().compareTo(BigDecimal.ZERO) != 0) {
				hours = hours.add(lb.getLeaveAmount());
			}		
		}
		return hours;
	}
	
	private void createLeaveBlock(String principalId, List<LeaveBlock> accrualLeaveBlocks, 
			java.util.Date currentDate, BigDecimal hrs, AccrualCategory anAC, String sysSchTimeOffId, 
			boolean createZeroLeaveBlock, String leaveDocId) {
		// Replacing Leave Code to earn code - KPME 1634
		EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(anAC.getEarnCode(), anAC.getEffectiveDate());
		if(ec == null) {
			throw new RuntimeException("Cannot find Earn Code for Accrual category " + anAC.getAccrualCategory());
		}
		// use rounding option and fract time allowed of Leave Code to round the leave block hours
		BigDecimal roundedHours = TkServiceLocator.getEarnCodeService().roundHrsWithEarnCode(hrs, ec);
		if(!createZeroLeaveBlock && roundedHours.compareTo(BigDecimal.ZERO) == 0) {
			return;	// do not create leave block with zero amount
		}
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setAccrualCategory(anAC.getAccrualCategory());
		aLeaveBlock.setLeaveDate(new java.sql.Date(currentDate.getTime()));
		aLeaveBlock.setPrincipalId(principalId);
		//More than one earn code can be associated with an accrual category. Which one does this get?
		aLeaveBlock.setEarnCode(anAC.getEarnCode());
		aLeaveBlock.setDateAndTime(new Timestamp(currentDate.getTime()));
		aLeaveBlock.setAccrualGenerated(true);
		aLeaveBlock.setBlockId(0L);
		aLeaveBlock.setScheduleTimeOffId(sysSchTimeOffId);
		aLeaveBlock.setLeaveAmount(roundedHours);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE);
		aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		aLeaveBlock.setDocumentId(leaveDocId);
		
		accrualLeaveBlocks.add(aLeaveBlock);
		
	}
	
	private void createEmptyLeaveBlockForStatusChange(String principalId, List<LeaveBlock> accrualLeaveBlocks, java.util.Date currentDate) {
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setAccrualCategory(null);
		aLeaveBlock.setLeaveDate(new java.sql.Date(currentDate.getTime()));
		aLeaveBlock.setPrincipalId(principalId);
		aLeaveBlock.setEarnCode(LMConstants.STATUS_CHANGE_EARN_CODE);	// fake leave code
		aLeaveBlock.setDateAndTime(new Timestamp(currentDate.getTime()));
		aLeaveBlock.setAccrualGenerated(true);
		aLeaveBlock.setBlockId(0L);
		aLeaveBlock.setScheduleTimeOffId(null);
		aLeaveBlock.setLeaveAmount(BigDecimal.ZERO);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE);
		aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		
		accrualLeaveBlocks.add(aLeaveBlock);
		
	}

	private void calculateHours(String accrualCategoryId, BigDecimal fte, BigDecimal rate, Map<String, BigDecimal> accumulatedAmounts ) {
		BigDecimal hours = rate.multiply(fte);
		BigDecimal oldHours = accumulatedAmounts.get(accrualCategoryId);
		BigDecimal newHours = oldHours == null ? hours : hours.add(oldHours);
		accumulatedAmounts.put(accrualCategoryId, newHours);
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
	
	private boolean isDateAnIntervalDate(java.util.Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {
			return isDateAtPayCalInterval(aDate, earnInterval, payCalName, aMap);
		} else {
			return this.isDateAtEarnInterval(aDate, earnInterval);
		}
	}
	
	private boolean isDateAtPayCalInterval(java.util.Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(StringUtils.isNotEmpty(payCalName) 
				&& !aMap.isEmpty()
				&& earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {	// only used for ac earn interval == pay calendar
			List<CalendarEntries> entryList = aMap.get(payCalName);
			if(CollectionUtils.isNotEmpty(entryList)) {
				for(CalendarEntries anEntry : entryList) {
					// endPeriodDate of calendar entry is the beginning hour of the next day, so we need to substract one day from it to get the real end date
					java.util.Date endDate = TKUtils.addDates(anEntry.getEndPeriodDate(), -1);
					if(aDate.compareTo(endDate) == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isDateAtEarnInterval(java.util.Date aDate, String earnInterval) {
		boolean atEarnInterval = false;
		if(LMConstants.ACCRUAL_EARN_INTERVAL_MAP.containsKey(earnInterval)) {
			Calendar aCal = Calendar.getInstance();
			aCal.setTime(aDate);
			
			if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.DAILY)) {
				atEarnInterval = true;
			} else if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.WEEKLY)) {
				// figure out if the day is a Saturday
				if(aCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
					atEarnInterval = true;
				}
			} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.SEMI_MONTHLY)) {
				// either the 15th or the last day of the month
				if(aCal.get(Calendar.DAY_OF_MONTH) == 15 || aCal.get(Calendar.DAY_OF_MONTH) == aCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					atEarnInterval = true;
				}
			} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.MONTHLY)) {
				// the last day of the month
				if(aCal.get(Calendar.DAY_OF_MONTH) == aCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					atEarnInterval = true;
				}
			} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.YEARLY)) {
				// the last day of the year
				if(aCal.get(Calendar.DAY_OF_YEAR) == aCal.getActualMaximum(Calendar.DAY_OF_YEAR)) {
					atEarnInterval = true;
				}
			}else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.NO_ACCRUAL)) {
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
		// get all active jobs that are effective before the endDate
		List<Job> activeJobs = TkServiceLocator.getJobService().getAllActiveLeaveJobs(principalId, endDate);
		List<Job> inactiveJobs = TkServiceLocator.getJobService().getAllInActiveLeaveJobsInRange(principalId, startDate, endDate);
		
		List<PrincipalHRAttributes> phaList = TkServiceLocator.getPrincipalHRAttributeService().getAllActivePrincipalHrAttributesForPrincipalId(principalId, endDate);
		List<PrincipalHRAttributes> inactivePhaList = TkServiceLocator.getPrincipalHRAttributeService().getAllInActivePrincipalHrAttributesForPrincipalId(principalId, endDate);
		
		if(activeJobs.isEmpty() || phaList.isEmpty()) {
			return rrAggregate;
		}
		
		Set<String> phaLpSet = new HashSet<String>();
		Set<String> calNameSet = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(phaList)) {
			for(PrincipalHRAttributes pha : phaList) {
				phaLpSet.add(pha.getLeavePlan());
				calNameSet.add(pha.getPayCalendar());
			}
		}
		
		List<LeavePlan> activeLpList = new ArrayList<LeavePlan> ();
		List<LeavePlan> inactiveLpList = new ArrayList<LeavePlan> ();
		for(String lpString : phaLpSet) {
			List<LeavePlan> aList = TkServiceLocator.getLeavePlanService().getAllActiveLeavePlan(lpString, endDate);
			activeLpList.addAll(aList);
			
			aList = TkServiceLocator.getLeavePlanService().getAllInActiveLeavePlan(lpString, endDate);
			inactiveLpList.addAll(aList);
		}
		
		// get all pay calendar entries for this employee. used to determine interval dates
		Map<String, List<CalendarEntries>> calEntryMap = new HashMap<String, List<CalendarEntries>>();
		for(String calName : calNameSet) {
			org.kuali.hr.time.calendar.Calendar aCal = TkServiceLocator.getCalendarService().getCalendarByGroup(calName);
			if(aCal != null) {
				List<CalendarEntries> aList = TkServiceLocator.getCalendarEntriesService().getAllCalendarEntriesForCalendarId(aCal.getHrCalendarId());
				Collections.sort(aList);
				calEntryMap.put(calName, aList);
			}
		}
		rrAggregate.setCalEntryMap(calEntryMap);		
		
		Set<String> lpStringSet = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(activeLpList)) {
			for(LeavePlan lp : activeLpList) {
				lpStringSet.add(lp.getLeavePlan());
			}
		}
		List<SystemScheduledTimeOff> sstoList = new ArrayList<SystemScheduledTimeOff>();
		for(String lpString : lpStringSet) {
			List<SystemScheduledTimeOff> aList =TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffsForLeavePlan(startDate, endDate, lpString);
			if(CollectionUtils.isNotEmpty(aList)) {
				sstoList.addAll(aList);
			}
		}
		
		List<AccrualCategory> activeAccrCatList = new ArrayList<AccrualCategory>();
		List<AccrualCategory> inactiveAccrCatList = new ArrayList<AccrualCategory>();
		for(String lpString : lpStringSet) {
			List<AccrualCategory> aList = TkServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(lpString, endDate);
			if(CollectionUtils.isNotEmpty(aList)) {
				activeAccrCatList.addAll(aList);
			}
			
			aList = TkServiceLocator.getAccrualCategoryService().getInActiveLeaveAccrualCategoriesForLeavePlan(lpString, endDate);
			if(CollectionUtils.isNotEmpty(aList)) {
				inactiveAccrCatList.addAll(aList);
			}
		}
		
		List<AccrualCategoryRule> activeRuleList = new ArrayList<AccrualCategoryRule>();
		List<AccrualCategoryRule> inactiveRuleList = new ArrayList<AccrualCategoryRule>();
		for(AccrualCategory ac : activeAccrCatList) {
			List<AccrualCategoryRule> aRuleList = TkServiceLocator.getAccrualCategoryRuleService().getActiveRulesForAccrualCategoryId(ac.getLmAccrualCategoryId(), endDate);
			activeRuleList.addAll(aRuleList);
			
			aRuleList = TkServiceLocator.getAccrualCategoryRuleService().getInActiveRulesForAccrualCategoryId(ac.getLmAccrualCategoryId(), endDate);
			inactiveRuleList.addAll(aRuleList);
		}
		
		List<LeaveCalendarDocumentHeader> lcDocList = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getAllDocumentHeadersInRangeForPricipalId(principalId, startDate, endDate);
		
		BigDecimal previousFte = null;
		List<Job> jobs = new ArrayList<Job>();
		
	    while (!gc.getTime().after(endDate)) {
	    	RateRange rateRange = new RateRange();
	    	java.util.Date currentDate = gc.getTime();
	    	
	    	jobs = this.getJobsForDate(activeJobs, inactiveJobs, currentDate);
	    	if(jobs.isEmpty()) {	// no jobs found for this day
	    		gc.add(Calendar.DATE, 1);
	    		continue;
	    	}
			rateRange.setJobs(jobs);
			
			// detect if there's a status change
			BigDecimal fteSum = TkServiceLocator.getJobService().getFteSumForJobs(jobs);
			rateRange.setAccrualRatePercentageModifier(fteSum);
			BigDecimal standardHours = TkServiceLocator.getJobService().getStandardHoursSumForJobs(jobs);
			rateRange.setStandardHours(standardHours);
			
			if(previousFte != null && !previousFte.equals(fteSum)) {
				rateRange.setStatusChanged(true);
				rrAggregate.setRateRangeChanged(true);
			}
			previousFte = fteSum;
			
			// figure out the PrincipalHRAttributes for this day
			PrincipalHRAttributes phra = this.getPrincipalHrAttributesForDate(phaList, currentDate);
			rateRange.setPrincipalHRAttributes(phra);
			
			if(rateRange.getPrincipalHRAttributes() != null) {
				// figure out if there's an end principalHrAttributes for the initial principalHRAttributes
				PrincipalHRAttributes endPhra = this.getInactivePrincipalHrAttributesForDate(inactivePhaList, rateRange.getPrincipalHRAttributes().getEffectiveDate(), currentDate);
				rateRange.setEndPrincipalHRAttributes(endPhra);
			}
			
			// get leave plan for this day
			if(rateRange.getPrincipalHRAttributes()!= null) {				
				rateRange.setLeavePlan(this.getLeavePlanForDate(activeLpList, inactiveLpList, rateRange.getPrincipalHRAttributes().getLeavePlan(), currentDate));
			}
			
			if(rateRange.getLeavePlan() != null) {
				// get accrual category list for this day
				List<AccrualCategory> acsForDay = this.getAccrualCategoriesForDate(activeAccrCatList, inactiveAccrCatList, rateRange.getLeavePlan().getLeavePlan(), currentDate);
				rateRange.setAcList(acsForDay);
				
				// get System scheduled time off for this day
				for(SystemScheduledTimeOff ssto : sstoList) {
					if(TKUtils.removeTime(ssto.getAccruedDate()).equals(TKUtils.removeTime(currentDate) )
							&& ssto.getLeavePlan().equals(rateRange.getLeavePlan().getLeavePlan())) {
						// if there exists a ssto accrualed leave block with this ssto id, it means the ssto hours has been banked by the employee
						// this logic depends on the inactivateOldAccruals() runs before buildRateRangeAggregate()
						List<LeaveBlock> sstoLbList = TkServiceLocator.getLeaveBlockService().getSSTOLeaveBlock(principalId, ssto.getLmSystemScheduledTimeOffId(), ssto.getAccruedDate());
						if(CollectionUtils.isEmpty(sstoLbList)) {
							rateRange.setSysScheTimeOff(ssto);
						}
					}	
				}
			}
			// set accrual category rules for the day
			if(CollectionUtils.isNotEmpty(rateRange.getAcList())) {
				List<AccrualCategoryRule> rulesForDay = new ArrayList<AccrualCategoryRule>();
				for(AccrualCategory ac : rateRange.getAcList()) {
					rulesForDay.addAll(this.getAccrualCategoryRulesForDate
											(activeRuleList, ac.getLmAccrualCategoryId(), currentDate, rateRange.getPrincipalHRAttributes().getServiceDate()));
				}
				rateRange.setAcRuleList(rulesForDay);
		    	
			}
			
			DateTime beginInterval = new DateTime(gc.getTime());
			gc.add(Calendar.DATE, 1);
			DateTime endInterval = new DateTime(gc.getTime());
			Interval range = new Interval(beginInterval, endInterval);
			rateRange.setRange(range);
			// assign leave document id to range if there is an existing leave doc for currentDate.
			// The doc Id will be assigned to leave blocks created at this rate range
			rateRange.setLeaveCalendarDocumentId(this.getLeaveDocumentForDate(lcDocList, currentDate));
			rateRangeList.add(rateRange);	       
	    }
		rrAggregate.setRateRanges(rateRangeList);
		rrAggregate.setCurrentRate(null);
		return rrAggregate;
	}
	
	private String getLeaveDocumentForDate(List<LeaveCalendarDocumentHeader> lcDocList, java.util.Date currentDate) {
		for(LeaveCalendarDocumentHeader lcdh : lcDocList) {
			if(!lcdh.getBeginDate().after(currentDate) && lcdh.getEndDate().after(currentDate)) {
				return lcdh.getDocumentId();
			}
		}
		return "";
	}
		
	public List<Job> getJobsForDate(List<Job> activeJobs, List<Job> inactiveJobs, java.util.Date currentDate) {
		List<Job> jobs = new ArrayList<Job>();
    	for(Job aJob : activeJobs) {
    		if(!aJob.getEffectiveDate().after(currentDate)) {
    			jobs.add(aJob);
    		}
    	}
    	if(CollectionUtils.isNotEmpty(jobs)) {
	    	List<Job> tempList = new ArrayList<Job>();
	    	tempList.addAll(jobs);
	    	for(Job aJob : tempList) {
	    		for(Job inactiveJob : inactiveJobs) {
	    			if(inactiveJob.getJobNumber().equals(aJob.getJobNumber())
	    				&& inactiveJob.getEffectiveDate().after(aJob.getEffectiveDate())
	    				&& !inactiveJob.getEffectiveDate().after(currentDate)) {
	    					// remove inactive job from list
	    					jobs.remove(aJob);
	    			}
	    		}
	    	}
    	}
    	return jobs;
	}
	
	public PrincipalHRAttributes getPrincipalHrAttributesForDate(List<PrincipalHRAttributes> activeList, java.util.Date currentDate) {
		List<PrincipalHRAttributes> phasForDay = new ArrayList<PrincipalHRAttributes>();
		for(PrincipalHRAttributes pha : activeList) {
			if(!pha.getEffectiveDate().after(currentDate) && !pha.getServiceDate().after(currentDate)) {
    			phasForDay.add(pha);
    		}
		}
		if(CollectionUtils.isNotEmpty(phasForDay)) {
			PrincipalHRAttributes pha = phasForDay.get(0);
			int indexOfMaxEffDt = 0;
			if(phasForDay.size() > 1) {
				for(int i = 1; i < phasForDay.size(); i++) {
					if( (phasForDay.get(i).getEffectiveDate().after(phasForDay.get(indexOfMaxEffDt).getEffectiveDate()))
							||(phasForDay.get(i).getEffectiveDate().equals(phasForDay.get(indexOfMaxEffDt).getEffectiveDate())
									&& phasForDay.get(i).getTimestamp().after(phasForDay.get(indexOfMaxEffDt).getTimestamp()))) {
						indexOfMaxEffDt = i;
					}
				}
				pha = phasForDay.get(indexOfMaxEffDt);
			}
			return pha;
		}
		return null;
	}
	
	public PrincipalHRAttributes getInactivePrincipalHrAttributesForDate(List<PrincipalHRAttributes> inactiveList, java.util.Date activeDate, java.util.Date currentDate) {
		List<PrincipalHRAttributes> inactivePhasForDay = new ArrayList<PrincipalHRAttributes>();
		for(PrincipalHRAttributes pha : inactiveList) {
			if( pha.getEffectiveDate().after(activeDate) && !pha.getServiceDate().after(currentDate)) {
				inactivePhasForDay.add(pha);
    		}
		}
		if(CollectionUtils.isNotEmpty(inactivePhasForDay)) {
			PrincipalHRAttributes pha = inactivePhasForDay.get(0);
			int indexOfMaxEffDt = 0;
			if(inactivePhasForDay.size() > 1) {
				for(int i = 1; i < inactivePhasForDay.size(); i++) {
					if( (inactivePhasForDay.get(i).getEffectiveDate().after(inactivePhasForDay.get(indexOfMaxEffDt).getEffectiveDate()))
							||(inactivePhasForDay.get(i).getEffectiveDate().equals(inactivePhasForDay.get(indexOfMaxEffDt).getEffectiveDate())
									&& inactivePhasForDay.get(i).getTimestamp().after(inactivePhasForDay.get(indexOfMaxEffDt).getTimestamp()))) {
						indexOfMaxEffDt = i;
					}
				}
				pha = inactivePhasForDay.get(indexOfMaxEffDt);
			}
			return pha;
		}
		return null;
	}
	
	public LeavePlan getLeavePlanForDate(List<LeavePlan> activeLpList, List<LeavePlan> inactiveLpList, String leavePlan, java.util.Date currentDate) {
		List<LeavePlan> lpsForDay = new ArrayList<LeavePlan>();
		for(LeavePlan lp : activeLpList) {
			if(lp.getLeavePlan().equals(leavePlan) && !lp.getEffectiveDate().after(currentDate)) {
				lpsForDay.add(lp);
			}
		}
		List<LeavePlan> aList = new ArrayList<LeavePlan>();
		aList.addAll(lpsForDay);
    	for(LeavePlan lp : aList) {
    		for(LeavePlan inactiveLp : inactiveLpList) {
    			if(inactiveLp.getLeavePlan().equals(lp.getLeavePlan())
    				&& inactiveLp.getEffectiveDate().after(lp.getEffectiveDate())
    				&& !inactiveLp.getEffectiveDate().after(currentDate)) {
    					// remove inactive leave plan from list
    					lpsForDay.remove(lp);
    			}
    		}
    	}
		if(CollectionUtils.isNotEmpty(lpsForDay)) {
			LeavePlan aLp = lpsForDay.get(0);
			int indexOfMaxEffDt = 0;
			if(lpsForDay.size() > 1) {
				for(int i = 1; i < lpsForDay.size(); i++) {
					if( (lpsForDay.get(i).getEffectiveDate().after(lpsForDay.get(indexOfMaxEffDt).getEffectiveDate()))
							||(lpsForDay.get(i).getEffectiveDate().equals(lpsForDay.get(indexOfMaxEffDt).getEffectiveDate())
									&& lpsForDay.get(i).getTimestamp().after(lpsForDay.get(indexOfMaxEffDt).getTimestamp()))) {
						indexOfMaxEffDt = i;
					}
				}
				aLp = lpsForDay.get(indexOfMaxEffDt);
			}
			return aLp;
		}
		return null;
	}
	
	public List<AccrualCategory> getAccrualCategoriesForDate(List<AccrualCategory> activeAccrCatList, List<AccrualCategory> inactiveAccrCatList, String leavePlan, java.util.Date currentDate) {
		Set<AccrualCategory> aSet = new HashSet<AccrualCategory>();
		for(AccrualCategory ac : activeAccrCatList) {
			if(ac.getLeavePlan().equals(leavePlan) && !ac.getEffectiveDate().after(currentDate)) {
				aSet.add(ac);
			}
		}
		List<AccrualCategory> list1 = new ArrayList<AccrualCategory>();
		list1.addAll(aSet);
    	for(AccrualCategory ac : list1) {
    		for(AccrualCategory inactiveAc : inactiveAccrCatList) {
    			if(inactiveAc.getAccrualCategory().equals(ac.getAccrualCategory())
    				&& inactiveAc.getEffectiveDate().after(ac.getEffectiveDate())
    				&& !inactiveAc.getEffectiveDate().after(currentDate)) {
    					// remove inactive accrual category from list
    				aSet.remove(ac);
    			}
    		}
    	}
    	List<AccrualCategory> acsForDay = new ArrayList<AccrualCategory>();
    	acsForDay.addAll(aSet);
    	return acsForDay;    	
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
		PrincipalHRAttributes phra = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
		if(phra != null) {
			// use the date from pay period to get the leave plan
			LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(phra.getLeavePlan(), asOfDate);  
			if(lp != null && StringUtils.isNotEmpty(lp.getPlanningMonths())) {
				Calendar aCal = Calendar.getInstance();
				// go back a year 
				aCal.setTime(asOfDate);
				aCal.add(Calendar.YEAR, -1);
				if(aCal.getActualMaximum(Calendar.DAY_OF_MONTH) < aCal.get(Calendar.DATE)) {
					aCal.set(Calendar.DATE, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				}
				Date startDate = new java.sql.Date(aCal.getTime().getTime());
				// go forward using planning months
				aCal.setTime(asOfDate);
				aCal.add(Calendar.MONTH, Integer.parseInt(lp.getPlanningMonths()));
				// max days in months differ, if the date is bigger than the max day, set it to the max day of the month
				if(aCal.getActualMaximum(Calendar.DAY_OF_MONTH) < aCal.get(Calendar.DATE)) {
					aCal.set(Calendar.DATE, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				}
				Date endDate = new java.sql.Date(aCal.getTime().getTime());
				TkServiceLocator.getLeaveAccrualService().runAccrual(principalId, startDate, endDate, true);
			}
		}
	}
	
	private boolean minimumPercentageReachedForPayPeriod(BigDecimal min, String earnInterval, int workDays, Date intervalDate, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(min == null || min.compareTo(BigDecimal.ZERO) == 0) {
			return true;
		}
		int daysInInterval = this.getWorkDaysInInterval(intervalDate, earnInterval, payCalName, aMap);
		if(daysInInterval == 0) {
			return true;
		}
		BigDecimal actualPercentage =  new BigDecimal(workDays).divide(new BigDecimal(daysInInterval), 2, BigDecimal.ROUND_HALF_EVEN);
		if(actualPercentage.compareTo(min) >= 0) {
			return true;
		}
		
		return false;	
	}

	private java.util.Date getPrevIntervalDate(Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {
			return this.getPrevPayCalIntervalDate(aDate, earnInterval, payCalName, aMap);
		} else {
			return this.getPreviousAccrualIntervalDate(earnInterval, aDate);
		}
	}
	
	@Override
	public java.util.Date getPreviousAccrualIntervalDate(String earnInterval, Date aDate) {
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(aDate);

		if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.DAILY)) {
			aCal.add(Calendar.DAY_OF_YEAR, -1);
		} else if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.WEEKLY)) {
			aCal.add(Calendar.WEEK_OF_YEAR, -1);
			aCal.set(Calendar.DAY_OF_WEEK, 7);	// set to the Saturday of previous week
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.SEMI_MONTHLY)) {
			aCal.add(Calendar.DAY_OF_YEAR, -15);
			if(aCal.get(Calendar.DAY_OF_MONTH) <=15) {
				aCal.set(Calendar.DAY_OF_MONTH, 15);
			} else {
				aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.MONTHLY)) {
			aCal.add(Calendar.MONTH, -1);
			aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.YEARLY)) {
			aCal.add(Calendar.YEAR, -1);
			aCal.set(Calendar.DAY_OF_YEAR, aCal.getActualMaximum(Calendar.DAY_OF_YEAR));
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.NO_ACCRUAL)) {
			// no change to calendar
		} 
		return aCal.getTime();
	}
	
	private java.util.Date getPrevPayCalIntervalDate(java.util.Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(StringUtils.isNotEmpty(payCalName) 
				&& !aMap.isEmpty()
				&& earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {	// only used for ac earn interval == pay calendar
			List<CalendarEntries> entryList = aMap.get(payCalName);
			if(CollectionUtils.isNotEmpty(entryList)) {
				for(CalendarEntries anEntry : entryList) {
					// endPeriodDate of calendar entry is the beginning hour of the next day, so we need to substract one day from it to get the real end date
					java.util.Date endDate = TKUtils.addDates(anEntry.getEndPeriodDate(), -1);
					if(anEntry.getBeginPeriodDate().compareTo(aDate) <= 0 && endDate.compareTo(aDate) >= 0) {
						// the day before the beginning date of the cal entry that contains the passed in date is the endDate of previous calendar entry
						java.util.Date prevIntvDate = TKUtils.addDates(anEntry.getBeginPeriodDate(), -1);
						return prevIntvDate;
					}
				}
			}
		}
		return aDate;
	}
	
	private java.util.Date getNextIntervalDate(Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {
			return this.getNextPayCalIntervalDate(aDate, earnInterval, payCalName, aMap);
		} else {
			return this.getNextAccrualIntervalDate(earnInterval, aDate);
		}
	}
	
	private java.util.Date getNextPayCalIntervalDate(Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(StringUtils.isNotEmpty(payCalName) 
				&& !aMap.isEmpty()
				&& earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {	// only used for ac earn interval == pay calendar
			List<CalendarEntries> entryList = aMap.get(payCalName);
			if(CollectionUtils.isNotEmpty(entryList)) {
				for(CalendarEntries anEntry : entryList) {
					// endPeriodDate of calendar entry is the beginning hour of the next day, so we need to substract one day from it to get the real end date
					java.util.Date endDate = TKUtils.addDates(anEntry.getEndPeriodDate(), -1);
					if(anEntry.getBeginPeriodDate().compareTo(aDate) <= 0 && endDate.compareTo(aDate) >= 0) {
						// the endDate of the cal entry that contains the passed in date is the next pay calendar interval date
						return endDate;
					}
				}
			}
		}
		return aDate;
	}
	
	@Override
	public java.util.Date getNextAccrualIntervalDate(String earnInterval, Date aDate) {
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(aDate);
		
		if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.DAILY)) {
			// no change to calendar
		} else if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.WEEKLY)) {
			aCal.set(Calendar.WEEK_OF_YEAR, 1);
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.SEMI_MONTHLY)) {
			if(aCal.get(Calendar.DAY_OF_MONTH) <=15) {
				aCal.set(Calendar.DAY_OF_MONTH, 15);
			} else {
				aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.MONTHLY)) {
			aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.YEARLY)) {
			aCal.set(Calendar.DAY_OF_YEAR, aCal.getActualMaximum(Calendar.DAY_OF_YEAR));
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.NO_ACCRUAL)) {
			// no change to calendar
		} 
		return aCal.getTime();
	}

	private int getWorkDaysInInterval(Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {
			return this.getWorkDaysInPayCalInterval(aDate, earnInterval, payCalName, aMap);
		} else {
			return this.getWorkDaysInAccrualInterval(earnInterval, aDate);
		}
	}
	
	private int getWorkDaysInPayCalInterval(Date aDate, String earnInterval, String payCalName,  Map<String, List<CalendarEntries>> aMap) {
		if(StringUtils.isNotEmpty(payCalName) 
				&& !aMap.isEmpty()
				&& earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL)) {	// only used for ac earn interval == pay calendar
			List<CalendarEntries> entryList = aMap.get(payCalName);
			if(CollectionUtils.isNotEmpty(entryList)) {
				for(CalendarEntries anEntry : entryList) {
					// endPeriodDate of calendar entry is the beginning hour of the next day, so we need to substract one day from it to get the real end date
					java.util.Date endDate = TKUtils.addDates(anEntry.getEndPeriodDate(), -1);
					if(anEntry.getBeginPeriodDate().compareTo(aDate) <= 0 && endDate.compareTo(aDate) >= 0) {
						return TKUtils.getWorkDays(anEntry.getBeginPeriodDate(), endDate);
					}
				}
			}
		}
		return 0;
	}
	
	@Override
	public int getWorkDaysInAccrualInterval(String earnInterval, Date aDate) {
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(aDate);
		
		if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.DAILY)) {
			return 1;
		} else if(earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.WEEKLY)) {
			return 5;	
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.SEMI_MONTHLY)) {
			if(aCal.get(Calendar.DAY_OF_MONTH) <= 15) {
				aCal.set(Calendar.DAY_OF_MONTH, 1);
				java.util.Date start = aCal.getTime();
				aCal.set(Calendar.DAY_OF_MONTH, 15);
				java.util.Date end = aCal.getTime();
				return TKUtils.getWorkDays(start, end);
			} else {
				aCal.set(Calendar.DAY_OF_MONTH, 16);
				java.util.Date start = aCal.getTime();
				aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				java.util.Date end = aCal.getTime();
				return TKUtils.getWorkDays(start, end);
			}
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.MONTHLY)) {
			aCal.set(Calendar.DAY_OF_MONTH, 1);
			java.util.Date start = aCal.getTime();
			aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			java.util.Date end = aCal.getTime();
			return TKUtils.getWorkDays(start, end);
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.YEARLY)) {
			aCal.set(Calendar.DAY_OF_YEAR, 1);
			java.util.Date start = aCal.getTime();
			aCal.set(Calendar.DAY_OF_YEAR, aCal.getActualMaximum(Calendar.DAY_OF_YEAR));
			java.util.Date end = aCal.getTime();
			return TKUtils.getWorkDays(start, end);
		} else if (earnInterval.equals(LMConstants.ACCRUAL_EARN_INTERVAL_CODE.NO_ACCRUAL)) {
			return 0;
		}		
		return 0;
	}
	
	public java.util.Date getRuleStartDate(String earnInterval, Date serviceDate, Long startAcc) {
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(serviceDate);
		String intervalValue = TkConstants.SERVICE_UNIT_OF_TIME.get(earnInterval);
		int startInt = startAcc.intValue();
		
		if (intervalValue.equals("Months")) {
			aCal.add(Calendar.MONTH, startInt);
			if(aCal.get(Calendar.DAY_OF_MONTH) > aCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				aCal.set(Calendar.DAY_OF_MONTH, aCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
		} else if (intervalValue.equals("Years")) {
			aCal.set(Calendar.YEAR, startInt);
		}else {
			// no change to calendar
		}
		return aCal.getTime();
	}
	
	public boolean getProrationFlag(String proration) {
		if(proration == null) {
			return true;
		}
		return proration.equals("Y") ? true : false;
	}
	
	@Override
	public boolean statusChangedSinceLastRun(String principalId) {
		PrincipalAccrualRan par = TkServiceLocator.getPrincipalAccrualRanService().getLastPrincipalAccrualRan(principalId);
		if(par == null) {
			return true;
		}
		Job aJob = TkServiceLocator.getJobService().getMaxTimestampJob(principalId);
		
		if(aJob != null && aJob.getTimestamp().after(par.getLastRanTs())) {
			return true;
		}
		
		Assignment anAssign = TkServiceLocator.getAssignmentService().getMaxTimestampAssignment(principalId);
		if(anAssign != null && anAssign.getTimestamp().after(par.getLastRanTs())) {
			return true;
		}
		
		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getMaxTimeStampPrincipalHRAttributes(principalId);
		if(pha != null && pha.getTimestamp().after(par.getLastRanTs())) {
			return true;
		}
		return false;
	}
	
    public List<AccrualCategoryRule> getAccrualCategoryRulesForDate(List<AccrualCategoryRule> acrList, String accrualCategoryId, java.util.Date currentDate, java.util.Date serviceDate) {
    	Calendar startCal = new GregorianCalendar();
    	Calendar endCal = new GregorianCalendar();
    	List<AccrualCategoryRule> aList = new ArrayList<AccrualCategoryRule>();
    	if(CollectionUtils.isNotEmpty(acrList)) {
	    	for(AccrualCategoryRule acr : acrList) {
	    		if(acr.getLmAccrualCategoryId().equals(accrualCategoryId)) {
		    		String uot = acr.getServiceUnitOfTime();
		    		int startTime = acr.getStart().intValue();
					int endTime = acr.getEnd().intValue();
					
					startCal.setTime(serviceDate);
					endCal.setTime(serviceDate);
		    		if(uot.equals("M")) {		// monthly
		    			startCal.add(Calendar.MONTH, startTime);
		    			endCal.add(Calendar.MONTH, endTime);
		    			endCal.add(Calendar.DATE, -1);
		    		} else if(uot.endsWith("Y")) { // yearly
		    			startCal.add(Calendar.YEAR, startTime);
		    			endCal.add(Calendar.YEAR, endTime);
		    			endCal.add(Calendar.DATE, -1);
		    		}
		    		
		    		// max days in months differ, if the date is bigger than the max day, set it to the max day of the month
					if(startCal.getActualMaximum(Calendar.DAY_OF_MONTH) < startCal.get(Calendar.DATE)) {
						startCal.set(Calendar.DATE, startCal.getActualMaximum(Calendar.DAY_OF_MONTH));
					}
					if(endCal.getActualMaximum(Calendar.DAY_OF_MONTH) < endCal.get(Calendar.DATE)) {
						endCal.set(Calendar.DATE, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
					}
		    		
		    		if(TKUtils.removeTime(currentDate).compareTo(TKUtils.removeTime(startCal.getTime())) >= 0 
		    				&& TKUtils.removeTime(currentDate).compareTo(TKUtils.removeTime(endCal.getTime())) <=0 ) {
		    			aList.add(acr);
		    		}
	    		}
	    	}
    	}
    	return aList;
	}
    
    public AccrualCategoryRule getRuleForAccrualCategory(List<AccrualCategoryRule> acrList, AccrualCategory ac) {
    	if(CollectionUtils.isNotEmpty(acrList)) {
	    	for(AccrualCategoryRule acr : acrList) {
	    		if(acr.getLmAccrualCategoryId().equals(ac.getLmAccrualCategoryId())) {
	    			return acr;
	    		}
	    	}
	    }
    	return null;
    }
}
