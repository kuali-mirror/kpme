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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.util.ObjectUtils;

import edu.emory.mathcs.backport.java.util.Collections;

public class AccrualCategoryMaxBalanceServiceImpl implements AccrualCategoryMaxBalanceService {

	@Override
	public Map<String, Set<LeaveBlock>> getMaxBalanceViolations(CalendarEntries entry, String principalId) {
		
		Map<String, Set<LeaveBlock>> newEligibilities = new HashMap<String,Set<LeaveBlock>>();
		
		newEligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE, new HashSet<LeaveBlock>());
		newEligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END, new HashSet<LeaveBlock>());
		newEligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND, new HashSet<LeaveBlock>());
		
		Interval thisEntryInterval = new Interval(entry.getBeginPeriodDate().getTime(),entry.getEndPeriodDate().getTime());

		Date asOfDate = TKUtils.getCurrentDate();
		
		if(!thisEntryInterval.contains(asOfDate.getTime()))
			asOfDate = new Date(DateUtils.addDays(entry.getEndPeriodDate(),-1).getTime());

		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
		
		List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), asOfDate);

		if(!accrualCategories.isEmpty()) {
			//could remove the mapping.
			Map<String, BigDecimal> accruedBalance = new HashMap<String, BigDecimal>();

			for(AccrualCategory accrualCategory : accrualCategories) {
				List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, pha.getServiceDate(), DateUtils.addDays(asOfDate,1), accrualCategory.getAccrualCategory());

				AccrualCategoryRule startRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, entry.getBeginPeriodDate(), pha.getServiceDate());
				AccrualCategoryRule endRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, entry.getEndPeriodDate(), pha.getServiceDate());
				if(ObjectUtils.isNotNull(startRule) && ObjectUtils.isNotNull(endRule)) {
					//employee has crossed into a new service interval under pha as defined by the accrual category rule.
					//if the employee is allowed to take action on an over-maxed accrual category under the previous rule
					//i.e. on leave-approve or on year-end ( on-demand should lose the opportunity at the point the rule changes. )
					//then create an allocative leave block on the last date of the service interval defined by start rule in order
					//to determine eligibility under that rule.
					//technically if the rule were to change during the period, and employees are only allowed to submit their calendars
					//once the period has ended, or on the end of the period, then the date that the employee submits the calendar
					//will have passed the end date of the previous service interval for the principal, hence the new rule.
					//In this case, any leave blocks that are found to be in excess of accrual category's max bal rule during the previous
					//interval would have to be "negated", or not considered eligible to begin with.
					String startServiceUnits = startRule.getServiceUnitOfTime();
					long startEnd = startRule.getEnd();
					long startStart = startRule.getStart();
					String endServiceUnits = endRule.getServiceUnitOfTime();
					long endEnd = endRule.getEnd();
					long endStart = endRule.getStart();
					//find the date the employee crosses into the new accrual category rule service interval.
					//assert date falls within the calendar period.
					//allocate a leave block.
				}
				else
					if(ObjectUtils.isNull(startRule)) {
						//nothing to do here.
					}
					else {
						//endRule was null.
						//create an allocative leave block on the last date of the service interval defined by start rule.
						//
					}
				//Add a max balance allocation leave block.
				LeaveBlock allocation = new LeaveBlock();
				allocation.setAccrualCategory(accrualCategory.getAccrualCategory());
				
				if(thisEntryInterval.contains(TKUtils.getCurrentDate().getTime()))
					allocation.setLeaveDate(TKUtils.getCurrentDate());
				else
					allocation.setLeaveDate(new java.sql.Date(DateUtils.addDays(entry.getEndPeriodDate(),-1).getTime()));
				
				allocation.setLeaveAmount(BigDecimal.ZERO);
				allocation.setPrincipalId(principalId);
				leaveBlocks.add(allocation);
				
				if(!leaveBlocks.isEmpty()) {
					Collections.sort(leaveBlocks, new Comparator() {
	
						@Override
						public int compare(Object o1, Object o2) {
							LeaveBlock l1 = (LeaveBlock) o1;
							LeaveBlock l2 = (LeaveBlock) o2;
							return l1.getLeaveDate().compareTo(l2.getLeaveDate());
						}
						
					});
				}
				
				accruedBalance.put(accrualCategory.getAccrualCategory(), new BigDecimal(0));
				if(leaveBlocks.isEmpty())
				{/*check for max balance infractions on asOfDate for rule in effect at that time.*/}
				for(LeaveBlock lb : leaveBlocks) {
					if(StringUtils.equals(lb.getRequestStatus(),LMConstants.REQUEST_STATUS.DISAPPROVED) || StringUtils.equals(lb.getRequestStatus(),LMConstants.REQUEST_STATUS.DEFERRED))
						continue;
					BigDecimal tally = accruedBalance.get(accrualCategory.getAccrualCategory());
					tally = tally.add(lb.getLeaveAmount());

					AccrualCategoryRule asOfLeaveDateRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, lb.getLeaveDate(), pha.getServiceDate());

					//Employee overrides...
					if(ObjectUtils.isNotNull(asOfLeaveDateRule)) {
						if(StringUtils.equals(asOfLeaveDateRule.getMaxBalFlag(),"Y")) {
							if(StringUtils.isNotBlank(asOfLeaveDateRule.getActionAtMaxBalance())) {

								if(ObjectUtils.isNotNull(asOfLeaveDateRule.getMaxBalanceActionFrequency())) {
									BigDecimal maxBalance = asOfLeaveDateRule.getMaxBalance();

									BigDecimal fte = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, TKUtils.getCurrentDate());
									BigDecimal adjustedMaxBalance = maxBalance.multiply(fte);
									
									BigDecimal maxAnnualCarryOver = null;
									if(ObjectUtils.isNotNull(asOfLeaveDateRule.getMaxCarryOver()))
										maxAnnualCarryOver = new BigDecimal(asOfLeaveDateRule.getMaxCarryOver());
									
									BigDecimal adjustedMaxAnnualCarryOver = null;
									if(ObjectUtils.isNotNull(maxAnnualCarryOver)) {
										adjustedMaxAnnualCarryOver = maxAnnualCarryOver.multiply(fte);
	                                }
									
									List<EmployeeOverride> overrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, lb.getLeaveDate());
									for(EmployeeOverride override : overrides) {
										if(StringUtils.equals(override.getAccrualCategory(),accrualCategory.getAccrualCategory())) {
											//Do not pro-rate override values for FTE.
											if(StringUtils.equals(override.getOverrideType(),"MB"))
												adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
											if(StringUtils.equals(override.getOverrideType(),"MAC"))
												adjustedMaxAnnualCarryOver = new BigDecimal(override.getOverrideValue());
										}
									}
									
/*									EmployeeOverride maxBalanceOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, pha.getLeavePlan(), accrualCategory.getAccrualCategory(), "MB", lb.getLeaveDate());
									EmployeeOverride maxAnnualCarryOverOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, pha.getLeavePlan(), accrualCategory.getAccrualCategory(), "MAC", lb.getLeaveDate());
									
									if(ObjectUtils.isNotNull(maxBalanceOverride)) {
										adjustedMaxBalance = new BigDecimal(maxBalanceOverride.getOverrideValue());
                                    }
									if(ObjectUtils.isNotNull(maxAnnualCarryOverOverride)) {
										adjustedMaxAnnualCarryOver = new BigDecimal(maxAnnualCarryOverOverride.getOverrideValue());
                                    }*/
									//override values are not pro-rated.

									//should extend a BalanceTransferBase class, or use an algorithm swapping pattern.
									//allow institutions to extend/customize/implement their own max_bal_action_frequency types.
									Calendar cal = pha.getLeaveCalObj();
									if(cal == null)
										throw new RuntimeException("Principal is without a leave calendar");
									List<CalendarEntries> leaveCalEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesEndingBetweenBeginAndEndDate(cal.getHrCalendarId(), entry.getBeginPeriodDate(), entry.getEndPeriodDate());
									CalendarEntries yearEndLeaveEntry = null;
									CalendarEntries leaveLeaveEntry = null;
									if(!leaveCalEntries.isEmpty()) {
										for(CalendarEntries leaveEntry : leaveCalEntries) {
											if(TkServiceLocator.getLeavePlanService().isLastCalendarPeriodOfLeavePlan(leaveEntry, pha.getLeavePlan(), lb.getLeaveDate()))
												yearEndLeaveEntry = leaveEntry;
											if(leaveEntry.getEndPeriodDate().compareTo(entry.getEndPeriodDate()) <= 0)
												leaveLeaveEntry = leaveEntry;
										}
									}
									if(StringUtils.equals(asOfLeaveDateRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
										//For year end transfer frequencies...

										if(yearEndLeaveEntry != null) {
											//DateTime leavePlanRollOver = TkServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(pha.getLeavePlan(), lb.getLeaveDate());
											Interval yearEndLeaveEntryInterval = new Interval(yearEndLeaveEntry.getBeginPeriodDate().getTime(),yearEndLeaveEntry.getEndPeriodDate().getTime());
											if(yearEndLeaveEntryInterval.contains(lb.getLeaveDate().getTime()) && lb.getLeaveDate().compareTo(entry.getBeginPeriodDate()) >= 0) {
												
												//AccrualCategoryRule rollOverRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, leavePlanRollOver.minusDays(1).toDate(), pha.getServiceDate());
												if((tally.compareTo(adjustedMaxBalance) > 0 ||
														(ObjectUtils.isNotNull(adjustedMaxAnnualCarryOver) &&
														tally.compareTo(adjustedMaxAnnualCarryOver) > 0))){
													//The leave amount of lb, when added to the accrued balance as of the leave date, exceeds the max balance
													// ( or max annual carryover ), and the rule is still in effect as of the last day of the leave plan calendar year.
													if(newEligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).isEmpty()) {
														newEligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).add(lb);
													} else {
														Set<LeaveBlock> eligibleLeaveBlocks = newEligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END);
														LeaveBlock tempLB = null;
														for(LeaveBlock block : eligibleLeaveBlocks) {
															//AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
	//														if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
															//the commented conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
															if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
																//this conditional accepts an accrual category rule change from block.leaveDate to lb.leaveDate
																tempLB = block;
																break;
															}
														}
														// could also use a "marker" leave block declared just outside the scope of this loop.
														if(tempLB != null)
															eligibleLeaveBlocks.remove(tempLB);
	
														eligibleLeaveBlocks.add(lb);
														newEligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END, eligibleLeaveBlocks);
													}
												}
												else if(!newEligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).isEmpty()) {
													Set<LeaveBlock> eligibleLeaveBlocks = newEligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END);
													LeaveBlock tempLB = null;
													for(LeaveBlock block : eligibleLeaveBlocks) {
														//AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
	//													if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
														//the commented conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
														if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
															//this conditional accepts an accrual category rule change from block.leaveDate to lb.leaveDate
															tempLB = block;
															break;
														}
													}
													if(tempLB != null) {
														eligibleLeaveBlocks.remove(tempLB);
														newEligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END, eligibleLeaveBlocks);
													}
												}
											}
										}
										//otherwise its not transferable under year end frequency.
									}
									else {
										// on-demand and leave-approve action frequencies.
										if(tally.compareTo(adjustedMaxBalance) > 0 ) {
											if(newEligibilities.get(asOfLeaveDateRule.getMaxBalanceActionFrequency()).isEmpty()) {
												newEligibilities.get(asOfLeaveDateRule.getMaxBalanceActionFrequency()).add(lb);
											} else {
												Set<LeaveBlock> eligibleLeaveBlocks = newEligibilities.get(asOfLeaveDateRule.getMaxBalanceActionFrequency());
												LeaveBlock tempLB = null;
												for(LeaveBlock block : eligibleLeaveBlocks) {
													//AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
//													if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
													//the commented conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
													if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
														//this conditional accepts an accrual category rule change from block.leaveDate to lb.leaveDate
														tempLB = block;
														break;
													}
												}
												
												if(tempLB != null)
													eligibleLeaveBlocks.remove(tempLB);

												eligibleLeaveBlocks.add(lb);
												newEligibilities.put(asOfLeaveDateRule.getMaxBalanceActionFrequency(), eligibleLeaveBlocks);
											}
										}
										else if(!newEligibilities.get(asOfLeaveDateRule.getMaxBalanceActionFrequency()).isEmpty()) {
											Set<LeaveBlock> eligibleLeaveBlocks = newEligibilities.get(asOfLeaveDateRule.getMaxBalanceActionFrequency());
											LeaveBlock tempLB = null;
											for(LeaveBlock block : eligibleLeaveBlocks) {
												//AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
//												if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
												//the commented conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
												if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
													//this conditional accepts an accrual category rule change from block.leaveDate to lb.leaveDate
													tempLB = block;
													break;
												}
											}
											if(tempLB != null) {
												eligibleLeaveBlocks.remove(tempLB);
												newEligibilities.put(asOfLeaveDateRule.getMaxBalanceActionFrequency(), eligibleLeaveBlocks);
											}
										}
									}
								}
							}
						}
					}
					accruedBalance.put(accrualCategory.getAccrualCategory(), tally);
				}
			}
		}
		return newEligibilities;
	}

}
