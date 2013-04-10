package org.kuali.hr.pm.positionreportsubcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.hr.pm.util.PmValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PositionReportSubCatValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Sub Category");
		PositionReportSubCategory prsc = (PositionReportSubCategory) this.getNewBo();
		
		if (prsc != null) {
			valid = true;
			valid &= this.validatePositionReportCategory(prsc);
			valid &= this.validateInstitution(prsc);
			valid &= this.validateCampus(prsc);
		}
		return valid;
	}
	
	private boolean validatePositionReportCategory(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getPositionReportCat())
				&& StringUtils.isNotEmpty(prsc.getPositionReportType())
				&& !PmValidationUtils.validatePositionReportCategory(prsc.getPositionReportCat(), prsc.getPositionReportType(), prsc.getInstitution(), prsc.getCampus(),prsc.getEffectiveLocalDate())) {
			String[] parameters = new String[4];
			parameters[0] = prsc.getPositionReportCat();
			parameters[1] = prsc.getInstitution();
			parameters[2] = prsc.getCampus();
			parameters[3] = prsc.getPositionReportType();
			this.putFieldError("positionReportCat", "institution.campus.type.inconsistent.positionReportCat", parameters);
			return false;
		}
		return true;
	}	

	private boolean validateInstitution(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getInstitution())) {
			if(!PmValidationUtils.validateInstitution(prsc.getInstitution(), prsc.getEffectiveLocalDate())) {
				this.putFieldError("institution", "error.existence", "Instituion '"
						+ prsc.getInstitution() + "'");
				return false;
			}
		}
		return true;
	}
	
	private boolean validateCampus(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getCampus())) {
			if(!PmValidationUtils.validateCampus(prsc.getCampus())) {
				this.putFieldError("campus", "error.existence", "Campus '"
						+ prsc.getCampus() + "'");
				return false;
			}
		}
		return true;
	}
}
