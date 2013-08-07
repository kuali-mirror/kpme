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
package org.kuali.kpme.tklm.api.time.calendar;

import java.util.List;

import org.kuali.kpme.core.api.calendar.web.CalendarDayContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockRendererContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timeblock.web.TimeBlockRendererContract;



/**
 * <p>TkCalendarWeekContract interface</p>
 *
 */
public interface TkCalendarDayContract extends CalendarDayContract {
	
	/**
	 * The list of TimeBlock objects associated with the TkCalendarDay
	 * 
	 * <p>
	 * timeblocks of a TkCalendarDay
	 * <p>
	 * 
	 * @return timeblocks for TkCalendarDay
	 */
	public List<? extends TimeBlockContract> getTimeblocks();

	/**
	 * The list of TimeBlockRenderer objects associated with the TkCalendarDay
	 * 
	 * <p>
	 * blockRenderers of a TkCalendarDay
	 * <p>
	 * 
	 * @return blockRenderers for TkCalendarDay
	 */
    public List<? extends TimeBlockRendererContract> getBlockRenderers();

    /**
	 * The dayNumberString associated with the TkCalendarDay
	 * 
	 * <p>
	 * dayNumberString of a TkCalendarDay
	 * <p>
	 * 
	 * @return dayNumberString for TkCalendarDay
	 */
    public String getDayNumberString();

    /**
     * TODO: Put a better comment
	 * The gray flag of the TimeCollectionRule
	 * 
	 * <p>
	 * gray flag of a TimeCollectionRule
	 * <p>
	 * 
	 * @return gray flag for TimeCollectionRule
	 */
	public Boolean getGray();
	
	/**
	 * The list of LeaveBlock objects associated with the TkCalendarDay
	 * 
	 * <p>
	 * leaveBlocks of a TkCalendarDay
	 * <p>
	 * 
	 * @return leaveBlocks for TkCalendarDay
	 */
	public List<? extends LeaveBlockContract> getLeaveBlocks();
	
	/**
	 * The list of LeaveBlockRenderer objects associated with the TkCalendarDay
	 * 
	 * <p>
	 * leaveBlockRenderers of a TkCalendarDay
	 * <p>
	 * 
	 * @return leaveBlockRenderers for TkCalendarDay
	 */
	public List<? extends LeaveBlockRendererContract> getLeaveBlockRenderers();

}
