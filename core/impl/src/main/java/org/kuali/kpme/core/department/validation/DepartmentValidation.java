/**
 * Copyright 2004-2014 The Kuali Foundation
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
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.kfs.coa.businessobject.Organization;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.department.DepartmentPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;

@SuppressWarnings("deprecation")
public class DepartmentValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		
		if (pbo instanceof DepartmentBo) {
			DepartmentBo department = (DepartmentBo) pbo;
			if(StringUtils.isBlank(department.getHrDeptId())) {
				// do not need to validate existing department when editing an existing department 
				valid &= validateDepartment(department);
			}
			valid &= validateChart(department.getChart());
			valid &= validateOrg(department.getOrg());
			valid &= validateChartAndOrg(department.getChart(), department.getOrg());
			valid &= validateRolePresent(department.getRoleMembers(), department.getEffectiveLocalDate(), department.isPayrollApproval());
		}

		return valid;
	}
	
	protected boolean validateDepartment(DepartmentBo department) {
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
		
		if (StringUtils.isNotEmpty(chart)) {
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
		
		if (StringUtils.isNotEmpty(organization)) {
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
		
		if (StringUtils.isNotEmpty(chart) && StringUtils.isNotEmpty(organization)) {
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

	boolean validateRolePresent(List<DepartmentPrincipalRoleMemberBo> roleMembers, LocalDate effectiveDate, boolean payrollProcessorRequired) {
		boolean valid = true;
		boolean activeFlag = false;
	    boolean payrollProcessorFlag = false;

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
			if (StringUtils.equals(role.getName(), KPMERole.PAYROLL_PROCESSOR.getRoleName())) {
               payrollProcessorFlag = true;
            }
		}

		if (!activeFlag) {
			this.putGlobalError("role.required");
		}

        if (payrollProcessorRequired && !payrollProcessorFlag) {
            this.putFieldError("add.roleMembers.roleName","role.payrollProcessorRequired");
        }

		return valid & activeFlag;
	}

	@Override
	public boolean processCustomAddCollectionLineBusinessRules(
			MaintenanceDocument document, String collectionName,
			PersistableBusinessObject line) {
		boolean valid = true;
		
		//TODO: Do we really need to use member type, id, role id? If there are duplicate role names listed in the drop downs, this is just going to cause confusion...
		if(line instanceof DepartmentPrincipalRoleMemberBo) {
			DepartmentPrincipalRoleMemberBo roleMember = (DepartmentPrincipalRoleMemberBo) line;
			DepartmentBo location = (DepartmentBo) document.getNewMaintainableObject().getDataObject();
			List<DepartmentPrincipalRoleMemberBo> existingRoleMembers = location.getRoleMembers();
			for(ListIterator<DepartmentPrincipalRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "roleMembers[" + index + "].";
				DepartmentPrincipalRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPrincipalId(),roleMember.getPrincipalId())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
						}
					}
				}
			}
			existingRoleMembers = location.getInactiveRoleMembers();
			for(ListIterator<DepartmentPrincipalRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "inactiveRoleMembers[" + index + "].";
				DepartmentPrincipalRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPrincipalId(),roleMember.getPrincipalId())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
						}
					}
				}
			}
		}
		
		return valid;
	}

}