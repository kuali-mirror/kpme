/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.paygrade.validation;

import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class PayGradeValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;
		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo != null && pbo instanceof PayGrade) {
			PayGrade aPayGrade = (PayGrade) pbo;
			valid &= this.validateSalGroup(aPayGrade);
		}
		return valid;
	}
	
	private boolean validateSalGroup(PayGrade aPayGrade){
		if (!ValidationUtils.validateSalGroup(aPayGrade.getSalGroup(), aPayGrade.getEffectiveDate())) {
			this.putFieldError("salGroup", "error.existence", "Salgroup '"+ aPayGrade.getSalGroup() + "'");
			return false;
		} 
		return true;
	}
}
