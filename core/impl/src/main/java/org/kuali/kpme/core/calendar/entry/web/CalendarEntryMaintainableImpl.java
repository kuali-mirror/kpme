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

import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.cache.CacheUtils;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class CalendarEntryMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -5910605947104384549L;

	@Override
	public void saveBusinessObject() {
		CalendarEntryBo calendarEntry = (CalendarEntryBo) super.getBusinessObject();
		
		CalendarContract calendar = HrServiceLocator.getCalendarService().getCalendarByGroup(calendarEntry.getCalendarName());
		calendarEntry.setHrCalendarId(calendar.getHrCalendarId());
		
		super.saveBusinessObject();
		
        CacheUtils.flushCache(CalendarEntryBo.CACHE_NAME);
	}

}