package org.kuali.kpme.core.kfs.coa.businessobject.validation;

import org.kuali.kpme.core.kfs.coa.businessobject.ProjectCode;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class ProjectCodeValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		// TODO Auto-generated method stub
		ProjectCode pc = (ProjectCode) document.getNewMaintainableObject().getBusinessObject();
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		isValid &= validateChart(pc);
		isValid &= validateOrganization(pc);
		return isValid;
	}

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		// TODO Auto-generated method stub
		return super.validateMaintenanceDocument(maintenanceDocument);
	}
	
	private boolean validateChart(ProjectCode pc) {
		// TODO Auto-generated method stub
		return false;
	}
	

	private boolean validateOrganization(ProjectCode pc) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
