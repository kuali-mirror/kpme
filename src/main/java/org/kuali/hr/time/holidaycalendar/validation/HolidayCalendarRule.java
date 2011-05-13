package org.kuali.hr.time.holidaycalendar.validation;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class HolidayCalendarRule extends MaintenanceDocumentRuleBase {

	boolean validateLocation(HolidayCalendar holidayCalendar) {
		String location = holidayCalendar.getLocation();
		if (location != null && !location.equals(TkConstants.WILDCARD_CHARACTER)
				&& !ValidationUtils.validateLocation(holidayCalendar.getLocation(), null)) {
			this.putFieldError("location", "error.existence", "location '"
					+ holidayCalendar.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for TimeCollectionRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof HolidayCalendar) {
			HolidayCalendar holidayCalendar = (HolidayCalendar) pbo;
			if (holidayCalendar != null) {
				valid = true;
				valid &= this.validateLocation(holidayCalendar);
			}
		}

		return valid;
	}

	@Override
	protected boolean processCustomApproveDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomApproveDocumentBusinessRules(document);
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomRouteDocumentBusinessRules(document);
	}

}
