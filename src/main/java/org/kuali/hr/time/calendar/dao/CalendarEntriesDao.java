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
package org.kuali.hr.time.calendar.dao;

import org.kuali.hr.time.calendar.CalendarEntries;

import java.util.Date;
import java.util.List;

public interface CalendarEntriesDao {

	public void saveOrUpdate(CalendarEntries calendarEntries);
	public CalendarEntries getCalendarEntries(String hrPyCalendarEntriesId);

    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(String hrPyCalendarId, Date endPeriodDate);
	public CalendarEntries getCurrentCalendarEntriesByCalendarId(String hrPyCalendarId, Date currentDate);
    public CalendarEntries getNextCalendarEntriesByCalendarId(String hrPyCalendarId, CalendarEntries calendarEntries);
    public CalendarEntries getPreviousCalendarEntriesByCalendarId(String hrPyCalendarId, CalendarEntries calendarEntries);


	public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
	public List<CalendarEntries> getFutureCalendarEntries(String hrCalendarId, Date currentDate, int numberOfEntries);

    public CalendarEntries getCalendarEntriesByBeginAndEndDate(Date beginPeriodDate, Date endPeriodDate);
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarId(String hrCalendarId);
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year);
    
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, Date cutOffTime);
}
