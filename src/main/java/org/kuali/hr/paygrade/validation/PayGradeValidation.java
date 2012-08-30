package org.kuali.hr.paygrade.validation;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class PayGradeValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;
		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo != null && pbo instanceof PayGrade) {
			PayGrade aPayGrade = (PayGrade) pbo;
			valid &= this.validateSalGroup(aPayGrade);
		}
		return valid;
	}
	
	private boolean validateSalGroup(PayGrade aPayGrade){
		if (!ValidationUtils.validateSalGroup(aPayGrade.getSalGroup(), aPayGrade.getEffectiveDate())) {
			this.putFieldError("salGroup", "error.existence", "Salgroup '"+ aPayGrade.getSalGroup() + "'");
			return false;
		} 
		return true;
	}
}
