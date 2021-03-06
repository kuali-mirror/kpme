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
package org.kuali.kpme.tklm.leave.calendar;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.calendar.LeaveCalendarDocumentContract;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaveCalendarDocument extends CalendarDocument implements Assignable, LeaveCalendarDocumentContract {
	private static final long serialVersionUID = -5029062030186479210L;

    private LeaveCalendarDocumentHeader documentHeader;
	/**
	 * This static member is needed by document search, to trigger the correct calendar document
	 * opening when clicking on a doc id link in the search results.
	 * It is distinguished from "HrConstants.LEAVE_CALENDAR_TYPE".
	 */
	public static final String LEAVE_CALENDAR_DOCUMENT_TYPE = "LeaveCalendarDocument";

	List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();

	public LeaveCalendarDocument(CalendarEntry calendarEntry) {
		setCalendarEntry(calendarEntry);
	}

	public LeaveCalendarDocument(LeaveCalendarDocumentHeader documentHeader) {
        this.documentHeader = documentHeader;
		setCalendarType(LEAVE_CALENDAR_DOCUMENT_TYPE);
	}

    @Override
	public LeaveCalendarDocumentHeader getDocumentHeader() {
		return documentHeader;
	}

	public void setDocumentHeader(
            LeaveCalendarDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

	public List<LeaveBlock> getLeaveBlocks() {
		return leaveBlocks;
	}

	public void setLeaveBlocks(List<LeaveBlock> leaveBlocks) {
		this.leaveBlocks = leaveBlocks;
	}



    @Override
	public String getPrincipalId() {
        return  getDocumentHeader() == null ? null : getDocumentHeader().getPrincipalId();
	}

    @Override
	public String getDocumentId() {
        return getDocumentHeader() == null ? null : getDocumentHeader().getDocumentId();
	}

    @Override
    public List<Assignment> getAssignments() {
        return getAllAssignments();
    }
	
}
