package org.kuali.kpme.pm.positionappointment.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PositionAppointmentValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for position appointment");
		PositionAppointment pa = (PositionAppointment) this.getNewDataObject();
		
		if (pa != null) {
			valid = true;
			valid &= this.validateInstitution(pa);
			valid &= this.validateCampus(pa);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionAppointment pa) {
		if (StringUtils.isNotEmpty(pa.getInstitution()) && !PmValidationUtils.validateInstitution(pa.getInstitution(), pa.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '" + pa.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionAppointment pa) {
		if (StringUtils.isNotEmpty(pa.getCampus()) && !PmValidationUtils.validateCampus(pa.getCampus())) {
			this.putFieldError("dataObject.campus", "error.existence", "Campus '" + pa.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
	
}
