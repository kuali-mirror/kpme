package org.kuali.hr.paygrade.validation;

import java.sql.Date;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PayGradeValidation extends MaintenanceDocumentRuleBase {	 
	
	boolean validateSalGroup(String salGroup, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateSalGroup(salGroup, asOfDate)) {
			this.putFieldError("salGroup", "error.existence",
					"Salary Group '" + salGroup + "'");
			valid = false;
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for PayGrade");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof PayGrade) {
			PayGrade payGrade = (PayGrade) pbo;
			if (payGrade != null) {
				valid = true;
				valid &= this.validateSalGroup(payGrade.getSalGroup(), payGrade.getEffectiveDate());
			}
		}
		return valid;
	}
}
