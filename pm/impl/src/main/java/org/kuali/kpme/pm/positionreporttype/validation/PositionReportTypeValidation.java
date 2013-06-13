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
package org.kuali.kpme.pm.positionreporttype.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionReportTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Type");
		PositionReportType prt = (PositionReportType) this.getNewBo();
		
		if (prt != null) {
			valid = true;
			valid &= this.validateInstitution(prt);
			valid &= this.validateLocation(prt);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionReportType prt) {
		if (StringUtils.isNotEmpty(prt.getInstitution())
				&& !ValidationUtils.validateInstitution(prt.getInstitution(), prt.getEffectiveLocalDate())) {
			this.putFieldError("institution", "error.existence", "Instituion '"
					+ prt.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLocation(PositionReportType prt) {
		if (StringUtils.isNotEmpty(prt.getLocation())
				&& !ValidationUtils.validateLocation(prt.getLocation(), prt.getEffectiveLocalDate())) {
			this.putFieldError("location", "error.existence", "Location '"
					+ prt.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}
}
