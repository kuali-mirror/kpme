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
package org.kuali.kpme.pm.positionreportcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PositionReportCatValidation extends MaintenanceDocumentRuleBase  {
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Category");
		PositionReportCategory prc = (PositionReportCategory) this.getNewBo();
		
		if (prc != null) {
			valid = true;
			valid &= this.validateInstitution(prc);
			valid &= this.validateLocation(prc);
			valid &= this.validatePositionReportType(prc);
		}
		return valid;
	}
	
	private boolean validatePositionReportType(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getPositionReportType())
				&& !PmValidationUtils.validatePositionReportType(prc.getPositionReportType(), prc.getInstitution(), prc.getLocation(), prc.getEffectiveLocalDate())) {
			String[] parameters = new String[3];
			parameters[0] = prc.getPositionReportType();
			parameters[1] = prc.getInstitution();
			parameters[2] = prc.getLocation();
			this.putFieldError("positionReportType", "institution.location.inconsistent.positionReportType", parameters);
			return false;
		}
		return true;
	}	
	
	private boolean validateInstitution(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getInstitution())) {
			if(!PmValidationUtils.validateInstitution(prc.getInstitution(), prc.getEffectiveLocalDate())) {
				this.putFieldError("institution", "error.existence", "Instituion '"
						+ prc.getInstitution() + "'");
				return false;
			}
		}
		return true;
	}
	
	private boolean validateLocation(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getLocation())) {
			if(!ValidationUtils.validateLocation(prc.getLocation(), prc.getEffectiveLocalDate())) {
				this.putFieldError("location", "error.existence", "Location '"
						+ prc.getLocation() + "'");
				return false;
			}
		}
		return true;
	}
}
