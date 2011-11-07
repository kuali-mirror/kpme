package org.kuali.hr.time.principal.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PrincipalHRAttributesRule extends MaintenanceDocumentRuleBase {

	private boolean validatePrincipalId(PrincipalHRAttributes principalHRAttr) {
		if (principalHRAttr.getPrincipalId() != null
				&& !ValidationUtils.validatePrincipalId(principalHRAttr
						.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + principalHRAttr.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	boolean validateEffectiveDate(PrincipalHRAttributes principalHRAttr) {
		boolean valid = true;
		if (principalHRAttr.getEffectiveDate() != null && !ValidationUtils.validateOneYearFutureDate(principalHRAttr.getEffectiveDate())) {
			this.putFieldError("effectiveDate", "error.date.exceed.year");
			valid = false;
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof PrincipalHRAttributes) {
			PrincipalHRAttributes principalHRAttr = (PrincipalHRAttributes) pbo;
			if (principalHRAttr != null) {
				valid = true;
				valid &= this.validatePrincipalId(principalHRAttr);
				valid &= this.validateEffectiveDate(principalHRAttr);

				if (StringUtils.isNotEmpty(principalHRAttr.getCalendarName())) {
					Calendar payCal = TkServiceLocator
							.getPayCalendarSerivce().getPayCalendarByGroup(
									principalHRAttr.getCalendarName());
					if (payCal == null) {
						this.putFieldError("pyCalendarGroup",
								"principal.cal.pay.invalid", principalHRAttr
										.getCalendarName());
						valid = false;
					}
				}
			}
		}
		return valid;
	}

}
