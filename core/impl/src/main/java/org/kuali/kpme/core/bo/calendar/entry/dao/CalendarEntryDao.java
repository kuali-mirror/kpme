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
package org.kuali.kpme.core.bo.calendar.entry.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;

public interface CalendarEntryDao {

	public void saveOrUpdate(CalendarEntry calendarEntry);
	public CalendarEntry getCalendarEntry(String hrCalendarEntryId);

    public CalendarEntry getCalendarEntryByIdAndPeriodEndDate(String hrPyCalendarId, DateTime endPeriodDate);
	public CalendarEntry getCurrentCalendarEntryByCalendarId(String hrPyCalendarId, DateTime currentDate);
    public CalendarEntry getNextCalendarEntryByCalendarId(String hrPyCalendarId, CalendarEntry calendarEntry);
    public CalendarEntry getPreviousCalendarEntryByCalendarId(String hrPyCalendarId, CalendarEntry calendarEntry);


	public List<CalendarEntry> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, DateTime asOfDate);
	public List<CalendarEntry> getFutureCalendarEntries(String hrCalendarId, DateTime currentDate, int numberOfEntries);
    
	public List<CalendarEntry> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, DateTime beginDate, DateTime endDate);

    public List<CalendarEntry> getAllCalendarEntriesForCalendarId(String hrCalendarId);
    
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year);
    
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, DateTime cutOffTime);

    public CalendarEntry getCalendarEntryByCalendarIdAndDateRange(String hrCalendarId, DateTime beginDate, DateTime endDate);
}
