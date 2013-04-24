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
package org.kuali.hr.tklm.time.calendar.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.tklm.time.calendar.Calendar;
import org.kuali.hr.tklm.time.calendar.CalendarEntry;

public interface CalendarDao {

	public void saveOrUpdate(Calendar calendarDates);

	public void saveOrUpdate(List<Calendar> calendarDatesList);

	public Calendar getCalendar(String hrPyCalendarId);

	public Calendar getCalendarByGroup(String pyCalendarGroup);
	
	public CalendarEntry getPreviousCalendarEntry(String tkCalendarId, DateTime beginDateCurrentCalendar);

    public List<Calendar> getCalendars(String calendarName, String calendarTypes, String flsaBeginDay, String flsaBeginTime);

}
