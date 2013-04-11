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
package org.kuali.hr.lm.earncodesec.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;


public class EarnCodeSecurityRule extends MaintenanceDocumentRuleBase {

	private boolean validateSalGroup(EarnCodeSecurity departmentEarnCode ) {
		if (!ValidationUtils.validateSalGroup(departmentEarnCode.getHrSalGroup(), departmentEarnCode.getEffectiveDate())) {
			this.putFieldError("hrSalGroup", "error.existence", "Salgroup '" + departmentEarnCode.getHrSalGroup()+ "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateDept(EarnCodeSecurity clr) {
		if (!ValidationUtils.validateDepartment(clr.getDept(), clr.getEffectiveDate()) && !StringUtils.equals(clr.getDept(), TkConstants.WILDCARD_CHARACTER)) {
			this.putFieldError("dept", "error.existence", "department '" + clr.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateEarnCode(EarnCodeSecurity departmentEarnCode ) {
		if (!ValidationUtils.validateEarnCode(departmentEarnCode.getEarnCode(), departmentEarnCode.getEffectiveDate())) {
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
		if (!TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin() && !TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getOrgAdminDepartments().contains(departmentEarnCode.getDept())) {
			this.putFieldError("dept", "error.department.permissions", departmentEarnCode.getDept());
			return false;
		} else {
			return true;
		}
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