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

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
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
		AccrualCategory fromCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
		AccrualCategory toCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(payoutEarnCode, effectiveDate);
		PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
		
		if(ObjectUtils.isNotNull(pha)) {
			if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
				AccrualCategoryRule acr = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat, effectiveDate, pha.getServiceLocalDate());
				if(ObjectUtils.isNotNull(acr)) {
					if(ObjectUtils.isNotNull(acr.getMaxBalFlag())
							&& StringUtils.isNotBlank(acr.getMaxBalFlag())
							&& StringUtils.isNotEmpty(acr.getMaxBalFlag())
							&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
						if(ObjectUtils.isNotNull(acr.getMaxPayoutEarnCode()) || StringUtils.equals(HrConstants.ACTION_AT_MAX_BALANCE.LOSE, acr.getActionAtMaxBalance())) {
/*							isValid &= validatePrincipal(pha,principalId);
							isValid &= validateEffectiveDate(effectiveDate);
							isValid &= validateAgainstLeavePlan(pha,fromCat,toCat,effectiveDate);
							isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,acr);
							isValid &= validateTransferToAccrualCategory(toCat,principalId,effectiveDate,acr);
*/
							isValid &= validatePayoutAmount(leavePayout.getPayoutAmount(),fromCat,toCat, principalId, effectiveDate, acr);
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
/*		}*/
		return isValid;

	}

	private static boolean validatePayoutAmount(BigDecimal payoutAmount,
			AccrualCategory fromCat, AccrualCategory toCat, String principalId,
			LocalDate effectiveDate, AccrualCategoryRule accrualRule) {

		BigDecimal balance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, fromCat, effectiveDate);
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
		
		if(balance.subtract(payoutAmount).compareTo(BigDecimal.ZERO) < 0 ) {
			if(StringUtils.equals(fromCat.getEarnCodeObj().getAllowNegativeAccrualBalance(),"Y"))
				isValid &= true;
			else {
				isValid &= false;
				GlobalVariables.getMessageMap().putError("leavePayout.payoutAmount", "maxBalance.amount.exceedsBalance");
			}
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
