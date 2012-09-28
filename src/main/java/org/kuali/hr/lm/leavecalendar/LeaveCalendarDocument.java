/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.leavecalendar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.core.document.calendar.CalendarDocumentContract;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;

public class LeaveCalendarDocument implements CalendarDocumentContract {

	public static final String LEAVE_CALENDAR_DOCUMENT_TYPE = "LeaveCalendarDocument";
	public static final String LEAVE_CALENDAR_DOCUMENT_TITLE = "LeaveCalendarDocument";

	LeaveCalendarDocumentHeader documentHeader;
	List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	private List<Assignment> assignments = new LinkedList<Assignment>();
	private CalendarEntries calendarEntry;

	public LeaveCalendarDocument(CalendarEntries calendarEntry) {
		this.calendarEntry = calendarEntry;
	}

	public LeaveCalendarDocument(
			LeaveCalendarDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
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
	public CalendarEntries getCalendarEntry() {
		return calendarEntry;
	}

	public void setCalendarEntry(CalendarEntries calendarEntry) {
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
    public java.sql.Date getAsOfDate(){
        return new java.sql.Date(getCalendarEntry().getBeginPeriodDateTime().getTime());
    }
	
	
}
