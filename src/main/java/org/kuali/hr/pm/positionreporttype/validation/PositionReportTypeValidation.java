package org.kuali.hr.pm.positionreporttype.validation;

import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionReportTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Type");
		PositionReportType pbo = (PositionReportType) this.getNewBo();
		
		if (pbo != null) {
			valid = true;
//				valid &= this.validateInstitution(pbo);
			valid &= this.validateCampus(pbo);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionReportType prt) {
		if (prt.getInstitution() != null
				&& !ValidationUtils.validateInstitution(prt.getInstitution(), prt.getEffectiveDate())) {
			this.putFieldError("institution", "error.existence", "Instituion '"
					+ prt.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionReportType prt) {
		if (prt.getCampus() != null
				&& !ValidationUtils.validateCampus(prt.getCampus())) {
			this.putFieldError("campus", "error.existence", "Campus '"
					+ prt.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
}
