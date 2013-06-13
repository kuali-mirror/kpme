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
package org.kuali.kpme.core.paygrade.validation;

import org.kuali.kpme.core.paygrade.PayGrade;
import org.kuali.kpme.core.salarygroup.SalaryGroup;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PayGradeValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;
		LOG.debug("entering custom validation for Pay Grade");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		if (pbo != null && pbo instanceof PayGrade) {
			PayGrade aPayGrade = (PayGrade) pbo;
			valid &= this.validateSalGroup(aPayGrade);
		}
		return valid;
	}
	
	private boolean validateSalGroup(PayGrade aPayGrade){
		SalaryGroup aSalGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup(aPayGrade.getSalGroup(), aPayGrade.getEffectiveLocalDate()) ;
		String errorMes = "Salgroup '"+ aPayGrade.getSalGroup() + "'";
		if(aSalGroup == null) {
			this.putFieldError("dataObject.salGroup", "error.existence", errorMes);
			return false;
		} else {
			if(!ValidationUtils.wildCardMatch(aSalGroup.getInstitution(), aPayGrade.getInstitution())) {
				String[] params = new String[3];
				params[0] = aPayGrade.getInstitution();
				params[1] = aSalGroup.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aSalGroup.getLocation(), aPayGrade.getLocation())) {
				String[] params = new String[3];
				params[0] = aPayGrade.getLocation();
				params[1] = aSalGroup.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		} 
		return true;
	}
}
