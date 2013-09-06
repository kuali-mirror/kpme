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
package org.kuali.kpme.core.department.validation;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.kfs.coa.businessobject.Organization;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.department.DepartmentPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;

@SuppressWarnings("deprecation")
public class DepartmentValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		
		if (pbo instanceof Department) {
			Department department = (Department) pbo;
			if(StringUtils.isBlank(department.getHrDeptId())) {
				// do not need to validate existing department when editing an existing department 
				valid &= validateDepartment(department);
			}
			valid &= validateChart(department.getChart());
			valid &= validateOrg(department.getOrg());
			valid &= validateChartAndOrg(department.getChart(), department.getOrg());
			valid &= validateRolePresent(department.getRoleMembers(), department.getEffectiveLocalDate());
		}

		return valid;
	}
	
	protected boolean validateDepartment(Department department) {
		boolean valid = true;
		
		if (StringUtils.isNotBlank(department.getDept()) && department.getEffectiveDate() != null) {
			List<Department> depts = HrServiceLocator.getDepartmentService().getDepartments(department.getDept());
			if (depts != null && depts.size() > 0) {
				 this.putFieldError("dept", "error.department.duplicate.exists", department.getDept());
				 valid = false;
			}
		}
		
		return valid;
	}

	protected boolean validateChart(String chart) {
		boolean valid = true;
		
		if (chart != null) {
			Chart chartObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, chart);

			if (chartObj == null) {
				this.putFieldError("chart", "dept.chart.notfound", chart);
				valid = false;
			}
		}
		
		return valid;
	}

	protected boolean validateOrg(String organization) {
		boolean valid = true;
		
		if (organization != null) {
			Organization organizationObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Organization.class, organization);

			if (organizationObj == null) {
				this.putFieldError("org", "dept.org.notfound", organization);
				valid = false;
			}
		}
		
		return valid;
	}

	boolean validateChartAndOrg(String chart, String organization) {
		boolean valid = true;
		
		if (chart != null && organization != null) {
			Chart chartObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, chart);
			Organization organizationObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Organization.class, organization);
			if (chartObj != null && organizationObj != null) {
				Chart organizationChart = organizationObj.getChartOfAccounts();
				if (!StringUtils.equals(chartObj.getChartOfAccountsCode(), organizationChart.getChartOfAccountsCode())) {
					String[] params = new String[] {organization, chart};
					this.putFieldError("org", "dept.org.chart.notmatch", params);
					valid = false;
				}
			}
		}
		
		return valid;
	}

	boolean validateRolePresent(List<DepartmentPrincipalRoleMemberBo> roleMembers, LocalDate effectiveDate) {
		boolean valid = true;
		boolean activeFlag = false;
		
		for (ListIterator<DepartmentPrincipalRoleMemberBo> iterator = roleMembers.listIterator(); iterator.hasNext(); ) {
			int index = iterator.nextIndex();
			RoleMemberBo roleMember = iterator.next();
			Role role = KimApiServiceLocator.getRoleService().getRole(roleMember.getRoleId());
			activeFlag |= roleMember.isActive();
			
			String prefix = "roleMembers[" + index + "].";
			
			if (roleMember.getActiveToDateValue() != null) {
				if (effectiveDate.compareTo(roleMember.getActiveToDate().toLocalDate()) >= 0
						|| roleMember.getActiveFromDateValue().compareTo(roleMember.getActiveToDateValue()) >= 0) {
					this.putFieldError(prefix + "expirationDate", "error.role.expiration");
					valid = false;
				} 
			}
			if (StringUtils.equals(role.getName(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName())) {
				if(roleMember.getActiveToDateValue() == null) {
					this.putFieldError(prefix + "expirationDate", "error.role.expiration.required");
					valid = false;
				} else {
					LocalDate dateLimit = roleMember.getActiveFromDate().toLocalDate().plusMonths(6).minusDays(1);
					if(roleMember.getActiveToDate().toLocalDate().isAfter(dateLimit)) {
						this.putFieldError(prefix + "expirationDate", "error.role.expiration.duration");
						valid = false;
					}
				}
			}
				
		}

		if (!activeFlag) {
			this.putGlobalError("role.required");
		}

		return valid & activeFlag;
	}

}