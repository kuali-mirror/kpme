package org.kuali.hr.time.accrual.validation;

import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class TimeOffAccrualRule extends MaintenanceDocumentRuleBase {

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for ClockLocationRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof TimeOffAccrual) {
			TimeOffAccrual tof = (TimeOffAccrual) pbo;
			if (tof != null) {
				valid = true;
				valid &= this.validatePrincipalId(tof);
				valid &= this.validateAccrualCategory(tof);
			}
		}

		return valid;
	}

	private boolean validatePrincipalId(TimeOffAccrual tof) {
		if (tof.getPrincipalId() != null
				&& !ValidationUtils.validatePrincipalId(tof.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + tof.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateAccrualCategory(TimeOffAccrual tof) {
		if (tof.getAccrualCategory() != null
				&& !ValidationUtils.validateAccrualCategory(tof
						.getAccrualCategory(), tof.getEffectiveDate())) {
			this.putFieldError("accrualCategory", "error.existence",
					"accrualCategory '" + tof.getAccrualCategory() + "'");
			return false;
		} else {
			return true;
		}
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
