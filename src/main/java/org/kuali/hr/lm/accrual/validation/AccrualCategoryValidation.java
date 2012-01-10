package org.kuali.hr.lm.accrual.validation;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class AccrualCategoryValidation extends MaintenanceDocumentRuleBase {
	private static final String ADD_LINE_LOCATION = "add.accrualCategoryRules.";
	
	boolean validateMaxBalAccCat(String maxBalanceTransferAccCat, String fieldPrefix) {
		boolean valid = true;
		if (StringUtils.isEmpty(maxBalanceTransferAccCat)) {
				this.putFieldError(fieldPrefix + "maxBalanceTransferToAccrualCategory", "error.required", "Max Balance Transfer To Accrual Category");
				valid = false;
		}
		return valid;
	}
	
	boolean validateMaxBalConvFact(BigDecimal maxBalanceTransferConvFactor, String fieldPrefix) {
		boolean valid = true;
		if (maxBalanceTransferConvFactor==null) {
			this.putFieldError(fieldPrefix + "maxBalanceTransferConversionFactor", "error.required", "Max Balance Transfer ConversionFactor");
			valid = false;
		}
		return valid;
	}
	
	boolean validateMaxBalTransferAmount(Long maxBalanceTransferAccCat, String fieldPrefix) {
		boolean valid = true;
		if (maxBalanceTransferAccCat==null) {
			this.putFieldError(fieldPrefix + "maxTransferAmount", "error.required", "Max Transfer Amount");
			valid = false;
		}
		return valid;
	}
	
	boolean validateMaxPayoutAmount(Long maxPayoutAmount, String fieldPrefix) {
		boolean valid = true;
		if (maxPayoutAmount==null) {
			this.putFieldError(fieldPrefix + "maxPayoutAmount", "error.required", "Max Payout Amount");
			valid = false;
		}
		return valid;
	}
	
	boolean validateMaxPayoutLeaveCode(String maxPayoutLeaveCode, String fieldPrefix) {
		boolean valid = true;
		if (maxPayoutLeaveCode==null) {
			this.putFieldError(fieldPrefix + "maxPayoutLeaveCode", "error.required", "Max Payout LeaveCode");
			valid = false;
		}
		return valid;
	}

	boolean validateAccrualRulePresent(List<AccrualCategoryRule> accrualCategoryRules) {
		boolean valid = false;

		if (accrualCategoryRules != null && accrualCategoryRules.size() > 0) {
			for (AccrualCategoryRule leaveAccrualCategoryRule : accrualCategoryRules) {
				valid |= leaveAccrualCategoryRule.isActive();
			}
		}

		if (!valid) {
			this.putGlobalError("accrual.rule.required");
		}

		return valid;
	}
	
	boolean validateMinPercentWorked(BigDecimal minPercentWorked, String fieldPrefix) {
		boolean valid = true;
		if (minPercentWorked==null) {
			this.putFieldError(fieldPrefix + "minPercentWorked", "error.required", "Min Percent Worked");
			valid = false;
		} else {
			if ( minPercentWorked.compareTo(new BigDecimal(100.00)) > 0 || 
				 minPercentWorked.compareTo(new BigDecimal(0.00)) < 0	) {
				this.putFieldError("minPercentWorked", "error.percentage.minimumPercentageWorked");
				valid = false;
			}
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Leave Accrual");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof AccrualCategory) {
			AccrualCategory leaveAccrualCategory = (AccrualCategory) pbo;
			if (leaveAccrualCategory != null) {
				valid = true;
				valid &= this.validateAccrualRulePresent(leaveAccrualCategory.getAccrualCategoryRules());
				valid &= this.validateMinPercentWorked(leaveAccrualCategory.getMinPercentWorked(), ADD_LINE_LOCATION);
			}
		}
		return valid;
	}
	
	@Override
	public boolean processCustomAddCollectionLineBusinessRules(
			MaintenanceDocument document, String collectionName,
			PersistableBusinessObject line) {
		boolean valid = true;
		LOG.debug("entering custom validation for Leave Accrual Rules");
		PersistableBusinessObject pbo = line;
		if (pbo instanceof AccrualCategoryRule) {
			AccrualCategoryRule leaveAccrualCategoryRule = (AccrualCategoryRule) pbo;
			if (leaveAccrualCategoryRule != null) {
				valid = true;
				if(StringUtils.equals("T", leaveAccrualCategoryRule.getActionAtMaxBalance())){
					valid &= validateMaxBalAccCat(leaveAccrualCategoryRule.getMaxBalanceTransferToAccrualCategory(), ADD_LINE_LOCATION);
					valid &= validateMaxBalConvFact(leaveAccrualCategoryRule.getMaxBalanceTransferConversionFactor(), ADD_LINE_LOCATION);
					valid &= validateMaxBalTransferAmount(leaveAccrualCategoryRule.getMaxTransferAmount(), ADD_LINE_LOCATION);
				}else if(StringUtils.equals("P", leaveAccrualCategoryRule.getActionAtMaxBalance())){
					valid &= validateMaxPayoutAmount(leaveAccrualCategoryRule.getMaxPayoutAmount(), ADD_LINE_LOCATION);
					valid &= validateMaxPayoutLeaveCode(leaveAccrualCategoryRule.getMaxPayoutLeaveCode(), ADD_LINE_LOCATION);
				}	
			}
		}
		return valid;
	}
}
