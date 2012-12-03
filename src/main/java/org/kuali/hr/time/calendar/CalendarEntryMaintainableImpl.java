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

import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class CalendarEntryMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -5910605947104384549L;

	@Override
	public PersistableBusinessObject getBusinessObject() {
		CalendarEntries calendarEntry = (CalendarEntries) super.getBusinessObject();
		
		if (calendarEntry.getBeginPeriodDateTime() != null) {
			calendarEntry.setBeginPeriodDate(new java.sql.Date(calendarEntry.getBeginPeriodDateTime().getTime()));
			calendarEntry.setBeginPeriodTime(new java.sql.Time(calendarEntry.getBeginPeriodDateTime().getTime()));
		}
		
		if (calendarEntry.getEndPeriodDateTime() != null) {
			calendarEntry.setEndPeriodDate(new java.sql.Date(calendarEntry.getEndPeriodDateTime().getTime()));
			calendarEntry.setEndPeriodTime(new java.sql.Time(calendarEntry.getEndPeriodDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchInitiateDateTime() != null) {
			calendarEntry.setBatchInitiateDate(new java.sql.Date(calendarEntry.getBatchInitiateDateTime().getTime()));
			calendarEntry.setBatchInitiateTime(new java.sql.Time(calendarEntry.getBatchInitiateDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchEndPayPeriodDateTime() != null) {
			calendarEntry.setBatchEndPayPeriodDate(new java.sql.Date(calendarEntry.getBatchEndPayPeriodDateTime().getTime()));
			calendarEntry.setBatchEndPayPeriodTime(new java.sql.Time(calendarEntry.getBatchEndPayPeriodDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchEmployeeApprovalDateTime() != null) {
			calendarEntry.setBatchEmployeeApprovalDate(new java.sql.Date(calendarEntry.getBatchEmployeeApprovalDateTime().getTime()));
			calendarEntry.setBatchEmployeeApprovalTime(new java.sql.Time(calendarEntry.getBatchEmployeeApprovalDateTime().getTime()));
		}
		
		if (calendarEntry.getBatchSupervisorApprovalDateTime() != null) {
			calendarEntry.setBatchSupervisorApprovalDate(new java.sql.Date(calendarEntry.getBatchSupervisorApprovalDateTime().getTime()));
			calendarEntry.setBatchSupervisorApprovalTime(new java.sql.Time(calendarEntry.getBatchSupervisorApprovalDateTime().getTime()));
		}
		
		return calendarEntry;
	}

	@Override
	public void saveBusinessObject() {
		CalendarEntries calendarEntry = (CalendarEntries) super.getBusinessObject();
		
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendarByGroup(calendarEntry.getCalendarName());
		calendarEntry.setHrCalendarId(calendar.getHrCalendarId());
		
		super.saveBusinessObject();
		
        CacheUtils.flushCache(CalendarEntries.CACHE_NAME);
	}

}