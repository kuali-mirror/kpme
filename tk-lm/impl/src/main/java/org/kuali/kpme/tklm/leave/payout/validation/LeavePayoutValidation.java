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
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

public class LeavePayoutValidation extends MaintenanceDocumentRuleBase {

/*	private boolean validateAgainstLeavePlan(PrincipalHRAttributes pha, AccrualCategory fromAccrualCategory, LocalDate effectiveDate) {
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
	*//**
	 * Transfer amount could be validated against several variables, including max transfer amount,
	 * max carry over.
	 * ( if transfers count as usage ).
	 * @param transferAmount
	 * @param debitedAccrualCategory
	 * @param creditedAccrualCategory
	 * @param principalId TODO
	 * @param effectiveDate TODO
	 * @param isSomeAdmin 
	 * @return true if transfer amount is valid
	 *//*
	private boolean validatePayoutAmount(BigDecimal transferAmount,
			AccrualCategory debitedAccrualCategory,
			EarnCode payoutEarnCode, String principalId, LocalDate effectiveDate, boolean isSomeAdmin) {
		if(transferAmount != null) {
			if(transferAmount.compareTo(BigDecimal.ZERO) <= 0 ) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount", "leavePayout.payoutAmount.negative");
				return false;
			}
			if(debitedAccrualCategory != null) {
				BigDecimal balance = LmServiceLocator.getLeaveSummaryService().getLeaveBalanceForAccrCatUpToDate(principalId, effectiveDate, effectiveDate, debitedAccrualCategory.getAccrualCategory(), effectiveDate);
				if(balance != null) {
					if(transferAmount.compareTo(balance) > 0 && !isSomeAdmin) {
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount", "leavePayout.payoutAmount.exceeds.balance");
						return false;
					}
				}
			}
		}
		else {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount", "leavePayout.payoutAmount.required");
		}

		return true;
	}
	
	*//**
	 * Is the "From" accrual category required to be over its maximum balance before a transfer can take place?
	 * The "From" accrual category must be defined in an accrual category rule as having a max bal rule.
	 * @param accrualCategory
	 * @param effectiveDate 
	 * @param principalId 
	 * @param fromAccrualCategory 
	 * @return
	 *//*
	private boolean validateTransferFromAccrualCategory(AccrualCategory accrualCategory, String principalId,
			LocalDate effectiveDate, String fromAccrualCategory) {
		boolean isValid = ValidationUtils.validateAccCategory(fromAccrualCategory, principalId, effectiveDate);
		if(!isValid) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory","balanceTransfer.accrualcategory.exists",fromAccrualCategory);
		}
		return isValid;
	}
	
	private boolean validateTransferToEarnCode(EarnCode transferToEarnCode, AccrualCategoryRule acr, String principalId, PrincipalHRAttributes pha, LocalDate effectiveDate, boolean isSomeAdmin) {
		boolean isValid = true;
        //commenting out for KPME-2847
		if(transferToEarnCode != null && !isSomeAdmin) {
			LeavePlan earnCodeLeavePlan = HrServiceLocator.getLeavePlanService().getLeavePlan(transferToEarnCode.getLeavePlan(),effectiveDate);
			if(earnCodeLeavePlan != null) {
				LeavePlan phaLeavePlan = HrServiceLocator.getLeavePlanService().getLeavePlan(pha.getLeavePlan(), effectiveDate);
				if(phaLeavePlan != null) {
					//transfer to earn code should be in the employee's leave plan.
					if(!StringUtils.equals(earnCodeLeavePlan.getLmLeavePlanId(),phaLeavePlan.getLmLeavePlanId()) && !isSomeAdmin) {
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.earnCode", "leavePayout.earncode.leaveplan.inconsistent");
						isValid &= false;
					}
				}
			}
			else {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.earnCode", "leavePayout.earncode.leaveplan.exists");
				isValid &= false;
			}
		}
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
			if(!(TkContext.isDepartmentAdmin() || HrContext.isSystemAdmin() || TkContext.isLocationAdmin())) {
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
			String principalId = leavePayout.getPrincipalId();
			LocalDate effectiveDate = leavePayout.getEffectiveLocalDate();
			String fromAccrualCategory = leavePayout.getFromAccrualCategory();
			EarnCode payoutEarnCode = leavePayout.getEarnCodeObj();
			AccrualCategory fromCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
			PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
			
			boolean isDeptAdmin = TkContext.isDepartmentAdmin();
			boolean isSysAdmin = HrContext.isSystemAdmin();
			boolean isLocAdmin = TkContext.isLocationAdmin();
			
			if(ObjectUtils.isNotNull(pha)) {
				if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
					if(isDeptAdmin || isSysAdmin || isLocAdmin) {
						isValid &= validatePayoutAmount(leavePayout.getPayoutAmount(),fromCat,payoutEarnCode, principalId, effectiveDate,true);
						isValid &= validateAgainstLeavePlan(pha,fromCat,effectiveDate);
						isValid &= validatePrincipal(pha,principalId,true);
						isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,fromAccrualCategory);
						isValid &= validateTransferToEarnCode(payoutEarnCode,null,principalId,pha,effectiveDate,true);
					} else {
						AccrualCategoryRule acr = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat, effectiveDate, pha.getServiceLocalDate());
						if(ObjectUtils.isNotNull(acr)) {
							if(ObjectUtils.isNotNull(acr.getMaxBalFlag())
									&& StringUtils.isNotBlank(acr.getMaxBalFlag())
									&& StringUtils.isNotEmpty(acr.getMaxBalFlag())
									&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
								if(ObjectUtils.isNotNull(payoutEarnCode)) {
									isValid &= validatePrincipal(pha,principalId,false);
									isValid &= validateEffectiveDate(effectiveDate);
									isValid &= validateAccrualCategoryRule(acr,payoutEarnCode,fromCat,effectiveDate);
									isValid &= validateTransferToEarnCode(payoutEarnCode,acr,principalId,pha,effectiveDate,false);
									isValid &= validateAgainstLeavePlan(pha,fromCat,effectiveDate);
									isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,fromAccrualCategory);
									isValid &= validatePayoutAmount(leavePayout.getPayoutAmount(),fromCat,payoutEarnCode, null, null,false);
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
	}*/
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean isValid = true;

