package org.kuali.kpme.core.bo.paystep.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.paystep.PayStep;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PayStepValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		LOG.debug("entering custom validation for pay step");
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);

		PayStep payStep = (PayStep) this.getNewBo();
		
		isValid &= validateInstitution(payStep);
		isValid &= validateCampus(payStep);
		isValid &= validateSalaryGroup(payStep);
		isValid &= validatePayGrade(payStep);
		isValid &= validatePayGradeInSalaryGroup(payStep);
		
		return isValid;
	}

	private boolean validatePayGrade(PayStep payStep) {
		if (StringUtils.isNotEmpty(payStep.getPayGrade())
				&& !PmValidationUtils.validatePayGrade(payStep.getPayGrade())) {
			return true;
		} else {
			this.putFieldError("payGrade", "error.existence", "Pay Grade '"
					+ payStep.getPayGrade() + "'");
			return false;
		}
	}

	private boolean validateSalaryGroup(PayStep payStep) {
		if(StringUtils.isNotEmpty(payStep.getSalaryGroup())
				&& ValidationUtils.validateSalGroup(payStep.getSalaryGroup(), payStep.getEffectiveLocalDate())) {
			return true;
		} else {
			this.putFieldError("salaryGroup", "error.existence", "Salary Group '"
					+ payStep.getSalaryGroup() + "'");
			return false;
		}
	}
	
	private boolean validatePayGradeInSalaryGroup(PayStep payStep) {
		if(StringUtils.isNotEmpty(payStep.getSalaryGroup())
				&& PmValidationUtils.validatePayGradeWithSalaryGroup(payStep.getSalaryGroup(),payStep.getPayGrade(),payStep.getEffectiveLocalDate())) {
			return true;
		} else {
			String[] params = new String[2];
			params[0] = payStep.getPayGrade();
			params[1] = payStep.getSalaryGroup();
			
			this.putFieldError("payGrade", "salaryGroup.contains.payGrade", params);
			return false;
		}
	}

	private boolean validateCampus(PayStep payStep) {
		if (StringUtils.isNotEmpty(payStep.getCampus())
				&& PmValidationUtils.validateCampus(payStep.getCampus())) {
			return true;
		} else {
			this.putFieldError("campus", "error.existence", "Campus '"
					+ payStep.getCampus() + "'");
			return false;
		}
	}

	private boolean validateInstitution(PayStep payStep) {
		if (StringUtils.isNotEmpty(payStep.getInstitution())
				&& PmValidationUtils.validateInstitution(payStep.getInstitution(), payStep.getEffectiveLocalDate())) {
			return true;
		} else {
			this.putFieldError("institution", "error.existence", "Instituion '"
					+ payStep.getInstitution() + "'");
			return false;
		}
	}


}
