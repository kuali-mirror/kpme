/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.leave.accrual.service;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.api.leave.accrual.AccrualCategoryMaxBalanceService;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class AccrualCategoryMaxBalanceServiceImpl implements AccrualCategoryMaxBalanceService {

	@Override
	public Map<String, Set<LeaveBlockContract>> getMaxBalanceViolations(CalendarEntry entry, String principalId) {

		Map<String, Set<LeaveBlockContract>> maxBalanceViolations = new HashMap<String,Set<LeaveBlockContract>>();
		
		Map<String, Set<LeaveBlockContract>> eligibilities = new HashMap<String, Set<LeaveBlockContract>>();
		
		eligibilities.put(HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE, new HashSet<LeaveBlockContract>());
		eligibilities.put(HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END, new HashSet<LeaveBlockContract>());
		eligibilities.put(HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND, new HashSet<LeaveBlockContract>());
		
		Interval thisEntryInterval = new Interval(entry.getBeginPeriodFullDateTime(),entry.getEndPeriodFullDateTime());

		LocalDate asOfDate = LocalDate.now();
		
		if(!thisEntryInterval.contains(asOfDate.toDate().getTime()))
			asOfDate = entry.getEndPeriodFullDateTime().minusDays(1).toLocalDate();

		PrincipalHRAttributesContract pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
		
		if(pha == null)
			return eligibilities;
		
		CalendarContract cal = pha.getLeaveCalObj();
		
		if(cal == null)
			return eligibilities;
		
		//Consider time sheet intervals that stagger a leave period end date...
		List<CalendarEntry> leaveCalEntries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate(cal.getHrCalendarId(), entry.getBeginPeriodFullDateTime(), entry.getEndPeriodFullDateTime());
        CalendarEntry yearEndLeaveEntry = null;
        CalendarEntry leaveLeaveEntry = null;
		if(!leaveCalEntries.isEmpty()) {
			for(CalendarEntry leaveEntry : leaveCalEntries) {
				if(StringUtils.equals(cal.getCalendarName(), leaveEntry.getCalendarName())) {
					if(leaveEntry.getEndPeriodFullDateTime().compareTo(entry.getBeginPeriodFullDateTime()) > 0)
						leaveLeaveEntry = leaveEntry;
				}
			}
		}
		
		Interval leavePeriodInterval = null;
		Interval yearEndPeriodInterval = null;
		if(leaveLeaveEntry != null) {
			leavePeriodInterval = new Interval(entry.getBeginPeriodFullDateTime(),leaveLeaveEntry.getEndPeriodFullDateTime());
			if(HrServiceLocator.getLeavePlanService().isLastCalendarPeriodOfLeavePlan(leaveLeaveEntry, pha.getLeavePlan(), asOfDate))
				yearEndPeriodInterval = leavePeriodInterval;
		}
		
		List<? extends AccrualCategoryContract> accrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), asOfDate);

		if(!accrualCategories.isEmpty()) {
			
			List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
			Map<String, BigDecimal> accruedBalance = new HashMap<String, BigDecimal>();

			for(AccrualCategoryContract accrualCategory : accrualCategories) {
				leaveBlocks.addAll(LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, pha.getServiceLocalDate(), asOfDate.plusDays(1), accrualCategory.getAccrualCategory()));
				accruedBalance.put(accrualCategory.getLmAccrualCategoryId(), BigDecimal.ZERO);
/*	Un-comment to consider service interval end-point changes. i.e. when defining a new action frequency - "ON_SERVICE_MILESTONE"
				List<AccrualCategoryRule> accrualRules = HrServiceLocator.getAccrualCategoryRuleService().getActiveAccrualCategoryRules(accrualCategory.getLmAccrualCategoryId());
				for(AccrualCategoryRule rule : accrualRules) {
					String serviceUnits = rule.getServiceUnitOfTime();
					LocalDate rollOverDate = null;
					//TODO: Accrual Category Rules' start and end field allow only whole integer values. This should be reflected in storage.
					if(StringUtils.equals(serviceUnits, "M")) {
						rollOverDate = pha.getServiceLocalDate().plusMonths(rule.getEnd().intValue());
					}
					else if(StringUtils.equals(serviceUnits, "Y")) {
						rollOverDate = pha.getServiceLocalDate().plusYears(rule.getEnd().intValue());
					}
					if(ObjectUtils.isNotNull(rollOverDate)) {
						if(thisEntryInterval.contains(rollOverDate.minusDays(1).toDate().getTime())) {
							//Add a max balance allocation leave block.
							LeaveBlock allocation = new LeaveBlock();
							allocation.setAccrualCategory(accrualCategory.getAccrualCategory());
							allocation.setLeaveLocalDate(rollOverDate.minusDays(1));
							allocation.setLeaveAmount(BigDecimal.ZERO);
							allocation.setPrincipalId(principalId);
							leaveBlocks.add(allocation);
						}
					}
				}
*/

				//Add a max balance allocation leave block.
				LeaveBlockBo allocation = new LeaveBlockBo();
				allocation.setAccrualCategory(accrualCategory.getAccrualCategory());
				
				if(thisEntryInterval.contains(LocalDate.now().toDate().getTime()))
					allocation.setLeaveLocalDate(LocalDate.now());
				else
					allocation.setLeaveLocalDate(entry.getEndPeriodFullDateTime().toLocalDate().minusDays(1));
				
				allocation.setLeaveAmount(BigDecimal.ZERO);
				allocation.setPrincipalId(principalId);
				allocation.setLeaveBlockType("allocation");
				leaveBlocks.add(LeaveBlockBo.to(allocation));
				
				if(ObjectUtils.isNotNull(leaveLeaveEntry)) {
						//if entry belongs to a time calendar, check the balances at the end date for the calendar year that ends
						//within entry's interval.
						//if entry belongs to a leave calendar, this empty block will simply duplicate and override
						//the block created above.
						allocation = new LeaveBlockBo();
						allocation.setAccrualCategory(accrualCategory.getAccrualCategory());
						allocation.setLeaveLocalDate(leaveLeaveEntry.getEndPeriodFullDateTime().toLocalDate().minusDays(1));
						allocation.setLeaveAmount(BigDecimal.ZERO);
						allocation.setPrincipalId(principalId);
						allocation.setLeaveBlockType("allocation");
						leaveBlocks.add(LeaveBlockBo.to(allocation));
				}
				
				if(!leaveBlocks.isEmpty()) {
					Collections.sort(leaveBlocks, new Comparator<LeaveBlockContract>() {
						
						@Override
						public int compare(LeaveBlockContract o1, LeaveBlockContract o2) {
							return o1.getLeaveDateTime().compareTo(o2.getLeaveDateTime());
						}
						
					});
				}
			}
			
			for(LeaveBlock lb : leaveBlocks) {
				if(StringUtils.equals(lb.getRequestStatus(),HrConstants.REQUEST_STATUS.DISAPPROVED) || StringUtils.equals(lb.getRequestStatus(),HrConstants.REQUEST_STATUS.DEFERRED))
					continue;
				AccrualCategory accrualCategory = lb.getAccrualCategoryObj();
				BigDecimal tally = accruedBalance.get(accrualCategory.getLmAccrualCategoryId());
				if(tally==null){
					tally = new BigDecimal(0);
				}
				tally = tally.add(lb.getLeaveAmount());
				
				AccrualCategoryRule asOfLeaveDateRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, lb.getLeaveLocalDate(), pha.getServiceLocalDate());

				//Employee overrides...
				if(ObjectUtils.isNotNull(asOfLeaveDateRule)) {
					if(StringUtils.equals(asOfLeaveDateRule.getMaxBalFlag(),"Y")) {
						if(StringUtils.isNotBlank(asOfLeaveDateRule.getActionAtMaxBalance())) {
							if(ObjectUtils.isNotNull(asOfLeaveDateRule.getMaxBalanceActionFrequency())) {
								
								if(maxBalanceViolations.get(asOfLeaveDateRule.getLmAccrualCategoryId()) == null)
									maxBalanceViolations.put(asOfLeaveDateRule.getLmAccrualCategoryId(), new HashSet<LeaveBlockContract>());
								
								BigDecimal maxBalance = asOfLeaveDateRule.getMaxBalance();

								BigDecimal fte = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, LocalDate.now());
								BigDecimal adjustedMaxBalance = maxBalance.multiply(fte);

								BigDecimal maxAnnualCarryOver = null;
								if(ObjectUtils.isNotNull(asOfLeaveDateRule.getMaxCarryOver()))
									maxAnnualCarryOver = new BigDecimal(asOfLeaveDateRule.getMaxCarryOver());

								BigDecimal adjustedMaxAnnualCarryOver = null;
								if(ObjectUtils.isNotNull(maxAnnualCarryOver)) {
									adjustedMaxAnnualCarryOver = maxAnnualCarryOver.multiply(fte);
								}

								List<EmployeeOverride> overrides = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, lb.getLeaveLocalDate());
								for(EmployeeOverride override : overrides) {
									if(StringUtils.equals(override.getAccrualCategory(),lb.getAccrualCategory())) {
										//Do not pro-rate override values for FTE.
										if(StringUtils.equals(override.getOverrideType(),"MB"))
											adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
										if(StringUtils.equals(override.getOverrideType(),"MAC"))
											adjustedMaxAnnualCarryOver = new BigDecimal(override.getOverrideValue());
									}
								}

								boolean yearEndCandidate = false;
								if(StringUtils.equals(asOfLeaveDateRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)
										&& adjustedMaxAnnualCarryOver != null) {
									yearEndCandidate = true;
								}
								//callers must determine if the violation exists on the calendar prior to the leave plan's calendar year end date.
								if(tally.compareTo(adjustedMaxBalance) > 0
										|| (yearEndCandidate && tally.compareTo(adjustedMaxAnnualCarryOver) > 0)) {
									if(maxBalanceViolations.get(asOfLeaveDateRule.getLmAccrualCategoryId()).isEmpty()) {
										maxBalanceViolations.get(asOfLeaveDateRule.getLmAccrualCategoryId()).add(lb);
									}
									else {
										Set<LeaveBlockContract> eligibleLeaveBlocks = maxBalanceViolations.get(asOfLeaveDateRule.getLmAccrualCategoryId());
										LeaveBlockContract previousInfraction = retreivePreviousInfraction(eligibleLeaveBlocks,lb,leavePeriodInterval,yearEndPeriodInterval,thisEntryInterval,asOfLeaveDateRule);

										if(previousInfraction != null)
											eligibleLeaveBlocks.remove(previousInfraction);
											/* Replace the leave block with the most recent infraction that occurred so long as the infraction takes place
											 * before the end of the [underlying] leave period. This includes accrual categories that undergo a rule change
											 * as the result of the employee crossing a service milestone.
											 * Doing so will correctly trigger the action on timesheets that stagger a leave
											 * period end date, since the block would exist on a leave period whose end date is not contained within
											 * the time period. For on-demand frequencies, we want to replace the block with the latest no matter what.
											 * Doing so will ensure that the latest rule is used to initiate the action.
											 * 
											 */
										eligibleLeaveBlocks.add(lb);
										maxBalanceViolations.put(asOfLeaveDateRule.getLmAccrualCategoryId(), eligibleLeaveBlocks);
									}
								}
								else	// the leave amount (usage), as of lb's leave date, is under the balance limit.
									if(!maxBalanceViolations.get(asOfLeaveDateRule.getLmAccrualCategoryId()).isEmpty()) {
										//if there was a previous infraction, it must be removed so long as the leave date of lb lies within
										//the same period as the previous infraction.
										Set<LeaveBlockContract> eligibleLeaveBlocks = maxBalanceViolations.get(asOfLeaveDateRule.getLmAccrualCategoryId());
										LeaveBlockContract previousInfraction = retreivePreviousInfraction(eligibleLeaveBlocks,lb,leavePeriodInterval,yearEndPeriodInterval,thisEntryInterval,asOfLeaveDateRule);
										if(previousInfraction != null) {
											eligibleLeaveBlocks.remove(previousInfraction);
											maxBalanceViolations.put(asOfLeaveDateRule.getLmAccrualCategoryId(), eligibleLeaveBlocks);
										}
								}
							}
						}
					}
				}
				accruedBalance.put(accrualCategory.getLmAccrualCategoryId(), tally);
			}
		}
		
		for(Entry<String,Set<LeaveBlockContract>> entries : maxBalanceViolations.entrySet()) {
			for(LeaveBlockContract lb : entries.getValue()) {
				AccrualCategoryRuleContract aRule = lb.getAccrualCategoryRule();
                eligibilities.get(aRule.getMaxBalanceActionFrequency()).add(lb);
			}
		}
		
		return eligibilities;
	}
	
	protected LeaveBlockContract retreivePreviousInfraction(Set<? extends LeaveBlockContract> eligibleLeaveBlocks, LeaveBlockContract lb, Interval leavePeriodInterval, Interval yearEndPeriodInterval, Interval thisEntryInterval, AccrualCategoryRule asOfLeaveDateRule) {
		LeaveBlockContract tempLB = null;
		for(LeaveBlockContract block : eligibleLeaveBlocks) {
			AccrualCategoryRuleContract blockRule = block.getAccrualCategoryRule();
			if(StringUtils.equals(asOfLeaveDateRule.getLmAccrualCategoryRuleId(),blockRule.getLmAccrualCategoryRuleId())) {
				if((StringUtils.equals(asOfLeaveDateRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)
						&& StringUtils.equals(blockRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
						|| ( leavePeriodInterval != null
							&& leavePeriodInterval.contains(lb.getLeaveDateTime())
							&& leavePeriodInterval.contains(block.getLeaveDateTime()))
						|| (leavePeriodInterval == null
							&& thisEntryInterval.contains(block.getLeaveDateTime())
							&& thisEntryInterval.contains(lb.getLeaveDateTime()))
						|| (StringUtils.equals(asOfLeaveDateRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)
							&& StringUtils.equals(blockRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)
							&& (yearEndPeriodInterval == null ||
								(yearEndPeriodInterval.contains(block.getLeaveDateTime())
										&& yearEndPeriodInterval.contains(lb.getLeaveDateTime())))
							//year end we replace if the two infractions are not within the same leave period.
							//if yearEndPeriodInterval != null, the conditional for leave approve should have already evaluated to true.
							//i.e. yearEndPeriodInterval != null will never be evaluated.
						|| block.getLeaveLocalDate().toDate().before(thisEntryInterval.getStart().toDate()))) {
					tempLB = block;
					break;
				}
			}
			else
				if(StringUtils.equals(blockRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)
						//always supersede on-demand action frequencies
						|| (StringUtils.equals(blockRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)
								&& ((leavePeriodInterval != null
										&& leavePeriodInterval.contains(block.getLeaveDateTime())
										&& leavePeriodInterval.contains(lb.getLeaveDateTime()))
									|| (leavePeriodInterval == null
										&& thisEntryInterval.contains(block.getLeaveDateTime())
										&& thisEntryInterval.contains(lb.getLeaveDateTime()))))
						//leave approve is replaced only if the replacement lies within the same leave period.
						|| (StringUtils.equals(blockRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)
								&& (yearEndPeriodInterval == null
									|| (yearEndPeriodInterval.contains(block.getLeaveDateTime())
										&& yearEndPeriodInterval.contains(lb.getLeaveDateTime()))))
						//year end is superseded only if the new rule goes into effect before the current leave plan calendar year end date.
						|| block.getLeaveLocalDate().toDate().before(thisEntryInterval.getStart().toDate())) {
					tempLB = block;
					break;
				}
		}
		return tempLB;
	}

}
