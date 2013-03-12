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
import java.util.ArrayList;
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
		
		Calendar cal = pha.getLeaveCalObj();
		
		if(cal == null)
			return newEligibilities;
		
		List<CalendarEntries> leaveCalEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesEndingBetweenBeginAndEndDate(cal.getHrCalendarId(), entry.getBeginPeriodDate(), entry.getEndPeriodDate());
		CalendarEntries yearEndLeaveEntry = null;
		CalendarEntries leaveLeaveEntry = null;
		if(!leaveCalEntries.isEmpty()) {
			for(CalendarEntries leaveEntry : leaveCalEntries) {
				if(StringUtils.equals(cal.getCalendarName(), leaveEntry.getCalendarName())) {
					if(TkServiceLocator.getLeavePlanService().isLastCalendarPeriodOfLeavePlan(leaveEntry, pha.getLeavePlan(), asOfDate))
						yearEndLeaveEntry = leaveEntry;
					if(leaveEntry.getEndPeriodDate().compareTo(entry.getEndPeriodDate()) <= 0)
						leaveLeaveEntry = leaveEntry;
				}
			}
		}
		
		List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), asOfDate);

		if(!accrualCategories.isEmpty()) {
			//could remove the mapping.
			Map<String, BigDecimal> accruedBalance = new HashMap<String, BigDecimal>();

			for(AccrualCategory accrualCategory : accrualCategories) {
				List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, pha.getServiceDate(), DateUtils.addDays(asOfDate,1), accrualCategory.getAccrualCategory());

/*	Un-comment to consider service interval end-point changes. i.e. when defining a new action frequency - "ON_SERVICE_MILESTONE"
 * 
 * 				List<AccrualCategoryRule> accrualRules = TkServiceLocator.getAccrualCategoryRuleService().getActiveAccrualCategoryRules(accrualCategory.getLmAccrualCategoryId());
				for(AccrualCategoryRule rule : accrualRules) {
					String serviceUnits = rule.getServiceUnitOfTime();
					Date rollOverDate = null;
					//TODO: Accrual Category Rules' start and end field allow only whole integer values. This should be reflected in storage.
					if(StringUtils.equals(serviceUnits, "M")) {
						rollOverDate = new java.sql.Date(DateUtils.addMonths(pha.getServiceDate(), (new BigDecimal(rule.getEnd()).intValue())).getTime());
					}
					else if(StringUtils.equals(serviceUnits, "Y")) {
						rollOverDate = new java.sql.Date(DateUtils.addYears(pha.getServiceDate(), (new BigDecimal(rule.getEnd()).intValue())).getTime());
					}
					if(ObjectUtils.isNotNull(rollOverDate)) {
						if(thisEntryInterval.contains(DateUtils.addDays(rollOverDate,-1).getTime())) {
							//Add a max balance allocation leave block.
							LeaveBlock allocation = new LeaveBlock();
							allocation.setAccrualCategory(accrualCategory.getAccrualCategory());
							allocation.setLeaveDate(new java.sql.Date(DateUtils.addDays(rollOverDate,-1).getTime()));
							allocation.setLeaveAmount(BigDecimal.ZERO);
							allocation.setPrincipalId(principalId);
							leaveBlocks.add(allocation);
						}
					}
				}
				*/

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
				
				if(ObjectUtils.isNotNull(yearEndLeaveEntry)) {
					if(TKUtils.getCurrentDate().after(DateUtils.addDays(yearEndLeaveEntry.getEndPeriodDate(), -1))) {
						//if entry belongs to a time calendar, check the balances at the end date for the leave period that ends
						//within entry's interval.
						//if entry belongs to a leave calendar, this empty block will simply duplicate and override
						//the block created above.
						allocation = new LeaveBlock();
						allocation.setAccrualCategory(accrualCategory.getAccrualCategory());
						allocation.setLeaveDate(new java.sql.Date(DateUtils.addDays(yearEndLeaveEntry.getEndPeriodDate(),-1).getTime()));
						
						allocation.setLeaveAmount(BigDecimal.ZERO);
						allocation.setPrincipalId(principalId);
						leaveBlocks.add(allocation);
					}
				}
				
				if(ObjectUtils.isNotNull(leaveLeaveEntry)) {
					if(TKUtils.getCurrentDate().after(DateUtils.addDays(leaveLeaveEntry.getEndPeriodDate(), -1))) {
						//if entry belongs to a time calendar, check the balances at the end date for the calendar year that ends
						//within entry's interval.
						//if entry belongs to a leave calendar, this empty block will simply duplicate and override
						//the block created above.
						allocation = new LeaveBlock();
						allocation.setAccrualCategory(accrualCategory.getAccrualCategory());
						allocation.setLeaveDate(new java.sql.Date(DateUtils.addDays(leaveLeaveEntry.getEndPeriodDate(),-1).getTime()));
						
						allocation.setLeaveAmount(BigDecimal.ZERO);
						allocation.setPrincipalId(principalId);
						leaveBlocks.add(allocation);
					}
				}
				
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
									
									if(StringUtils.equals(asOfLeaveDateRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
										//For year end transfer frequencies...
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
													AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
													if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
													//the above conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
													//if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
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
												AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
												if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
												//the above conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
												//if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
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
													AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
													if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
													//the above conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
													//if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
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
												AccrualCategoryRule blockRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(block.getAccrualCategoryRuleId());
												if(StringUtils.equals(blockRule.getLmAccrualCategoryRuleId(),asOfLeaveDateRule.getLmAccrualCategoryRuleId())) {
												//the above conditional flags lb as a separate infraction if the accrual category rule changed from block.leaveDate to lb.leaveDate
												//if(StringUtils.equals(block.getAccrualCategory(),lb.getAccrualCategory())) {
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
