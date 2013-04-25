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
package org.kuali.hr.tklm.leave.transfer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.block.LeaveBlockHistory;
import org.kuali.hr.tklm.leave.override.EmployeeOverride;
import org.kuali.hr.tklm.leave.transfer.BalanceTransfer;
import org.kuali.hr.tklm.leave.transfer.dao.BalanceTransferDao;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
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
			String principalId, LocalDate effectiveDate) {
		return balanceTransferDao.getAllBalanceTransferForPrincipalIdAsOfDate(principalId,effectiveDate);
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(
			LocalDate effectiveDate) {
		return balanceTransferDao.getAllBalanceTransferByEffectiveDate(effectiveDate);
	}

	@Override
	public BalanceTransfer getBalanceTransferById(String balanceTransferId) {
		return balanceTransferDao.getBalanceTransferById(balanceTransferId);
	}
	
	@Override
	public BalanceTransfer initializeTransfer(String principalId, String accrualCategoryRule, BigDecimal accruedBalance, LocalDate effectiveDate) {
		//Initially, principals may be allowed to edit the transfer amount when prompted to submit this balance transfer, however,
		//a base transfer amount together with a forfeited amount is calculated to bring the balance back to its limit in accordance
		//with transfer limits. This "default" transfer object is used to adjust forfeiture when the user changes the transfer amount.
		BalanceTransfer bt = null;
		AccrualCategoryRule accrualRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRule);

		if(ObjectUtils.isNotNull(accrualRule) && ObjectUtils.isNotNull(accruedBalance)) {
			bt = new BalanceTransfer();
			//These two objects are essential to balance transfers triggered when the employee submits their leave calendar for approval.
			//Neither of these objects should be null, otherwise this method could not have been called.
			AccrualCategory fromAccrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getLmAccrualCategoryId());
			AccrualCategory toAccrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getMaxBalanceTransferToAccrualCategory(),effectiveDate);
			BigDecimal fullTimeEngagement = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
			
			BigDecimal transferConversionFactor = null;
			if(ObjectUtils.isNotNull(accrualRule.getMaxBalanceTransferConversionFactor()))
				transferConversionFactor = accrualRule.getMaxBalanceTransferConversionFactor();
			
			// AccrualRule.maxBalance == null -> no balance limit. No balance limit -> no accrual triggered transfer / payout / lose.
			// execution point should not be here if max balance on accrualRule is null, unless there exists an employee override.
			BigDecimal maxBalance = accrualRule.getMaxBalance();
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
					//Do not pro-rate override values for FTE.
					if(StringUtils.equals(override.getOverrideType(),"MB"))
						adjustedMaxBalance = new BigDecimal(override.getOverrideValue());
					if(StringUtils.equals(override.getOverrideType(),"MTA"))
						adjustedMaxTransferAmount = new BigDecimal(override.getOverrideValue());
					if(StringUtils.equals(override.getOverrideType(),"MAC"))
						adjustedMaxCarryOver = new BigDecimal(override.getOverrideValue());
				}
			}
			
			BigDecimal transferAmount = accruedBalance.subtract(adjustedMaxBalance);
			if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
				//Move all time in excess of employee's fte adjusted max balance to forfeiture.
				bt.setForfeitedAmount(transferAmount);
				//There is no transfer to another accrual category.
				bt.setTransferAmount(BigDecimal.ZERO);
				bt.setAmountTransferred(BigDecimal.ZERO);
				// to accrual category is a required field on maintenance document. Set as from accrual category
				// to remove validation errors when routing, approving, etc.
				bt.setToAccrualCategory(fromAccrualCategory.getAccrualCategory());
			}
			else {
				// ACTION_AT_MAX_BAL = TRANSFER
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
			
			assert(adjustedMaxBalance.compareTo(accruedBalance.subtract(bt.getTransferAmount().add(bt.getForfeitedAmount()))) == 0);

			// Max Carry Over logic for Year End transfers.
			if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {

				//At this point, transfer amount and forfeiture have been set so that the new accrued balance will be the
				//adjusted max balance, so this amount is used to check against carry over.
				if(adjustedMaxBalance.compareTo(adjustedMaxCarryOver) > 0) {
					BigDecimal carryOverDiff = adjustedMaxBalance.subtract(adjustedMaxCarryOver);
					
					if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)){
						//add carry over excess to forfeiture.
						bt.setForfeitedAmount(bt.getForfeitedAmount().add(carryOverDiff));
					}
					else {
						//maximize the transfer amount.
						BigDecimal potentialTransferAmount = bt.getTransferAmount().add(carryOverDiff);

						//Can this amount be added to the transfer amount without exceeding adjusted max transfer amount??
						if(potentialTransferAmount.compareTo(adjustedMaxTransferAmount) <= 0) {
							//yes
							bt.setTransferAmount(bt.getTransferAmount().add(carryOverDiff));
						}
						else {
							//no
							BigDecimal carryOverExcess = potentialTransferAmount.subtract(adjustedMaxTransferAmount);
							//move excess to forfeiture
							bt.setForfeitedAmount(bt.getForfeitedAmount().add(carryOverExcess));
							//the remainder (if any) can be added to the transfer amount.
							bt.setTransferAmount(bt.getTransferAmount().add(carryOverDiff.subtract(carryOverExcess)));
							
							assert(bt.getTransferAmount().compareTo(adjustedMaxTransferAmount)==0);
							// assert that the new balance will be equal to the adjusted max carry over < adjusted max balance.
						}
					}
					assert(adjustedMaxCarryOver.compareTo(accruedBalance.subtract(bt.getTransferAmount().add(bt.getForfeitedAmount()))) == 0);
				}
				//otherwise, given balance will be at or under the max annual carry over.
			}
			
			bt.setEffectiveLocalDate(effectiveDate);
			bt.setAccrualCategoryRule(accrualCategoryRule);
			bt.setFromAccrualCategory(fromAccrualCategory.getAccrualCategory());
			bt.setPrincipalId(principalId);
			if(ObjectUtils.isNotNull(transferConversionFactor))
				bt.setAmountTransferred(bt.getTransferAmount().multiply(transferConversionFactor).setScale(2));
			else
				bt.setAmountTransferred(bt.getTransferAmount());
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

			if(ObjectUtils.isNotNull(balanceTransfer.getAmountTransferred())) {
				if(balanceTransfer.getAmountTransferred().compareTo(BigDecimal.ZERO) > 0 ) {

					aLeaveBlock = new LeaveBlock();
					//Create a leave block that adds the adjusted transfer amount to the "transfer to" accrual category.
					aLeaveBlock.setPrincipalId(balanceTransfer.getPrincipalId());
					aLeaveBlock.setLeaveDate(balanceTransfer.getEffectiveDate());
					aLeaveBlock.setEarnCode(balanceTransfer.getCreditedAccrualCategory().getEarnCode());
					aLeaveBlock.setAccrualCategory(balanceTransfer.getToAccrualCategory());
					aLeaveBlock.setDescription("Amount transferred");
					aLeaveBlock.setLeaveAmount(balanceTransfer.getAmountTransferred());
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setTransactionDocId(balanceTransfer.getDocumentHeaderId());
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
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
				}
			}

			if(ObjectUtils.isNotNull(transferAmount)) {
				if(transferAmount.compareTo(BigDecimal.ZERO) > 0) {					
					//Create leave block that removes the correct transfer amount from the originating accrual category.
					aLeaveBlock = new LeaveBlock();
					aLeaveBlock.setPrincipalId(balanceTransfer.getPrincipalId());
					aLeaveBlock.setLeaveDate(balanceTransfer.getEffectiveDate());
					aLeaveBlock.setEarnCode(balanceTransfer.getDebitedAccrualCategory().getEarnCode());
					aLeaveBlock.setAccrualCategory(balanceTransfer.getFromAccrualCategory());
					aLeaveBlock.setDescription("Transferred amount");
					aLeaveBlock.setLeaveAmount(balanceTransfer.getTransferAmount().negate());
					aLeaveBlock.setAccrualGenerated(true);
					aLeaveBlock.setTransactionDocId(balanceTransfer.getDocumentHeaderId());
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
					aLeaveBlock.setBlockId(0L);

					//Want to store the newly created leave block id on this maintainable object.
					//when the status of the maintenance document encapsulating this maintainable changes
					//the id will be used to fetch and update the leave block statuses.
					aLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(aLeaveBlock);

					balanceTransfer.setDebitedLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());
					// save history
					LeaveBlockHistory lbh = new LeaveBlockHistory(aLeaveBlock);
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
					aLeaveBlock.setTransactionDocId(balanceTransfer.getDocumentHeaderId());
					aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
					aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
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
	
	public BalanceTransferDao getBalanceTransferDao() {
		return balanceTransferDao;
	}
	
	public void setBalanceTransferDao(BalanceTransferDao balanceTransferDao) {
		this.balanceTransferDao = balanceTransferDao;
	}

	@Override
	public void submitToWorkflow(BalanceTransfer balanceTransfer)
			throws WorkflowException {
		
		//balanceTransfer.setStatus(TkConstants.ROUTE_STATUS.ENROUTE);
        EntityNamePrincipalName principalName = null;
        if (balanceTransfer.getPrincipalId() != null) {
            principalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(balanceTransfer.getPrincipalId());
        }

		MaintenanceDocument document = KRADServiceLocatorWeb.getMaintenanceDocumentService().setupNewMaintenanceDocument(BalanceTransfer.class.getName(),
				"BalanceTransferDocumentType",KRADConstants.MAINTENANCE_NEW_ACTION);

        String personName = (principalName != null  && principalName.getDefaultName() != null) ? principalName.getDefaultName().getCompositeName() : StringUtils.EMPTY;
        String date = TKUtils.formatDate(balanceTransfer.getEffectiveLocalDate());
        document.getDocumentHeader().setDocumentDescription(personName + " (" + balanceTransfer.getPrincipalId() + ")  - " + date);
		Map<String,String[]> params = new HashMap<String,String[]>();
		
		KRADServiceLocatorWeb.getMaintenanceDocumentService().setupMaintenanceObject(document, KRADConstants.MAINTENANCE_NEW_ACTION, params);
		BalanceTransfer btObj = (BalanceTransfer) document.getNewMaintainableObject().getDataObject();
		
		btObj.setAccrualCategoryRule(balanceTransfer.getAccrualCategoryRule());
		btObj.setEffectiveDate(balanceTransfer.getEffectiveDate());
		btObj.setForfeitedAmount(balanceTransfer.getForfeitedAmount());
		btObj.setFromAccrualCategory(balanceTransfer.getFromAccrualCategory());
		btObj.setPrincipalId(balanceTransfer.getPrincipalId());
		btObj.setToAccrualCategory(balanceTransfer.getToAccrualCategory());
		btObj.setTransferAmount(balanceTransfer.getTransferAmount());
		btObj.setAmountTransferred(balanceTransfer.getAmountTransferred());
		btObj.setSstoId(balanceTransfer.getSstoId());
		btObj.setDocumentHeaderId(document.getDocumentHeader().getWorkflowDocument().getDocumentId());
/*        TkServiceLocator.getBalanceTransferService().saveOrUpdate(btObj);
		document.getNewMaintainableObject().setDataObject(btObj);*/
		KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
		document.getDocumentHeader().getWorkflowDocument().saveDocument("");

		document.getDocumentHeader().getWorkflowDocument().route("");
		
	}
	
	@Override
	public BalanceTransfer transferSsto(BalanceTransfer balanceTransfer) {
		if(ObjectUtils.isNull(balanceTransfer))
			throw new RuntimeException("did not supply a valid BalanceTransfer object.");
		else {
			List<LeaveBlock> sstoLbList = TkServiceLocator.getLeaveBlockService().getSSTOLeaveBlocks(balanceTransfer.getPrincipalId(), balanceTransfer.getSstoId(), balanceTransfer.getEffectiveLocalDate());
			String leaveDocId = "";
			if(CollectionUtils.isNotEmpty(sstoLbList)) {
				leaveDocId = sstoLbList.get(0).getDocumentId();
			}
			List<LeaveBlock> lbList = new ArrayList<LeaveBlock>();
			// create a new leave block with transferred amount, make sure system scheduled timeoff id is added to it
			LeaveBlock aLeaveBlock = new LeaveBlock();
			aLeaveBlock.setPrincipalId(balanceTransfer.getPrincipalId());
			aLeaveBlock.setLeaveDate(balanceTransfer.getEffectiveDate());
			aLeaveBlock.setEarnCode(balanceTransfer.getCreditedAccrualCategory().getEarnCode());
			aLeaveBlock.setAccrualCategory(balanceTransfer.getToAccrualCategory());
			aLeaveBlock.setDescription("System Scheduled Time off Amount transferred");
			aLeaveBlock.setLeaveAmount(balanceTransfer.getAmountTransferred());
			aLeaveBlock.setAccrualGenerated(false);
			aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
			aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
			aLeaveBlock.setBlockId(0L);
			aLeaveBlock.setScheduleTimeOffId(balanceTransfer.getSstoId());
			aLeaveBlock.setDocumentId(leaveDocId);
			
			lbList.add(aLeaveBlock);
			TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(lbList);

	    	balanceTransfer.setAccruedLeaveBlockId(aLeaveBlock.getLmLeaveBlockId());	
			return balanceTransfer;
		}
	}

	@Override
	public List<BalanceTransfer> getBalanceTransfers(String viewPrincipal,
			LocalDate beginPeriodDate, LocalDate endPeriodDate) {
		// TODO Auto-generated method stub
		return balanceTransferDao.getBalanceTransfers(viewPrincipal, beginPeriodDate, endPeriodDate);
	}

	@Override
	public void saveOrUpdate(BalanceTransfer balanceTransfer) {
		// TODO Auto-generated method stub
		balanceTransferDao.saveOrUpdate(balanceTransfer);
	}
	
    public List<BalanceTransfer> getBalanceTransfers(String principalId, String fromAccrualCategory, String transferAmount, String toAccrualCategory, String amountTransferred, String forfeitedAmount, LocalDate fromEffdt, LocalDate toEffdt) {
    	return balanceTransferDao.getBalanceTransfers(principalId, fromAccrualCategory, transferAmount, toAccrualCategory, amountTransferred, forfeitedAmount, fromEffdt, toEffdt);
    }

}
