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
package org.kuali.kpme.core.calendar.web;


import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.Map;

public class CalendarInquirableImpl extends KualiInquirableImpl {

    @Override
    @SuppressWarnings("rawtypes")
    public BusinessObject getBusinessObject(Map fieldValues) {
        Calendar calObj = null;

        if (StringUtils.isNotBlank((String) fieldValues.get("hrCalendarId"))) {
            calObj = HrServiceLocator.getCalendarService().getCalendar((String) fieldValues.get("hrCalendarId"));
        } else if (fieldValues.containsKey("calendarName")) {
        	String calName = (String) fieldValues.get("calendarName");
            calObj = HrServiceLocator.getCalendarService().getCalendarByGroup(calName);
        } else {
            calObj = (Calendar) super.getBusinessObject(fieldValues);
        }

        return calObj;
    }
}
