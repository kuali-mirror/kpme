package org.kuali.kpme.core.bo.institution.validation;

import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class InstitutionValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		
		boolean valid = false;
		
		LOG.debug("entering custom validation for InstitutionRule");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		if (pbo instanceof Institution) {
			Institution institution = (Institution) pbo;
			if (institution != null) {
				valid = true;
			}
		}

		return valid;
	}

}
