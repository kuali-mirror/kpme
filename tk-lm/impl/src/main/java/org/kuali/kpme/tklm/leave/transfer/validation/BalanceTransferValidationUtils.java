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

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferValidationUtils {

	public static boolean validateTransfer(BalanceTransfer balanceTransfer) {
		boolean isValid = true;
		if(StringUtils.isNotEmpty(balanceTransfer.getSstoId())) {
			return isValid && validateSstoTranser(balanceTransfer) ;
		}
		String principalId = balanceTransfer.getPrincipalId();
		LocalDate effectiveDate = balanceTransfer.getEffectiveLocalDate();
		String fromAccrualCategory = balanceTransfer.getFromAccrualCategory();
		String toAccrualCategory = balanceTransfer.getToAccrualCategory();
		AccrualCategory fromCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
		AccrualCategory toCat = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, effectiveDate);
		PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
		
		if(ObjectUtils.isNotNull(pha)) {
			if(ObjectUtils.isNotNull(pha.getLeavePlan())) {
				AccrualCategoryRule acr = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(fromCat,
						effectiveDate, pha.getServiceLocalDate());
				if(ObjectUtils.isNotNull(acr)) {
					if(ObjectUtils.isNotNull(acr.getMaxBalFlag())
							&& StringUtils.isNotBlank(acr.getMaxBalFlag())
							&& StringUtils.isNotEmpty(acr.getMaxBalFlag())
							&& StringUtils.equals(acr.getMaxBalFlag(), "Y")) {
						if(ObjectUtils.isNotNull(acr.getMaxBalanceTransferToAccrualCategory()) || StringUtils.equals(HrConstants.ACTION_AT_MAX_BALANCE.LOSE, acr.getActionAtMaxBalance())) {
/*							isValid &= validatePrincipal(pha,principalId);
							isValid &= validateEffectiveDate(effectiveDate);
							isValid &= validateAgainstLeavePlan(pha,fromCat,toCat,effectiveDate);
							isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,acr);
							isValid &= validateTransferToAccrualCategory(toCat,principalId,effectiveDate,acr);*/
							isValid &= validateMaxCarryOver(balanceTransfer.getAmountTransferred(),toCat,principalId,effectiveDate,acr, pha);
							isValid &= validateTransferAmount(balanceTransfer.getTransferAmount(),fromCat,toCat, principalId, effectiveDate, acr);
							isValid &= validateForfeitedAmount(balanceTransfer.getForfeitedAmount());
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
/*		}*/
		return isValid;

	}

	private static boolean validateForfeitedAmount(BigDecimal forfeitedAmount) {
		return forfeitedAmount.compareTo(BigDecimal.ZERO) >= 0 ? true: false;
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
	
	private static boolean validateMaxCarryOver(BigDecimal amountTransferred,
			AccrualCategory toCat, String principalId, LocalDate effectiveDate,
			AccrualCategoryRule acr, PrincipalHRAttributes pha) {
/*		List<AccrualCategoryRule> rules = toCat.getAccrualCategoryRules();
		Date serviceDate = pha.getServiceDate();
		Interval interval = new Interval(serviceDate.getTime(), effectiveDate.getTime());
		for(AccrualCategoryRule rule : rules) {
			String unitOfTime = rule.getServiceUnitOfTime();
			if(StringUtils.equals(unitOfTime, HrConstants.SERVICE_TIME_MONTHS))
		}*/
		return true;
	}

	private static boolean validateTransferAmount(BigDecimal transferAmount,
			AccrualCategory fromCat, AccrualCategory toCat, String principalId,
			LocalDate effectiveDate, AccrualCategoryRule accrualRule) {

		//transfer amount must be less than the max transfer amount defined in the accrual category rule.
		//it cannot be negative.
		boolean isValid = true;
		
		BigDecimal balance = LmServiceLocator.getAccrualService().getAccruedBalanceForPrincipal(principalId, fromCat, effectiveDate);
		
		BigDecimal maxTransferAmount = null;
		BigDecimal adjustedMaxTransferAmount = null;
		if(ObjectUtils.isNotNull(accrualRule.getMaxTransferAmount())) {
			maxTransferAmount = new BigDecimal(accrualRule.getMaxTransferAmount());
			BigDecimal fullTimeEngagement = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, effectiveDate);
			adjustedMaxTransferAmount = maxTransferAmount.multiply(fullTimeEngagement);
		}
		
		//use override if one exists.
		EmployeeOverride maxTransferAmountOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, fromCat.getLeavePlan(), fromCat.getAccrualCategory(), "MTA", effectiveDate);
		if(ObjectUtils.isNotNull(maxTransferAmountOverride))
			adjustedMaxTransferAmount = new BigDecimal(maxTransferAmountOverride.getOverrideValue());
				
		if(ObjectUtils.isNotNull(adjustedMaxTransferAmount)) {
			if(transferAmount.compareTo(adjustedMaxTransferAmount) > 0) {
				isValid &= false;
				String fromUnitOfTime = HrConstants.UNIT_OF_TIME.get(fromCat.getUnitOfTime());
				GlobalVariables.getMessageMap().putError("balanceTransfer.transferAmount","balanceTransfer.transferAmount.maxTransferAmount",adjustedMaxTransferAmount.toString(),fromUnitOfTime);
			}
		}
		// check for a positive amount.
		if(transferAmount.compareTo(BigDecimal.ZERO) < 0 ) {
			isValid &= false;
			GlobalVariables.getMessageMap().putError("balanceTransfer.transferAmount","balanceTransfer.transferAmount.negative");
		}
		
		if(balance.subtract(transferAmount).compareTo(BigDecimal.ZERO) < 0 ) {
			if(StringUtils.equals(fromCat.getEarnCodeObj().getAllowNegativeAccrualBalance(),"Y"))
				isValid &= true;
			else {
				isValid &= false;
				GlobalVariables.getMessageMap().putError("balanceTransfer.transferAmount", "maxBalance.amount.exceedsBalance");
			}
		}
		
		return isValid;
	}
	
	public static boolean validateSstoTranser(BalanceTransfer bt) {
		// make sure from accrual category is consistent with the ssto's
		SystemScheduledTimeOff ssto = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(bt.getSstoId());
		if(ssto == null) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.transferSSTO.sstoDoesNotExis", bt.getSstoId());
			return false;
		}
		if(!ssto.getAccrualCategory().equals(bt.getFromAccrualCategory())) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.transferSSTO.fromACWrong", bt.getFromAccrualCategory(), ssto.getAccrualCategory());
			return false;
		}
		
		if(bt.getFromAccrualCategory().equals(bt.getToAccrualCategory())) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.transferSSTO.fromAndToACTheSame");
			return false;
		}
		
		AccrualCategory fromAC = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(bt.getFromAccrualCategory(), bt.getEffectiveLocalDate());
		if(fromAC == null) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.transferSSTO.acDoesNotExist", bt.getFromAccrualCategory());
			return false;
		}
		AccrualCategory toAC = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(bt.getToAccrualCategory(), bt.getEffectiveLocalDate());
		if(toAC == null) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.transferSSTO.acDoesNotExist", bt.getToAccrualCategory());
			return false;
		}
		// make sure the leave plan of from/to accrual categories are consistent with the employee's leave plan
		PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(bt.getPrincipalId(),bt.getEffectiveLocalDate());
		if(StringUtils.isNotEmpty(fromAC.getLeavePlan())){
			if(!fromAC.getLeavePlan().equals(pha.getLeavePlan())) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.fromAccrualCategory", "balanceTransfer.transferSSTO.wrongACLeavePlan", fromAC.getLeavePlan(), pha.getLeavePlan());
				return false;
			}
		}
		if(StringUtils.isNotEmpty(toAC.getLeavePlan())){
			if(!fromAC.getLeavePlan().equals(pha.getLeavePlan())) {
				GlobalVariables.getMessageMap().putError("document.newMaintainableObject.toAccrualCategory", "balanceTransfer.transferSSTO.wrongACLeavePlan", toAC.getLeavePlan(), pha.getLeavePlan());
				return false;
			}
		}
		
		return true;
	}

}
