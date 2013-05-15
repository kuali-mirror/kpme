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
package org.kuali.kpme.tklm.time.clocklog.dao;

import java.util.List;

import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;

public interface ClockLogDao {

	/**
	 * Save or update ClockLog passed in
	 * @param clockLog
	 */
    public void saveOrUpdate(ClockLog clockLog);
    /**
     * Save or update List of ClockLogs passed in
     * @param clockLogList
     */
    public void saveOrUpdate(List<ClockLog> clockLogList);
    /**
     * Get last ClockLog for a given principalId
     * @param principalId
     * @return
     */
    public ClockLog getLastClockLog(String principalId);
    /**
     * Get last ClockLog for a given principalId and clockAction
     * @param principalId
     * @param clockAction
     * @return
     */
	public ClockLog getLastClockLog(String principalId, String clockAction);
	
	/**
	 * Returns the last clock log for this user's assignment in a certain period
	 * @param principalId
	 * @param jobNumber
	 * @param workArea
	 * @param task
	 * @param calendarEntry
	 * @return
	 */
	public ClockLog getLastClockLog(String principalId, String jobNumber, String workArea, String task, CalendarEntry payCalendarEntry);
	
	/**
	 * Fetch clock log by id
	 * @param tkClockLogId
	 * @return
	 */
	public ClockLog getClockLog(String tkClockLogId);
}
