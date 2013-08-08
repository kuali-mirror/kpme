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
package org.kuali.kpme.tklm.api.time.detail;

import java.util.List;

import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;


/**
 * <p>TimeDetailSummaryContract interface</p>
 *
 */
public interface TimeDetailSummaryContract {
	
	/**
	 * The list of TimeBlock objects associated with the TimeDetailSummary
	 * 
	 * <p>
	 * timeBlocks of a TimeDetailSummary
	 * <p>
	 * 
	 * @return timeBlocks for TimeDetailSummary
	 */
	public List<? extends TimeBlockContract> getTimeBlocks();

	/**
	 * The numberOfDays associated with the TimeDetailSummary
	 * 
	 * <p>
	 * numberOfDays of a TimeDetailSummary
	 * <p>
	 * 
	 * @return numberOfDays for TimeDetailSummary
	 */
	public Integer getNumberOfDays();

}
