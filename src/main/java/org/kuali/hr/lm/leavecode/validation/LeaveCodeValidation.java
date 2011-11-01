package org.kuali.hr.lm.leavecode.validation;

import java.sql.Date;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class LeaveCodeValidation extends MaintenanceDocumentRuleBase {	 
	boolean validateEffectiveDate(Date effectiveDate) {
		boolean valid = true;
		if (!ValidationUtils.validateOneYearFutureDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year");
			valid = false;
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
			}
		}
		return valid;
	}
}
