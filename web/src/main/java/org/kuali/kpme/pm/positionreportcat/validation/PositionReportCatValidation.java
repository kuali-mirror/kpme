package org.kuali.kpme.pm.positionreportcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PositionReportCatValidation extends MaintenanceDocumentRuleBase  {
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Category");
		PositionReportCategory prc = (PositionReportCategory) this.getNewBo();
		
		if (prc != null) {
			valid = true;
			valid &= this.validateInstitution(prc);
			valid &= this.validateCampus(prc);
			valid &= this.validatePositionReportType(prc);
		}
		return valid;
	}
	
	private boolean validatePositionReportType(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getPositionReportType())
				&& !PmValidationUtils.validatePositionReportType(prc.getPositionReportType(), prc.getInstitution(), prc.getCampus(), prc.getEffectiveLocalDate())) {
			String[] parameters = new String[3];
			parameters[0] = prc.getPositionReportType();
			parameters[1] = prc.getInstitution();
			parameters[2] = prc.getCampus();
			this.putFieldError("positionReportType", "institution.campus.inconsistent.positionReportType", parameters);
			return false;
		}
		return true;
	}	
	
	private boolean validateInstitution(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getInstitution())) {
			if(!PmValidationUtils.validateInstitution(prc.getInstitution(), prc.getEffectiveLocalDate())) {
				this.putFieldError("institution", "error.existence", "Instituion '"
						+ prc.getInstitution() + "'");
				return false;
			}
		}
		return true;
	}
	
	private boolean validateCampus(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getCampus())) {
			if(!PmValidationUtils.validateCampus(prc.getCampus())) {
				this.putFieldError("campus", "error.existence", "Campus '"
						+ prc.getCampus() + "'");
				return false;
			}
		}
		return true;
	}
}
