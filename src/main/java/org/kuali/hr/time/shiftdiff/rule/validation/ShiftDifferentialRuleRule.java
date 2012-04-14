package org.kuali.hr.time.shiftdiff.rule.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

public class ShiftDifferentialRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateSalGroup(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getHrSalGroup() != null
				&& !StringUtils.equals(shiftDifferentialRule.getHrSalGroup(),
						TkConstants.WILDCARD_CHARACTER)
				&& !ValidationUtils.validateSalGroup(shiftDifferentialRule
						.getHrSalGroup(), shiftDifferentialRule
						.getEffectiveDate())) {
			this.putFieldError("hrSalGroup", "error.existence", "Salgroup '"
					+ shiftDifferentialRule.getHrSalGroup() + "'");
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
				&& !StringUtils.equals(shiftDifferentialRule.getLocation(),
						TkConstants.WILDCARD_CHARACTER)
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
				&& !StringUtils.equals(shiftDifferentialRule.getPayGrade(),
						TkConstants.WILDCARD_CHARACTER)
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
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for TimeCollectionRule");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
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

}