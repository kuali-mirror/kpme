/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.calendar;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class CalendarEntryMaintainableImpl extends KualiMaintainableImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PersistableBusinessObject getBusinessObject() {
		//used to setup the divided time/date fields
		CalendarEntries payEntry = (CalendarEntries)super.getBusinessObject();
		if(payEntry.getBeginPeriodTime()==null){
			payEntry.setBeginPeriodDateTime(payEntry.getBeginPeriodDateTime());
			payEntry.setEndPeriodDateTime(payEntry.getEndPeriodDateTime());
		}
		return payEntry;
	}

	@Override
	public void saveBusinessObject() {
		CalendarEntries payEntry = (CalendarEntries)super.getBusinessObject();
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendarByGroup(payEntry.getCalendarName());
		payEntry.setHrCalendarId(calendar.getHrCalendarId());
		
		java.sql.Date beginDate = payEntry.getBeginPeriodDate();
		java.sql.Time beginTime = payEntry.getBeginPeriodTime();
		LocalTime beginTimeLocal = new LocalTime(beginTime.getTime());
		DateTime beginDateTime = new DateTime(beginDate.getTime());
		beginDateTime = beginDateTime.plus(beginTimeLocal.getMillisOfDay());
		payEntry.setBeginPeriodDateTime(new java.util.Date(beginDateTime.getMillis()));

		java.sql.Date endDate = payEntry.getEndPeriodDate();
		java.sql.Time endTime = payEntry.getEndPeriodTime();
		LocalTime endTimeLocal = new LocalTime(endTime.getTime());
		DateTime endDateTime = new DateTime(endDate.getTime());
		endDateTime = endDateTime.plus(endTimeLocal.getMillisOfDay());
		payEntry.setEndPeriodDateTime(new java.util.Date(endDateTime.getMillis()));
		
		super.saveBusinessObject();
        CacheUtils.flushCache(CalendarEntries.CACHE_NAME);
	}






}
