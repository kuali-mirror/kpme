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
package org.kuali.hr.core.earncode.security.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.ValidationUtils;
import org.kuali.hr.core.department.Department;
import org.kuali.hr.core.earncode.security.EarnCodeSecurity;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;


public class EarnCodeSecurityRule extends MaintenanceDocumentRuleBase {

	private boolean validateSalGroup(EarnCodeSecurity departmentEarnCode ) {
		if (!ValidationUtils.validateSalGroup(departmentEarnCode.getHrSalGroup(), departmentEarnCode.getEffectiveLocalDate())) {
			this.putFieldError("hrSalGroup", "error.existence", "Salgroup '" + departmentEarnCode.getHrSalGroup()+ "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateDept(EarnCodeSecurity clr) {
		if (!ValidationUtils.validateDepartment(clr.getDept(), clr.getEffectiveLocalDate()) && !StringUtils.equals(clr.getDept(), TkConstants.WILDCARD_CHARACTER)) {
			this.putFieldError("dept", "error.existence", "department '" + clr.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateEarnCode(EarnCodeSecurity departmentEarnCode ) {
		if (!ValidationUtils.validateEarnCode(departmentEarnCode.getEarnCode(), departmentEarnCode.getEffectiveLocalDate())) {
			this.putFieldError("earnCode", "error.existence", "Earncode '" + departmentEarnCode.getEarnCode()+ "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateDuplication(EarnCodeSecurity departmentEarnCode) {
		if(ValidationUtils.duplicateDeptEarnCodeExists(departmentEarnCode)) {
			this.putFieldError("effectiveDate", "deptEarncode.duplicate.exists");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateLocation(EarnCodeSecurity departmentEarnCode) {
		if (departmentEarnCode.getLocation() != null
				&& !ValidationUtils.validateLocation(departmentEarnCode.getLocation(), null) && 
				!StringUtils.equals(departmentEarnCode.getLocation(), TkConstants.WILDCARD_CHARACTER)) {
			this.putFieldError("location", "error.existence", "location '"
					+ departmentEarnCode.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateDepartmentCurrentUser(EarnCodeSecurity departmentEarnCode) {
		boolean isValid = true;
		
		String principalId = GlobalVariables.getUserSession().getPrincipalId();
		String department = departmentEarnCode.getDept();
		Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
		String location = departmentObj != null ? departmentObj.getLocation() : null;
		
		if (!TKContext.isSystemAdmin() 
				&& !TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
    			&& !LmServiceLocator.getLMRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
    			&& !TkServiceLocator.getTKRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
    			&& !LmServiceLocator.getLMRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())) {
			this.putFieldError("dept", "error.department.permissions", department);
			isValid = false;
		}
		
		return isValid;
	}
	
	private boolean isEarnCodeUsedByActiveTimeBlocks(EarnCodeSecurity departmentEarnCode){
		// KPME-1106 can not deactivate a department earn code if it used in active time blocks
		boolean valid = true;
		List<TimeBlock> latestEndTimestampTimeBlocks =  TkServiceLocator.getTimeBlockService().getLatestEndTimestampForEarnCode(departmentEarnCode.getEarnCode());
		
		if ( !departmentEarnCode.isActive() && !latestEndTimestampTimeBlocks.isEmpty() && departmentEarnCode.getEffectiveDate().before(latestEndTimestampTimeBlocks.get(0).getEndDate()) ){
			this.putFieldError("active", "deptEarncode.deptEarncode.inactivate", departmentEarnCode.getEarnCode());
			return false;
		} 
		
		return valid;
		
	}

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for EarnCodeSecurity");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof EarnCodeSecurity) {
			EarnCodeSecurity departmentEarnCode = (EarnCodeSecurity) pbo;

			if (departmentEarnCode != null) {
				valid = true;
				valid &= this.validateSalGroup(departmentEarnCode);
				valid &= this.validateDept(departmentEarnCode);
				valid &= this.validateEarnCode(departmentEarnCode);
				valid &= this.validateDuplication(departmentEarnCode);
				valid &= this.validateLocation(departmentEarnCode);
				valid &= this.validateDepartmentCurrentUser(departmentEarnCode);
				valid &= this.isEarnCodeUsedByActiveTimeBlocks(departmentEarnCode);
			}

		}

		return valid;
	}

}