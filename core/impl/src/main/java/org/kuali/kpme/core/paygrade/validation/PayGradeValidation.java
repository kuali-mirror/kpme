/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.paygrade.PayGradeBo;
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
		if (pbo != null && pbo instanceof PayGradeBo) {
			PayGradeBo aPayGrade = (PayGradeBo) pbo;
			valid &= this.validateSalGroup(aPayGrade);
			valid &= this.validateRateAttrubutes(aPayGrade);
		}
		return valid;
	}
	
	private boolean validateSalGroup(PayGradeBo aPayGrade){
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
	
	private boolean validateRateAttrubutes(PayGradeBo aPayGrade){
		boolean isValid = true;
		// check if one of the min, mid or max provided then Rate time should be required
		if(aPayGrade.getMinRate() != null || aPayGrade.getMidPointRate() != null || aPayGrade.getMaxRate() != null ) {
			if(aPayGrade.getRateType() == null) {
				this.putFieldError("dataObject.rateType", "error.required", "Rate Type");
				isValid = false;
			}
			
			// check for min rate
			if(isValid && aPayGrade.getMinRate() != null) {
				isValid = (aPayGrade.getMidPointRate() != null ?(aPayGrade.getMinRate().compareTo(aPayGrade.getMidPointRate()) < 0)  : true);
				isValid = isValid && (aPayGrade.getMaxRate() != null ? (aPayGrade.getMinRate().compareTo(aPayGrade.getMaxRate()) < 0)  : true);
				if(!isValid) {
					this.putFieldError("dataObject.minRate", "error.minrate.invalid");
				}
			}
			// check for mid point rate
			if(isValid && aPayGrade.getMidPointRate() != null) {
				isValid = isValid && (aPayGrade.getMinRate() != null ? (aPayGrade.getMidPointRate().compareTo(aPayGrade.getMinRate()) > 0)  : true);
				isValid = isValid && (aPayGrade.getMaxRate() != null ? (aPayGrade.getMidPointRate().compareTo(aPayGrade.getMaxRate()) < 0)  : true);
				if(!isValid) {
					this.putFieldError("dataObject.midPointRate", "error.midpointrate.invalid");
				}
			}
			// check for max rate
			if(isValid && aPayGrade.getMaxRate() != null) {
				isValid = isValid && (aPayGrade.getMinRate() != null ? aPayGrade.getMaxRate().compareTo(aPayGrade.getMinRate()) > 0  : true);
				isValid = isValid && (aPayGrade.getMidPointRate() != null ? aPayGrade.getMaxRate().compareTo(aPayGrade.getMidPointRate()) > 0  : true);
				if(!isValid) {
					this.putFieldError("dataObject.maxRate", "error.maxrate.invalid");
				}
			}
		}
		return isValid;
	}
}