		LOG.debug("entering custom validation for Balance Transfer");

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();

		if(pbo instanceof LeavePayout) {

			LeavePayout leavePayout = (LeavePayout) pbo;
			String fromAccrualCat = leavePayout.getFromAccrualCategory();
			String toEarnCode = leavePayout.getEarnCode();
			String principalId = leavePayout.getPrincipalId();
			BigDecimal payoutAmount = leavePayout.getPayoutAmount();
			
			isValid &= validateEffectiveDate(leavePayout.getEffectiveLocalDate());
			isValid &= validateAccrualCateogry(fromAccrualCat,leavePayout.getEffectiveLocalDate());
			isValid &= validateEarnCode(toEarnCode,leavePayout.getEffectiveLocalDate());
			isValid &= validatePayoutAmount(principalId,payoutAmount,fromAccrualCat,leavePayout.getEffectiveLocalDate());
			if(validatePrincipalId(principalId,leavePayout.getEffectiveLocalDate())) {
				isValid &= validatePrincipal(principalId,leavePayout.getEffectiveDate(),GlobalVariables.getUserSession().getPrincipalId(),document.getDocumentHeader().getWorkflowDocument());
			}
			else {
				isValid &= false;
			}

		}
		return isValid;
	}

	private boolean validateEffectiveDate(LocalDate date) {
		//Limit on future dates?
		if(date.isAfter(LocalDate.now().plusYears(1))) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.effectiveDate", "leavePayout.effectiveDate.overOneYear");
			return false;
		}
		return true;
	}

	private boolean validateAccrualCateogry(String fromAccrualCat,
			LocalDate effectiveLocalDate) {
		boolean valid = true;
		if(StringUtils.isNotEmpty(fromAccrualCat)) {
			valid &= ValidationUtils.validateAccCategory(fromAccrualCat, effectiveLocalDate);
			if(!valid) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "leavePayout.fromAccrualCategory.exists");
			}
		}
		return valid;
	}
	
	private boolean validatePayoutAmount(String principalId, BigDecimal payoutAmount,
			String fromAccrualCat, LocalDate effectiveLocalDate) {
		boolean isValid = true;
		if(payoutAmount != null) {
			LeaveSummary leaveSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDateForAccrualCategory(principalId, effectiveLocalDate, fromAccrualCat);
			if(leaveSummary != null) {
				LeaveSummaryRow leaveSummaryRow = leaveSummary.getLeaveSummaryRowForAccrualCtgy(fromAccrualCat);
				if(leaveSummaryRow != null) {
					BigDecimal accruedBalance = leaveSummaryRow.getAccruedBalance();
					if(payoutAmount.compareTo(accruedBalance) > 0) {
						isValid &= false;
						GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount", "leavePayout.payoutAmount.exceeds.balance");
					}
				}
			}
			if(payoutAmount.compareTo(BigDecimal.ZERO) < 0 ) {
				isValid  &= false;
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.payoutAmount", "leavePayout.payoutAmount.negative");
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
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "leavePayout.principal.exists");
			}
		}
		return isValid;
	}

	private boolean validateEarnCode(String toEarnCode,
			LocalDate effectiveLocalDate) {
		boolean isValid = true;
		if(StringUtils.isNotEmpty(toEarnCode)) {
			isValid &= ValidationUtils.validateEarnCode(toEarnCode, effectiveLocalDate);
			if(!isValid) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.earnCode", "leavePayout.earncode.exists");	
			}
		}
		return isValid;
	}
	
	private boolean validatePrincipal(String principalId, Date effectiveDate, String userPrincipalId, WorkflowDocument workflowDocument) {
		boolean isValid = true;
		PrincipalHRAttributesContract pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, LocalDate.fromDateFields(effectiveDate));

		if(pha == null) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "balanceTransfer.principal.noAttributes");
			isValid &= false;
		}
		else {
			boolean canCreate = false;
			if(!StringUtils.equals(principalId,userPrincipalId)) {
				List<Job> principalsJobs = (List<Job>) HrServiceLocator.getJobService().getActiveLeaveJobs(principalId, LocalDate.fromDateFields(effectiveDate));
				for(Job job : principalsJobs) {
					
					
					if(job.isEligibleForLeave()) {
						
						String department = job != null ? job.getDept() : null;
						DepartmentContract departmentObj = job != null ? HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.fromDateFields(effectiveDate)) : null;
						String location = departmentObj != null ? departmentObj.getLocation() : null;

						//logged in user may ONLY submit documents for principals in authorized departments / location.
			        	if (LmServiceLocator.getLMPermissionService().isAuthorizedInDepartment(userPrincipalId, "Create Leave Payout", department, new DateTime(effectiveDate.getTime()))
							|| LmServiceLocator.getLMPermissionService().isAuthorizedInLocation(userPrincipalId, "Create Leave Payout", location, new DateTime(effectiveDate.getTime()))) {
								canCreate = true;
								break;
						}
			        	else {
			        		//do NOT block approvers, processors, delegates from approving the document.
							List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getActiveAssignmentsForJob(principalId, job.getJobNumber(), LocalDate.fromDateFields(effectiveDate));
							for(Assignment assignment : assignments) {
								if(HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))
										|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))
										|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))
										|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userPrincipalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), assignment.getWorkArea(), new DateTime(effectiveDate))) {
									canCreate = true;
									break;
								}
							}
			        	}
					}
				}
			}
			else {
				//should be able to submit their own transaction documents...
				//max balance triggered transactions go through this validation. Set a userPrincipal to system and deny LEAVE DEPT/LOC Admins ability to submit their own
				//transactions these simplified rules??
				canCreate = false;
			}
			
			if(!canCreate) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "balanceTransfer.userNotAuthorized");
				isValid &= false;
			}
		}
		return isValid;
	}
}
