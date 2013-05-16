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
package org.kuali.kpme.pm.positiontype.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.positiontype.PositionType;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Type");
		PositionType positionType = (PositionType) this.getNewDataObject();
		
		if (positionType != null) {
			valid = true;
			valid &= this.validateInstitution(positionType);
			valid &= this.validateCampus(positionType);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionType positionType) {
		if (StringUtils.isNotEmpty(positionType.getInstitution())
				&& !PmValidationUtils.validateInstitution(positionType.getInstitution(), positionType.getEffectiveLocalDate())) {
			this.putFieldError("institution", "error.existence", "Institution '"
					+ positionType.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionType positionType) {
		if (StringUtils.isNotEmpty(positionType.getCampus())
				&& !PmValidationUtils.validateCampus(positionType.getCampus())) {
			this.putFieldError("campus", "error.existence", "Campus '"
					+ positionType.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
}
