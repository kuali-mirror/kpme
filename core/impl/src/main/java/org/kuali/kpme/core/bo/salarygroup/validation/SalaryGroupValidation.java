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
package org.kuali.kpme.core.bo.salarygroup.validation;


import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.salarygroup.SalaryGroup;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class SalaryGroupValidation  extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Salary Group");
		SalaryGroup sg = (SalaryGroup) this.getNewDataObject();
		
		if (sg != null) {
			valid = true;
			valid &= this.validateInstitution(sg);
			valid &= this.validateCampus(sg);
			valid &= this.validateLeavePlan(sg);
		}
		return valid;
	}
	
	private boolean validateInstitution(SalaryGroup sg) {
		if (StringUtils.isNotEmpty(sg.getInstitution())
				&& !ValidationUtils.validateInstitution(sg.getInstitution(), sg.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '"
					+ sg.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(SalaryGroup sg) {
		if (StringUtils.isNotEmpty(sg.getCampus())
				&& !ValidationUtils.validateCampus(sg.getCampus())) {
			this.putFieldError("dataObject.campus", "error.existence", "Campus '"
					+ sg.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLeavePlan(SalaryGroup sg) {
		if (StringUtils.isNotEmpty(sg.getLeavePlan())
				&& !ValidationUtils.validateLeavePlan(sg.getLeavePlan(), sg.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.leavePlan", "error.existence", "Leave Plan '"
					+ sg.getLeavePlan() + "'");
			return false;
		} else {
			return true;
		}
	}
}
