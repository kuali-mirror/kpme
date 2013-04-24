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
package org.kuali.hr.tklm.time.timeblock.service;

import java.util.List;

import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeBlockHistory;

public interface TimeBlockHistoryService {
	/**
	 * Save TimeBlock history
	 * @param timeBlockHistory
	 */
	public void saveTimeBlockHistory(TimeBlockHistory timeBlockHistory);
	/**
	 * Save a List of TimeBlockHistory objects
	 * @param timeBlockHistories
	 * @return
	 */
	public List<TimeBlockHistory> saveTimeBlockHistoryList(List<TimeBlockHistory> timeBlockHistories);
	/**
	 * Fetch a TimeBlockHistory by a particular id
	 * @param tkTimeBlockId
	 * @return
	 */
    public TimeBlockHistory getTimeBlockHistoryByTkTimeBlockId(String tkTimeBlockId);
    /**
	 * Add Time Block history Details to Time Block History using timeHourDetails
	 * of given time block
	 * @param timeBlockHistory
	 * @param timeBlock
	 */
    public void addTimeBlockHistoryDetails(TimeBlockHistory timeBlockHistory, TimeBlock timeBlock);
}
