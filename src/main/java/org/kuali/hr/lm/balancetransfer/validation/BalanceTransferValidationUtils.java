package org.kuali.hr.lm.balancetransfer.validation;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferValidationUtils {

	public static boolean validateTransfer(BalanceTransfer balanceTransfer) {
		boolean isValid = true;
		String principalId = balanceTransfer.getPrincipalId();
		Date effectiveDate = balanceTransfer.getEffectiveDate();
		String fromAccrualCategory = balanceTransfer.getFromAccrualCategory();
		String toAccrualCategory = balanceTransfer.getToAccrualCategory();
		AccrualCategory fromCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
		AccrualCategory toCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, effectiveDate);
		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId,effectiveDate);
		
/*		boolean isDeptAdmin = TKContext.getUser().isDepartmentAdmin();
		boolean isSysAdmin = TKContext.getUser().isSystemAdmin();
		if(isDeptAdmin || isSysAdmin) {
			
			isValid &= validateTransferAmount(balanceTransfer.getTransferAmount(),fromCat,toCat, principalId, effectiveDate);
		}
		else {*/
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
/*							isValid &= validatePrincipal(pha,principalId);
							isValid &= validateEffectiveDate(effectiveDate);
							isValid &= validateAgainstLeavePlan(pha,fromCat,toCat,effectiveDate);
							isValid &= validateTransferFromAccrualCategory(fromCat,principalId,effectiveDate,acr);
							isValid &= validateTransferToAccrualCategory(toCat,principalId,effectiveDate,acr);*/
							isValid &= validateTransferAmount(balanceTransfer.getTransferAmount(),fromCat,toCat, principalId, effectiveDate, acr);
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

	private static boolean validateTransferAmount(BigDecimal transferAmount,
			AccrualCategory fromCat, AccrualCategory toCat, String principalId,
			Date effectiveDate, AccrualCategoryRule accrualRule) {

		//transfer amount must be less than the max transfer amount defined in the accrual category rule.
		//it cannot be negative.
		boolean isValid = true;
		BigDecimal maxTransferAmount = new BigDecimal(accrualRule.getMaxTransferAmount());
		String fromUnitOfTime = TkConstants.UNIT_OF_TIME.get(fromCat.getUnitOfTime());
		if(ObjectUtils.isNotNull(maxTransferAmount)) {
			if(transferAmount.compareTo(maxTransferAmount) > 0) {
				isValid &= false;
				GlobalVariables.getMessageMap().putError("balanceTransfer.transferAmount","balanceTransfer.transferAmount.maxTransferAmount",maxTransferAmount.toString(),fromUnitOfTime);
			}
		}
		if(transferAmount.compareTo(BigDecimal.ZERO) < 0 ) {
			isValid &= false;
			GlobalVariables.getMessageMap().putError("balanceTransfer.transferAmount","balanceTransfer.transferAmount.negative");
		}
				
		return isValid;
	}

}
