package org.kuali.hr.time.paycalendar.validation;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class PayCalendarEntriesRule extends MaintenanceDocumentRuleBase {
    @Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = false;

        PayCalendarEntries payCalendarEntries = (PayCalendarEntries) this.getNewBo();

        valid = validateCalendarGroup(payCalendarEntries.getCalendarGroup());
        return valid;
    }
    
    protected boolean validateCalendarGroup(String calendarGroup) {
    	boolean valid = ValidationUtils.validatePayCalendar(calendarGroup);
        if (!valid) {
            this.putFieldError("calendarGroup", "payCalendar.notfound");
        }
        return valid;
    }

}
