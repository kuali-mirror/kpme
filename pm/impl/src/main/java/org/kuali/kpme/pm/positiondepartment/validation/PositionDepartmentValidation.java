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
package org.kuali.kpme.pm.positiondepartment.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionDepartmentValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Department");
		PositionDepartment positionDepartment = (PositionDepartment) this.getNewDataObject();
		
		if (positionDepartment != null) {
			valid = true;
			valid &= this.validateInstitution(positionDepartment);
			valid &= this.validateLocation(positionDepartment);
			valid &= this.validateDepartment(positionDepartment);
			valid &= this.validateAffiliation(positionDepartment);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionDepartment positionDepartment) {
		if (StringUtils.isNotEmpty(positionDepartment.getInstitution())
				&& !ValidationUtils.validateInstitution(positionDepartment.getInstitution(), positionDepartment.getEffectiveLocalDate())) {
			this.putFieldError("institution", "error.existence", "Institution '"
					+ positionDepartment.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLocation(PositionDepartment positionDepartment) {
		if (StringUtils.isNotEmpty(positionDepartment.getLocation())
				&& !ValidationUtils.validateLocation(positionDepartment.getLocation(), positionDepartment.getEffectiveLocalDate())) {
			this.putFieldError("location", "error.existence", "Location '"
					+ positionDepartment.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateDepartment(PositionDepartment positionDepartment) {
		if (StringUtils.isNotEmpty(positionDepartment.getDepartment())
				&& !ValidationUtils.validateDepartment(positionDepartment.getDepartment(), positionDepartment.getEffectiveLocalDate())) {
			this.putFieldError("department", "error.existence", "Department '"
					+ positionDepartment.getDepartment() + "'");
			return false;
		}
		Department dep = HrServiceLocator.getDepartmentService().getDepartment(positionDepartment.getDepartment(), positionDepartment.getEffectiveLocalDate());
		if(dep == null ) {
			this.putFieldError("department", "error.existence", "Department '"
					+ positionDepartment.getDepartment() + "'");
			return false;
		} else {
			if(!ValidationUtils.wildCardMatch(dep.getLocation(), positionDepartment.getLocation())) {
				String[] params = new String[3];
				params[0] = positionDepartment.getLocation();
				params[1] = dep.getLocation();
				params[2] = "Department '" + positionDepartment.getDepartment() + "'";
				this.putFieldError("department", "location.inconsistent", params);
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validateAffiliation(PositionDepartment positionDepartment) {
		if (StringUtils.isNotEmpty(positionDepartment.getPositionDeptAffl())
				&& !PmValidationUtils.validateAffiliation(positionDepartment.getPositionDeptAffl(), positionDepartment.getEffectiveLocalDate())) {
			this.putFieldError("positionDeptAffl", "error.existence", "Affiliation '"
					+ positionDepartment.getPositionDeptAffl() + "'");
			return false;
		} else {
			return true;
		}
	}
}

