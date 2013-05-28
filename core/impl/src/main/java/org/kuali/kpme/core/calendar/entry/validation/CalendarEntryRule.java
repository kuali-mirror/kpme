/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.calendar.entry.validation;

import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class CalendarEntryRule extends MaintenanceDocumentRuleBase {
    @Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = false;

        CalendarEntry calendarEntry = (CalendarEntry) this.getNewBo();

        valid = validateCalendarGroup(calendarEntry.getCalendarName());
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
