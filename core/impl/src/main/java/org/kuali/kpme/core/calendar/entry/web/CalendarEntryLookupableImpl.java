/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.calendar.entry.web;

import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.List;
import java.util.Map;

public class CalendarEntryLookupableImpl extends KPMELookupableImpl {
    
    @Override
    public List<? extends BusinessObject> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean bounded) {
        String calendarName = searchCriteria.get("calendarName");
        String calendarTypes = searchCriteria.get("calendarTypes");
        String fromBeginPeriodDateTime = TKUtils.getFromDateString(searchCriteria.get("beginPeriodDateTime"));
        String toBeginPeriodDateTime = TKUtils.getToDateString(searchCriteria.get("beginPeriodDateTime"));
        String fromEndPeriodDateTime = TKUtils.getFromDateString(searchCriteria.get("endPeriodDateTime"));
        String toEndPeriodDateTime = TKUtils.getToDateString(searchCriteria.get("endPeriodDateTime"));

        return ModelObjectUtils.transform(HrServiceLocator.getCalendarEntryService().getSearchResults(calendarName, calendarTypes, TKUtils.formatDateString(fromBeginPeriodDateTime),
                TKUtils.formatDateString(toBeginPeriodDateTime), TKUtils.formatDateString(fromEndPeriodDateTime), TKUtils.formatDateString(toEndPeriodDateTime)), CalendarEntryBo.toCalendarEntryBo);
    }
}
