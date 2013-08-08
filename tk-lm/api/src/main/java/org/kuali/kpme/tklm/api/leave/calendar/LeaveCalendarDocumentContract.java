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
package org.kuali.kpme.tklm.api.leave.calendar;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.document.calendar.CalendarDocumentContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;


/**
 * <p>LeaveCalendarDocumentContract interface</p>
 *
 */
public interface LeaveCalendarDocumentContract extends CalendarDocumentContract {
	
	/**
	 * The list of LeaveBlock objects associated with the LeaveCalendarDocument
	 * 
	 * <p>
	 * leaveBlocks of a LeaveCalendarDocument
	 * <p>
	 * 
	 * @return leaveBlocks for LeaveCalendarDocument
	 */
	public List<? extends LeaveBlockContract> getLeaveBlocks();

	/**
	 * The principal id associated with the LeaveCalendarDocument
	 * 
	 * <p>
	 * getDocumentHeader().getPrincipalId() of a LeaveCalendarDocument
	 * <p>
	 * 
	 * @return getDocumentHeader().getPrincipalId() for LeaveCalendarDocument
	 */
	public String getPrincipalId();

	/**
	 * The document id associated with the LeaveCalendarDocument
	 * 
	 * <p>
	 * getDocumentHeader().getDocumentId() of a LeaveCalendarDocument
	 * <p>
	 * 
	 * @return getDocumentHeader().getDocumentId() for LeaveCalendarDocument
	 */
	public String getDocumentId();
	
	/**
	 * The end date of calendar entry associated with the LeaveCalendarDocument
	 * 
	 * <p>
	 * calendar entry end date of a LeaveCalendarDocument
	 * <p>
	 * 
	 * @return getCalendarEntry().getEndPeriodFullDateTime().toLocalDate() for LeaveCalendarDocument
	 */
    public LocalDate getDocEndDate();
}
