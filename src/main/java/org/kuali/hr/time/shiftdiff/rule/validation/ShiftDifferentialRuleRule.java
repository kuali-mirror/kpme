package org.kuali.hr.time.shiftdiff.rule.validation;

import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

public class ShiftDifferentialRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateSalGroup(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getTkSalGroup() != null
				&& !ValidationUtils.validateSalGroup(shiftDifferentialRule
						.getTkSalGroup(), shiftDifferentialRule
						.getEffectiveDate())) {
			this.putFieldError("tkSalGroup", "error.existence", "Salgroup '"
					+ shiftDifferentialRule.getTkSalGroup() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateEarnCode(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getEarnCode() != null
				&& !ValidationUtils.validateEarnCode(shiftDifferentialRule
						.getEarnCode(), shiftDifferentialRule
						.getEffectiveDate())) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ shiftDifferentialRule.getEarnCode() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateLocation(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getLocation() != null
				&& !ValidationUtils.validateLocation(shiftDifferentialRule
						.getLocation(), shiftDifferentialRule
						.getEffectiveDate())) {
			this.putFieldError("location", "error.existence", "location '"
					+ shiftDifferentialRule.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	boolean validatePayGrade(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getPayGrade() != null
				&& !ValidationUtils.validatePayGrade(shiftDifferentialRule
						.getPayGrade(), shiftDifferentialRule
						.getEffectiveDate())) {
			this.putFieldError("payGrade", "error.existence", "pay grade '"
					+ shiftDifferentialRule.getPayGrade() + "'");
			return false;
		} else {
			return true;
		}
	}
	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for TimeCollectionRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof ShiftDifferentialRule) {
			ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) pbo;
			shiftDifferentialRule.setUserPrincipalId(GlobalVariables
					.getUserSession().getLoggedInUserPrincipalName());
			if (shiftDifferentialRule != null) {
				valid = true;
				valid &= this.validateLocation(shiftDifferentialRule);
				valid &= this.validateSalGroup(shiftDifferentialRule);
				valid &= this.validatePayGrade(shiftDifferentialRule);
				valid &= this.validateEarnCode(shiftDifferentialRule);
			}
		}

		return valid;
	}

	@Override
	protected boolean processCustomApproveDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomApproveDocumentBusinessRules(document);
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomRouteDocumentBusinessRules(document);
	}
}