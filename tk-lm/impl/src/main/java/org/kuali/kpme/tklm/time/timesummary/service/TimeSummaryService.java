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
package org.kuali.kpme.tklm.time.timesummary.service;

import java.util.List;

import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;

public interface TimeSummaryService {
	/**
	 * Fetch TimeSummary
	 * @param timesheetDocument
	 * @return
	 */
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument);

    List<String> getHeaderForSummary(CalendarEntryContract cal, List<Boolean> dayArrangements);
}
