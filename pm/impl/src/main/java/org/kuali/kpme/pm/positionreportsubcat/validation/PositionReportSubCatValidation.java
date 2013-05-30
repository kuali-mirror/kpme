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
package org.kuali.kpme.pm.positionreportsubcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PositionReportSubCatValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Sub Category");
		PositionReportSubCategory prsc = (PositionReportSubCategory) this.getNewBo();
		
		if (prsc != null) {
			valid = true;
			valid &= this.validatePositionReportCategory(prsc);
			valid &= this.validateInstitution(prsc);
			valid &= this.validateLocation(prsc);
		}
		return valid;
	}
	
	private boolean validatePositionReportCategory(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getPositionReportCat())
				&& StringUtils.isNotEmpty(prsc.getPositionReportType())
				&& !PmValidationUtils.validatePositionReportCategory(prsc.getPositionReportCat(), prsc.getPositionReportType(), prsc.getInstitution(), prsc.getLocation(),prsc.getEffectiveLocalDate())) {
			String[] parameters = new String[4];
			parameters[0] = prsc.getPositionReportCat();
			parameters[1] = prsc.getInstitution();
			parameters[2] = prsc.getLocation();
			parameters[3] = prsc.getPositionReportType();
			this.putFieldError("positionReportCat", "institution.location.type.inconsistent.positionReportCat", parameters);
			return false;
		}
		return true;
	}	

	private boolean validateInstitution(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getInstitution())) {
			if(!PmValidationUtils.validateInstitution(prsc.getInstitution(), prsc.getEffectiveLocalDate())) {
				this.putFieldError("institution", "error.existence", "Instituion '"
						+ prsc.getInstitution() + "'");
				return false;
			}
		}
		return true;
	}
	
	private boolean validateLocation(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getLocation())) {
			if(!ValidationUtils.validateLocation(prsc.getLocation(), prsc.getEffectiveLocalDate())) {
				this.putFieldError("location", "error.existence", "Location '"
						+ prsc.getLocation() + "'");
				return false;
			}
		}
		return true;
	}
}
