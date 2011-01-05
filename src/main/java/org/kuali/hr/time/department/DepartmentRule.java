package org.kuali.hr.time.department;


import java.util.HashMap;
import java.util.Map;

import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.businessobject.Organization;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class DepartmentRule extends MaintenanceDocumentRuleBase {

	boolean validateChart(String value) {
		boolean valid = false;
		
		Chart chart = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, value);
		valid = (chart != null);
		
		if (!valid) {
			this.putFieldError("chart", "dept.chart.notfound", value);
		}

		return valid;
	}

	boolean validateOrg(String value) {
		boolean valid = false;
		
		Map<String,String> primaryKeys = new HashMap<String, String>();
		primaryKeys.put("organizationCode", value);
		Organization org = (Organization) KNSServiceLocator.getBusinessObjectService().findByPrimaryKey(Organization.class, primaryKeys); //findBySinglePrimaryKey(Organization.class, value);
		valid = (org != null);
		
		if (!valid) {
			this.putFieldError("org", "dept.org.notfound", value);
		}
		
		return valid;
	}

	/**
	 * The calling method doesn't seem to examine the return value.
	 */
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;

		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof Department) {
			Department clr = (Department) pbo;
			if (clr != null) {
				valid = true;
				valid &= validateChart(clr.getChart());
				valid &= validateOrg(clr.getOrg());
			}
		}

		return valid;
	}

	@Override
	protected boolean processCustomApproveDocumentBusinessRules(MaintenanceDocument document) {
		return super.processCustomApproveDocumentBusinessRules(document);
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		return super.processCustomRouteDocumentBusinessRules(document);
	}
}
