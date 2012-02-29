package org.kuali.hr.lm.leaveplan.validation;

import java.sql.Date;

import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class LeavePlanValidation extends MaintenanceDocumentRuleBase {

	boolean validateEffectiveDate(Date effectiveDate) {
		boolean valid = true;
		if (effectiveDate != null
				&& !ValidationUtils.validateOneYearFutureDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
			valid = false;
		}
		return valid;
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		PersistableBusinessObject pbo = this.getNewBo();
		if(pbo instanceof LeavePlan) {
			LeavePlan leavePlanObj = (LeavePlan) pbo;
			if(leavePlanObj != null) {
				valid = true;
				valid &= this.validateEffectiveDate(leavePlanObj.getEffectiveDate());
			}
		}
		return valid;
	}
	
	
	
}
