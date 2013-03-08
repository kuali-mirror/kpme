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
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferValidation extends MaintenanceDocumentRuleBase {

	//the "to" and "from" accrual categories should be in the supplied principal's leave plan as of the effective date.
	private boolean validateLeavePlan(PrincipalHRAttributes pha,
			AccrualCategory fromAccrualCategory, AccrualCategory toAccrualCategory, Date effectiveDate) {
		boolean isValid = true;
		
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
	
	//See isTransferAmountUnderMaxLimit for futher validation
	private boolean validateTransferAmount(BigDecimal transferAmount,
			AccrualCategory debitedAccrualCategory,
			AccrualCategory creditedAccrualCategory, String principalId, Date effectiveDate) {
		
		if(transferAmount.compareTo(BigDecimal.ZERO) < 0 ) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount", "balanceTransfer.amount.negative");
			return false;
		}

		return true;
	}

	//Effective date not more than one year in advance
	private boolean validateEffectiveDate(Date date) {
		if(DateUtils.addYears(TKUtils.getCurrentDate(), 1).compareTo(date) > 0)
			return true;
		else
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.effectiveDate", "balanceTransfer.effectiveDate.error");
		return false;
	}
	
	private boolean validateTransferFromAccrualCategory(AccrualCategory accrualCategory, String principalId,
			Date effectiveDate, AccrualCategoryRule acr) {
		//accrualCategory has rules
		//PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, effectiveDate);
		
		return true;
	}
	
	//Transfer to accrual category should match the value defined in the accrual category rule
	private boolean validateTransferToAccrualCategory(AccrualCategory accrualCategory, String principalId, Date effectiveDate, AccrualCategoryRule acr) {
		AccrualCategory maxBalTranToAccCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(acr.getMaxBalanceTransferToAccrualCategory(),effectiveDate);
		if(!StringUtils.equals(maxBalTranToAccCat.getLmAccrualCategoryId(),accrualCategory.getLmAccrualCategoryId())) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.toAccrualCategory.noMatch",accrualCategory.getAccrualCategory());
			return false;
		}
		return true;
	}

	//no validation
	private boolean validatePrincipal(PrincipalHRAttributes pha, String principalId) {
		return true;
	}
	
	//transfer amount must be under max limit when submitted via max balance triggered action or by a work area approver.
	private boolean isTransferAmountUnderMaxLimit(String principalId, Date effectiveDate, String accrualCategory,
			BigDecimal transferAmount, AccrualCategoryRule accrualRule, String leavePlan) {
	
		if(ObjectUtils.isNotNull(accrualRule)) {

			BigDecimal maxTransferAmount = null;
			if(ObjectUtils.isNotNull(accrualRule.getMaxTransferAmount())) {
				maxTransferAmount = new BigDecimal(accrualRule.getMaxTransferAmount());
			}
			if(ObjectUtils.isNotNull(maxTransferAmount)) {
				EmployeeOverride eo = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, leavePlan, accrualCategory, TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MTA"), effectiveDate);
				if(ObjectUtils.isNotNull(eo))
					if(ObjectUtils.isNull(eo.getOverrideValue()))
						maxTransferAmount = new BigDecimal(Long.MAX_VALUE);
					else
						maxTransferAmount = new BigDecimal(eo.getOverrideValue());
				else {
					BigDecimal fteSum = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
					maxTransferAmount = maxTransferAmount.multiply(fteSum);
				}
				if(transferAmount.compareTo(maxTransferAmount) > 0) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount","balanceTransfer.exceeds.transferLimit");
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		LOG.debug("entering custom validation for Balance Transfer");

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();

		if(pbo instanceof BalanceTransfer) {

			BalanceTransfer balanceTransfer = (BalanceTransfer) pbo;
			
			// if this balance transfer is on a system scheduled time off, then don't do further validation
			if(StringUtils.isNotEmpty(balanceTransfer.getSstoId())) {
				isValid &= BalanceTransferValidationUtils.validateSstoTranser(balanceTransfer);
				return isValid;
			}
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
									effectiveDate, pha.getServiceDate());
							if(ObjectUtils.isNotNull(acr)) {
								if(StringUtils.isNotBlank(acr.getMaxBalFlag())
										&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
									if(ObjectUtils.isNotNull(toCat)) {
										
										isValid &= validatePrincipal(pha,principalId);
										isValid &= validateEffectiveDate(effectiveDate);
										isValid &= validateLeavePlan(pha,fromCat,toCat,effectiveDate);
										isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,acr);
										isValid &= validateTransferToAccrualCategory(toCat,principalId,effectiveDate,acr);
										isValid &= validateTransferAmount(balanceTransfer.getTransferAmount(),fromCat,toCat, null, null);
										isValid &= isTransferAmountUnderMaxLimit(principalId,effectiveDate,fromAccrualCategory,balanceTransfer.getTransferAmount(),acr,pha.getLeavePlan());
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
