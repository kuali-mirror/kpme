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
package org.kuali.hr.lm.leavepayout.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.Interval;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.lm.leavepayout.dao.LeavePayoutDao;

import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeavePayoutServiceImpl implements LeavePayoutService {

    private LeavePayoutDao leavePayoutDao;

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalId(
            String principalId) {
        return leavePayoutDao.getAllLeavePayoutsForPrincipalId(principalId);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalIdAsOfDate(
            String principalId, Date effectiveDate) {
        return leavePayoutDao.getAllLeavePayoutsForPrincipalIdAsOfDate(principalId,effectiveDate);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsByEffectiveDate(
            Date effectiveDate) {
        return leavePayoutDao.getAllLeavePayoutsByEffectiveDate(effectiveDate);
    }

    @Override
    public LeavePayout getLeavePayoutById(String lmLeavePayoutId) {
        return leavePayoutDao.getLeavePayoutById(lmLeavePayoutId);
    }

    public LeavePayoutDao getLeavePayoutDao() {
        return leavePayoutDao;
    }

    public void setLeavePayoutDao(LeavePayoutDao leavePayoutDao) {
        this.leavePayoutDao = leavePayoutDao;
    }

	@Override
	public LeavePayout initializePayout(String principalId,
			String accrualCategoryRule, BigDecimal accruedBalance,
			Date effectiveDate) {
		//Initially, principals may be allowed to edit the transfer amount when prompted to submit this balance transfer, however,
		//a base transfer amount together with a forfeited amount is calculated to bring the balance back to its limit in accordance
		//with transfer limits.
		LeavePayout leavePayout = null;
		AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRule);

		if(ObjectUtils.isNotNull(accrualRule) && ObjectUtils.isNotNull(accruedBalance)) {
			leavePayout = new LeavePayout();
			//Leave summary is not a requirement, per se, but the information it contains is.
			//The only thing that is obtained from leave summary is the accrued balance of the leave summary row matching the
			//passed accrualCategoryRules accrual category.
			//These two objects are essential to balance transfers triggered when the employee submits their leave calendar for approval.
			//Neither of these objects should be null, otherwise this method could not have been called.
			AccrualCategory fromAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
			BigDecimal fullTimeEngagement = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
			
			// AccrualRule.maxBalance == null -> no balance limit. No balance limit -> no accrual triggered transfer / payout / lose.
			// execution point should not be here if max balance on accrualRule is null, unless there exists an employee override.
			BigDecimal maxBalance = accrualRule.getMaxBalance();
			BigDecimal adjustedMaxBalance = maxBalance.multiply(fullTimeEngagement).setScale(2);
			
			BigDecimal maxPayoutAmount = null;
			BigDecimal adjustedMaxPayoutAmount = null;
			if(ObjectUtils.isNotNull(accrualRule.getMaxPayoutAmount())) {
				maxPayoutAmount = new BigDecimal(accrualRule.getMaxPayoutAmount());
				adjustedMaxPayoutAmount = maxPayoutAmount.multiply(fullTimeEngagement).setScale(2);
			}
			else {
				// no limit on transfer amount
				maxPayoutAmount = new BigDecimal(Long.MAX_VALUE);
				adjustedMaxPayoutAmount = maxPayoutAmount;
			}

			BigDecimal maxCarryOver = null;
			BigDecimal adjustedMaxCarryOver = null;
			if(ObjectUtils.isNotNull(accrualRule.getMaxCarryOver())) {
				maxCarryOver = new BigDecimal(accrualRule.getMaxCarryOver());
				adjustedMaxCarryOver = maxCarryOver.multiply(fullTimeEngagement).setScale(2);
			}
			else {
				//no limit to carry over.
				maxCarryOver = new BigDecimal(Long.MAX_VALUE);
				adjustedMaxCarryOver = maxCarryOver;
			}
			
			List<EmployeeOverride> overrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, effectiveDate);
			for(EmployeeOverride override : overrides) {
				if(StringUtils.equals(override.getAccrualCategory(),fromAccrualCategory.getAccrualCategory())) {
					if(StringUtils.equals(override.getOverrideType(),"MB"))
						adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
					//override values are not pro-rated for FTE.
					if(StringUtils.equals(override.getOverrideType(),"MPA"))
						adjustedMaxPayoutAmount = new BigDecimal(override.getOverrideValue());
					if(StringUtils.equals(override.getOverrideType(),"MAC"))
						adjustedMaxCarryOver = new BigDecimal(override.getOverrideValue());
				}
			}
			
			
			BigDecimal transferAmount = accruedBalance.subtract(adjustedMaxBalance);
			if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
				//TODO: REMOVE CONDITIONAL
				//Move all time in excess of employee's fte adjusted max balance to forfeiture.
				leavePayout.setForfeitedAmount(transferAmount);
				//There is no transfer to another accrual category.
				leavePayout.setPayoutAmount(BigDecimal.ZERO);
				// to accrual category is a required field on maintenance document. Set as from accrual category
				// to remove validation errors when routing, approving, etc.
				leavePayout.setEarnCode(fromAccrualCategory.getEarnCode());
			}
			else {
				// ACTION_AT_MAX_BAL = PAYOUT
				leavePayout.setEarnCode(accrualRule.getMaxPayoutEarnCode());
				if(transferAmount.compareTo(adjustedMaxPayoutAmount) > 0) {
					//there's forfeiture.
					//bring transfer amount down to the adjusted maximum transfer amount, and place excess in forfeiture.
					//accruedBalance - adjustedMaxPayoutAmount - adjustedMaxBalance = forfeiture.
					//transferAmount = accruedBalance - adjustedMaxBalance; forfeiture = transferAmount - adjustedMaxPayoutAmount.
					BigDecimal forfeiture = transferAmount.subtract(adjustedMaxPayoutAmount).setScale(2);
					forfeiture = forfeiture.stripTrailingZeros();
					leavePayout.setForfeitedAmount(forfeiture);
					leavePayout.setPayoutAmount(adjustedMaxPayoutAmount);
				}
				else {
					leavePayout.setPayoutAmount(transferAmount);
					leavePayout.setForfeitedAmount(BigDecimal.ZERO);
				}
			}
			
			// Max Carry Over logic for Year End transfers.
			if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				if(ObjectUtils.isNotNull(maxCarryOver)) {
					if(ObjectUtils.isNull(adjustedMaxCarryOver))
						adjustedMaxCarryOver = maxCarryOver.multiply(fullTimeEngagement).setScale(2);
					//otherwise, adjustedMaxCarryOver has an employee override value, which trumps accrual rule defined MAC.
					//At this point, transfer amount and forfeiture have been set so that the new accrued balance will be the
					//adjusted max balance, so this amount is used to check against carry over.
					if(adjustedMaxBalance.compareTo(adjustedMaxCarryOver) > 0) {
						BigDecimal carryOverDiff = adjustedMaxBalance.subtract(adjustedMaxCarryOver);
						
						if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)){
							//add carry over excess to forfeiture.
							leavePayout.setForfeitedAmount(leavePayout.getForfeitedAmount().add(carryOverDiff));
						}
						else {
							//maximize the transfer amount.
							BigDecimal potentialPayoutAmount = leavePayout.getPayoutAmount().add(carryOverDiff);
	
							//Can this amount be added to the transfer amount??
							if(potentialPayoutAmount.compareTo(adjustedMaxPayoutAmount) <= 0) {
								//yes
								leavePayout.setPayoutAmount(leavePayout.getPayoutAmount().add(carryOverDiff));
							}
							else {
								//no
								BigDecimal carryOverExcess = potentialPayoutAmount.subtract(adjustedMaxPayoutAmount);
								//move excess to forfeiture
								leavePayout.setForfeitedAmount(leavePayout.getForfeitedAmount().add(carryOverExcess));
								//the remainder (if any) can be added to the transfer amount ( unless action is LOSE ).
								leavePayout.setPayoutAmount(leavePayout.getPayoutAmount().add(carryOverDiff.subtract(carryOverExcess)));
								assert(leavePayout.getPayoutAmount().compareTo(adjustedMaxPayoutAmount)==0);
							}
						}
					}
					//otherwise, given balance will be at or under the max annual carry over.
				}
			}
			
			leavePayout.setEffectiveDate(effectiveDate);
			leavePayout.setAccrualCategoryRule(accrualCategoryRule);
			leavePayout.setFromAccrualCategory(fromAccrualCategory.getAccrualCategory());
			leavePayout.setPrincipalId(principalId);
		}
		return leavePayout;
	}

	@Override
	public LeavePayout payout(LeavePayout leavePayout) {
		if(ObjectUtils.isNull(leavePayout))
			throw new RuntimeException("did not supply a valid LeavePayout object.");
		else {
			List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
			BigDecimal transferAmount = leavePayout.getPayoutAmount();
			LeaveBlock aLeaveBlock = null;
			
			if(ObjectUtils.isNotNull(transferAmount)) {
				if(transferAmount.compareTo(BigDecimal.ZERO) > 0) {
		
					aLeaveBlock = new LeaveBlock();
					//Create a leave block that adds the adjusted transfer amount to the "transfer to" accrual category.
					aLeaveBlock.setPrincipalId(leavePayout.getPrincipalId());
					aLeaveBlock.setLeaveDate(leavePayout.getEffectiveDate());
					aLeaveBlock.setEarnCode(leavePayout.getEarnCode());
					aLeaveBlock.setAccrualCategory(leavePayout.getEarnCodeObj().getAccrualCategory());
					aLeaveBlock.setDescription("Amount payed out");
					aLeaveBlock.setLeaveAmount(leavePayout.getPayoutAmount());
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
					aLeaveBlock.setDocumentId(leavePayout.getLeaveCalendarDocumentId());
					aLeaveBlock.setBlockId(0L);

					//Want to store the newly created leave block id on this maintainable object
					//when the status of the maintenance document encapsulating this maintainable changes
					//the id will be used to fetch and update the leave block statuses.
			    	aLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(aLeaveBlock);

			    	leavePayout.setPayoutLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());
			        // save history
			        LeaveBlockHistory lbh = new LeaveBlockHistory(aLeaveBlock);
			        lbh.setAction(LMConstants.ACTION.ADD);
			        TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					leaveBlocks.add(aLeaveBlock);
					
					//Create leave block that removes the correct transfer amount from the originating accrual category.
					aLeaveBlock = new LeaveBlock();
					aLeaveBlock.setPrincipalId(leavePayout.getPrincipalId());
					aLeaveBlock.setLeaveDate(leavePayout.getEffectiveDate());
					aLeaveBlock.setEarnCode(leavePayout.getFromAccrualCategoryObj().getEarnCode());
					aLeaveBlock.setAccrualCategory(leavePayout.getFromAccrualCategory());
					aLeaveBlock.setDescription("Payout amount");
					aLeaveBlock.setLeaveAmount(leavePayout.getPayoutAmount().negate());
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
					aLeaveBlock.setDocumentId(leavePayout.getLeaveCalendarDocumentId());
					aLeaveBlock.setBlockId(0L);
					
					//Want to store the newly created leave block id on this maintainable object.
					//when the status of the maintenance document encapsulating this maintainable changes
					//the id will be used to fetch and update the leave block statuses.
			    	aLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(aLeaveBlock);

			    	leavePayout.setPayoutFromLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());
			        // save history
			        lbh = new LeaveBlockHistory(aLeaveBlock);
			        lbh.setAction(LMConstants.ACTION.ADD);
			        TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					
					leaveBlocks.add(aLeaveBlock);
				}
			}
			
			BigDecimal forfeitedAmount = leavePayout.getForfeitedAmount();
			if(ObjectUtils.isNotNull(forfeitedAmount)) {
				//Any amount forfeited must come out of the originating accrual category in order to bring balance back to max.
				if(forfeitedAmount.compareTo(BigDecimal.ZERO) > 0) {
					//for balance transfers with action = lose, transfer amount must be moved to forfeitedAmount
					aLeaveBlock = new LeaveBlock();
					aLeaveBlock.setPrincipalId(leavePayout.getPrincipalId());
					aLeaveBlock.setLeaveDate(leavePayout.getEffectiveDate());
					aLeaveBlock.setEarnCode(leavePayout.getFromAccrualCategoryObj().getEarnCode());
					aLeaveBlock.setAccrualCategory(leavePayout.getFromAccrualCategory());
					aLeaveBlock.setDescription("Forfeited payout amount");
					aLeaveBlock.setLeaveAmount(forfeitedAmount.negate());
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
					aLeaveBlock.setDocumentId(leavePayout.getLeaveCalendarDocumentId());
					aLeaveBlock.setBlockId(0L);
					
					//Want to store the newly created leave block id on this maintainable object
					//when the status of the maintenance document encapsulating this maintainable changes
					//the id will be used to fetch and update the leave block statuses.
			    	aLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(aLeaveBlock);

			    	leavePayout.setForfeitedLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());
			        // save history
			        LeaveBlockHistory lbh = new LeaveBlockHistory(aLeaveBlock);
			        lbh.setAction(LMConstants.ACTION.ADD);
			        TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					
					leaveBlocks.add(aLeaveBlock);
				}
			}

			return leavePayout;
		}
	}

	@Override
	public Map<String,ArrayList<String>> getEligiblePayouts(CalendarEntries calendarEntry,
			String principalId) throws Exception {
		//Employee override check here, or return base-eligible accrual categories,
		//filtering out those that have increased balance limits due to employee override in caller?
		//null check inserted to fix LeaveCalendarWebTest failures on kpme-trunk-build-unit #2069	
		List<String> eligibleAccrualCategories = new ArrayList<String>();
		Map<String, ArrayList<String>> eligibilities = new HashMap<String,ArrayList<String>>();
		//TODO: create map for MAX_BAL_ACTION_FREQ in LMConstants
		eligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE, new ArrayList<String>());
		eligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END, new ArrayList<String>());
		eligibilities.put(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND, new ArrayList<String>());

		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, calendarEntry.getEndPeriodDate());
		List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), calendarEntry.getEndPeriodDate());
		
		org.kuali.hr.time.calendar.Calendar leaveCalendar = pha.getLeaveCalObj();
		CalendarEntries thisLeaveEntry = null;
		Interval thisEntryInterval = new Interval(calendarEntry.getBeginPeriodDate().getTime(),calendarEntry.getEndPeriodDate().getTime());

		if(ObjectUtils.isNotNull(leaveCalendar)) {
			for(CalendarEntries entry : leaveCalendar.getCalendarEntries()) {
				if(thisEntryInterval.contains(DateUtils.addDays(entry.getEndPeriodTime(),-1).getTime()))
					thisLeaveEntry = entry;
			}
		}
		// this calendar entry interval does not contain a leave calendar's rollover date.
		if(ObjectUtils.isNull(thisLeaveEntry))
			return eligibilities;
		//TODO: Find the end period date for the corresponding leave calendar.
		// must check if this date falls within the interval of the calendar entries begin / end.
		// if so, get the leave blocks and calculate the accrued balance.
		//LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, getCalendarEntry());
		if(!accrualCategories.isEmpty()) {

			//null check inserted to fix LeaveCalendarWebTst failures on kpme-trunk-build-unit #2069
			for(AccrualCategory accrualCategory : accrualCategories) {
				//TODO: Iterate over Accrual Categories within this calendar entry.
				AccrualCategoryRule rule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, calendarEntry.getEndPeriodDate(), pha.getServiceDate());
				//Employee overrides...
				if(ObjectUtils.isNotNull(rule)) {
					if(StringUtils.equals(rule.getMaxBalFlag(),"Y")) {
						if(StringUtils.equals(rule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.PAYOUT)) {
							//There is a disagreement between the constant value LMConstants.MAX_BAL_ACTION_FREQ, and the value being
							//set on LM_ACCRUAL_CATEGORY_RULES_T table. Temporarily have changed the constant to reflect the field
							//value being set for MAX_BAL_ACTION_FREQ when accrual category rule records are saved.
							if(ObjectUtils.isNotNull(rule.getMaxBalanceActionFrequency())) {
								BigDecimal maxBalance = rule.getMaxBalance();
								
								List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, pha.getServiceDate(), calendarEntry.getEndPeriodDate());
								LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
								LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(accrualCategory.getLmAccrualCategoryId());
								BigDecimal accruedBalance = row.getAccruedBalance();
/*								for(LeaveBlock leaveBlock : leaveBlockMap.get(accrualCategory.getAccrualCategory())) {
									//TODO: limit leave blocks to those created on or after the calendar year period containing this calendar entry.
									if(StringUtils.equals(leaveBlock.getRequestStatus(),LMConstants.REQUEST_STATUS.APPROVED))
										accruedBalance = accruedBalance.add(leaveBlock.getLeaveAmount());
								}*/
								BigDecimal fte = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, TKUtils.getCurrentDate());
								BigDecimal adjustedMaxBalance = maxBalance.multiply(fte);
								BigDecimal maxAnnualCarryOver = null;
								if(ObjectUtils.isNotNull(rule.getMaxCarryOver()))
									maxAnnualCarryOver = new BigDecimal(rule.getMaxCarryOver());
								BigDecimal adjustedMaxAnnualCarryOver = null;
								if(ObjectUtils.isNotNull(maxAnnualCarryOver))
									adjustedMaxAnnualCarryOver = maxAnnualCarryOver.multiply(fte);
									
								List<EmployeeOverride> overrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId, TKUtils.getCurrentDate());
								for(EmployeeOverride override : overrides) {
									if(StringUtils.equals(override.getAccrualCategory(),accrualCategory.getAccrualCategory())) {
										if(StringUtils.equals(override.getOverrideType(),"MB"))
											adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
										if(StringUtils.equals(override.getOverrideType(),"MAC"))
											adjustedMaxAnnualCarryOver = new BigDecimal(override.getOverrideValue());
										//override values are not pro-rated.
									}
								}
								//should extend a BalanceTransferBase class, or use an algorithm swapping pattern.
								//allow institutions to extend/customize/implement their own max_bal_action_frequency types.
								if(StringUtils.equals(rule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
									//For year end transfer frequencies...
									//Should use an "asOfDate" or effective date for principalHRAttributes. If getting eligibilities for a past calendar,
									//pha may not be the same.
									LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(pha.getLeavePlan(),TKUtils.getCurrentDate());
									StringBuilder sb = new StringBuilder("");
									String calendarYearStart = lp.getCalendarYearStart();
									// mm/dd
									sb.append(calendarYearStart+"/");
									if(lp.getCalendarYearStartMonth().equals("01") && calendarEntry.getBeginPeriodDate().getMonth() == 11) {
										//a calendar may start on 01/15, with monthly intervals.
										//calendarEntry.beginPeriodDate.year = calendarYearStart.year - 1
										sb.append(DateUtils.toCalendar(DateUtils.addYears(calendarEntry.getBeginPeriodDate(),1)).get(Calendar.YEAR));
									}
									else
										sb.append(DateUtils.toCalendar(calendarEntry.getBeginPeriodDateTime()).get(Calendar.YEAR));
									//if the calendar being submitted is the final calendar in the leave plans calendar year.
									//must check the calendar year start month. If its the first month of the year, add a year to the date.
									//otherwise, the end period date and the calendar year start date have the same year.
									if(thisEntryInterval.contains(DateUtils.addDays(TKUtils.formatDateString(sb.toString()),-1).getTime())) {
										//BigDecimal accruedBalanceLessPendingTransfers = lsr.getAccruedBalance().add(adjustment);
										if(accruedBalance.compareTo(adjustedMaxBalance) > 0 ||
												(ObjectUtils.isNotNull(adjustedMaxAnnualCarryOver) &&
												accruedBalance.compareTo(adjustedMaxAnnualCarryOver) > 0)) {
											eligibleAccrualCategories.add(rule.getLmAccrualCategoryRuleId());
											eligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).add(rule.getLmAccrualCategoryRuleId());
										}
									}
									//otherwise its not transferable under year end frequency.
								}
								else {
									//BigDecimal accruedBalanceLessPendingTransfers = lsr.getAccruedBalance().add(adjustment);
									if(accruedBalance.compareTo(adjustedMaxBalance) > 0 ) {
										eligibleAccrualCategories.add(rule.getLmAccrualCategoryRuleId());
										eligibilities.get(rule.getMaxBalanceActionFrequency()).add(rule.getLmAccrualCategoryRuleId());
									}
								}
							}
						}
					}
				}
			}
		}
		return eligibilities;
	}

	@Override
	public void submitToWorkflow(LeavePayout leavePayout)
			throws WorkflowException {
		//leavePayout.setStatus(TkConstants.ROUTE_STATUS.ENROUTE);
        EntityNamePrincipalName principalName = null;
        if (leavePayout.getPrincipalId() != null) {
            principalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(leavePayout.getPrincipalId());
        }

		MaintenanceDocument document = KRADServiceLocatorWeb.getMaintenanceDocumentService().setupNewMaintenanceDocument(LeavePayout.class.getName(),
				"LeavePayoutDocumentType",KRADConstants.MAINTENANCE_NEW_ACTION);

        String personName = (principalName != null  && principalName.getDefaultName() != null) ? principalName.getDefaultName().getCompositeName() : StringUtils.EMPTY;
        String date = TKUtils.formatDate(new java.sql.Date(leavePayout.getEffectiveDate().getTime()));
        document.getDocumentHeader().setDocumentDescription(personName + " (" + leavePayout.getPrincipalId() + ")  - " + date);
		Map<String,String[]> params = new HashMap<String,String[]>();
		
		KRADServiceLocatorWeb.getMaintenanceDocumentService().setupMaintenanceObject(document, KRADConstants.MAINTENANCE_NEW_ACTION, params);
		LeavePayout lpObj = (LeavePayout) document.getNewMaintainableObject().getDataObject();
		
		lpObj.setAccrualCategoryRule(leavePayout.getAccrualCategoryRule());
		lpObj.setEffectiveDate(leavePayout.getEffectiveDate());
		lpObj.setForfeitedAmount(leavePayout.getForfeitedAmount());
		lpObj.setFromAccrualCategory(leavePayout.getFromAccrualCategory());
		lpObj.setPrincipalId(leavePayout.getPrincipalId());
		lpObj.setEarnCode(leavePayout.getEarnCode());
		lpObj.setPayoutAmount(leavePayout.getPayoutAmount());
		lpObj.setLmLeavePayoutId(document.getDocumentHeader().getWorkflowDocument().getDocumentId());
		
		document.getNewMaintainableObject().setDataObject(lpObj);
		KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
		document.getDocumentHeader().getWorkflowDocument().saveDocument("");

		document.getDocumentHeader().getWorkflowDocument().route("");
		
		lpObj.setStatus(document.getDocumentHeader().getWorkflowDocument().getStatus().getCode());
		TkServiceLocator.getLeavePayoutService().saveOrUpdate(lpObj);
		
	}

	@Override
	public List<LeavePayout> getLeavePayouts(String viewPrincipal,
			Date beginPeriodDate, Date endPeriodDate) {
		// TODO Auto-generated method stub
		return leavePayoutDao.getLeavePayouts(viewPrincipal, beginPeriodDate, endPeriodDate);
	}

	@Override
	public void saveOrUpdate(LeavePayout payout) {
		leavePayoutDao.saveOrUpdate(payout);
	}
}
