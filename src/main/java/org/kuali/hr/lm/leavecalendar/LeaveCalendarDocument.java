package org.kuali.hr.lm.leavecalendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.calendar.CalendarEntries;

public class LeaveCalendarDocument {

	public static final String LEAVE_CALENDAR_DOCUMENT_TYPE = "LeaveCalendarDocument";
	public static final String LEAVE_CALENDAR_DOCUMENT_TITLE = "LeaveCalendarDocument";

	LeaveCalendarDocumentHeader leaveCalendarDocumentHeader;
	List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
	private CalendarEntries CalendarEntry;

	public LeaveCalendarDocument(CalendarEntries calendarEntry) {
		CalendarEntry = calendarEntry;
	}

	public LeaveCalendarDocument(
			LeaveCalendarDocumentHeader leaveCalendarDocumentHeader) {
		this.leaveCalendarDocumentHeader = leaveCalendarDocumentHeader;
	}

	public LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader() {
		return leaveCalendarDocumentHeader;
	}

	public void setLeaveCalendarDocumentHeader(
		LeaveCalendarDocumentHeader leaveCalendarDocumentHeader) {
		this.leaveCalendarDocumentHeader = leaveCalendarDocumentHeader;
	}

	public List<LeaveBlock> getLeaveBlocks() {
		return leaveBlocks;
	}

	public void setLeaveBlocks(List<LeaveBlock> leaveBlocks) {
		this.leaveBlocks = leaveBlocks;
	}

	public CalendarEntries getCalendarEntry() {
		return CalendarEntry;
	}

	public void setCalendarEntry(CalendarEntries calendarEntry) {
		CalendarEntry = calendarEntry;
	}

	public String getPrincipalId() {
		return getLeaveCalendarDocumentHeader().getPrincipalId();
	}

	public String getDocumentId() {
		if (getLeaveCalendarDocumentHeader() != null) {
			return getLeaveCalendarDocumentHeader().getDocumentId();
		} else {
			return null;
		}
	}
}
