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
		LOG.debug("entering custom validation for Leave Accrual");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if(pbo instanceof BalanceTransfer) {
			BalanceTransfer balanceTransfer = (BalanceTransfer) pbo;
			AccrualCategory toAccrualCategory = balanceTransfer.getCreditedAccrualCategory();
			BigDecimal transferAmount = balanceTransfer.getTransferAmount();
			EarnCode payoutEarnCode = balanceTransfer.getPayoutEarnCode();
			AccrualCategory fromAccrualCategory = balanceTransfer.getDebitedAccrualCategory();
			
			if(isValid) {
				isValid = isMaxCarryOverConditionMet(balanceTransfer);
				isValid = isMaxPayoutConditionMet(balanceTransfer);
				isValid = isTransferAmountUnderMaxLimit(balanceTransfer);
			}
		}
		return isValid; 
	}

	private boolean isTransferAmountUnderMaxLimit(
			BalanceTransfer balanceTransfer) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean isMaxPayoutConditionMet(BalanceTransfer btd) {
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
		System.out.println("****************************");
		System.out.println("principalID: " + principalId);
		System.out.println("****************************");
		Date date = btd.getEffectiveDate();
		BigDecimal transferAmount = btd.getTransferAmount();
		String toAccrualCategory = btd.getToAccrualCategory();
		System.out.println("****************************");
		System.out.println("To Accrual Cat: " + toAccrualCategory);
		System.out.println("****************************");
		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, date);
		System.out.println("****************************");
		System.out.println("PHA Service Date: " + pha.getServiceDate());
		System.out.println("****************************");
		Date serviceDate = pha.getServiceDate();
		AccrualCategory debitedAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory,date);
		//need to set this elsewhere.
		btd.setAccrualCategoryRule(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(debitedAccrualCategory, TKUtils.getCurrentDate(), serviceDate).getLmAccrualCategoryRuleId());
		//This is the rule for the "From" accrual category... should also provide the rule for "To" accrual categoroy.
		//There is no accrual category rule that has been attached to the balance transfer document at this state.
		//It should be checked, however, that the "To and/or From Accrual Categories have been supplied". If this data is validated
		//then the accrual category rule id can be queried by the accrual category rule service.
		//In a "process(Custom)SaveDocument method, the accrual category rule ID, as well as the balance trasnfer rule id - if applicable -
		//can be attached to the balance transfer document prior to saving/submitting, etc/...
		AccrualCategoryRule acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(btd.getAccrualCategoryRule());
		if(ObjectUtils.isNotNull(acr.getMaxCarryOver())) {
			//BigDecimal currentCarryOver = TkServiceLocator.getCalendarEntriesService().
			BigDecimal maxCarryOver = new BigDecimal(acr.getMaxCarryOver());
			BigDecimal fteSum = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, date);
			BigDecimal adjustedTransferAmount = acr.getMaxBalanceTransferConversionFactor().multiply(transferAmount);
			//TkServiceLocator.getAccrualCategoryService().getCurrentCarryOverTotalForPrincipalId(principalId,btd.getFromAccrualCategory());
			/*
			 * TODO: Sum adjustedTransferAmount with credited accrual category's current
			 * carry over amount, use result to compare with max carryover percentage.
			 */
			//replace the augden with with the carry over for the current pay calendar.
			if(adjustedTransferAmount.add(BigDecimal.ZERO).compareTo(fteSum.multiply(maxCarryOver)) > 0) {
				return false;
			}
		}
		
		return true;
	}

}
