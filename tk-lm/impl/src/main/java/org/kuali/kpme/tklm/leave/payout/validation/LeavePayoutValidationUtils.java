/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.api.leave.summary.LeaveSummaryContract;
import org.kuali.kpme.tklm.api.leave.summary.LeaveSummaryRowContract;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutValidationUtils {

	public static boolean validatePayout(LeavePayout leavePayout) {
		boolean isValid = true;
		String principalId = leavePayout.getPrincipalId();
		LocalDate effectiveDate = leavePayout.getEffectiveLocalDate();
		String fromAccrualCategory = leavePayout.getFromAccrualCategory();
		String payoutEarnCode = leavePayout.getEarnCode();
		
		if(!ValidationUtils.validateAccrualCategory(fromAccrualCategory, effectiveDate)) {
			GlobalVariables.getMessageMap().putError("leavePayout.fromAccrualCategory", "leavePayout.fromAccrualCategory.exists");
			isValid &= false;
		}
		
		if(!ValidationUtils.validateEarnCode(payoutEarnCode, effectiveDate)) {
			GlobalVariables.getMessageMap().putError("leavePayout.earnCode", "leavePayout.earncode.exists");
			isValid &= false;
		}
		
		if(!ValidationUtils.validatePrincipalId(principalId)) {
			GlobalVariables.getMessageMap().putError("leavePayout.principalId", "leavePayout.principal.exists");
			isValid &= false;
		}
		
		if(effectiveDate.isAfter(LocalDate.now().plusYears(1))) {
			GlobalVariables.getMessageMap().putError("leavePayout.effectiveDate", "leavePayout.effectiveDate.overOneYear");
			isValid &= false;
		}
		
		
		
		if(isValid) {
			
			AccrualCategory fromCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
			EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(payoutEarnCode, effectiveDate);
			PrincipalHRAttributesContract pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
			
			if(ObjectUtils.isNotNull(pha)) {
				if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
					AccrualCategoryRule acr = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat, effectiveDate, pha.getServiceLocalDate());

					if(ObjectUtils.isNotNull(acr)) {
						if(ObjectUtils.isNotNull(acr.getMaxBalFlag())
								&& StringUtils.isNotBlank(acr.getMaxBalFlag())
								&& StringUtils.isNotEmpty(acr.getMaxBalFlag())
								&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
							if(ObjectUtils.isNotNull(acr.getMaxPayoutEarnCode()) || StringUtils.equals(HrConstants.ACTION_AT_MAX_BALANCE.LOSE, acr.getActionAtMaxBalance())) {
								isValid = validateForfeitedAmount(leavePayout.getForfeitedAmount());
								isValid &= validatePayoutAmount(leavePayout.getPayoutAmount(),fromCat, earnCode, principalId, effectiveDate, acr);
							}
							else {
								//should never be the case if accrual category rules are validated correctly.
								GlobalVariables.getMessageMap().putError("leavePayout.fromAccrualCategory",
										"leavePayout.fromAccrualCategory.rules.payoutToEarnCode",
										fromAccrualCategory);
								isValid &= false;
							}
						}
						else {
							//max bal flag null, blank, empty, or "N"
							GlobalVariables.getMessageMap().putError("leavePayout.fromAccrualCategory",
									"leavePayout.fromAccrualCategory.rules.maxBalFlag", fromAccrualCategory);
							isValid &= false;
						}
					}
					else {
						//department admins must validate amount to transfer does not exceed current balance.
						GlobalVariables.getMessageMap().putError("leavePayout.fromAccrualCategory",
								"leavePayout.fromAccrualCategory.rules.exist",fromCat.getAccrualCategory());
						isValid &= false;
					}
				}
				else {
					//if the principal doesn't have a leave plan, there aren't any accrual categories that can be debited/credited.
					GlobalVariables.getMessageMap().putError("leavePayout.principalId","leavePayout.principal.noLeavePlan");
					isValid &=false;
				}
			}
			else  {
				//if the principal has no principal hr attributes, they're not a principal.
				GlobalVariables.getMessageMap().putError("leavePayout.principalId","leavePayout.principal.noAttributes");
				isValid &= false;
			}
		}
		return isValid;

	}

	private static boolean validateForfeitedAmount(BigDecimal forfeitedAmount) {
		if(forfeitedAmount.compareTo(BigDecimal.ZERO) < 0) {
			GlobalVariables.getMessageMap().putError("leavePayout.forfeitedAmount", "balanceTransfer.transferAmount.negative");
			return false;
		}
		return true;
	}

	private static boolean validatePayoutAmount(BigDecimal payoutAmount,
			AccrualCategory fromCat, EarnCode earnCode, String principalId,
			LocalDate effectiveDate, AccrualCategoryRule accrualRule) {

		LeaveSummaryContract leaveSummary = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDateForAccrualCategory(principalId, effectiveDate, fromCat.getAccrualCategory());
		LeaveSummaryRowContract row = leaveSummary.getLeaveSummaryRowForAccrualCtgy(fromCat.getAccrualCategory());
		BigDecimal balance = row.getAccruedBalance();
		//transfer amount must be less than the max transfer amount defined in the accrual category rule.
		//it cannot be negative.
		boolean isValid = true;
		
		BigDecimal maxPayoutAmount = null;
		BigDecimal adjustedMaxPayoutAmount = null;
		if(ObjectUtils.isNotNull(accrualRule.getMaxPayoutAmount())) {
			maxPayoutAmount = new BigDecimal(accrualRule.getMaxPayoutAmount());
			BigDecimal fullTimeEngagement = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
			adjustedMaxPayoutAmount = maxPayoutAmount.multiply(fullTimeEngagement);
		}
		
		//use override if one exists.
		EmployeeOverride maxPayoutAmountOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, fromCat.getLeavePlan(), fromCat.getAccrualCategory(), "MPA", effectiveDate);
		if(ObjectUtils.isNotNull(maxPayoutAmountOverride))
			adjustedMaxPayoutAmount = new BigDecimal(maxPayoutAmountOverride.getOverrideValue());
				
				
		if(ObjectUtils.isNotNull(adjustedMaxPayoutAmount)) {
			if(payoutAmount.compareTo(adjustedMaxPayoutAmount) > 0) {
				isValid &= false;
				String fromUnitOfTime = HrConstants.UNIT_OF_TIME.get(fromCat.getUnitOfTime());
				GlobalVariables.getMessageMap().putError("leavePayout.payoutAmount","leavePayout.payoutAmount.maxPayoutAmount",adjustedMaxPayoutAmount.toString(),fromUnitOfTime);
			}
		}
		// check for a positive amount.
		if(payoutAmount.compareTo(BigDecimal.ZERO) < 0 ) {
			isValid &= false;
			GlobalVariables.getMessageMap().putError("leavePayout.payoutAmount","leavePayout.payoutAmount.negative");
		}
		
		if(payoutAmount.compareTo(balance) > 0 ) {
			isValid &= false;
			GlobalVariables.getMessageMap().putError("leavePayout.payoutAmount", "maxBalance.amount.exceedsBalance", balance.toString());
		}	
		return isValid;
	}

	private boolean validateMaxBalance() {
		//This validation could assert that the payout amount, together with forfeiture
		//brings the balance for the given accrual category back to, or under, the balance limit
		//without exceeding the total accrued balance.
		return true;
	}
	
	private boolean validateMaxCarryOver() {
		//This validation could assert that the payout amount, together with forfeiture
		//brings the balance for the given accrual category back to, or under, the max carry over limit
		//without exceeding the total accrued balance.
		return true;
	}
	
}
