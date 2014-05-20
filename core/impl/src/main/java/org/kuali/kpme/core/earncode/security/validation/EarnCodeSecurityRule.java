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
package org.kuali.kpme.core.earncode.security.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.bo.validation.HrKeyedBusinessObjectValidation;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurityBo;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.List;

@SuppressWarnings("deprecation")

//public class EarnCodeSecurityRule extends MaintenanceDocumentRuleBase {
    public class EarnCodeSecurityRule extends HrKeyedBusinessObjectValidation {
	private boolean validateSalGroup(EarnCodeSecurityBo departmentEarnCode ) {
		if (!ValidationUtils.validateSalGroup(departmentEarnCode.getHrSalGroup(), departmentEarnCode.getEffectiveLocalDate())) {
			this.putFieldError("hrSalGroup", "error.existence", "Salgroup '" + departmentEarnCode.getHrSalGroup()+ "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateDept(EarnCodeSecurityBo departmentEarnCode) {

		if (StringUtils.equals(departmentEarnCode.getDept(), HrConstants.WILDCARD_CHARACTER)) {
            return true;
        }

        if ( (departmentEarnCode.getDepartmentObj() != null)
            && (StringUtils.equals(departmentEarnCode.getDepartmentObj().getGroupKeyCode(), departmentEarnCode.getGroupKeyCode() ) ) ) {
            return true;
        } else {
            this.putFieldError("dept", "error.department.groupkey.nomatch", departmentEarnCode.getDept());
            return false;
        }

    	// KPME-3376
		// We need groupKeyCode to validate a department, but since EarnCodeSecurityBo doesn't have it,
		// we could validate a department based on EarnCodeSecurityBo's location, which is done in this.validateDeptForLocation(departmentEarnCode). 
		// So, there is no need for the validation below.  If we ever add groupKeyCode to EarnCodeSecurityBo, use this validation with dept and groupkey code
        //if (!ValidationUtils.validateDepartment(departmentEarnCode.getDept(), departmentEarnCode.getEffectiveLocalDate())) { //!StringUtils.equals(departmentEarnCode.getDept(), HrConstants.WILDCARD_CHARACTER)
		//	this.putFieldError("dept", "error.existence", "department '" + departmentEarnCode.getDept() + "'");
		//	return false;
        //}

        //no longer needed since we now have groupKeyCode with a department
        //return this.validateDeptForLocation(departmentEarnCode);
	}

    //KPME-2630
    private boolean validateDeptForLocation(EarnCodeSecurityBo departmentEarnCode){
        if (StringUtils.equals(departmentEarnCode.getLocation(), HrConstants.WILDCARD_CHARACTER)) {
            return true;
        }
        List<String> depts = HrServiceLocator.getDepartmentService().getDepartmentValuesWithLocation(departmentEarnCode.getLocation(), departmentEarnCode.getEffectiveLocalDate());
        if (depts.isEmpty()) {
            this.putFieldError("dept", "error.department.location.nomatch", departmentEarnCode.getDept());
            return false;
        } else {
            if (depts.contains(departmentEarnCode.getDept())) {
                return true;
            }
            this.putFieldError("dept", "error.department.location.nomatch", departmentEarnCode.getDept());
            return false;
        }
    }

	private boolean validateEarnCode(EarnCodeSecurityBo departmentEarnCode ) {
		if (!ValidationUtils.validateEarnCode(departmentEarnCode.getEarnCode(), departmentEarnCode.getEffectiveLocalDate())) {
			this.putFieldError("earnCode", "error.existence", "Earncode '" + departmentEarnCode.getEarnCode()+ "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateDuplication(EarnCodeSecurityBo departmentEarnCode) {
		if(ValidationUtils.duplicateDeptEarnCodeExists(departmentEarnCode)) {
			this.putFieldError("effectiveDate", "deptEarncode.duplicate.exists");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateLocation(EarnCodeSecurityBo departmentEarnCode) {
        if (StringUtils.equals(departmentEarnCode.getLocation(), HrConstants.WILDCARD_CHARACTER))
        {
            return true;
        }

        if (departmentEarnCode.getLocation() == null)
        {
            return true;
        }

        if (ValidationUtils.validateLocation(departmentEarnCode.getLocation(), departmentEarnCode.getEffectiveLocalDate()))
        {
            if ( (departmentEarnCode.getGroupKey() != null) && (departmentEarnCode.getGroupKey().getLocationId() != null)
                    && (StringUtils.equals(departmentEarnCode.getLocation(), departmentEarnCode.getGroupKey().getLocationId() ) ) ) {
                return true;
            }
            else
            {
                this.putFieldError("location", "error.groupkey.location.nomatch", departmentEarnCode.getLocation());
                return false;

            }
		} else {
            this.putFieldError("location", "error.existence", "location '" + departmentEarnCode.getLocation() + "'");
            return false;
        }
	}
	
	private boolean validateDepartmentCurrentUser(EarnCodeSecurityBo departmentEarnCode) {
		boolean isValid = true;
		
		String principalId = GlobalVariables.getUserSession().getPrincipalId();
		String department = departmentEarnCode.getDept(); 
 
		List<Department> departmentObjs = HrServiceLocator.getDepartmentService().getDepartments(department, departmentEarnCode.getLocation(), LocalDate.now());
		
		for (Department departmentObj : departmentObjs) {
			// KPME-3376
			// For now, leave the line below although it's redundant (getDepartments above already takes location - created to reduce
			// the number of departments to be returned).  
			String location = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
	
	        DateTime asOfDate = LocalDate.now().toDateTimeAtStartOfDay();
	
	        //TODO - performance
			if (!HrContext.isSystemAdmin() 
					&& !HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, asOfDate)
	    			&& !HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, asOfDate)
	    			&& !HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, asOfDate)
	    			&& !HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, asOfDate)
	    			&& !HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), location, asOfDate)
	    			&& !HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), location, asOfDate)) {
				this.putFieldError("dept", "error.department.permissions", department);
				isValid = false;
				break;
			}
		}
		
		return isValid;
	}
	
	private boolean isEarnCodeUsedByActiveTimeBlocks(EarnCodeSecurityBo departmentEarnCode){
		// KPME-1106 can not deactivate a department earn code if it used in active time blocks
		boolean valid = true;
		DateTime latestEndTimestamp =  HrServiceLocator.getCalendarBlockService().getLatestEndTimestampForEarnCode(departmentEarnCode.getEarnCode(), "Time");

		if(latestEndTimestamp == null) {
			return valid;
		}
		else {
			LocalDate deptEarnCodeEffectiveDate = LocalDate.fromDateFields(departmentEarnCode.getEffectiveDate());
			LocalDate latestEndTimestampLocalDate = latestEndTimestamp.toLocalDate();

			if ( !departmentEarnCode.isActive() && deptEarnCodeEffectiveDate.isBefore(latestEndTimestampLocalDate) ){
				this.putFieldError("active", "deptEarncode.deptEarncode.inactivate", departmentEarnCode.getEarnCode());
				return false;
			} 
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
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		if (pbo instanceof EarnCodeSecurityBo) {
			EarnCodeSecurityBo departmentEarnCode = (EarnCodeSecurityBo) pbo;

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