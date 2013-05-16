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
package org.kuali.kpme.pm.positionappointment.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PositionAppointmentValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for position appointment");
		PositionAppointment pa = (PositionAppointment) this.getNewDataObject();
		
		if (pa != null) {
			valid = true;
			valid &= this.validateInstitution(pa);
			valid &= this.validateCampus(pa);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionAppointment pa) {
		if (StringUtils.isNotEmpty(pa.getInstitution()) && !PmValidationUtils.validateInstitution(pa.getInstitution(), pa.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '" + pa.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionAppointment pa) {
		if (StringUtils.isNotEmpty(pa.getCampus()) && !PmValidationUtils.validateCampus(pa.getCampus())) {
			this.putFieldError("dataObject.campus", "error.existence", "Campus '" + pa.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
	
}
