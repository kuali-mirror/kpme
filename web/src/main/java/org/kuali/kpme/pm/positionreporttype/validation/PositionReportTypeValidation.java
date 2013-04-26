package org.kuali.kpme.pm.positionreporttype.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionReportTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Type");
		PositionReportType prt = (PositionReportType) this.getNewBo();
		
		if (prt != null) {
			valid = true;
			valid &= this.validateInstitution(prt);
			valid &= this.validateCampus(prt);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionReportType prt) {
		if (StringUtils.isNotEmpty(prt.getInstitution())
				&& !PmValidationUtils.validateInstitution(prt.getInstitution(), prt.getEffectiveLocalDate())) {
			this.putFieldError("institution", "error.existence", "Instituion '"
					+ prt.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionReportType prt) {
		if (StringUtils.isNotEmpty(prt.getCampus())
				&& !PmValidationUtils.validateCampus(prt.getCampus())) {
			this.putFieldError("campus", "error.existence", "Campus '"
					+ prt.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
}
