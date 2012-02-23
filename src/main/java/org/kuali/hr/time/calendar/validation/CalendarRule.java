package org.kuali.hr.time.calendar.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class CalendarRule extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = true;

		Calendar calendar = (Calendar) this.getNewBo();
		if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")){
			valid = validateFLSABeginDay(calendar.getFlsaBeginDay());
		}
		return valid;
	}

	boolean validateFLSABeginDay(String flsaBeginDay) {
		boolean valid = true;
		if (StringUtils.isEmpty(flsaBeginDay)) {
			this.putFieldError("flsaBeginDay", "error.required",
					"FLSA Begin Day");
			valid = false;
		}
		return valid;
	}

}
