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
package org.kuali.kpme.core.calendar.web;

import org.kuali.kpme.core.calendar.CalendarBo;
import org.kuali.kpme.core.lookup.KPMELookupableImpl;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.web.form.LookupForm;

import java.util.List;
import java.util.Map;

public class CalendarLookupableImpl extends KPMELookupableImpl {

    @Override
    public List<? extends BusinessObject> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {

        String calendarName = searchCriteria.get("calendarName");
        String calendarTypes = searchCriteria.get("calendarTypes");
        String flsaBeginDay = searchCriteria.get("flsaBeginDay");
        String flsaBeginTime = searchCriteria.get("flsaBeginTime");

        return ModelObjectUtils.transform(HrServiceLocator.getCalendarService().getCalendars(calendarName, calendarTypes, flsaBeginDay, flsaBeginTime), CalendarBo.toCalendarBo);
    }
}
