package org.kuali.hr.pm.positionreportcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
import org.kuali.hr.pm.util.PmValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PositionReportCatValidation extends MaintenanceDocumentRuleBase  {
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Type");
		PositionReportCategory prc = (PositionReportCategory) this.getNewBo();
		
		if (prc != null) {
			valid = true;
			valid &= this.validatePositionReportType(prc);
//				valid &= this.validateInstitution(prc);
			valid &= this.validateCampus(prc);
		}
		return valid;
	}
	
	private boolean validatePositionReportType(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getPositionReportType())
				&& !PmValidationUtils.validatePositionReportType(prc.getPositionReportType(), prc.getEffectiveDate())) {
			this.putFieldError("positionReportType", "error.existence", "Position Report Type '"
					+ prc.getPositionReportType() + "'");
			return false;
		}
		return true;
	}	
	
	private boolean validateInstitution(PositionReportCategory prc) {
		if (StringUtils.isNotEmpty(prc.getInstitution())) {
			if(!PmValidationUtils.validateInstitution(prc.getInstitution(), prc.getEffectiveDate())) {
				this.putFieldError("institution", "error.existence", "Instituion '"
						+ prc.getInstitution() + "'");
				return false;
			}
			if(StringUtils.isNotEmpty(prc.getPositionReportType())
					&& !PmValidationUtils.validateInstitutionWithPRT(prc.getPositionReportType(), prc.getInstitution(), prc.getEffectiveDate())) {
				String[] parameters = new String[2];
				parameters[0] = prc.getPositionReportType();
				parameters[1] = prc.getInstitution();
				this.putFieldError("institution", "institution.inconsistent.positionReportType", prc.getInstitution());
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
			if(StringUtils.isNotEmpty(prc.getPositionReportType())
					&& !PmValidationUtils.validateCampusWithPRT(prc.getPositionReportType(),prc.getCampus(), prc.getEffectiveDate())) {
				String[] parameters = new String[2];
				parameters[0] = prc.getPositionReportType();
				parameters[1] = prc.getCampus();
				this.putFieldError("campus", "campus.inconsistent.positionReportType", parameters);
				return false;
			}
		}
		return true;
	}
}
