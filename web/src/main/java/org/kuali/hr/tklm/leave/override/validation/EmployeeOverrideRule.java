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
package org.kuali.hr.tklm.leave.override.validation;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.hr.core.accrualcategory.AccrualCategory;
import org.kuali.hr.core.principal.PrincipalHRAttributes;
import org.kuali.hr.tklm.leave.override.EmployeeOverride;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class EmployeeOverrideRule extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof EmployeeOverride) {
			EmployeeOverride eo = (EmployeeOverride) pbo;
			valid &= this.validatePrincipalHRAttributes(eo);
			valid &= this.validateAccrualCategory(eo);
			valid &= this.validateLeavePlan(eo);
		}
		return valid;
	}
	
	boolean validateLeavePlan(EmployeeOverride eo) {
		if(StringUtils.isEmpty(eo.getLeavePlan())) {
			this.putFieldError("leavePlan", "error.employeeOverride.leavePlan.notfound");
			return false;
		} else if(eo.getEffectiveDate() != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().
				getAccrualCategory(eo.getAccrualCategory(), eo.getEffectiveLocalDate());
			PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().
				getPrincipalCalendar(eo.getPrincipalId(), eo.getEffectiveLocalDate());
			if(ac != null && pha != null && !ac.getLeavePlan().equals(pha.getLeavePlan())) {
				this.putFieldError("leavePlan", "error.employeeOverride.leavePlan.inconsistent", eo.getAccrualCategory() );
				return false;
			}
		}
		return true;
	}
	
	boolean validateAccrualCategory(EmployeeOverride eo) {
		if(eo.getAccrualCategory() != null && eo.getEffectiveDate() != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().
				getAccrualCategory(eo.getAccrualCategory(), eo.getEffectiveLocalDate());
			if(ac == null) {
				this.putFieldError("accrualCategory", "error.employeeOverride.accrualCategory.notfound", eo.getAccrualCategory() );
				return false;
			}
		}
		return true;
	}
	
	boolean validatePrincipalHRAttributes(EmployeeOverride eo) {
		if(eo.getPrincipalId() != null && eo.getEffectiveDate() != null) {
			PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(eo.getPrincipalId(), eo.getEffectiveLocalDate());
			if(pha == null) {
				this.putFieldError("principalId", "error.employeeOverride.principalHRAttributes.notfound", eo.getPrincipalId() );
				return false;
			}
		}
		return true;
	}

}
