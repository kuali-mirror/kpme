package org.kuali.hr.lm.leavecalendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.lm.ledger.Ledger;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.calendar.CalendarEntries;

public class LeaveCalendarDocument {

    public static final String LEAVE_CALENDAR_DOCUMENT_TYPE = "LeaveCalendarDocument";
    public static final String LEAVE_CALENDAR_DOCUMENT_TITLE = "LeaveCalendarDocument";

    LeaveCalendarDocumentHeader leaveCalendarDocumentHeader;
    List<Ledger> ledgers = new ArrayList<Ledger>();
    private CalendarEntries CalendarEntry;

    public LeaveCalendarDocument(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader) {
        this.leaveCalendarDocumentHeader = leaveCalendarDocumentHeader;
    }

    public LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader() {
        return leaveCalendarDocumentHeader;
    }

    public void setLeaveCalendarDocumentHeader(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader) {
        this.leaveCalendarDocumentHeader = leaveCalendarDocumentHeader;
    }

    public List<Ledger> getLedgers() {
        return ledgers;
    }

    public void setLedgers(List<Ledger> ledgers) {
        this.ledgers = ledgers;
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
        return getLeaveCalendarDocumentHeader().getDocumentId();
    }
}
