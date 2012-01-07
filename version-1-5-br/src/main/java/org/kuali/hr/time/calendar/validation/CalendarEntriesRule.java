package org.kuali.hr.time.calendar.validation;

import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class CalendarEntriesRule extends MaintenanceDocumentRuleBase {
    @Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = false;

        CalendarEntries calendarEntries = (CalendarEntries) this.getNewBo();

        valid = validateCalendarGroup(calendarEntries.getCalendarName());
        return valid;
    }

    protected boolean validateCalendarGroup(String pyCalendarGroup) {
    	boolean valid = ValidationUtils.validateCalendar(pyCalendarGroup);
        if (!valid) {
            this.putFieldError("calendarName", "calendar.notfound");
        }
        return valid;
    }

}
