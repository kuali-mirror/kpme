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
package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;


public interface TimeBlockHistoryDetailService{
	/**
	 * Fetch TimeBlockHistoryDetail based on id given
	 * @param timeBlockHistoryDetailId
	 * @return
	 */
	public TimeBlockHistoryDetail getTimeBlockHistoryDetail(String timeBlockHistoryDetailId);
	/**
	 * Save time block history detail
	 * @param timeBlockHistoryDetail
	 * @return
	 */
	public TimeBlockHistoryDetail saveTimeBlockHistoryDetail(TimeBlockHistoryDetail timeBlockHistoryDetail);
	/**
	 * Remove TimeBlockHistoryDetail for the given id
	 * @param timeBlockHistoryId
	 */
    public void removeTimeBlockHistoryDetails(Long timeBlockHistoryId);
    
	/**
	 * Fetch List of TimeBlockHistoryDetail based on time block history id
	 * @param timeBlockHistoryId
	 * @return
	 */
    public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetailsForTimeBlockHistory(String timeBlockHistoryId);
	

}
