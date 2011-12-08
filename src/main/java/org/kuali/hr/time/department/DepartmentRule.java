package org.kuali.hr.time.department;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.businessobject.Organization;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentRule extends MaintenanceDocumentRuleBase {

	boolean validateChart(String value) {
		boolean valid = true;
		if (value != null) {
			Chart chart = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(Chart.class, value);
			valid = (chart != null);

			if (!valid) {
				this.putFieldError("chart", "dept.chart.notfound", value);
			}
		}
		return valid;
	}

	boolean validateOrg(String value) {
		boolean valid = true;
		if (value != null) {
			Organization org = this.getOrganization(value);
			valid = (org != null);

			if (!valid) {
				this.putFieldError("org", "dept.org.notfound", value);
			}
		}
		return valid;
	}

	boolean validateChartAndOrg(String chartString, String orgString) {
		if(chartString != null && orgString != null) {
			Chart chart = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, chartString);
			Organization org = this.getOrganization(orgString);
			if(chart != null && org != null) {
				Chart chartTemp = org.getChartOfAccounts();
				if(!chart.getChartOfAccountsCode().equals(chartTemp.getChartOfAccountsCode())) {
					String[] params = new String[]{orgString, chartString};
					this.putFieldError("org", "dept.org.chart.notmatch", params);
					return false;
				}
			}
		}
		return true;
	}

	Organization getOrganization(String orgCode) {
		Map<String, String> primaryKeys = new HashMap<String, String>();
		primaryKeys.put("organizationCode", orgCode);
		return (Organization) KNSServiceLocator.getBusinessObjectService().findByPrimaryKey(Organization.class, primaryKeys);
	}

	/**
	 * Checks for not null and size > 0, but follows the validation pattern
	 * given in this class for future expanding.
	 *
	 * @return true if there is a role, false otherwise.
	 */
	protected boolean validateRolePresent(List<TkRole> roles) {
		boolean valid = false;

		if (roles != null && roles.size() > 0) {
			for (TkRole role : roles) {
				valid |= role.isActive()
						&& StringUtils.equals(role.getRoleName(),
								TkConstants.ROLE_TK_DEPT_ADMIN);
			}
		}

		if (!valid) {
			this.putGlobalError("role.required");
		}

		return valid;
	}

	/**
	 * The calling method doesn't seem to examine the return value.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof Department) {
			Department clr = (Department) pbo;
			valid = validateChart(clr.getChart());
			valid &= validateOrg(clr.getOrg());
			valid &= validateChartAndOrg(clr.getChart(), clr.getOrg());
			valid &= validateRolePresent(clr.getRoles());
		}

		return valid;
	}

}
