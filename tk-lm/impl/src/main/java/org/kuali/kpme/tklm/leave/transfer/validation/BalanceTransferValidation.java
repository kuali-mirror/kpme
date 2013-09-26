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
package org.kuali.kpme.tklm.leave.transfer.validation;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferValidation extends MaintenanceDocumentRuleBase {

/*	//the "to" and "from" accrual categories should be in the supplied principal's leave plan as of the effective date.
	private boolean validateLeavePlan(PrincipalHRAttributes pha,
			AccrualCategory fromAccrualCategory, AccrualCategory toAccrualCategory, LocalDate effectiveDate) {
		boolean isValid = true;
		if(fromAccrualCategory == null || toAccrualCategory == null) {
			return false;
		}
		List<AccrualCategory> accrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(pha.getLeavePlan(), effectiveDate);
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
			AccrualCategory creditedAccrualCategory, String principalId, LocalDate effectiveDate) {
		if(transferAmount != null) {
			if(transferAmount.compareTo(BigDecimal.ZERO) < 0 ) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount", "balanceTransfer.amount.negative");
				return false;
			}
		}

		return true;
	}

	//Effective date not more than one year in advance
	private boolean validateEffectiveDate(LocalDate date) {
		if(date != null) {
			if(DateUtils.addYears(LocalDate.now().toDate(), 1).compareTo(date.toDate()) > 0)
				return true;
			else
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.effectiveDate", "balanceTransfer.effectiveDate.error");
			}
		return false;
	}
	
	private boolean validateTransferFromAccrualCategory(AccrualCategory accrualCategory, String principalId,
			LocalDate effectiveDate, AccrualCategoryRule acr, String fromAccrualCategory) {
		boolean isValid = true;
		if(accrualCategory == null) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.accrualcategory.exists",fromAccrualCategory);
			isValid &= false;
		}
		return isValid;
	}
	
	//Transfer to accrual category should match the value defined in the accrual category rule
	private boolean validateTransferToAccrualCategory(AccrualCategory accrualCategory, String principalId, LocalDate effectiveDate, AccrualCategoryRule acr, String toAccrualCategory, boolean isSomeAdmin) {
		boolean isValid = true;
		if(accrualCategory != null) {
			if(acr != null) {
				//processCustomRouteDocumentBusinessRule will provide the invalidation on system triggered transfers
				//if the accrual category rule is null, i.o.w. this code block should never be reached when acr is null on sys triggered transfers.
				AccrualCategory maxBalTranToAccCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(acr.getMaxBalanceTransferToAccrualCategory(),effectiveDate);
				if(!StringUtils.equals(maxBalTranToAccCat.getLmAccrualCategoryId(),accrualCategory.getLmAccrualCategoryId()) && !isSomeAdmin) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.toAccrualCategory.noMatch",accrualCategory.getAccrualCategory());
					isValid &= false;
				}
			}
		}
		else {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.accrualcategory.exists",toAccrualCategory);
			isValid &= false;
		}
		return isValid;
	}

	//no validation
	private boolean validatePrincipal(PrincipalHRAttributes pha, String principalId) {
		boolean isValid = true;
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
		return isValid;
	}
	
	//transfer amount must be under max limit when submitted via max balance triggered action or by a work area approver.
	private boolean isTransferAmountUnderMaxLimit(String principalId, LocalDate effectiveDate, String accrualCategory,
			BigDecimal transferAmount, AccrualCategoryRule accrualRule, String leavePlan) {
	
		if(ObjectUtils.isNotNull(accrualRule)) {

			BigDecimal maxTransferAmount = null;
			if(ObjectUtils.isNotNull(accrualRule.getMaxTransferAmount())) {
				maxTransferAmount = new BigDecimal(accrualRule.getMaxTransferAmount());
			}
			if(ObjectUtils.isNotNull(maxTransferAmount)) {
				EmployeeOverride eo = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, leavePlan, accrualCategory, TkConstants.EMPLOYEE_OVERRIDE_TYPE.get("MTA"), effectiveDate);
				if(ObjectUtils.isNotNull(eo))
					if(ObjectUtils.isNull(eo.getOverrideValue()))
						maxTransferAmount = new BigDecimal(Long.MAX_VALUE);
					else
						maxTransferAmount = new BigDecimal(eo.getOverrideValue());
				else {
					BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
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

				*//**
				 * Validation is basically governed by accrual category rules. Get accrual category
				 * rules for both the "To" and "From" accrual categories, pass to validators along with the
				 * values needing to be validated.
				 * 
				 * Balance transfers initiated from the leave calendar display should already have all values
				 * populated, thus validated, including the accrual category rule for the "From" accrual category.
				 * 
				 * Balance transfers initiated via the Maintenance tab will have no values populated.
				 *//*
				String principalId = balanceTransfer.getPrincipalId();
				LocalDate effectiveDate = balanceTransfer.getEffectiveLocalDate();
				String fromAccrualCategory = balanceTransfer.getFromAccrualCategory();
				String toAccrualCategory = balanceTransfer.getToAccrualCategory();
				AccrualCategory fromCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
				AccrualCategory toCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, effectiveDate);
				PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
				
				// TODO Check for role in specific dept / location
				boolean isDeptAdmin = TkContext.isDepartmentAdmin();
				boolean isSysAdmin = HrContext.isSystemAdmin();
				boolean isLocAdmin = TkContext.isLocationAdmin();
				
				if(ObjectUtils.isNotNull(pha)) {
					if(isDeptAdmin || isSysAdmin || isLocAdmin) {
						isValid &= validateLeavePlan(pha,fromCat,toCat,effectiveDate);
						isValid &= validatePrincipal(pha,principalId);
						isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,null,fromAccrualCategory);
						isValid &= validateTransferToAccrualCategory(toCat,principalId,effectiveDate,null,toAccrualCategory,true);
						isValid &= validateTransferAmount(balanceTransfer.getTransferAmount(),fromCat,toCat, principalId, effectiveDate);
					}
					else {
						if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
							AccrualCategoryRule acr = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat,
									effectiveDate, pha.getServiceLocalDate());
							if(ObjectUtils.isNotNull(acr)) {
								if(StringUtils.isNotBlank(acr.getMaxBalFlag())
										&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
									if(ObjectUtils.isNotNull(toCat)) {
										
										isValid &= validatePrincipal(pha,principalId);
										isValid &= validateEffectiveDate(effectiveDate);
										isValid &= validateLeavePlan(pha,fromCat,toCat,effectiveDate);
										isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,acr,fromAccrualCategory);
										isValid &= validateTransferToAccrualCategory(toCat,principalId,effectiveDate,acr,toAccrualCategory,false);
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
				}
				else  {
					//if the principal has no principal hr attributes, they're not a principal.
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId","balanceTransfer.principal.noAttributes");
					isValid &= false;
				}

			}
		}
		return isValid; 
	}*/
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean isValid = true;

		LOG.debug("entering custom validation for Balance Transfer");

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();

		if(pbo instanceof BalanceTransfer) {

			BalanceTransfer balanceTransfer = (BalanceTransfer) pbo;
			String fromAccrualCat = balanceTransfer.getFromAccrualCategory();
			String toAccrualCat = balanceTransfer.getToAccrualCategory();
			String principalId = balanceTransfer.getPrincipalId();
			BigDecimal transferAmount = balanceTransfer.getTransferAmount();
			
			isValid &= validateFromAccrualCateogry(fromAccrualCat,balanceTransfer.getEffectiveLocalDate());
			isValid &= validateToAccrualCateogry(toAccrualCat,balanceTransfer.getEffectiveLocalDate());
			isValid &= validatePrincipalId(principalId,balanceTransfer.getEffectiveLocalDate());
			isValid &= validateTransferAmount(principalId,transferAmount,fromAccrualCat,balanceTransfer.getEffectiveLocalDate());

		}
				
		return isValid;
	}

	private boolean validateFromAccrualCateogry(String fromAccrualCat,
			LocalDate effectiveLocalDate) {
		boolean isValid = true;
		if(StringUtils.isNotEmpty(fromAccrualCat)) {
			if(!isValid) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.accrualcategory.exists", fromAccrualCat);
			}
		}
		return isValid;
	}
	
	private boolean validateToAccrualCateogry(String toAccrualCat,
			LocalDate effectiveLocalDate) {
		boolean isValid = true;
		if(StringUtils.isNotEmpty(toAccrualCat)) {
			if(!isValid) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.accrualcategory.exists", toAccrualCat);
			}
		}
		return isValid;
	}
	
	private boolean validateTransferAmount(String principalId, BigDecimal transferAmount,
			String fromAccrualCat, LocalDate effectiveLocalDate) {
		boolean isValid = false;
		if(transferAmount != null) {
			LeaveSummary leaveSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDateForAccrualCategory(principalId, effectiveLocalDate, fromAccrualCat);
			if(leaveSummary != null) {
				LeaveSummaryRow leaveSummaryRow = leaveSummary.getLeaveSummaryRowForAccrualCtgy(fromAccrualCat);
				if(leaveSummaryRow != null) {
					BigDecimal accruedBalance = leaveSummaryRow.getAccruedBalance();
					if(transferAmount.compareTo(accruedBalance) > 0) {
						isValid &= false;
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount", "balanceTransfer.transferAmount.exceeds.balance");
					}
				}
			}
			if(transferAmount.compareTo(BigDecimal.ZERO) < 0 ) {
				isValid  &= false;
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.transferAmount", "balanceTransfer.transferAmount.negative");
			}
		}
		return isValid;
	}

	private boolean validatePrincipalId(String principalId,
			LocalDate effectiveLocalDate) {
		boolean isValid = true;
		if(StringUtils.isNotEmpty(principalId)) {
			isValid &= ValidationUtils.validatePrincipalId(principalId);
			if(!isValid) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "balanceTransfer.principal.exists");
			}
		}
		return isValid;
	}
	
}
