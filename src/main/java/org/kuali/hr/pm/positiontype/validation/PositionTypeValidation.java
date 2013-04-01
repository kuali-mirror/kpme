package org.kuali.hr.pm.positiontype.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.pm.positiontype.PositionType;
import org.kuali.hr.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Type");
		PositionType positionType = (PositionType) this.getNewDataObject();
		
		if (positionType != null) {
			valid = true;
			valid &= this.validateInstitution(positionType);
			valid &= this.validateCampus(positionType);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionType positionType) {
		if (StringUtils.isNotEmpty(positionType.getInstitution())
				&& !PmValidationUtils.validateInstitution(positionType.getInstitution(), positionType.getEffectiveDate())) {
			this.putFieldError("institution", "error.existence", "Instituion '"
					+ positionType.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionType positionType) {
		if (StringUtils.isNotEmpty(positionType.getCampus())
				&& !PmValidationUtils.validateCampus(positionType.getCampus())) {
			this.putFieldError("campus", "error.existence", "Campus '"
					+ positionType.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
}
