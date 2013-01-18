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
package org.kuali.hr.lm.balancetransfer.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.balancetransfer.dao.BalanceTransferDao;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferServiceImpl implements BalanceTransferService {

	private BalanceTransferDao balanceTransferDao;
	
	@Override
	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(
			String principalId) {
		return balanceTransferDao.getAllBalanceTransfersForPrincipalId(principalId);
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferForPrincipalIdAsOfDate(
			String principalId, Date effectiveDate) {
		return balanceTransferDao.getAllBalanceTransferForPrincipalIdAsOfDate(principalId,effectiveDate);
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(
			Date effectiveDate) {
		return balanceTransferDao.getAllBalanceTransferByEffectiveDate(effectiveDate);
	}

	@Override
	public BalanceTransfer getBalanceTransferById(String balanceTransferId) {
		return balanceTransferDao.getBalanceTransferById(balanceTransferId);
	}
	
	@Override
	public BalanceTransfer initializeTransfer(String principalId, String accrualCategoryRule, LeaveSummary leaveSummary, Date effectiveDate) {
		//Initially, principals may be allowed to edit the transfer amount when prompted to submit this balance transfer, however,
		//a base transfer amount together with a forfeited amount is calculated to bring the balance back to its limit in accordance
		//with transfer limits.
		String pendingDates = leaveSummary.getPendingDatesString();
		TKUtils.formatDateString(pendingDates);
		BalanceTransfer bt = null;
		AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRule);
		/**
		 * Balance transfers triggered by an accrual category rule require at least the following elements:
		 * 
		 * AccrualCategoryRule:
		 * 
		 * 	MaxBalFlag=Y;
		 *  ActionAtMaxBal=TRANSFER || LOSE;
		 *  MaxBalActionFreq!=null;
		 *  MaxBal > 0;
		 *  MaxBalTransferConvFator != null; ( unless action = LOSE )
		 *  MaxBalTransferToAccrualCategory != null ( unless action = LOSE ).
		 *  Active=Y;
		 *  
		 * LeaveSummary:
		 * 
		 *  LeaveSummaryRow with accrual category matching the eligible accrual category rule's accrual category. set leave summary row's accrual
		 *  	category rule..?
		 *  LeaveSummaryRow.balance > AccrualCategoryRule.MaxBal;
		 *  
		 * 
		 */
		if(ObjectUtils.isNotNull(accrualRule) && ObjectUtils.isNotNull(leaveSummary)) {
			bt = new BalanceTransfer();
			//These two objects are essential to balance transfers triggered when the employee submits their leave calendar for approval.
			//Neither of these objects should be null, otherwise this method could not have been called.
			AccrualCategory fromAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
			AccrualCategory toAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getMaxBalanceTransferToAccrualCategory(),effectiveDate);
			LeaveSummaryRow balanceInformation = leaveSummary.getLeaveSummaryRowForAccrualCategory(accrualRule.getLmAccrualCategoryId());

			// AccrualRule.maxBalance == null -> no balance limit. No balance limit -> no accrual triggered transfer / payout / lose.
			BigDecimal maxBalance = accrualRule.getMaxBalance();
			BigDecimal fullTimeEngagement = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
			BigDecimal adjustedMaxBalance = maxBalance.multiply(fullTimeEngagement).setScale(2);
			
			BigDecimal maxTransferAmount = null;
			BigDecimal adjustedMaxTransferAmount = null;
			if(ObjectUtils.isNotNull(accrualRule.getMaxTransferAmount())) {
				maxTransferAmount = new BigDecimal(accrualRule.getMaxTransferAmount());
				adjustedMaxTransferAmount = maxTransferAmount.multiply(fullTimeEngagement).setScale(2);
			}
			else {
				// no limit on transfer amount
				maxTransferAmount = new BigDecimal(Long.MAX_VALUE);
				adjustedMaxTransferAmount = maxTransferAmount;
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
					if(StringUtils.equals(override.getOverrideType(),"MTA"))
						adjustedMaxTransferAmount = new BigDecimal(override.getOverrideValue());
					if(StringUtils.equals(override.getOverrideType(),"MAC"))
						adjustedMaxCarryOver = new BigDecimal(override.getOverrideValue());
				}
			}
			
			
			BigDecimal transferAmount = balanceInformation.getAccruedBalance().subtract(adjustedMaxBalance);
			if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
				//Move all time in excess of employee's fte adjusted max balance to forfeiture.
				bt.setForfeitedAmount(transferAmount);
				//There is no transfer to another accrual category.
				bt.setTransferAmount(BigDecimal.ZERO);
				bt.setToAccrualCategory(null);
			}
			else {
				bt.setToAccrualCategory(toAccrualCategory.getAccrualCategory());
				if(transferAmount.compareTo(adjustedMaxTransferAmount) > 0) {
					//there's forfeiture.
					//bring transfer amount down to the adjusted maximum transfer amount, and place excess in forfeiture.
					//accruedBalance - adjustedMaxTransferAmount - adjustedMaxBalance = forfeiture.
					//transferAmount = accruedBalance - adjustedMaxBalance; forfeiture = transferAmount - adjustedMaxTransferAmount.
					BigDecimal forfeiture = transferAmount.subtract(adjustedMaxTransferAmount).setScale(2);
					forfeiture = forfeiture.stripTrailingZeros();
					bt.setForfeitedAmount(forfeiture);
					bt.setTransferAmount(adjustedMaxTransferAmount);
				}
				else {
					bt.setTransferAmount(transferAmount);
					bt.setForfeitedAmount(BigDecimal.ZERO);
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

						BigDecimal potentialTransferAmount = bt.getTransferAmount().add(carryOverDiff);
						//Can this amount be added to the transfer amount??
						if(potentialTransferAmount.compareTo(adjustedMaxTransferAmount) <= 0) {
							//yes
							bt.setTransferAmount(bt.getTransferAmount().add(carryOverDiff));
						}
						else {
							//no
							BigDecimal carryOverExcess = potentialTransferAmount.subtract(adjustedMaxTransferAmount);
							//move excess to forfeiture
							bt.setForfeitedAmount(bt.getForfeitedAmount().add(carryOverExcess));
							//the remainder (if any) can be added to the transfer amount
							bt.setTransferAmount(bt.getTransferAmount().add(carryOverDiff.subtract(carryOverExcess)));
							assert(bt.getTransferAmount().compareTo(adjustedMaxTransferAmount)==0);
						}
					}
					//otherwise, given balance will be under the max annual carry over.
				}
			}
			
			bt.setEffectiveDate(effectiveDate);
			bt.setAccrualCategoryRule(accrualCategoryRule);
			bt.setFromAccrualCategory(fromAccrualCategory.getAccrualCategory());
			bt.setPrincipalId(principalId);
			
		}
		return bt;
	}

	@Override
	public BalanceTransfer transfer(BalanceTransfer balanceTransfer) {
		if(ObjectUtils.isNull(balanceTransfer))
			throw new RuntimeException("did not supply a valid BalanceTransfer object.");
		else {
			List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
			BigDecimal transferAmount = balanceTransfer.getTransferAmount();
			LeaveBlock aLeaveBlock = null;
			
			if(ObjectUtils.isNotNull(transferAmount)) {
				if(transferAmount.compareTo(BigDecimal.ZERO) > 0) {
					/**
					 * Possible Balance Transfer Types???
					 * 
					 * (A)ccrual Generated (M)aintenance, (C)arry Over
					 * 
					 */
					//Here, use the accrual category rule defined on the balance transfer object. If the balance transfer was initiated via a
					//trigger, this rule will be set to the "from" accrual category rule on the transfer. Transfers initiated via the
					//maintenance tab do not set the accrual category rule on the balance transfer object, defaulting to 1-1 transfer conversion factor.
					//May need to move this variable's scope a level up if leave block's request status varies
					//on action_at_max_bal/max_bal_action_freq. ( i.e. forfeiture is auto-approved on action = LOSE. )
					AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(balanceTransfer.getAccrualCategoryRule());

					BigDecimal transferConversionFactor = null;
					if(ObjectUtils.isNotNull(aRule)) {
						transferConversionFactor = aRule.getMaxBalanceTransferConversionFactor();
						if(ObjectUtils.isNull(transferConversionFactor))
							transferConversionFactor = BigDecimal.ONE;
					}
					else
						transferConversionFactor = BigDecimal.ONE;
		
					aLeaveBlock = new LeaveBlock();
					//Create a leave block that adds the adjusted transfer amount to the "transfer to" accrual category.
					aLeaveBlock.setPrincipalId(balanceTransfer.getPrincipalId());
					aLeaveBlock.setLeaveDate(balanceTransfer.getEffectiveDate());
					aLeaveBlock.setEarnCode(balanceTransfer.getCreditedAccrualCategory().getEarnCode());
					aLeaveBlock.setAccrualCategory(balanceTransfer.getToAccrualCategory());
					aLeaveBlock.setDescription("Amount transferred");
					aLeaveBlock.setLeaveAmount(transferConversionFactor.multiply(balanceTransfer.getTransferAmount()));
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
					aLeaveBlock.setDocumentId(balanceTransfer.getLeaveCalendarDocumentId());
					aLeaveBlock.setBlockId(0L);

					//Want to store the newly created leave block id on this maintainable object
					//when the status of the maintenance document encapsulating this maintainable changes
					//the id will be used to fetch and update the leave block statuses.
			    	aLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(aLeaveBlock);

			    	balanceTransfer.setAccruedLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());
			        // save history
			        LeaveBlockHistory lbh = new LeaveBlockHistory(aLeaveBlock);
			        lbh.setAction(LMConstants.ACTION.ADD);
			        TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					leaveBlocks.add(aLeaveBlock);
					
					//Create leave block that removes the correct transfer amount from the originating accrual category.
					aLeaveBlock = new LeaveBlock();
					aLeaveBlock.setPrincipalId(balanceTransfer.getPrincipalId());
					aLeaveBlock.setLeaveDate(balanceTransfer.getEffectiveDate());
					aLeaveBlock.setEarnCode(balanceTransfer.getDebitedAccrualCategory().getEarnCode());
					aLeaveBlock.setAccrualCategory(balanceTransfer.getFromAccrualCategory());
					aLeaveBlock.setDescription("Transferred amount");
					aLeaveBlock.setLeaveAmount(balanceTransfer.getTransferAmount().negate());
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
					aLeaveBlock.setDocumentId(balanceTransfer.getLeaveCalendarDocumentId());
					aLeaveBlock.setBlockId(0L);
					
					//Want to store the newly created leave block id on this maintainable object.
					//when the status of the maintenance document encapsulating this maintainable changes
					//the id will be used to fetch and update the leave block statuses.
			    	aLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(aLeaveBlock);

			    	balanceTransfer.setDebitedLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());
			        // save history
			        lbh = new LeaveBlockHistory(aLeaveBlock);
			        lbh.setAction(LMConstants.ACTION.ADD);
			        TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					
					leaveBlocks.add(aLeaveBlock);
				}
			}
			
			BigDecimal forfeitedAmount = balanceTransfer.getForfeitedAmount();
			if(ObjectUtils.isNotNull(forfeitedAmount)) {
				//Any amount forfeited must come out of the originating accrual category in order to bring balance back to max.
				if(forfeitedAmount.compareTo(BigDecimal.ZERO) > 0) {
					//for balance transfers with action = lose, transfer amount must be moved to forfeitedAmount
					aLeaveBlock = new LeaveBlock();
					aLeaveBlock.setPrincipalId(balanceTransfer.getPrincipalId());
					aLeaveBlock.setLeaveDate(balanceTransfer.getEffectiveDate());
					aLeaveBlock.setEarnCode(balanceTransfer.getDebitedAccrualCategory().getEarnCode());
					aLeaveBlock.setAccrualCategory(balanceTransfer.getFromAccrualCategory());
					aLeaveBlock.setDescription("Forfeited balance transfer amount");
					aLeaveBlock.setLeaveAmount(forfeitedAmount.negate());
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
					aLeaveBlock.setDocumentId(balanceTransfer.getLeaveCalendarDocumentId());
					aLeaveBlock.setBlockId(0L);
					
					//Want to store the newly created leave block id on this maintainable object
					//when the status of the maintenance document encapsulating this maintainable changes
					//the id will be used to fetch and update the leave block statuses.
			    	aLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(aLeaveBlock);

			    	balanceTransfer.setForfeitedLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());
			        // save history
			        LeaveBlockHistory lbh = new LeaveBlockHistory(aLeaveBlock);
			        lbh.setAction(LMConstants.ACTION.ADD);
			        TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					
					leaveBlocks.add(aLeaveBlock);
				}
			}

			return balanceTransfer;
		}
	}
	
	//TODO: Move to LeaveCalendarService or implement as an accessor on LeaveCalendarDocument object.
	@Override
	public List<String> getEligibleTransfers(LeaveCalendarDocument document, final String actionFrequency) throws Exception {
		List<String> eligibleAccrualCategories = new ArrayList<String>();
		//Employee override check here, or return base-eligible accrual categories,
		//filtering out those that have increased balance limits due to employee override in caller?
		if(ObjectUtils.isNotNull(document)) {
			//null check inserted to fix LeaveCalendarWebTest failures on kpme-trunk-build-unit #2069		
			
			LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(document.getPrincipalId(),document.getCalendarEntry());

			if(ObjectUtils.isNotNull(leaveSummary)) {
				//null check inserted to fix LeaveCalendarWebTst failures on kpme-trunk-build-unit #2069
				
/*				List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(document.getPrincipalId(),
						document.getCalendarEntry().getBeginPeriodDateTime(), document.getCalendarEntry().getEndPeriodDateTime());*/
				
				for(LeaveSummaryRow lsr : leaveSummary.getLeaveSummaryRows()) {
					
					
/*					BigDecimal adjustment = new BigDecimal(0);
					for(LeaveBlock lb : leaveBlocks) {
						if(StringUtils.equals(lb.getAccrualCategory(),lsr.getAccrualCategory())) {
							if(StringUtils.equals(lb.getRequestStatus(),LMConstants.REQUEST_STATUS.PLANNED) ||
									StringUtils.equals(lb.getRequestStatus(), LMConstants.REQUEST_STATUS.USAGE)) 
								adjustment = adjustment.add(lb.getLeaveAmount());
							//add leave into adjustment for blocks whose leave amount hasn't been factored into accrued balance
							if(StringUtils.equals(lb.getRequestStatus(),LMConstants.REQUEST_STATUS.REQUESTED)) {
								//leave blocks move to requested from planned/usage upon submission of leave calendar document.
								if(lb.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER)) {
									// if the type is balance transfer, add only those for the accrual category.
									//BT created leave blocks don't get factored into the accrued balance until status is final/approved.
									// adjust accrued balance for "usage" leave amounts.
									if(lb.getDescription().contains("Forfeited balance transfer amount") ||
											lb.getDescription().contains("Transferred amount"))
										adjustment = adjustment.add(lb.getLeaveAmount());
								}
								// "Amount transferred" descriptions are credited/accrued leave amounts
								// on the "transfer to" accrual category of a balance transfer.
								// These are positive amounts, and can take the accrued balance on the credited accrual category
								// over their balance limit. We will allow the accrued balance on the credited accrual category to "go over"
								// when considering a requested leave block status.
								else {
									adjustment = adjustment.add(lb.getLeaveAmount());
								}
							}
						}
					}*/
					
					String accrualCategoryRuleId = lsr.getAccrualCategoryRuleId();
					AccrualCategoryRule rule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
					//Employee overrides...
					if(ObjectUtils.isNotNull(rule)) {
						if(StringUtils.equals(rule.getMaxBalFlag(),"Y")) {
							if(StringUtils.equals(rule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.TRANSFER)
									|| StringUtils.equals(rule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
								//There is a disagreement between the constant value LMConstants.MAX_BAL_ACTION_FREQ, and the value being
								//set on LM_ACCRUAL_CATEGORY_RULES_T table. Temporarily have changed the constant to reflect the field
								//value being set for MAX_BAL_ACTION_FREQ when accrual category rule records are saved.
								if(StringUtils.equals(rule.getMaxBalanceActionFrequency(),actionFrequency)) {
									BigDecimal maxBalance = rule.getMaxBalance();
		
									BigDecimal fullTimeEngagement = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(document.getPrincipalId(), TKUtils.getCurrentDate());
									BigDecimal adjustedMaxBalance = maxBalance.multiply(fullTimeEngagement);
									
									List<EmployeeOverride> overrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(document.getPrincipalId(), TKUtils.getCurrentDate());
									for(EmployeeOverride override : overrides) {
										if(StringUtils.equals(override.getAccrualCategory(),lsr.getAccrualCategoryId())) {
											if(StringUtils.equals(override.getOverrideType(),"MB"))
												adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
											//override values are not pro-rated.
										}
									}
									//should extend a BalanceTransferBase class, or use an algorithm swapping pattern.
									//allow institutions to extend/customize/implement their own max_bal_action_frequency types.
									if(StringUtils.equals(actionFrequency,LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
										//For year end transfer frequencies...
										PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(document.getPrincipalId(), TKUtils.getCurrentDate());
										LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(pha.getLeavePlan(),TKUtils.getCurrentDate());
										StringBuilder sb = new StringBuilder("");
										String calendarYearStart = lp.getCalendarYearStart();
										// mm/dd
										sb.append(calendarYearStart+"/");
										if(lp.getCalendarYearStartMonth().equals("01")) {
											//a calendar may start on 01/15, with monthly intervals.
											sb.append(DateUtils.toCalendar(DateUtils.addYears(document.getCalendarEntry().getBeginPeriodDate(),1)).get(Calendar.YEAR));
										}
										else
											sb.append(DateUtils.toCalendar(document.getCalendarEntry().getBeginPeriodDateTime()).get(Calendar.YEAR));
										//if the calendar being submitted is the final calendar in the leave plans calendar year.
										//must check the calendar year start month. If its the first month of the year, add a year to the date.
										//otherwise, the end period date and the calendar year start date have the same year.
										if(document.getCalendarEntry().getEndPeriodDate().equals(TKUtils.formatDateString(sb.toString()))) {
											//BigDecimal accruedBalanceLessPendingTransfers = lsr.getAccruedBalance().add(adjustment);
											if(lsr.getAccruedBalance().compareTo(adjustedMaxBalance) > 0 ) {
												eligibleAccrualCategories.add(rule.getLmAccrualCategoryRuleId());
											}
										}
										//otherwise its not transferable under year end frequency.
									}
									else {
										//BigDecimal accruedBalanceLessPendingTransfers = lsr.getAccruedBalance().add(adjustment);
										if(lsr.getAccruedBalance().compareTo(adjustedMaxBalance) > 0 ) {
											eligibleAccrualCategories.add(rule.getLmAccrualCategoryRuleId());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return eligibleAccrualCategories;
	}

	public BalanceTransferDao getBalanceTransferDao() {
		return balanceTransferDao;
	}
	
	public void setBalanceTransferDao(BalanceTransferDao balanceTransferDao) {
		this.balanceTransferDao = balanceTransferDao;
	}

	@Override
	public void submitToWorkflow(BalanceTransfer balanceTransfer)
			throws WorkflowException {
		
		balanceTransfer.setStatus(TkConstants.ROUTE_STATUS.ENROUTE);

		MaintenanceDocument document = KRADServiceLocatorWeb.getMaintenanceDocumentService().setupNewMaintenanceDocument(BalanceTransfer.class.getName(),
				"BalanceTransferDocumentType",KRADConstants.MAINTENANCE_NEW_ACTION);
		
		document.getDocumentHeader().setDocumentDescription("Accrual Triggered Transfer");
		Map<String,String[]> params = new HashMap<String,String[]>();
		
		KRADServiceLocatorWeb.getMaintenanceDocumentService().setupMaintenanceObject(document, KRADConstants.MAINTENANCE_NEW_ACTION, params);
		BalanceTransfer btObj = (BalanceTransfer) document.getNewMaintainableObject().getDataObject();
		
		btObj.setAccrualCategoryRule(balanceTransfer.getAccrualCategoryRule());
		btObj.setEffectiveDate(balanceTransfer.getEffectiveDate());
		btObj.setForfeitedAmount(balanceTransfer.getForfeitedAmount());
		btObj.setFromAccrualCategory(balanceTransfer.getFromAccrualCategory());
		btObj.setLeaveCalendarDocumentId(balanceTransfer.getLeaveCalendarDocumentId());
		btObj.setPrincipalId(balanceTransfer.getPrincipalId());
		btObj.setToAccrualCategory(balanceTransfer.getToAccrualCategory());
		btObj.setTransferAmount(balanceTransfer.getTransferAmount());
		
		document.getNewMaintainableObject().setDataObject(btObj);
		KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
		document.getDocumentHeader().getWorkflowDocument().saveDocument("");

		document.getDocumentHeader().getWorkflowDocument().route("");

		
	}
}
