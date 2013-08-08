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
package org.kuali.kpme.tklm.api.time.timeblock.web;

import java.util.List;

import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailRendererContract;

/**
 * <p>TimeBlockRendererContract interface</p>
 *
 */
public interface TimeBlockRendererContract {

	/**
	 * The list of TimeHourDetailRenderer objects associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * detailRenderers for a TimeBlockRenderer
	 * <p>
	 * 
	 * @return detailRenderers for TimeBlockRenderer
	 */
    public List<? extends TimeHourDetailRendererContract> getDetailRenderers();

    /**
	 * The TimeBlock object associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * timeBlock for a TimeBlockRenderer
	 * <p>
	 * 
	 * @return timeBlock for TimeBlockRenderer
	 */
    public TimeBlockContract getTimeBlock();

    /**
	 * The time range associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * If timeBlock's earn code type is T, construct a string (begin time in hh:mm aa format - end time in hh:mm aa format)
	 * <p>
	 * 
	 * @return a string representation of time range for TimeBlockRenderer
	 */
    public String getTimeRange();

    /**
	 * The title associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * Constructs a title string based on work area description and task description
	 * <p>
	 * 
	 * @return work area description - task description if task is not the default one
	 */
    public String getTitle();

    /**
	 * The amount of the TimeBlock object associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * If timeBlock's earn code type is A
	 *   If timeBlock's amount is not null, "A: $x.xx"
	 *   else "A: $0.00"
	 * Else ""
	 * <p>
	 * 
	 * @return "A: $x.xx" if timeBlock's amount is not null, "A: $0.00" if null, "" if earn code type is not A
	 */
    public String getAmount();

    /**
	 * The earn code type of the TimeBlock object associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * timeBlock.getEarnCodeType() for a TimeBlockRenderer
	 * <p>
	 * 
	 * @return timeBlock.getEarnCodeType() for TimeBlockRenderer
	 */
    public String getEarnCodeType();

    /**
	 * The assignmentClass associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * assignmentClass for a TimeBlockRenderer
	 * <p>
	 * 
	 * @return assignmentClass for TimeBlockRenderer
	 */
	public String getAssignmentClass();
	
	/**
	 * TODO: Put a better comment
	 * The lunchLabel associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * lunchLabel for a TimeBlockRenderer
	 * <p>
	 * 
	 * @return lunchLabel for TimeBlockRenderer
	 */
	public String getLunchLabel();
	
	/**
	 * TODO: Put a better comment
	 * The lunchLabelId associated with the TimeBlockRenderer
	 * 
	 * <p>
	 * lunchLabelId for a TimeBlockRenderer
	 * <p>
	 * 
	 * @return lunchLabelId for TimeBlockRenderer
	 */
    public String getLunchLabelId();

}
