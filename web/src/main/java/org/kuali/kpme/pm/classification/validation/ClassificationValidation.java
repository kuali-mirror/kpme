package org.kuali.kpme.pm.classification.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class ClassificationValidation extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Classification");
		Classification clss = (Classification) this.getNewDataObject();
		
		if (clss != null) {
			valid = true;
			valid &= this.validateInstitution(clss);
			valid &= this.validateCampus(clss);
			valid &= this.validateSalGroup(clss);
//			valid &= this.validatePercentTime(clss);
			valid &= this.validateLeavePlan(clss);
			valid &= this.validateReportingGroup(clss);
			valid &= this.validatePositionType(clss);
		}
		return valid;
	}
	
	private boolean validateInstitution(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getInstitution())
				&& !PmValidationUtils.validateInstitution(clss.getInstitution(), clss.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '"
					+ clss.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getCampus())
				&& !PmValidationUtils.validateCampus(clss.getCampus())) {
			this.putFieldError("dataObject.campus", "error.existence", "Campus '"
					+ clss.getCampus() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLeavePlan(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getLeavePlan())
				&& !ValidationUtils.validateLeavePlan(clss.getLeavePlan(), clss.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.leavePlan", "error.existence", "Leave Plan '"
					+ clss.getLeavePlan() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateSalGroup(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getSalaryGroup())
			&& (PmValidationUtils.isWildCard(clss.getSalaryGroup())
					|| (!PmValidationUtils.isWildCard(clss.getSalaryGroup()) 
							&& !ValidationUtils.validateSalGroup(clss.getSalaryGroup(), clss.getEffectiveLocalDate())))) {
					this.putFieldError("dataObject.salaryGroup", "error.existence", "Salary Group '"
							+ clss.getSalaryGroup() + "'");
					return false;
			}
		return true;
	}
	
	private boolean validateReportingGroup(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getPositionReportGroup())
				&& (PmValidationUtils.isWildCard(clss.getPositionReportGroup())
					|| (!PmValidationUtils.isWildCard(clss.getPositionReportGroup()) 
							&& !PmValidationUtils.validatePstnRptGrp(clss.getPositionReportGroup(),clss.getInstitution(), clss.getCampus(), clss.getEffectiveLocalDate())))) {
			String[] parameters = new String[4];
			parameters[0] = clss.getPositionReportGroup();
			parameters[1] = clss.getInstitution();
			parameters[2] = clss.getCampus();
			parameters[3] = clss.getEffectiveLocalDate().toString();
			this.putFieldError("dataObject.positionReportGroup", "institution.campus.inconsistent.positionReportGroup", parameters);

			return false;
		} 
		return true;
	}
	private boolean validatePositionType(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getPositionType())
				&& (PmValidationUtils.isWildCard(clss.getPositionType())
					|| (!PmValidationUtils.isWildCard(clss.getPositionType()) 
							&& !PmValidationUtils.validatePositionType(clss.getPositionType(),clss.getInstitution(), clss.getCampus(), clss.getEffectiveLocalDate())))) {
				String[] parameters = new String[4];
				parameters[0] = clss.getPositionType();
				parameters[1] = clss.getInstitution();
				parameters[2] = clss.getCampus();
				parameters[3] = clss.getEffectiveLocalDate().toString();
				this.putFieldError("dataObject.positionType", "institution.campus.inconsistent.positionType", parameters);

				return false;
		}
		return true;
	}
	
}
