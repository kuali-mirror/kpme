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
package org.kuali.kpme.core.bo.calendar.entry.service;

import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntryPeriodType;
import org.springframework.cache.annotation.Cacheable;

public interface CalendarEntryService {

    /**
     * Method to directly access the CalendarEntry object by ID.
     *
     * @param hrCalendarEntryId The ID to retrieve.
     * @return a CalendarEntry object.
     */
    @Cacheable(value= CalendarEntry.CACHE_NAME, key="'hrCalendarEntryId=' + #p0")
	public CalendarEntry getCalendarEntry(String hrCalendarEntryId);

    /**
     * Method to obtain the current CalendarEntry object based on the
     * indicated calendar and asOfDate.
     * @param hrCalendarId The calendar to reference.
     * @param asOfDate The date reference point.
     * @return the current CalendarEntry effective by the asOfDate.
     */
    @Cacheable(value= CalendarEntry.CACHE_NAME, key="'hrCalendarId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public CalendarEntry getCurrentCalendarEntryByCalendarId(String hrCalendarId, DateTime asOfDate);

    /**
     * Method to obtain the CalendarEntry object based in a date range
     * @param hrCalendarId The calendar to reference.
     * @param beginDate
     * @param endDate
     * @return the current CalendarEntry effective by the asOfDate.
     */
    @Cacheable(value= CalendarEntry.CACHE_NAME, key="'hrCalendarId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2")
    public CalendarEntry getCalendarEntryByCalendarIdAndDateRange(String hrCalendarId, DateTime beginDate, DateTime endDate);

    @Cacheable(value= CalendarEntry.CACHE_NAME, key="'hrCalendarId=' + #p0 + '|' + 'endPeriodDate=' + #p1")
    public CalendarEntry getCalendarEntryByIdAndPeriodEndDate(String hrCalendarId, DateTime endPeriodDate);

    public CalendarEntry getPreviousCalendarEntryByCalendarId(String hrCalendarId, CalendarEntry pce);
    public CalendarEntry getNextCalendarEntryByCalendarId(String hrCalendarId, CalendarEntry pce);

    /**
     * Provides a list of CalendarEntry that are in the indicated window
     * of time from the as of date.
     * @param thresholdDays ± days from the asOfDate to form the window of time.
     * @param asOfDate The central date to query from.
     * @return A list of CalendarEntry.
     */
    @Cacheable(value= CalendarEntry.CACHE_NAME, key="'thresholdDays=' + #p0 + '|' + 'endPeriodDate=' + #p1")
	public List<CalendarEntry> getCurrentCalendarEntriesNeedsScheduled(int thresholdDays, DateTime asOfDate);
	
	public CalendarEntry createNextCalendarEntry(CalendarEntry calendarEntry, CalendarEntryPeriodType type);
	
	public List<CalendarEntry> getFutureCalendarEntries(String hrCalendarId, DateTime currentDate, int numberOfEntries);

    public CalendarEntry getCalendarEntryByBeginAndEndDate(DateTime beginPeriodDate, DateTime endPeriodDate);

    public List<CalendarEntry> getCalendarEntriesEndingBetweenBeginAndEndDate(String hrCalendarId, DateTime beginDate, DateTime endDate);

    @Cacheable(value= CalendarEntry.CACHE_NAME, key="'hrCalendarId=' + #p0")
    public List<CalendarEntry> getAllCalendarEntriesForCalendarId(String hrCalendarId);
    @Cacheable(value= CalendarEntry.CACHE_NAME, key="'hrCalendarId=' + #p0 + '|' + 'year=' + #p1")
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year);
    
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToPlanningMonths(String hrCalendarId, String principalId);
    
    public List<CalendarEntry> getAllCalendarEntriesForCalendarIdUpToCutOffTime(String hrCalendarId, DateTime cutOffTime);

}
