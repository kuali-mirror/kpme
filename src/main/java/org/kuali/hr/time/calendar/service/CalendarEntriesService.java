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
package org.kuali.hr.time.calendar.service;

import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.CalendarEntryPeriodType;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;

public interface CalendarEntriesService {

    /**
     * Method to directly access the CalendarEntries object by ID.
     *
     * @param hrCalendarEntriesId The ID to retrieve.
     * @return a CalendarEntries object.
     */
    @Cacheable(value= CalendarEntries.CACHE_NAME, key="'hrCalendarEntriesId=' + #p0")
	public CalendarEntries getCalendarEntries(String hrCalendarEntriesId);

    /**
     * Method to obtain the current CalendarEntries object based on the
     * indicated calendar and asOfDate.
     * @param hrCalendarId The calendar to reference.
     * @param asOfDate The date reference point.
     * @return the current CalendarEntries effective by the asOfDate.
     */
    @Cacheable(value= CalendarEntries.CACHE_NAME, key="'hrCalendarId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public CalendarEntries getCurrentCalendarEntriesByCalendarId(String hrCalendarId, Date asOfDate);
    @Cacheable(value= CalendarEntries.CACHE_NAME, key="'hrCalendarId=' + #p0 + '|' + 'endPeriodDate=' + #p1")
    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(String hrCalendarId, Date endPeriodDate);

    public CalendarEntries getPreviousCalendarEntriesByCalendarId(String hrCalendarId, CalendarEntries pce);
    public CalendarEntries getNextCalendarEntriesByCalendarId(String hrCalendarId, CalendarEntries pce);

    /**
     * Provides a list of CalendarEntries that are in the indicated window
     * of time from the as of date.
     * @param thresholdDays ± days from the asOfDate to form the window of time.
     * @param asOfDate The central date to query from.
     * @return A list of CalendarEntries.
     */
    @Cacheable(value= CalendarEntries.CACHE_NAME, key="'thresholdDays=' + #p0 + '|' + 'endPeriodDate=' + #p1")
	public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate);
	
	public CalendarEntries createNextCalendarEntry(CalendarEntries calendarEntries, CalendarEntryPeriodType type);
	
	public List<CalendarEntries> getFutureCalendarEntries(String hrCalendarId, Date currentDate, int numberOfEntries);

    public CalendarEntries getCalendarEntriesByBeginAndEndDate(Date beginPeriodDate, Date endPeriodDate);

    @Cacheable(value= CalendarEntries.CACHE_NAME, key="'hrCalendarId=' + #p0")
    public List<CalendarEntries> getAllCalendarEntriesForCalendarId(String hrCalendarId);
    @Cacheable(value= CalendarEntries.CACHE_NAME, key="'hrCalendarId=' + #p0 + '|' + 'year=' + #p1")
    public List<CalendarEntries> getAllCalendarEntriesForCalendarIdAndYear(String hrCalendarId, String year);
}
