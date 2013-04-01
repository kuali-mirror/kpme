package org.kuali.hr.time.salgroup.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.pm.util.PmValidationUtils;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class SalaryGroupValidation  extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Salary Group");
		SalGroup sg = (SalGroup) this.getNewDataObject();
		
		if (sg != null) {
			valid = true;
			valid &= this.validateInstitution(sg);
			valid &= this.validateCampus(sg);
		}
		return valid;
	}
	
	private boolean validateInstitution(SalGroup sg) {
		if (StringUtils.isNotEmpty(sg.getInstitution())
				&& !PmValidationUtils.validateInstitution(sg.getInstitution(), sg.getEffectiveDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '"
					+ sg.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(SalGroup sg) {
		if (StringUtils.isNotEmpty(sg.getCampus())
				&& !PmValidationUtils.validateCampus(sg.getCampus())) {
			this.putFieldError("dataObject.campus", "error.existence", "Campus '"
					+ sg.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
}
