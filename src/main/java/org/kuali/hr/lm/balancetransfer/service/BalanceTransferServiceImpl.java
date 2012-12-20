/**
 * Copyright 2004-2012 The Kuali Foundation
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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.balancetransfer.dao.BalanceTransferDao;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferServiceImpl implements BalanceTransferService {

	private BalanceTransferDao balanceTransferDao;
	
	@Override
	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(
			String principalId) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return balanceTransferDao.getAllBalanceTransferByEffectiveDate(effectiveDate);
	}

	@Override
	public BalanceTransfer getBalanceTransferById(String balanceTransferId) {
		// TODO Auto-generated method stub
		return balanceTransferDao.getBalanceTransferById(balanceTransferId);
	}

	public BalanceTransferDao getBalanceTransferDao() {
		return balanceTransferDao;
	}
	
	public void setBalanceTransferDao(BalanceTransferDao balanceTransferDao) {
		this.balanceTransferDao = balanceTransferDao;
	}


	@Override
	public BalanceTransfer initiateBalanceTransferOnDemand(String principalId, LeaveSummaryRow leaveSummaryRow, String accrualCategoryRule) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * change to "get/initiateAccrualGeneratedBalanceTransfer".
	 * Updated method will provide a common endpoint for all max balance action frequencies triggered by
	 * accrual categories that have exceeded max balance.
	 * with action_at_max_bal=LOSE, all service-unit-time over max balance must be moved to forfeiture
	 */
	@Override
	public BalanceTransfer getTransferOnLeaveApprove(String principalId, String accrualCategoryRule, LeaveSummary leaveSummary) {
		//Initially, principals may be allowed to edit the transfer amount when prompted to submit this balance transfer, however,
		//a base transfer amount together with a forfeited amount is calculated to bring the balance back to its limit.
		//If the principal reduces the transfer amount, and the amount is not at max transfer limit, they must either be notified
		//the difference will be forfeited, or forced to re-enter the transfer amount.
		BalanceTransfer bt = null;
		AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRule);
		/**
		 * Balance transfers triggered by an accrual category rule require the following elements:
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
			AccrualCategory toAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualRule.getMaxBalanceTransferToAccrualCategory(),TKUtils.getCurrentDate());
			LeaveSummaryRow balanceInformation = leaveSummary.getLeaveSummaryRowForAccrualCategory(accrualRule.getLmAccrualCategoryId());
			//if leave summary row for accrual category under transfer could not be retrieved, there's a problem.
			//The same can be said about accrualRule fields. Should add null check conditionals to allow reuse.
			//instantiate a transfer amount that, when subtracted from the current balance, would bring the balance of the accrual
			//category back to it's maximum defined limit.
			BigDecimal transferAmount = balanceInformation.getAccruedBalance().subtract(accrualRule.getMaxBalance());
			if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
				//The only condition in which this if block would be executed is if
				//a transfer is initiated by a dept/sys admin in, say, the case of an employee transferring
				//to a new position with accrual category balances in-tact.
				//Dept/Sys admins must manually enter information in a form provided by a link in the maintenance tab.
				
				bt.setForfeitedAmount(transferAmount);
				transferAmount = BigDecimal.ZERO;
				
			}
			else {
				BigDecimal maxTransferAmount = new BigDecimal(accrualRule.getMaxTransferAmount());
				if(transferAmount.compareTo(maxTransferAmount) > 0) {
					//there's forfeiture
					BigDecimal forfeiture = (balanceInformation.getAccruedBalance().subtract(maxTransferAmount)).subtract(accrualRule.getMaxBalance());
					bt.setForfeitedAmount(forfeiture);
					//forfeiture, together with the maximum transfer amount, will bring the balance back to its
					//maximum limit.
					bt.setTransferAmount(maxTransferAmount);
				}
				else {
					bt.setTransferAmount(transferAmount);
				}
			}
			//transfers triggered by accrual category rules are effective now.
			bt.setEffectiveDate(TKUtils.getCurrentDate());
			bt.setAccrualCategoryRule(accrualCategoryRule);
			bt.setFromAccrualCategory(fromAccrualCategory.getAccrualCategory());
			bt.setPrincipalId(principalId);
			bt.setToAccrualCategory(toAccrualCategory.getAccrualCategory());
		}
		return bt;
	}

	@Override
	public BalanceTransfer initiateBalanceTransferOnYearEnd(String principalId,
			BigDecimal transferAmount, AccrualCategory fromAcc,
			AccrualCategory toAcc) {
		//This stub is being provided for the use case of housing carry over from one calendar year to the next
		//It seems unlikely that an institution would allow a maximum balance to be exceeded
		//until the final period in the principals calendar, except for perhaps principals who are
		//payed on a one time annual basis. This would be the other use case for "Year-End" frequency transfers.
		BalanceTransfer bt = new BalanceTransfer();
		
		bt.setTransferAmount(transferAmount);
		bt.setEffectiveDate(TKUtils.getCurrentDate());
		bt.setFromAccrualCategory(fromAcc.getAccrualCategory());
		bt.setToAccrualCategory(toAcc.getAccrualCategory());
		//bt.setDebitedAccrualCategory(fromAcc);
		//bt.setCreditedAccrualCategory(toAcc);
		//must check max carry over.
		
		return KRADServiceLocator.getBusinessObjectService().save(bt);
	}

	@Override
	public void transfer(BalanceTransfer balanceTransfer) {
		// TODO Auto-generated method stub
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();

		BigDecimal transferAmount = balanceTransfer.getTransferAmount();
		LeaveBlock aLeaveBlock = null;
		if(ObjectUtils.isNotNull(transferAmount) && transferAmount.compareTo(BigDecimal.ZERO) > 0) {

			AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(balanceTransfer.getAccrualCategoryRule());
			//Transfers initiated by department and system admins may not have associated balance transfer rules.
			BigDecimal transferConversionFactor = null;
			if(ObjectUtils.isNotNull(aRule)) {
				transferConversionFactor = aRule.getMaxBalanceTransferConversionFactor();
				if(ObjectUtils.isNull(transferConversionFactor))
					transferConversionFactor = BigDecimal.ONE;
			}

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
			aLeaveBlock.setBlockId(0L);
			
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
			aLeaveBlock.setBlockId(0L);
			
			leaveBlocks.add(aLeaveBlock);
		}
		
		BigDecimal forfeitedAmount = balanceTransfer.getForfeitedAmount();
		//Any amount forfeited must come out of the originating accrual category in order to bring balance back to max.
		if(ObjectUtils.isNotNull(forfeitedAmount) && forfeitedAmount.compareTo(BigDecimal.ZERO) > 0) {
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
			aLeaveBlock.setBlockId(0L);
			
			leaveBlocks.add(aLeaveBlock);
		}
		
		TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(leaveBlocks);
	}
//	@Override
//	public boolean balanceTransferQualificationMet(LeaveCalendarDocument document) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	@Override
	public List<String> getAccrualCategoryRuleIdsForEligibleTransfers(LeaveCalendarDocument document, String actionFrequency) throws Exception {
		List<String> eligibleAccrualCategories = new ArrayList<String>();
		CalendarEntries calendarEntries = document.getCalendarEntry();
		String principalId = document.getPrincipalId();
		//Employee override check here, or return base-eligible accrual categories,
		//filtering out those that have increased balance limits due to employee override in caller?
		//TODO: Create max bal action freq map to check @param actionFrequency
		LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(document.getPrincipalId(),document.getCalendarEntry());

		//A better alternative to placing the id of the accrual category in the request/response string
		//would be to use the id of the accrual category rule that's triggering the transfer.
		//to/from accrual categories as well as balance/transfer limits can be retrieved with reduced number of calls.
		for(LeaveSummaryRow lsr : leaveSummary.getLeaveSummaryRows()) {
			/*			
			if(lsr.isTransferable()) {
				//this conditional could replace the branches below, with the addition of
				//frequency testing, given the conditionals up to the max balance action frequency
				//are used to set the field value on the leave summary row.
				//this field value is currently used in tags to display/hide the transfer button for
				//on-demand max balance action frequency.
			}
			*/
			String accrualCategoryRuleId = lsr.getAccrualCategoryRuleId();
			AccrualCategoryRule rule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
			//Employee overrides...
			if(ObjectUtils.isNotNull(rule)) {
				if(StringUtils.equals(rule.getMaxBalFlag(),"Y")) {
					if(StringUtils.equals(rule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.TRANSFER)) {
						//There is a disagreement between the constant value LMConstants.MAX_BAL_ACTION_FREQ, and the value being
						//set on LM_ACCRUAL_CATEGORY_RULES_T table. Temporarily have changed the constant to reflect the field
						//value being set for MAX_BAL_ACTION_FREQ when accrual category rule records are saved.
						if(StringUtils.equals(rule.getMaxBalanceActionFrequency(),actionFrequency)) {
							BigDecimal maxBalance = rule.getMaxBalance();
							if(lsr.getAccruedBalance().compareTo(maxBalance) > 0 ) {
								eligibleAccrualCategories.add(rule.getLmAccrualCategoryRuleId());
							}
						}
					}
				}
			}
		}
		return eligibleAccrualCategories;
	}

}
