package org.kuali.hr.time.principal.calendar.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PrincipalCalendarRule extends MaintenanceDocumentRuleBase {

	private boolean validatePrincipalId(PrincipalCalendar principalCal) {
		if (principalCal.getPrincipalId() != null
				&& !ValidationUtils.validatePrincipalId(principalCal
						.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + principalCal.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof PrincipalCalendar) {
			PrincipalCalendar principalCal = (PrincipalCalendar) pbo;
			if (principalCal != null) {
				valid = true;
				valid &= this.validatePrincipalId(principalCal);

				if (StringUtils.isNotEmpty(principalCal.getPyCalendarGroup())) {
					Calendar payCal = TkServiceLocator
							.getPayCalendarSerivce().getPayCalendarByGroup(
									principalCal.getPyCalendarGroup());
					if (payCal == null) {
						this.putFieldError("pyCalendarGroup",
								"principal.cal.pay.invalid", principalCal
										.getPyCalendarGroup());
						valid = false;
					}
				}

				if (StringUtils.isNotEmpty(principalCal
						.getHolidayCalendarGroup())) {
					HolidayCalendar holidayCal = TkServiceLocator
							.getHolidayCalendarService()
							.getHolidayCalendarByGroup(
									principalCal.getHolidayCalendarGroup());
					if (holidayCal == null) {
						this.putFieldError("holidayCalendarGroup",
								"principal.cal.holiday.invalid", principalCal
										.getHolidayCalendarGroup());
						valid = false;
					}
				}
			}
		}
		return valid;
	}

}
