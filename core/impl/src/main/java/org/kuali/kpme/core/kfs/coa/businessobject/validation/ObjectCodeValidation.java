package org.kuali.kpme.core.kfs.coa.businessobject.validation;

import org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class ObjectCodeValidation extends MaintenanceDocumentRuleBase {
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		// TODO Auto-generated method stub
		ObjectCode objectCode = (ObjectCode) document.getNewMaintainableObject().getBusinessObject();
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		isValid &= validateChart(objectCode);
		return isValid;
	}

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		// TODO Auto-generated method stub
		return super.validateMaintenanceDocument(maintenanceDocument);
	}
	
	private boolean validateChart(ObjectCode objectCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
