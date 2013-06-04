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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

public class ShiftDifferentialRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateSalGroup(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getHrSalGroup() != null
				&& !StringUtils.equals(shiftDifferentialRule.getHrSalGroup(),
						HrConstants.WILDCARD_CHARACTER)
				&& !ValidationUtils.validateSalGroup(shiftDifferentialRule
						.getHrSalGroup(), shiftDifferentialRule
						.getEffectiveLocalDate())) {
			this.putFieldError("hrSalGroup", "error.existence", "Salgroup '"
					+ shiftDifferentialRule.getHrSalGroup() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateEarnCode(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getEarnCode() != null
				&& !ValidationUtils.validateEarnCode(shiftDifferentialRule
						.getEarnCode(), shiftDifferentialRule
						.getEffectiveLocalDate())) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ shiftDifferentialRule.getEarnCode() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateLocation(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getLocation() != null
				&& !StringUtils.equals(shiftDifferentialRule.getLocation(),
						HrConstants.WILDCARD_CHARACTER)
				&& !ValidationUtils.validateLocation(shiftDifferentialRule
						.getLocation(), shiftDifferentialRule
						.getEffectiveLocalDate())) {
			this.putFieldError("location", "error.existence", "location '"
					+ shiftDifferentialRule.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validatePayGrade(ShiftDifferentialRule shiftDifferentialRule) {
		if (shiftDifferentialRule.getPayGrade() != null
				&& !StringUtils.equals(shiftDifferentialRule.getPayGrade(),
						HrConstants.WILDCARD_CHARACTER)
				&& !ValidationUtils.validatePayGrade(shiftDifferentialRule
						.getPayGrade(), shiftDifferentialRule.getHrSalGroup(), shiftDifferentialRule
						.getEffectiveLocalDate())) {
			this.putFieldError("payGrade", "error.existence", "pay grade '"
					+ shiftDifferentialRule.getPayGrade() + "'");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for ShiftDifferentialRule");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof ShiftDifferentialRule) {
			ShiftDifferentialRule shiftDifferentialRule = (ShiftDifferentialRule) pbo;
			shiftDifferentialRule.setUserPrincipalId(GlobalVariables
					.getUserSession().getLoggedInUserPrincipalName());
			if (shiftDifferentialRule != null) {
				valid = true;
				valid &= this.validateLocation(shiftDifferentialRule);
				valid &= this.validateSalGroup(shiftDifferentialRule);
				valid &= this.validatePayGrade(shiftDifferentialRule);
				valid &= this.validateEarnCode(shiftDifferentialRule);
			}
		}

		return valid;
	}

}
