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
package org.kuali.kpme.tklm.leave.calendar;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;

public class LeaveCalendarDocument extends CalendarDocument {

	public static final String LEAVE_CALENDAR_DOCUMENT_TYPE = "LeaveCalendarDocument";

	List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();

	public LeaveCalendarDocument(CalendarEntry calendarEntry) {
		this.calendarEntry = calendarEntry;
	}

	public LeaveCalendarDocument(
			LeaveCalendarDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
		setCalendarType(LEAVE_CALENDAR_DOCUMENT_TYPE);
	}

    @Override
	public LeaveCalendarDocumentHeader getDocumentHeader() {
		return (LeaveCalendarDocumentHeader) super.getDocumentHeader();
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
	public CalendarEntry getCalendarEntry() {
		return calendarEntry;
	}

	public void setCalendarEntry(CalendarEntry calendarEntry) {
		this.calendarEntry = calendarEntry;
	}

	public String getPrincipalId() {
		return getDocumentHeader().getPrincipalId();
	}

	public String getDocumentId() {
		if (getDocumentHeader() != null) {
			return getDocumentHeader().getDocumentId();
		} else {
			return null;
		}
	}

    @Override
	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

    @Override
    public LocalDate getAsOfDate(){
        return getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate();
    }
	
	
}
