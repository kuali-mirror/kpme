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
package org.kuali.kpme.tklm.leave.payout.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TKUtils;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.kpme.tklm.leave.payout.dao.LeavePayoutDao;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.util.LMConstants;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutServiceImpl implements LeavePayoutService {

    private LeavePayoutDao leavePayoutDao;

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalId(
            String principalId) {
        return leavePayoutDao.getAllLeavePayoutsForPrincipalId(principalId);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalIdAsOfDate(
            String principalId, LocalDate effectiveDate) {
        return leavePayoutDao.getAllLeavePayoutsForPrincipalIdAsOfDate(principalId,effectiveDate);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsByEffectiveDate(
    		LocalDate effectiveDate) {
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
			LocalDate effectiveDate) {
		//Initially, principals may be allowed to edit the transfer amount when prompted to submit this balance transfer, however,
		//a base transfer amount together with a forfeited amount is calculated to bring the balance back to its limit in accordance
		//with transfer limits.
		LeavePayout leavePayout = null;
		AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRule);

		if(ObjectUtils.isNotNull(accrualRule) && ObjectUtils.isNotNull(accruedBalance)) {
			leavePayout = new LeavePayout();
			//Leave summary is not a requirement, per se, but the information it contains is.
			//The only thing that is obtained from leave summary is the accrued balance of the leave summary row matching the
			//passed accrualCategoryRules accrual category.
			//These two objects are essential to balance transfers triggered when the employee submits their leave calendar for approval.
			//Neither of these objects should be null, otherwise this method could not have been called.
			AccrualCategory fromAccrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
			BigDecimal fullTimeEngagement = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
			
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
			
			EmployeeOverride maxBalanceOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, fromAccrualCategory.getLeavePlan(), fromAccrualCategory.getAccrualCategory(), "MB", effectiveDate);
			EmployeeOverride maxPayoutAmountOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, fromAccrualCategory.getLeavePlan(), fromAccrualCategory.getAccrualCategory(), "MPA", effectiveDate);
			EmployeeOverride maxAnnualCarryOverOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, fromAccrualCategory.getLeavePlan(), fromAccrualCategory.getAccrualCategory(), "MAC", effectiveDate);
			//Do not pro-rate override values for FTE.
			if(maxBalanceOverride != null)
				adjustedMaxBalance = new BigDecimal(maxBalanceOverride.getOverrideValue());
			if(maxPayoutAmountOverride != null)
				adjustedMaxPayoutAmount = new BigDecimal(maxPayoutAmountOverride.getOverrideValue());
			if(maxAnnualCarryOverOverride != null)
				adjustedMaxCarryOver = new BigDecimal(maxAnnualCarryOverOverride.getOverrideValue());
			
			
			BigDecimal transferAmount = accruedBalance.subtract(adjustedMaxBalance);
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
				
			assert(adjustedMaxBalance.compareTo(accruedBalance.subtract(leavePayout.getPayoutAmount().add(leavePayout.getForfeitedAmount()))) == 0);
			
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
	
							//Can this entire amount be added to the transfer amount??
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
								
								assert(adjustedMaxCarryOver.compareTo(accruedBalance.subtract(leavePayout.getPayoutAmount().add(leavePayout.getForfeitedAmount()))) == 0);
							}
						}
					}
					//otherwise, given balance will be at or under the max annual carry over.
				}
			}
			
			leavePayout.setEffectiveLocalDate(effectiveDate);
			leavePayout.setAccrualCategoryRule(accrualCategoryRule);
			leavePayout.setFromAccrualCategory(fromAccrualCategory.getAccrualCategory());
			leavePayout.setPrincipalId(principalId);
			leavePayout.setEarnCode(accrualRule.getMaxPayoutEarnCode());

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
					aLeaveBlock.setTransactionDocId(leavePayout.getDocumentHeaderId());
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
			        LmServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
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
					aLeaveBlock.setTransactionDocId(leavePayout.getDocumentHeaderId());
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
			        LmServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					
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
					aLeaveBlock.setTransactionDocId(leavePayout.getDocumentHeaderId());
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
			        LmServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
					
					leaveBlocks.add(aLeaveBlock);
				}
			}

			return leavePayout;
		}
	}

	@Override
	public void submitToWorkflow(LeavePayout leavePayout)
			throws WorkflowException {
		//leavePayout.setStatus(HrConstants.ROUTE_STATUS.ENROUTE);
        EntityNamePrincipalName principalName = null;
        if (leavePayout.getPrincipalId() != null) {
            principalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(leavePayout.getPrincipalId());
        }

		MaintenanceDocument document = KRADServiceLocatorWeb.getMaintenanceDocumentService().setupNewMaintenanceDocument(LeavePayout.class.getName(),
				"LeavePayoutDocumentType",KRADConstants.MAINTENANCE_NEW_ACTION);

        String personName = (principalName != null  && principalName.getDefaultName() != null) ? principalName.getDefaultName().getCompositeName() : StringUtils.EMPTY;
        String date = TKUtils.formatDate(leavePayout.getEffectiveLocalDate());
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
		lpObj.setDocumentHeaderId(document.getDocumentHeader().getWorkflowDocument().getDocumentId());
		
		document.getNewMaintainableObject().setDataObject(lpObj);
		KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
		document.getDocumentHeader().getWorkflowDocument().saveDocument("");

		document.getDocumentHeader().getWorkflowDocument().route("");
	}

	@Override
	public List<LeavePayout> getLeavePayouts(String viewPrincipal,
			LocalDate beginPeriodDate, LocalDate endPeriodDate) {
		// TODO Auto-generated method stub
		return leavePayoutDao.getLeavePayouts(viewPrincipal, beginPeriodDate, endPeriodDate);
	}

	@Override
	public void saveOrUpdate(LeavePayout payout) {
		leavePayoutDao.saveOrUpdate(payout);
	}

	@Override
	public List<LeavePayout> getLeavePayouts(String principalId, String fromAccrualCategory, String payoutAmount, String earnCode, String forfeitedAmount, LocalDate fromEffdt, LocalDate toEffdt) {
		return leavePayoutDao.getLeavePayouts(principalId, fromAccrualCategory, payoutAmount, earnCode, forfeitedAmount, fromEffdt, toEffdt);
	}
}
