package org.kuali.hr.pm.positionreportgroup.validation;

import org.kuali.hr.pm.positionreportgroup.PositionReportGroup;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionReportGroupValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Type");
		PositionReportGroup prg = (PositionReportGroup) this.getNewBo();
		
		if (prg != null) {
			valid = true;
//				valid &= this.validateInstitution(prg);
			valid &= this.validateCampus(prg);
		}
		return valid;
	}
	
	private boolean validateInstitution(PositionReportGroup prg) {
		if (prg.getInstitution() != null
				&& !ValidationUtils.validateInstitution(prg.getInstitution(), prg.getEffectiveDate())) {
			this.putFieldError("institution", "error.existence", "Instituion '"
					+ prg.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionReportGroup prg) {
		if (prg.getCampus() != null
				&& !ValidationUtils.validateCampus(prg.getCampus())) {
			this.putFieldError("campus", "error.existence", "Campus '"
					+ prg.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
}
