package org.kuali.kpme.pm.pstnrptgrpsubcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PstnRptGrpSubCatValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Group Sub Category");
		PositionReportGroupSubCategory prgsc = (PositionReportGroupSubCategory) this.getNewDataObject();
		
		if (prgsc != null) {
			valid = true;
			valid &= this.validatePstnRptSubCat(prgsc);
			valid &= this.validateInstitution(prgsc);
			valid &= this.validateCampus(prgsc);
			valid &= this.validatePstnRptGroup(prgsc);
			
		}
		return valid;
	}
	
	private boolean validatePstnRptSubCat(PositionReportGroupSubCategory prgsc) {
		if (StringUtils.isNotEmpty(prgsc.getPositionReportSubCat())
				&& StringUtils.isNotEmpty(prgsc.getInstitution())
				&& StringUtils.isNotEmpty(prgsc.getCampus())
				&& !PmValidationUtils.validatePositionReportSubCat(prgsc.getPositionReportSubCat(), prgsc.getInstitution(), prgsc.getCampus(), prgsc.getEffectiveLocalDate())) {
			String[] parameters = new String[3];
			parameters[0] = prgsc.getPositionReportSubCat();
			parameters[1] = prgsc.getInstitution();
			parameters[2] = prgsc.getCampus();
			this.putFieldError("dataObject.positionReportSubCat", "institution.campus.inconsistent.positionReportSubCat", parameters);
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateInstitution(PositionReportGroupSubCategory prgsc) {
		if (StringUtils.isNotEmpty(prgsc.getInstitution())
				&& !PmValidationUtils.validateInstitution(prgsc.getInstitution(), prgsc.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '"
					+ prgsc.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PositionReportGroupSubCategory prgsc) {
		if (StringUtils.isNotEmpty(prgsc.getCampus())
				&& !PmValidationUtils.validateCampus(prgsc.getCampus())) {
			this.putFieldError("dataObject.campus", "error.existence", "Campus '"
					+ prgsc.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validatePstnRptGroup(PositionReportGroupSubCategory prgsc) {
		if (StringUtils.isNotEmpty(prgsc.getPositionReportGroup())
				&& StringUtils.isNotEmpty(prgsc.getInstitution())
				&& StringUtils.isNotEmpty(prgsc.getCampus())
				&& !PmValidationUtils.validatePstnRptGrp(prgsc.getPositionReportGroup(), prgsc.getInstitution(), prgsc.getCampus(), prgsc.getEffectiveLocalDate())) {
			String[] parameters = new String[3];
			parameters[0] = prgsc.getPositionReportGroup();
			parameters[1] = prgsc.getInstitution();
			parameters[2] = prgsc.getCampus();
			this.putFieldError("dataObject.positionReportGroup", "institution.campus.inconsistent.positionReportGroup", parameters);
			return false;
		} else {
			return true;
		}
	}
}
