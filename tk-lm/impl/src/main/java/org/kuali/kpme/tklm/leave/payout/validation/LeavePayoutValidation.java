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
package org.kuali.kpme.tklm.leave.payout.validation;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.leaveplan.LeavePlan;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutValidation extends MaintenanceDocumentRuleBase {

	private boolean validateAgainstLeavePlan(PrincipalHRAttributes pha, AccrualCategory fromAccrualCategory, LocalDate effectiveDate) {
		boolean isValid = true;
		if(pha==null) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "leavePayout.principal.noLeavePlan");
			isValid &= false;
		}
		else {
			List<AccrualCategory> accrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), effectiveDate);
			if(accrualCategories.size() > 0) {
				if(fromAccrualCategory != null) {
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
			}
			else {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "leavePayout.principal.noACinLeavePlan");
				isValid &=false;
			}
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
		if(transferAmount != null) {
			if(transferAmount.compareTo(BigDecimal.ZERO) <= 0 ) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount", "leavePayout.payoutAmount.negative");
				return false;
			}
			if(debitedAccrualCategory != null) {
				BigDecimal balance = LmServiceLocator.getLeaveSummaryService().getLeaveBalanceForAccrCatUpToDate(principalId, effectiveDate, effectiveDate, debitedAccrualCategory.getAccrualCategory(), effectiveDate);
				if(balance != null) {
					if(transferAmount.compareTo(balance) > 0) {
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount", "leavePayout.payoutAmount.exceeds.balance");
						return false;
					}
				}
			}
		}

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
	 * @param fromAccrualCategory 
	 * @return
	 */
	private boolean validateTransferFromAccrualCategory(AccrualCategory accrualCategory, String principalId,
			LocalDate effectiveDate, String fromAccrualCategory) {
		boolean isValid = ValidationUtils.validateAccCategory(fromAccrualCategory, principalId, effectiveDate);
		if(!isValid) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory","balanceTransfer.accrualcategory.exists",fromAccrualCategory);
		}
		return isValid;
	}
	
	//no validation
	private boolean validatePrincipal(PrincipalHRAttributes pha, String principalId) {
		boolean isValid = true;
		if(principalId != null) {
			if(pha == null) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "balanceTransfer.principal.noAttributes");
				isValid &= false;
			}
			else {
				Person person = KimApiServiceLocator.getPersonService().getPerson(principalId);
				if(person != null) {
					if(!person.isActive()) {
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "balanceTransfer.principal.active");
						isValid &= false;
					}
				}
				else {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "balanceTransfer.principal.exists");
					isValid &= false;
				}
			}
		}
		return isValid;
	}
	
	private boolean validateTransferToEarnCode(EarnCode transferToEarnCode, AccrualCategoryRule acr, String principalId, PrincipalHRAttributes pha, LocalDate effectiveDate) {
		boolean isValid = true;
        //commenting out for KPME-2847
		/*if(transferToEarnCode != null) {
			LeavePlan earnCodeLeavePlan = HrServiceLocator.getLeavePlanService().getLeavePlan(transferToEarnCode.getLeavePlan(),effectiveDate);
			if(earnCodeLeavePlan != null) {
				LeavePlan phaLeavePlan = HrServiceLocator.getLeavePlanService().getLeavePlan(pha.getLeavePlan(), effectiveDate);
				if(phaLeavePlan != null) {
					//transfer to earn code should be in the employee's leave plan.
					if(!StringUtils.equals(earnCodeLeavePlan.getLmLeavePlanId(),phaLeavePlan.getLmLeavePlanId())) {
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.earnCode", "leavePayout.earncode.leaveplan.inconsistent");
						isValid &= false;
					}
				}
			}
			else {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.earnCode", "leavePayout.earncode.leaveplan.exists");
				isValid &= false;
			}
		}*/
		if (transferToEarnCode == null) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.earnCode", "leavePayout.earncode.exists");
			isValid &= false;
		}
		return isValid;
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
				EmployeeOverride eo = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, leavePlan, accrualCategory, TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MPA"), effectiveDate);
				if(ObjectUtils.isNotNull(eo))
					if(ObjectUtils.isNull(eo.getOverrideValue()))
						maxPayoutAmount = new BigDecimal(Long.MAX_VALUE);
					else
						maxPayoutAmount = new BigDecimal(eo.getOverrideValue());
				else {
					BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
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
	
	private boolean validateAccrualCategoryRule(AccrualCategoryRule acr,
			EarnCode payoutEarnCode, AccrualCategory fromCat, LocalDate effectiveDate) {
		if(acr != null) {
			EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(acr.getMaxPayoutEarnCode(),effectiveDate);
			if(earnCode == null) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "leavePayout.earncode.exists");
				return false;
			}
			else {
				if(!StringUtils.equals(earnCode.getHrEarnCodeId(), payoutEarnCode.getHrEarnCodeId())) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.earnCode", "leavePayout.payoutEarnCode.noMatch",payoutEarnCode.getEarnCode());
					return false;
				}
			}
		}
		else {
			if(!(TkContext.isDepartmentAdmin() || HrContext.isSystemAdmin())) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "leavePayout.fromAccrualCategory.rules.exist",fromCat.getAccrualCategory());
				return false;
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
			AccrualCategory fromCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
			PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
			
			boolean isDeptAdmin = TkContext.isDepartmentAdmin();
			boolean isSysAdmin = HrContext.isSystemAdmin();
			if(ObjectUtils.isNotNull(pha)) {
				if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
					if(isDeptAdmin || isSysAdmin) {
						isValid &= validatePayoutAmount(leavePayout.getPayoutAmount(),fromCat,payoutEarnCode, principalId, effectiveDate);
						isValid &= validateAgainstLeavePlan(pha,fromCat,effectiveDate);
						isValid &= validatePrincipal(pha,principalId);
						isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,fromAccrualCategory);
						isValid &= validateTransferToEarnCode(payoutEarnCode,null,principalId,pha,effectiveDate);
					} else {
						AccrualCategoryRule acr = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat, effectiveDate, pha.getServiceLocalDate());
						if(ObjectUtils.isNotNull(acr)) {
							if(ObjectUtils.isNotNull(acr.getMaxBalFlag())
									&& StringUtils.isNotBlank(acr.getMaxBalFlag())
									&& StringUtils.isNotEmpty(acr.getMaxBalFlag())
									&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
								if(ObjectUtils.isNotNull(payoutEarnCode)) {
									isValid &= validatePrincipal(pha,principalId);
									isValid &= validateEffectiveDate(effectiveDate);
									isValid &= validateAccrualCategoryRule(acr,payoutEarnCode,fromCat,effectiveDate);
									isValid &= validateTransferToEarnCode(payoutEarnCode,acr,principalId,pha,effectiveDate);
									isValid &= validateAgainstLeavePlan(pha,fromCat,effectiveDate);
									isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,fromAccrualCategory);
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
								GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory",
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
		return isValid;
	}
}
