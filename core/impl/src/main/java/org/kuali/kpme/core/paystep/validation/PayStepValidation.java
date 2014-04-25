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
package org.kuali.kpme.core.paystep.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.paygrade.PayGrade;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.bo.validation.HrKeyedBusinessObjectValidation;
import org.kuali.kpme.core.paystep.PayStepBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

@SuppressWarnings("deprecation")
public class PayStepValidation extends HrKeyedBusinessObjectValidation {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		LOG.debug("entering custom validation for pay step");
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);

		PayStepBo payStep = (PayStepBo) this.getNewDataObject();
		
		isValid &= validateSalaryGroup(payStep);
		isValid &= validatePayGrade(payStep);
		isValid &= validatePayGradeInSalaryGroup(payStep);
		isValid &= this.validateGroupKeyCode(payStep);
		
		return isValid;
	}

	private boolean validatePayGrade(PayStepBo payStep) {
		PayGrade aPayGrade = HrServiceLocator.getPayGradeService().getPayGrade(payStep.getPayGrade(), payStep.getSalaryGroup(), payStep.getEffectiveLocalDate());
		String errorMes = "Pay Grade '" + payStep.getPayGrade() + "'";
		if(aPayGrade == null) {
			this.putFieldError("dataObject.payGrade", "error.existence", errorMes);
			return false;
		} else {
			if (!aPayGrade.getGroupKeyCode().equals(payStep.getGroupKeyCode())) {
				String[] params = new String[3];
				params[0] = payStep.getGroupKeyCode();
				params[1] = aPayGrade.getGroupKeyCode();
				params[2] = errorMes;
				this.putFieldError("dataObject.groupKeyCode", "groupKeyCode.inconsistent", params);
				return false;
			}
		}
		return true;
	}

	private boolean validateSalaryGroup(PayStepBo payStep) {
		SalaryGroup aSalGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup(payStep.getSalaryGroup(), payStep.getEffectiveLocalDate());
		String errorMes = "SalaryGroup '" + payStep.getSalaryGroup() + "'";
		if(aSalGroup != null) {
			if (!aSalGroup.getGroupKeyCode().equals(payStep.getGroupKeyCode())) {
				String[] params = new String[3];
				params[0] = payStep.getGroupKeyCode();
				params[1] = aSalGroup.getGroupKeyCode();
				params[2] = errorMes;
				this.putFieldError("dataObject.groupKeyCode", "groupKeyCode.inconsistent", params);
				return false;
			}
		} else {
			this.putFieldError("dataObject.salaryGroup", "error.existence", errorMes);
			return false;
		}
		
		return true;
	}
	
	private boolean validatePayGradeInSalaryGroup(PayStepBo payStep) {
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
}
