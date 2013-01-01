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
package org.kuali.hr.lm.balancetransfer.validation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferValidation extends MaintenanceDocumentRuleBase {

	private boolean validateAgainstLeavePlan(PrincipalHRAttributes pha,
			AccrualCategory fromAccrualCategory, AccrualCategory toAccrualCategory, Date effectiveDate) {
		boolean isValid = true;
		LeavePlan lp = TkServiceLocator.getLeavePlanService().getLeavePlan(pha.getLeavePlan(),effectiveDate);

		List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), effectiveDate);
		if(accrualCategories.size() > 0) {
			boolean isFromInLeavePlan = false;
			boolean isToInLeavePlan = false;
			for(AccrualCategory activeAccrualCategory : accrualCategories) {
				if(StringUtils.equals(activeAccrualCategory.getLmAccrualCategoryId(),fromAccrualCategory.getLmAccrualCategoryId())) {
					isFromInLeavePlan = true;
				}
				if(StringUtils.equals(activeAccrualCategory.getLmAccrualCategoryId(), toAccrualCategory.getLmAccrualCategoryId())) {
					isToInLeavePlan = true;
				}
			}
			if(!isFromInLeavePlan) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.accrualCategory.notInLeavePlan", fromAccrualCategory.getAccrualCategory());
				isValid &= false;
			}
			if(!isToInLeavePlan) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.accrualCategory.notInLeavePlan", toAccrualCategory.getAccrualCategory());
				isValid &= false;			
			}
		}
		else {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "balanceTransfer.principal.noACinLeavePlan");
			isValid &=false;
		}
		return isValid;
	}
	
	//Employee Overrides???
	/**
	 * Transfer amount must be validated against several variables, including max transfer amount,
	 * max carry over ( for the converted amount deposited into the "to" accrual category, max usage
	 * ( if transfers count as usage ).
	 * @param transferAmount
	 * @param debitedAccrualCategory
	 * @param creditedAccrualCategory
	 * @param principalId TODO
	 * @param effectiveDate TODO
	 * @return true if transfer amount is valid
	 */
	private boolean validateTransferAmount(BigDecimal transferAmount,
			AccrualCategory debitedAccrualCategory,
			AccrualCategory creditedAccrualCategory, String principalId, Date effectiveDate) {
		
		if(transferAmount.compareTo(BigDecimal.ZERO) < 0 ) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount", "balanceTransfer.amount.negative");
			return false;
		}
		//TkServiceLocator.getAccrualCategoryService().getCurrentBalanceForPrincipal(principalId, debitedAccrualCategory, effectiveDate);

		return true;
	}

	/**
	 * Are there any rules in place for effective date? i.e. not more than one year in advance...
	 * @param date
	 * @return
	 */
	private boolean validateEffectiveDate(Date date) {
		//Limit on future dates?
		return true;
	}
	
	/**
	 * Is the "From" accrual category required to be over its maximum balance before a transfer can take place?
	 * The "From" accrual category must be defined in an accrual category rule as having a max bal rule.
	 * @param accrualCategory
	 * @param effectiveDate 
	 * @param principalId 
	 * @param acr 
	 * @return
	 */
	private boolean validateTransferFromAccrualCategory(AccrualCategory accrualCategory, String principalId,
			Date effectiveDate, AccrualCategoryRule acr) {
		//accrualCategory has rules
		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, effectiveDate);
		
		return true;
	}
	
	/**
	 * The "To" accrual category must be one for which the applicable accrual category rule specifies
	 * as the accrual category marked for transfer
	 * @param accrualCategory
	 * @param acr 
	 * @param effectiveDate 
	 * @param principalId 
	 * @return
	 */
	private boolean validateTransferToAccrualCategory(AccrualCategory accrualCategory, String principalId, Date effectiveDate, AccrualCategoryRule acr) {
		AccrualCategory maxBalTranToAccCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(acr.getMaxBalanceTransferToAccrualCategory(),effectiveDate);
		if(!StringUtils.equals(maxBalTranToAccCat.getLmAccrualCategoryId(),accrualCategory.getLmAccrualCategoryId())) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.toAccrualCategory.noMatch",accrualCategory.getAccrualCategory());
			return false;
		}
		return true;
	}

	private boolean validatePrincipal(PrincipalHRAttributes pha, String principalId) {
		// TODO Auto-generated method stub
		// Principal has to have an associated leave plan.
		// leave plan should contain the transfer "from" accrual category.
		
		return true;
	}
	
	/**
	 * KPME-1527: When saving, evaluate amounts recorded for the accrual category against max carry over.
	 * when comparing this amount against max carry over, max carry over must be
	 * multiplied by entire full time engagement for all leave eligible jobs.
	 */
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
		//btd.setAccrualCategoryRule(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(debitedAccrualCategory, TKUtils.getCurrentDate(), serviceDate).getLmAccrualCategoryRuleId());

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
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount", "balanceTransfer.exceeds.maxCarryOver");
				return false;
			}
		}
		
		return true;
	}
	

	private boolean isTransferAmountUnderMaxLimit(
			BigDecimal transferAmount, String accrualCategoryRuleId) {
	
		//Accrual Category rule can only be defined on a balance transfer prior to saving if initiated via the leave calendar display.
		//For payout on termination/retirement, no rule will yet be defined.
		AccrualCategoryRule acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
		
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
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount","balanceTransfer.exceeds.transferLimit");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Is not called when saving a document, but is called on submit.
	 * 
	 * The branched logic in this method contains references to accrual category rule properties
	 * that, when balance transfer is triggered through system interaction, would naturally have
	 * been checked in the process of triggering the balance transfer. i.e. existence of accrual
	 * category rule, maxBalFlag = Y, leave plan for principal id existence and accrual category
	 * existence under that leave plan. The context in which the balance transfers are triggered
	 * would themselves validate these checks.
	 * 
	 */
	@Override
	public boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
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
				String principalId = balanceTransfer.getPrincipalId();
				Date effectiveDate = balanceTransfer.getEffectiveDate();
				String fromAccrualCategory = balanceTransfer.getFromAccrualCategory();
				String toAccrualCategory = balanceTransfer.getToAccrualCategory();
				AccrualCategory fromCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
				AccrualCategory toCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, effectiveDate);
				PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
				
				boolean isDeptAdmin = TKContext.getUser().isDepartmentAdmin();
				boolean isSysAdmin = TKContext.getUser().isSystemAdmin();
				if(isDeptAdmin || isSysAdmin) {
					isValid &= validateTransferAmount(balanceTransfer.getTransferAmount(),fromCat,toCat, principalId, effectiveDate);
				}
				else {
					if(ObjectUtils.isNotNull(pha)) {
						if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
							AccrualCategoryRule acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat,
									TKUtils.getCurrentDate(), pha.getServiceDate());
							if(ObjectUtils.isNotNull(acr)) {
								if(ObjectUtils.isNotNull(acr.getMaxBalFlag())
										&& StringUtils.isNotBlank(acr.getMaxBalFlag())
										&& StringUtils.isNotEmpty(acr.getMaxBalFlag())
										&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
									if(ObjectUtils.isNotNull(acr.getMaxBalanceTransferToAccrualCategory())) {
										isValid &= validatePrincipal(pha,principalId);
										isValid &= validateEffectiveDate(effectiveDate);
										isValid &= validateAgainstLeavePlan(pha,fromCat,toCat,effectiveDate);
										isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,acr);
										isValid &= validateTransferToAccrualCategory(toCat,principalId,effectiveDate,acr);
										isValid &= validateTransferAmount(balanceTransfer.getTransferAmount(),fromCat,toCat, null, null);
									}
									else {
										//should never be the case if accrual category rules are validated correctly.
										GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory",
												"balanceTransfer.fromAccrualCategory.rules.transferToAccrualCategory",
												fromAccrualCategory);
										isValid &= false;
									}
								}
								else {
									//max bal flag null, blank, empty, or "N"
									GlobalVariables.getMessageMap().putError("document.newMaintinableObject.fromAccrualCategory",
											"balanceTransfer.fromAccrualCategory.rules.maxBalFlag", fromAccrualCategory);
									isValid &= false;
								}
							}
							else {
								//department admins must validate amount to transfer does not exceed current balance.
								GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory",
										"balanceTransfer.fromAccrualCategory.rules.exist",fromCat.getAccrualCategory());
								isValid &= false;
							}
						}
						else {
							//if the principal doesn't have a leave plan, there aren't any accrual categories that can be debited/credited.
							GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId","balanceTransfer.principal.noLeavePlan");
							isValid &=false;
						}
					}
					else  {
						//if the principal has no principal hr attributes, they're not a principal.
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId","balanceTransfer.principal.noAttributes");
						isValid &= false;
					}
				}
			}
		}
		return isValid; 
	}

}
