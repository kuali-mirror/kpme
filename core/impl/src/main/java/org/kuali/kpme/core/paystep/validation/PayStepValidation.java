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
package org.kuali.kpme.core.paystep.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.paygrade.PayGrade;
import org.kuali.kpme.core.paystep.PayStep;
import org.kuali.kpme.core.salarygroup.SalaryGroup;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PayStepValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		LOG.debug("entering custom validation for pay step");
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);

		PayStep payStep = (PayStep) this.getNewDataObject();
		
		isValid &= validateInstitution(payStep);
		isValid &= validateLocation(payStep);
		isValid &= validateSalaryGroup(payStep);
		isValid &= validatePayGrade(payStep);
		isValid &= validatePayGradeInSalaryGroup(payStep);
		
		return isValid;
	}

	private boolean validatePayGrade(PayStep payStep) {
		PayGrade aPayGrade = HrServiceLocator.getPayGradeService().getPayGrade(payStep.getPayGrade(), payStep.getSalaryGroup(), payStep.getEffectiveLocalDate());
		String errorMes = "Pay Grade '" + payStep.getPayGrade() + "'";
		if(aPayGrade == null) {
			this.putFieldError("dataObject.payGrade", "error.existence", errorMes);
			return false;
		} else {
			if(!ValidationUtils.wildCardMatch(aPayGrade.getInstitution(), payStep.getInstitution())) {
				String[] params = new String[3];
				params[0] = payStep.getInstitution();
				params[1] = aPayGrade.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aPayGrade.getLocation(), payStep.getLocation())) {
				String[] params = new String[3];
				params[0] = payStep.getLocation();
				params[1] = aPayGrade.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		}
		return true;
	}

	private boolean validateSalaryGroup(PayStep payStep) {
		SalaryGroup aSalGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup(payStep.getSalaryGroup(), payStep.getEffectiveLocalDate());
		String errorMes = "SalaryGroup '" + payStep.getSalaryGroup() + "'";
		if(aSalGroup != null) {
			if(!ValidationUtils.wildCardMatch(aSalGroup.getInstitution(), payStep.getInstitution())) {
				String[] params = new String[3];
				params[0] = payStep.getInstitution();
				params[1] = aSalGroup.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aSalGroup.getLocation(), payStep.getLocation())) {
				String[] params = new String[3];
				params[0] = payStep.getLocation();
				params[1] = aSalGroup.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		} else {
			this.putFieldError("dataObject.salaryGroup", "error.existence", errorMes);
			return false;
		}
		
		return true;
	}
	
	private boolean validatePayGradeInSalaryGroup(PayStep payStep) {
		if(StringUtils.isNotEmpty(payStep.getSalaryGroup())
				&& ValidationUtils.validatePayGradeWithSalaryGroup(payStep.getSalaryGroup(),payStep.getPayGrade(),payStep.getEffectiveLocalDate())) {
			return true;
		} else {
			String[] params = new String[2];
			params[0] = payStep.getPayGrade();
			params[1] = payStep.getSalaryGroup();
			
			this.putFieldError("dataObject.payGrade", "salaryGroup.contains.payGrade", params);
			return false;
		}
	}

	private boolean validateLocation(PayStep payStep) {
		if (StringUtils.isNotEmpty(payStep.getLocation())
				&& ValidationUtils.validateLocation(payStep.getLocation(), payStep.getEffectiveLocalDate())) {
			return true;
		} else {
			this.putFieldError("dataObject.location", "error.existence", "Location '"
					+ payStep.getLocation() + "'");
			return false;
		}
	}

	private boolean validateInstitution(PayStep payStep) {
		if (StringUtils.isNotEmpty(payStep.getInstitution())
				&& ValidationUtils.validateInstitution(payStep.getInstitution(), payStep.getEffectiveLocalDate())) {
			return true;
		} else {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '"
					+ payStep.getInstitution() + "'");
			return false;
		}
	}

}
