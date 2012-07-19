package org.kuali.hr.lm.leaveadjustment.validation;

import java.math.BigDecimal;
import java.sql.Date;

import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class LeaveAdjustmentValidation extends MaintenanceDocumentRuleBase{
	
	boolean validateLeavePlan(String leavePlan, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateLeavePlan(leavePlan, asOfDate)) {
			this.putFieldError("leavePlan", "error.existence", "leavePlan '"
					+ leavePlan + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateEarnCode(String earnCode, String accrualCategory, String principalId, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateEarnCodeOfAccrualCategory(earnCode, accrualCategory, principalId, asOfDate)) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ earnCode + "'");
			valid = false;
		}
		return valid;
	}

	boolean validatePrincipal(String principalId) {
		boolean valid = true;
		if (!ValidationUtils.validatePrincipalId(principalId)) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + principalId + "'");
			valid = false;
		}
		return valid;
	}

	boolean validateAccrualCategory(String accrualCategory, Date asOfDate, String principalId) {
		boolean valid = true;
		if (!ValidationUtils.validateAccCategory(accrualCategory, principalId, asOfDate)) {
			this.putFieldError("accrualCategory", "error.existence", "accrualCategory '"
					+ accrualCategory + "'");
			valid = false;
		}
		return valid;
	}
	
	private boolean validateFraction(String earnCode, BigDecimal amount, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateEarnCodeFraction(earnCode, amount, asOfDate)) {
			EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
			if(ec != null && ec.getFractionalTimeAllowed() != null) {
				BigDecimal fracAllowed = new BigDecimal(ec.getFractionalTimeAllowed());
				String[] parameters = new String[2];
				parameters[0] = earnCode;
				parameters[1] = Integer.toString(fracAllowed.scale());
				this.putFieldError("adjustmentAmount", "error.amount.fraction", parameters);
				valid = false;
			 }
		}
		return valid;
	}
		
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for LeaveAdjustment");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof LeaveAdjustment) {
			LeaveAdjustment leaveAdjustment = (LeaveAdjustment) pbo;

			if (leaveAdjustment != null) {
				valid = true;
				if(leaveAdjustment.getPrincipalId() != null) {
					valid &= this.validatePrincipal(leaveAdjustment.getPrincipalId());
				}
				if(leaveAdjustment.getAccrualCategory() != null) {
					valid &= this.validateAccrualCategory(leaveAdjustment.getAccrualCategory(),leaveAdjustment.getEffectiveDate(), leaveAdjustment.getPrincipalId());
				}
				if(leaveAdjustment.getLeavePlan() != null) {
					valid &= this.validateLeavePlan(leaveAdjustment.getLeavePlan(), leaveAdjustment.getEffectiveDate());
				}
				if(leaveAdjustment.getEarnCode() != null) {
					valid &= this.validateEarnCode(leaveAdjustment.getEarnCode(), leaveAdjustment.getAccrualCategory(), leaveAdjustment.getPrincipalId(), leaveAdjustment.getEffectiveDate());
					if(leaveAdjustment.getAdjustmentAmount() != null) {
						valid &= this.validateFraction(leaveAdjustment.getEarnCode(), leaveAdjustment.getAdjustmentAmount(), leaveAdjustment.getEffectiveDate());
					}
				}
			}
		}

		return valid;
	}

}
