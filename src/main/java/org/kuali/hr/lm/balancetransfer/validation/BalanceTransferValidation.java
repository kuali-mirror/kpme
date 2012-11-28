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
package org.kuali.hr.lm.balancetransfer.validation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

/**
 * KPME-1527: When saving, evaluate amounts recorded for the accrual category against max carry over.
 * when comparing this amount against max carry over, max carry over must be
 * multiplied by entire full time engagement for all leave eligible jobs.
 */
public class BalanceTransferValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean isValid = true;
		LOG.debug("entering custom validation for Balance Transfer");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if(pbo instanceof BalanceTransfer) {
			
			BalanceTransfer balanceTransfer = (BalanceTransfer) pbo;

			if(isValid) {

				/**
				 * Validation is basically governed by accrual category rules. Get accrual category
				 * rules for both the "To" and "From" accrual categories, pass to validators along with the
				 * values needing to be validated.
				 * 
				 * Balance transfers initiated from the leave calendar display should already have all values
				 * populated, thus validated, including the accrual category rule for the "From" accrual category.
				 * 
				 * Balance transfers initiated via the Maintenance tab will have no values populated.
				 */
				
				isValid &= validateEffectiveDate(balanceTransfer); // Institutionally dependent policy
				isValid &= validateTransferFromAccrualCategory(balanceTransfer); // Global rule.
				isValid &= validateTransferToAccrualCategory(balanceTransfer);
				isValid &= validateTransferAmount(balanceTransfer);
				isValid &= validatePrincipal(balanceTransfer);

			}
		}
		return isValid; 
	}
	
	//Employee Overrides???

	/**
	 * Are there any rules in place for effective date? i.e. not more than one year in advance...
	 * @param balanceTransfer
	 * @return
	 */
	private boolean validateEffectiveDate(BalanceTransfer balanceTransfer) {
		//Limit on future dates?
		return true;
	}
	
	/**
	 * Transfer amount must be validated against several variables, including max transfer amount,
	 * max carry over ( for the converted amount deposited into the "to" accrual category, max usage
	 * ( if transfers count as usage ).
	 * @param balanceTransfer
	 * @return
	 */
	private boolean validateTransferAmount(BalanceTransfer balanceTransfer) {
		//transfer amount should be less than or equal to the maximumm allowed transfer amount
		boolean isValid = true;
		isValid &= isTransferAmountUnderMaxLimit(balanceTransfer);
		isValid &= isMaxCarryOverConditionMet(balanceTransfer);
		return isValid;
	}
	
	/**
	 * Is the "From" accrual category required to be over its maximum balance before a transfer can take place?
	 * The "From" accrual category must be defined in an accrual category rule as having a max bal rule.
	 * @param balanceTransfer
	 * @return
	 */
	private boolean validateTransferFromAccrualCategory(BalanceTransfer balanceTransfer) {
		//accrual category should have a valid rule for use with max balance transfers
		return true;
	}
	
	/**
	 * The "To" accrual category must be one for which the applicable accrual category rule specifies
	 * as the accrual category marked for transfer
	 * @param balanceTransfer
	 * @return
	 */
	private boolean validateTransferToAccrualCategory(BalanceTransfer balanceTransfer) {
		//to accrual category should be allowed to accept transfers
		return true;
	}

	private boolean validatePrincipal(BalanceTransfer balanceTransfer) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Validate transfer amount requested is less than adjusted max carryover for all
	 * LM eligible jobs. Adjusted max carryover = Sum(FTE_leaveEligibleJobs) x accrual category's max carryover rule.
	 * 
	 * Document should be in such a state as to contain the referenced properties.
	 * 
	 * @param document
	 * @return
	 */
	private boolean isMaxCarryOverConditionMet(BalanceTransfer btd) {
		String principalId = btd.getPrincipalId();
		Date date = btd.getEffectiveDate();
		BigDecimal transferAmount = btd.getTransferAmount();
		String toAccrualCategory = btd.getToAccrualCategory();
		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, date);
		Date serviceDate = pha.getServiceDate();
		AccrualCategory debitedAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory,date);
		
		//need to set this elsewhere. This is also using the "to" accrual category to set the accrual category rule
		//same statement can be found in the validation of max transfer limit. Could use that or put it in its own method
		//prior to running all of the validations.
		btd.setAccrualCategoryRule(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(debitedAccrualCategory, TKUtils.getCurrentDate(), serviceDate).getLmAccrualCategoryRuleId());

		//Accrual Category rule can only be defined on a balance transfer prior to saving if initiated via the leave calendar display.
		//For payout on termination/retirement, no rule will yet be defined.
		AccrualCategoryRule acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(btd.getAccrualCategoryRule());
		if(ObjectUtils.isNotNull(acr.getMaxCarryOver())) {
			//TkServiceLocator.getAccrualCategoryService().getCurrentCarryOverTotalForPrincipalId(principalId,btd.getFromAccrualCategory());
			//BigDecimal currentCarryOver = TkServiceLocator.getCalendarEntriesService().
			BigDecimal maxCarryOver = new BigDecimal(acr.getMaxCarryOver());
			BigDecimal fteSum = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, date);
			BigDecimal adjustedTransferAmount = acr.getMaxBalanceTransferConversionFactor().multiply(transferAmount);
			/*
			 * TODO: Sum adjustedTransferAmount with credited accrual category's current
			 * carry over amount, use result to compare with max carryover percentage.
			 *
			 * replace the augden with the carry over for the current pay calendar.
			 */
			//If transfer would take the credited accrual category over its max carry over limit, fail unless
			//there is an employee override in place.
			if(adjustedTransferAmount.add(BigDecimal.ZERO).compareTo(fteSum.multiply(maxCarryOver)) > 0) {
				GlobalVariables.getMessageMap().putError("document.transferAmount", "balanceTransfer.exceeds.maxCarryOver");
				return false;
			}
		}
		
		return true;
	}
	

	private boolean isTransferAmountUnderMaxLimit(
			BalanceTransfer balanceTransfer) {
		BigDecimal transferAmount = balanceTransfer.getTransferAmount();
	
		//Accrual Category rule can only be defined on a balance transfer prior to saving if initiated via the leave calendar display.
		//For payout on termination/retirement, no rule will yet be defined.
		
		AccrualCategoryRule acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(balanceTransfer.getAccrualCategoryRule());
		
		if(ObjectUtils.isNotNull(acr)) {
			//TkServiceLocator.getAccrualCategoryService().getCurrentCarryOverTotalForPrincipalId(principalId,btd.getFromAccrualCategory());
			//BigDecimal currentCarryOver = TkServiceLocator.getCalendarEntriesService().
			BigDecimal maxTransferAmount = null;
			if(ObjectUtils.isNotNull(acr.getMaxTransferAmount())) {
				maxTransferAmount = new BigDecimal(acr.getMaxTransferAmount());
			}
			//Can't transfer more than the maximum amount defined by the accrual category rule.
			//unless there is an employee override in effect.
			if(ObjectUtils.isNotNull(maxTransferAmount)) {
				if(transferAmount.compareTo(maxTransferAmount) > 0) {
					GlobalVariables.getMessageMap().putError("document.transferAmount","balanceTransfer.exceeds.transferLimit");
					return false;
				}
			}
		}
		return true;
	}	

}
