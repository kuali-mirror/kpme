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
package org.kuali.kpme.tklm.api.time.timesummary;

import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;

import java.util.List;

public interface TimeSummaryService {
	/**
	 * Fetch TimeSummary
	 * @param timesheetDocumentId
	 * @return
	 */
	TimeSummaryContract getTimeSummaryForDocument(String timesheetDocumentId);

    TimeSummaryContract getTimeSummary(String principalId, List<TimeBlock> timeBlocks, CalendarEntry calendarEntry, List<Assignment> assignments);

    List<String> getHeaderForSummary(CalendarEntry cal, List<Boolean> dayArrangements);
}
