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
package org.kuali.kpme.tklm.time.detail;

import java.util.LinkedList;
import java.util.List;

import org.kuali.kpme.tklm.api.time.detail.TimeDetailSummaryContract;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;

public class TimeDetailSummary implements TimeDetailSummaryContract {
	private List<TimeBlockBo> timeBlocks = new LinkedList<TimeBlockBo>();
	private Integer numberOfDays;

	public void setTimeBlocks(List<TimeBlockBo> timeBlocks) {
		this.timeBlocks = timeBlocks;
	}

	public List<TimeBlockBo> getTimeBlocks() {
		return timeBlocks;
	}

	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public Integer getNumberOfDays() {
		return numberOfDays;
	}
	
}
