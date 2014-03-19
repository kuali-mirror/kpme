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
package org.kuali.kpme.core.calendar.entry.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;

public interface CalendarEntryDao {

	public void saveOrUpdate(CalendarEntryBo calendarEntry);
	public CalendarEntryBo getCalendarEntry(String hrCalendarEntryId);

    public CalendarEntryBo getCalendarEntryByIdAndPeriodEndDate(String hrPyCalendarId, DateTime endPeriodDate);
	public CalendarEntryBo getCurrentCalendarEntryByCalendarId(String hrPyCalendarId, DateTime currentDate);
    public CalendarEntryBo getNextCalendarEntryByCalendarId(String hrPyCalendarId, CalendarEntryContract calendarEntry);
    public CalendarEntryBo getPreviousCalendarEntryByCalendarId(String hrPyCalendarId, CalendarEntryContract calendarEntry);


	public List<CalendarEntryBo> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, DateTime asOfDate);
	public List<CalendarEntryBo> getFutureCalendarEntries(String hrCalendarId, DateTime currentDate, int numberOfEntries);
    
	public List<CalendarEntryBo> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, DateTime beginDate, DateTime endDate);

    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarId(String hrCalendarId);
    
    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year);
    
    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, DateTime cutOffTime);

    public CalendarEntryBo getCalendarEntryByCalendarIdAndDateRange(String hrCalendarId, DateTime beginDate, DateTime endDate);
    
    public List<CalendarEntryBo> getAllCalendarEntriesForCalendarIdWithinLeavePlanYear(String hrCalendarId, String leavePlan, LocalDate dateWithinYear);

    public List<CalendarEntryBo> getSearchResults(String calendarName, String calendarTypes, LocalDate fromBeginDate, LocalDate toBeginDate, LocalDate fromendDate, LocalDate toEndDate);
}
