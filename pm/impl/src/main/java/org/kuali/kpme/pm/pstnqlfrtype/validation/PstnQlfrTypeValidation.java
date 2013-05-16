package org.kuali.kpme.pm.pstnqlfrtype.validation;

import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PstnQlfrTypeValidation extends MaintenanceDocumentRuleBase {
	public static final String VALUE_SEPARATOR = ",";	// separator for select values
	
//	@Override
//	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
//		boolean valid = false;
//		LOG.debug("entering custom validation for Salary Group");
//		PstnQlfrType pqt = (PstnQlfrType) this.getNewDataObject();
//		
//		if (pqt != null) {
//			valid = true;
//			valid &= this.validateSelectValues(pqt);
//		}
//		return valid;
//	}
	
//	private boolean validateSelectValues(PstnQlfrType pqt) {
//		if (StringUtils.isNotEmpty(pqt.getSelectValues())) {
//			String[] values = pqt.getSelectValues().split(VALUE_SEPARATOR);
//			for(String aValue : values) {
//				String realName = StringUtils.trim(aValue);
//				if(StringUtils.isNotEmpty(realName)) {
//					if(!PmValidationUtils.validatePositionQualificationValue(realName)) {	// if any value is invalid, there's error
//						this.putFieldError("dataObject.selectValues", "error.existence", "Position Qualification Type '"
//								+ realName + "'");
//						return false;
//					}
//				}
//			}
//		}
//		return true;
//	}
}
