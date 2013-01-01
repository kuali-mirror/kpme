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
package org.kuali.hr.time.accrual.validation;

import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class TimeOffAccrualRule extends MaintenanceDocumentRuleBase {

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for ClockLocationRule");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof TimeOffAccrual) {
			TimeOffAccrual tof = (TimeOffAccrual) pbo;
			if (tof != null) {
				valid = true;
				valid &= this.validatePrincipalId(tof);
				valid &= this.validateAccrualCategory(tof);
			}
		}

		return valid;
	}

	private boolean validatePrincipalId(TimeOffAccrual tof) {
		if (tof.getPrincipalId() != null
				&& !ValidationUtils.validatePrincipalId(tof.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + tof.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateAccrualCategory(TimeOffAccrual tof) {
		if (tof.getAccrualCategory() != null
				&& !ValidationUtils.validateAccrualCategory(tof
						.getAccrualCategory(), tof.getEffectiveDate())) {
			this.putFieldError("accrualCategory", "error.existence",
					"accrualCategory '" + tof.getAccrualCategory() + "'");
			return false;
		} else {
			return true;
		}
	}

}
