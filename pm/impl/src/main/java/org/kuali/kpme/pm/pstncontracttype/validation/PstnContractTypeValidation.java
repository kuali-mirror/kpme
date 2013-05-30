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
package org.kuali.kpme.pm.pstncontracttype.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.pstncontracttype.PstnContractType;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PstnContractTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for PstnContractType");
		PstnContractType pstnContractType = (PstnContractType) this.getNewDataObject();
		
		if (pstnContractType != null) {
			valid = true;
			valid &= this.validateInstitution(pstnContractType);
			valid &= this.validateLocation(pstnContractType);
		}
		return valid;
	}
	
	private boolean validateInstitution(PstnContractType pstnContractType) {
		if (StringUtils.isNotEmpty(pstnContractType.getInstitution())
				&& !PmValidationUtils.validateInstitution(pstnContractType.getInstitution(), pstnContractType.getEffectiveLocalDate())) {
			this.putFieldError("institution", "error.existence", "Institution '"
					+ pstnContractType.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLocation(PstnContractType pstnContractType) {
		if (StringUtils.isNotEmpty(pstnContractType.getLocation())
				&& !ValidationUtils.validateLocation(pstnContractType.getLocation(), pstnContractType.getEffectiveLocalDate())) {
			this.putFieldError("location", "error.existence", "Location '"
					+ pstnContractType.getLocation() + "'");
			return false;
		} else {
			return true;
			
		}
	}
}
