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
package org.kuali.hr.core.calendar;

import java.sql.Date;
import java.sql.Time;

import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class CalendarEntryMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -5910605947104384549L;

	@Override
	public PersistableBusinessObject getBusinessObject() {
		CalendarEntry calendarEntry = (CalendarEntry) super.getBusinessObject();
		
		if (calendarEntry.getBeginPeriodDateTime() != null) {
			calendarEntry.setBeginPeriodDate(new Date(calendarEntry.getBeginPeriodDateTime().getTime()));
			calendarEntry.setBeginPeriodTime(new Time(calendarEntry.getBeginPeriodDateTime().getTime()));
		}
		
		if (calendarEntry.getEndPeriodDateTime() != null) {
			calendarEntry.setEndPeriodDate(new Date(calendarEntry.getEndPeriodDateTime().getTime()));
			calendarEntry.setEndPeriodTime(new Time(calendarEntry.getEndPeriodDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchInitiateDateTime() != null) {
			calendarEntry.setBatchInitiateDate(new Date(calendarEntry.getBatchInitiateDateTime().getTime()));
			calendarEntry.setBatchInitiateTime(new Time(calendarEntry.getBatchInitiateDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchEndPayPeriodDateTime() != null) {
			calendarEntry.setBatchEndPayPeriodDate(new Date(calendarEntry.getBatchEndPayPeriodDateTime().getTime()));
			calendarEntry.setBatchEndPayPeriodTime(new Time(calendarEntry.getBatchEndPayPeriodDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchEmployeeApprovalDateTime() != null) {
			calendarEntry.setBatchEmployeeApprovalDate(new Date(calendarEntry.getBatchEmployeeApprovalDateTime().getTime()));
			calendarEntry.setBatchEmployeeApprovalTime(new Time(calendarEntry.getBatchEmployeeApprovalDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchSupervisorApprovalDateTime() != null) {
			calendarEntry.setBatchSupervisorApprovalDate(new Date(calendarEntry.getBatchSupervisorApprovalDateTime().getTime()));
			calendarEntry.setBatchSupervisorApprovalTime(new Time(calendarEntry.getBatchSupervisorApprovalDateTime().getTime()));
		}
		
		return calendarEntry;
	}

	@Override
	public void saveBusinessObject() {
		CalendarEntry calendarEntry = (CalendarEntry) super.getBusinessObject();
		
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendarByGroup(calendarEntry.getCalendarName());
		calendarEntry.setHrCalendarId(calendar.getHrCalendarId());
		
		super.saveBusinessObject();
		
        CacheUtils.flushCache(CalendarEntry.CACHE_NAME);
	}

}