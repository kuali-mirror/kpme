package org.kuali.hr.time.paycalendar.validation;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PayCalendarEntriesRule extends MaintenanceDocumentRuleBase {
    @Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = false;

        PayCalendarEntries payCalendarEntries = (PayCalendarEntries) this.getNewBo();

        valid = validateCalendarGroup(payCalendarEntries.getPyCalendarGroup());
        return valid;
    }

    protected boolean validateCalendarGroup(String pyCalendarGroup) {
    	boolean valid = ValidationUtils.validatePayCalendar(pyCalendarGroup);
        if (!valid) {
            this.putFieldError("pyCalendarGroup", "payCalendar.notfound");
        }
        return valid;
    }

}
