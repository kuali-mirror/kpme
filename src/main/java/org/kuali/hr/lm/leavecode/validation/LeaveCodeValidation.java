package org.kuali.hr.lm.leavecode.validation;

import java.sql.Date;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class LeaveCodeValidation extends MaintenanceDocumentRuleBase {	 
	boolean validateEffectiveDate(Date effectiveDate) {
		boolean valid = true;
		if (effectiveDate != null && !ValidationUtils.validateOneYearFutureDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
			valid = false;
		}
		return valid;
	}
	
	boolean validateLeaveCodeRole(LeaveCode leaveCode) {
		boolean valid = true;
		if (!leaveCode.getEmployee() && !leaveCode.getDepartmentAdmin() && !leaveCode.getApprover()) {
			this.putFieldError("employee", "error.leavecode.role.required");
			valid = false;
		}
		return valid;
	}
	
	boolean validateLeavePlan(String leavePlan, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateLeavePlan(leavePlan, asOfDate)) {
			this.putFieldError("leavePlan", "error.existence", "leavePlan '"
					+ leavePlan + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateAccrualCategory(String accrualCategory, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateAccCategory(accrualCategory, asOfDate)) {
			this.putFieldError("accrualCategory", "error.existence", "accrualCategory '"
					+ accrualCategory + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateEarnCode(String earnCode, Date asOfDate) {
		boolean valid = true;
		if (!StringUtils.isEmpty(earnCode) && !ValidationUtils.validateEarnCode(earnCode, asOfDate)) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ earnCode + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateDefaultAmountOfTime(Long defaultAmountofTime) {
		boolean valid = true;
		if ( defaultAmountofTime != null ){
			if (defaultAmountofTime.compareTo(new Long(24)) > 0  || defaultAmountofTime.compareTo(new Long(0)) < 0) {
				this.putFieldError("defaultAmountofTime", "error.leaveCode.hours", "Default Amount of Time '"
						+ defaultAmountofTime + "'");
				valid = false;
			}
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Leave Code");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof LeaveCode) {
			LeaveCode leaveCode = (LeaveCode) pbo;
			if (leaveCode != null) {
				valid = true;
				valid &= this.validateEffectiveDate(leaveCode.getEffectiveDate());
				valid &= this.validateLeaveCodeRole(leaveCode);
				valid &= this.validateLeavePlan(leaveCode.getLeavePlan(), leaveCode.getEffectiveDate());
				valid &= this.validateAccrualCategory(leaveCode.getAccrualCategory(), leaveCode.getEffectiveDate());
				valid &= this.validateEarnCode(leaveCode.getEarnCode(), leaveCode.getEffectiveDate());
				valid &= this.validateDefaultAmountOfTime(leaveCode.getDefaultAmountofTime());
			}
		}
		return valid;
	}
}
