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
package org.kuali.kpme.pm.position.validation;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.position.Position;
import org.kuali.kpme.pm.position.PositionDuty;
import org.kuali.kpme.pm.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PositionValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position");
		Position aPosition = (Position) this.getNewDataObject();

		if (aPosition != null) {
			valid = true;
			valid &= this.validateOverviewPage(aPosition);
			valid &= this.validateClassificationPage(aPosition);
			valid &= this.validateDutyListPercentage(aPosition);
			valid &= this.validatePrimaryDepartment(aPosition);
		}
		return valid;
	}

	private boolean validateDutyListPercentage(Position aPosition) {
		if (CollectionUtils.isNotEmpty(aPosition.getDutyList())) {
			BigDecimal sum = BigDecimal.ZERO;
			for (PositionDuty aDuty : aPosition.getDutyList()) {
				if (aDuty != null && aDuty.getPercentage() != null) {
					sum = sum.add(aDuty.getPercentage());
				}
			}
			if (sum.compareTo(new BigDecimal(100)) > 0) {
				String[] parameters = new String[1];
				parameters[0] = sum.toString();
				this.putFieldError("dataObject.dutyList",
						"duty.percentage.exceedsMaximum", parameters);
				return false;
			}
		}
		return true;
	}

	// KPME-3016  
	// Now each section is its own page that if you want to show errors globally, you have to catch them globally
	private boolean validateOverviewPage(Position aPosition) {

		if (aPosition.getEffectiveDate() == null
				|| StringUtils.isEmpty(aPosition.getDescription())
				|| StringUtils.isEmpty(aPosition.getPositionStatus())
				|| StringUtils.isEmpty(aPosition.getAppointmentType())
				|| StringUtils.isEmpty(aPosition.getTemporary())
				|| StringUtils.isEmpty(aPosition.getContract())) {

			this.putFieldError("positionNumber", "error.overview.fields.required");
			return false;
		} else {
			// validate appointment type
			if (!PmValidationUtils.validatePositionAppointmentType(aPosition.getAppointmentType(), aPosition.getInstitution(), aPosition.getLocation(), aPosition.getEffectiveLocalDate())) {				
				this.putFieldError("appointmentType", "error.existence", "Appointment Type '" + aPosition.getAppointmentType() + "'");
				return false;
			}
			// validate contract type
			if (!StringUtils.isEmpty(aPosition.getContractType())) {
				if (!PmValidationUtils.validatePositionContractType(aPosition.getContractType(), aPosition.getInstitution(), aPosition.getLocation(), aPosition.getEffectiveLocalDate())) {
					this.putFieldError("contractType", "error.existence", "Contract Type '" + aPosition.getContractType() + "'");
					return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean validateClassificationPage(Position aPosition) {

		if (StringUtils.isEmpty(aPosition.getPmPositionClassId())
				|| StringUtils.isEmpty(aPosition.getTenureEligible())
				|| StringUtils.isEmpty(aPosition.getBenefitsEligible())
				|| StringUtils.isEmpty(aPosition.getLeaveEligible())) {

			this.putFieldError("pmPositionClassId", "error.classication.fields.required");
			return false;
		}
		
		return true;
	}
	
	private boolean validatePrimaryDepartment(Position aPosition) {

		if (CollectionUtils.isNotEmpty(aPosition.getDepartmentList())) {
			for (PositionDepartment aDepartment : aPosition.getDepartmentList()) {
				if(aDepartment != null && aDepartment.getPositionDeptAfflObj() != null) {
					PositionDepartmentAffiliation pda = (PositionDepartmentAffiliation)aDepartment.getPositionDeptAfflObj();
					if (pda.isPrimaryIndicator()) {
						return true;
					}
				}
			}
		}

		this.putFieldError("primaryDepartment", "error.primaryDepartment.required");
		return false;
	}


}
