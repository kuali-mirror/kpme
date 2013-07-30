package org.kuali.kpme.core.kfs.coa.businessobject.validation;

import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode;
import org.kuali.kpme.core.kfs.coa.businessobject.Organization;
import org.kuali.kpme.core.kfs.coa.businessobject.ProjectCode;
import org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kpme.core.util.ValidationUtils;
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
		isValid &= validateConsistency(pc);
		return isValid;
	}

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		// TODO Auto-generated method stub
		return super.validateMaintenanceDocument(maintenanceDocument);
	}
	
	private boolean validateChart(ProjectCode projectCode) {
		// TODO Auto-generated method stub
		return ValidationUtils.validateChart(projectCode.getChartOfAccountsCode());
	}
	

	private boolean validateOrganization(ProjectCode pc) {
		// TODO Auto-generated method stub
		return ValidationUtils.validateOrganization(pc.getOrganizationCode());
	}
	
	private boolean validateConsistency(ProjectCode projectCode) {
		// TODO Auto-generated method stub
		Chart chart = projectCode.getChartOfAccounts();
		
		Organization organization = projectCode.getOrganization();
		Chart organizationChart = organization.getChartOfAccounts();
		
		return organizationChart.equals(chart) ? true : false;
	}
	
}
