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
//				valid &= this.validateInstitution(prsc);
			valid &= this.validateCampus(prsc);
		}
		return valid;
	}
	
	private boolean validatePositionReportCategory(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getPositionReportCat())
				&& !PmValidationUtils.validatePositionReportCategory(prsc.getPositionReportCat(), prsc.getEffectiveDate())) {
			this.putFieldError("positionReportCategory", "error.existence", "Position Report Category '"
					+ prsc.getPositionReportCat() + "'");
			return false;
		}
		return true;
	}	

	private boolean validateInstitution(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getInstitution())) {
			if(!PmValidationUtils.validateInstitution(prsc.getInstitution(), prsc.getEffectiveDate())) {
				this.putFieldError("institution", "error.existence", "Instituion '"
						+ prsc.getInstitution() + "'");
				return false;
			}
			if(StringUtils.isNotEmpty(prsc.getPositionReportType())
					&& !PmValidationUtils.validateInstitutionWithPRT(prsc.getPositionReportType(), prsc.getInstitution(), prsc.getEffectiveDate())) {
				String[] parameters = new String[2];
				parameters[0] = prsc.getPositionReportType();
				parameters[1] = prsc.getInstitution();
				this.putFieldError("institution", "institution.inconsistent.positionReportType", prsc.getInstitution());
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
			if(StringUtils.isNotEmpty(prsc.getPositionReportType())
					&& !PmValidationUtils.validateCampusWithPRT(prsc.getPositionReportType(),prsc.getCampus(), prsc.getEffectiveDate())) {
				String[] parameters = new String[2];
				parameters[0] = prsc.getPositionReportType();
				parameters[1] = prsc.getCampus();
				this.putFieldError("campus", "campus.inconsistent.positionReportType", parameters);
				return false;
			}
		}
		return true;
	}
}
