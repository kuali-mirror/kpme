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
package org.kuali.kpme.tklm.api.leave.calendar.web;

import java.util.List;

import org.kuali.kpme.core.api.calendar.web.CalendarDayContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockRendererContract;


/**
 * <p>LeaveCalendarDayContract interface</p>
 *
 */
public interface LeaveCalendarDayContract extends CalendarDayContract {
	
	/**
   	 * The list of LeaveBlockRenderer objects associated with the LeaveCalendarDay
   	 * 
   	 * <p>
   	 * leaveBlockRenderers of a LeaveCalendarDay
   	 * <p>
   	 * 
   	 * @return leaveBlockRenderers for LeaveCalendarDay
   	 */
	public List<? extends LeaveBlockRendererContract> getLeaveBlockRenderers();

	/**
   	 * The list of LeaveBlock objects associated with the LeaveCalendarDay
   	 * 
   	 * <p>
   	 * leaveBlocks of a LeaveCalendarDay
   	 * <p>
   	 * 
   	 * @return leaveBlocks for LeaveCalendarDay
   	 */
	public List<? extends LeaveBlockContract> getLeaveBlocks();

}
