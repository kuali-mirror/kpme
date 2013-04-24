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
package org.kuali.hr.tklm.leave.payout.validation;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.earncode.EarnCode;
import org.kuali.hr.core.principal.PrincipalHRAttributes;
import org.kuali.hr.tklm.leave.employeeoverride.EmployeeOverride;
import org.kuali.hr.tklm.leave.payout.LeavePayout;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TKContext;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutValidation extends MaintenanceDocumentRuleBase {

	private boolean validateAgainstLeavePlan(PrincipalHRAttributes pha, AccrualCategory fromAccrualCategory, LocalDate effectiveDate) {
		boolean isValid = true;

		List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), effectiveDate);
		if(accrualCategories.size() > 0) {
			boolean isFromInLeavePlan = false;
			for(AccrualCategory activeAccrualCategory : accrualCategories) {
				if(StringUtils.equals(activeAccrualCategory.getLmAccrualCategoryId(),fromAccrualCategory.getLmAccrualCategoryId())) {
					isFromInLeavePlan = true;
				}
			}
			if(!isFromInLeavePlan) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "leavePayout.accrualCategory.notInLeavePlan", fromAccrualCategory.getAccrualCategory());
				isValid &= false;
			}
		}
		else {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "leavePayout.principal.noACinLeavePlan");
			isValid &=false;
		}
		
		return isValid;
	}
	
	//Employee Overrides???
	/**
	 * Transfer amount could be validated against several variables, including max transfer amount,
	 * max carry over ( for the converted amount deposited into the "to" accrual category, max usage
	 * ( if transfers count as usage ).
	 * @param transferAmount
	 * @param debitedAccrualCategory
	 * @param creditedAccrualCategory
	 * @param principalId TODO
	 * @param effectiveDate TODO
	 * @return true if transfer amount is valid
	 */
	private boolean validatePayoutAmount(BigDecimal transferAmount,
			AccrualCategory debitedAccrualCategory,
			EarnCode payoutEarnCode, String principalId, LocalDate effectiveDate) {
		
		if(transferAmount.compareTo(BigDecimal.ZERO) < 0 ) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount", "leavePayout.amount.negative");
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
	private boolean validateEffectiveDate(LocalDate date) {
		//Limit on future dates?
		if(date.isAfter(LocalDate.now().plusYears(1))) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.effectiveDate", "leavePayout.effectiveDate.overOneYear");
			return false;
		}
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
			LocalDate effectiveDate, AccrualCategoryRule acr) {
		//accrualCategory has rules
		//PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, effectiveDate);
		
		return true;
	}
	
	private boolean validatePrincipal(PrincipalHRAttributes pha, String principalId) {
		// TODO Auto-generated method stub
		// Principal has to have an associated leave plan.
		// leave plan should contain the transfer "from" accrual category.
		
		return true;
	}
	
	//transfer amount must be under max limit when submitted via max balance triggered action or by a work area approver.
	private boolean isPayoutAmountUnderMaxLimit(String principalId, LocalDate effectiveDate, String accrualCategory,
				BigDecimal payoutAmount, AccrualCategoryRule accrualRule, String leavePlan) {
	
		if(ObjectUtils.isNotNull(accrualRule)) {

			BigDecimal maxPayoutAmount = null;
			if(ObjectUtils.isNotNull(accrualRule.getMaxTransferAmount())) {
				maxPayoutAmount = new BigDecimal(accrualRule.getMaxTransferAmount());
			}
			if(ObjectUtils.isNotNull(maxPayoutAmount)) {
				EmployeeOverride eo = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, leavePlan, accrualCategory, TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MPA"), effectiveDate);
				if(ObjectUtils.isNotNull(eo))
					if(ObjectUtils.isNull(eo.getOverrideValue()))
						maxPayoutAmount = new BigDecimal(Long.MAX_VALUE);
					else
						maxPayoutAmount = new BigDecimal(eo.getOverrideValue());
				else {
					BigDecimal fteSum = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
					maxPayoutAmount = maxPayoutAmount.multiply(fteSum);
				}
				if(payoutAmount.compareTo(maxPayoutAmount) > 0) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount","leavePayout.exceeds.payoutLimit");
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean isValid = true;
		LOG.debug("entering custom validation for Balance Transfer");

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();

		if(pbo instanceof LeavePayout) {

			LeavePayout leavePayout = (LeavePayout) pbo;

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
			String principalId = leavePayout.getPrincipalId();
			LocalDate effectiveDate = leavePayout.getEffectiveLocalDate();
			String fromAccrualCategory = leavePayout.getFromAccrualCategory();
			EarnCode payoutEarnCode = leavePayout.getEarnCodeObj();
			AccrualCategory fromCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
			PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
			
			boolean isDeptAdmin = TKContext.isDepartmentAdmin();
			boolean isSysAdmin = TKContext.isSystemAdmin();
			if(isDeptAdmin || isSysAdmin) {
				isValid &= validatePayoutAmount(leavePayout.getPayoutAmount(),fromCat,payoutEarnCode, principalId, effectiveDate);
			}
			else {
				if(ObjectUtils.isNotNull(pha)) {
					if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
						AccrualCategoryRule acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat, effectiveDate, pha.getServiceLocalDate());
						if(ObjectUtils.isNotNull(acr)) {
							if(ObjectUtils.isNotNull(acr.getMaxBalFlag())
									&& StringUtils.isNotBlank(acr.getMaxBalFlag())
									&& StringUtils.isNotEmpty(acr.getMaxBalFlag())
									&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
								if(ObjectUtils.isNotNull(payoutEarnCode)) {
									isValid &= validatePrincipal(pha,principalId);
									isValid &= validateEffectiveDate(effectiveDate);
									isValid &= validateAgainstLeavePlan(pha,fromCat,effectiveDate);
									isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,acr);
									isValid &= validatePayoutAmount(leavePayout.getPayoutAmount(),fromCat,payoutEarnCode, null, null);
									isValid &= isPayoutAmountUnderMaxLimit(principalId, effectiveDate, fromAccrualCategory, leavePayout.getPayoutAmount(), acr, pha.getLeavePlan());
								}
								else {
									//should never be the case if accrual category rules are validated correctly.
									GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory",
											"leavePayout.fromAccrualCategory.rules.payoutToEarnCode",
											fromAccrualCategory);
									isValid &= false;
								}
							}
							else {
								//max bal flag null, blank, empty, or "N"
								GlobalVariables.getMessageMap().putError("document.newMaintinableObject.fromAccrualCategory",
										"leavePayout.fromAccrualCategory.rules.maxBalFlag", fromAccrualCategory);
								isValid &= false;
							}
						}
						else {
							//department admins must validate amount to transfer does not exceed current balance.
							GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory",
									"leavePayout.fromAccrualCategory.rules.exist",fromCat.getAccrualCategory());
							isValid &= false;
						}
					}
					else {
						//if the principal doesn't have a leave plan, there aren't any accrual categories that can be debited/credited.
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId","leavePayout.principal.noLeavePlan");
						isValid &=false;
					}
				}
				else  {
					//if the principal has no principal hr attributes, they're not a principal.
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId","leavePayout.principal.noAttributes");
					isValid &= false;
				}
			}
		}
		return isValid;
	}

}
