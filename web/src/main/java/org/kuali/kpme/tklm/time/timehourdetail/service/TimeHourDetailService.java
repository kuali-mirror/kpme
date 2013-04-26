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
package org.kuali.kpme.tklm.time.timehourdetail.service;

import java.util.List;

import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeHourDetail;


public interface TimeHourDetailService{
	/**
	 * Fetch TimeHourDetail based on id given
	 * @param timeHourDetailId
	 * @return
	 */
	public TimeHourDetail getTimeHourDetail(String timeHourDetailId);
	/**
	 * Save time hour detail for a given TimeBlock
	 * @param timeBlock
	 * @return
	 */
	public TimeHourDetail saveTimeHourDetail(TimeBlock timeBlock);
	/**
	 * Remove TimeHourDetail for the given id
	 * @param timeBlockId
	 */
    public void removeTimeHourDetails(String timeBlockId);
    
	/**
	 * Fetch List of TimeHourDetail based on time block id
	 * @param timeBlockId
	 * @return
	 */
    public List<TimeHourDetail> getTimeHourDetailsForTimeBlock(String timeBlockId);


    void removeTimeHourDetail(String timeHourDetailId);
}
