package org.kuali.kpme.pm.pstncontracttype.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.positiontype.PositionType;
import org.kuali.kpme.pm.pstncontracttype.PstnContractType;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PstnContractTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for PstnContractType");
		PstnContractType pstnContractType = (PstnContractType) this.getNewDataObject();
		
		if (pstnContractType != null) {
			valid = true;
			valid &= this.validateInstitution(pstnContractType);
			valid &= this.validateCampus(pstnContractType);
		}
		return valid;
	}
	
	private boolean validateInstitution(PstnContractType pstnContractType) {
		if (StringUtils.isNotEmpty(pstnContractType.getInstitution())
				&& !PmValidationUtils.validateInstitution(pstnContractType.getInstitution(), pstnContractType.getEffectiveLocalDate())) {
			this.putFieldError("institution", "error.existence", "Institution '"
					+ pstnContractType.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateCampus(PstnContractType pstnContractType) {
		if (StringUtils.isNotEmpty(pstnContractType.getCampus())
				&& !PmValidationUtils.validateCampus(pstnContractType.getCampus())) {
			this.putFieldError("campus", "error.existence", "Campus '"
					+ pstnContractType.getCampus() + "'");
			return false;
		} else {
			return true;
			
		}
	}
}
