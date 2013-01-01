/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.department;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.businessobject.Organization;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class DepartmentRule extends MaintenanceDocumentRuleBase {
	
	boolean validateDepartment(Department department) {
		boolean valid = true;
		Department existingDept = TkServiceLocator.getDepartmentService().getDepartment(department.getDept(), department.getEffectiveDate());
		
		if ( department.getHrDeptId() == null ){ //KPME-1456. xichen. Only go through this validation when create a new dept. 
			if (department.getEffectiveDate() != null) {	    
				if (existingDept != null){
					if ( existingDept.getDept().equalsIgnoreCase(department.getDept()) && 
						 existingDept.getLocation().equalsIgnoreCase(department.getLocation()) ){
						// error.department.duplicate.exists=There is an exact duplicate version of this Department.					
						this.putFieldError("dept", "error.department.duplicate.exists", department.getDept());
						valid = false;
					}
				}
			}
		} 
		
		return valid;
	}

	boolean validateChart(String value) {
		boolean valid = true;
		if (value != null) {
			Chart chart = KRADServiceLocator.getBusinessObjectService()
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
			Chart chart = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, chartString);
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
		return (Organization) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(Organization.class, primaryKeys);
	}

	/**
	 * Checks for not null and size > 0, but follows the validation pattern
	 * given in this class for future expanding.
	 *
	 * @return true if there is a role, false otherwise.
	 */
	protected boolean validateRolePresent(List<TkRole> roles, Date effectiveDate) {
		boolean valid = false;

		if (roles != null && roles.size() > 0) {
			int pos = 0;
			for (TkRole role : roles) {
				valid |= role.isActive()
						&& StringUtils.equals(role.getRoleName(),
								TkConstants.ROLE_TK_DEPT_ADMIN);
				if(role.getExpirationDate() != null){
					StringBuffer prefix = new StringBuffer("roles[");
					prefix.append(pos).append("].");
					if (effectiveDate.compareTo(role.getExpirationDate()) >= 0
						|| role.getEffectiveDate().compareTo(
								role.getExpirationDate()) >= 0) {
						this.putFieldError(prefix + "expirationDate",
							"error.role.expiration");
					} else if (TKUtils.getDaysBetween(role.getEffectiveDate(), role.getExpirationDate()) > 180) {
		        		   this.putFieldError(prefix + "expirationDate",
		     						"error.role.expiration.duration");
		     				valid = false;
		        	}
				}
				pos++;
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

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof Department) {
			Department clr = (Department) pbo;
			valid = validateChart(clr.getChart());
			valid &= validateOrg(clr.getOrg());
			valid &= validateChartAndOrg(clr.getChart(), clr.getOrg());
			valid &= validateRolePresent(clr.getRoles(), clr.getEffectiveDate());
			valid &= validateDepartment(clr); // KPME1400
		}

		return valid;
	}

}
