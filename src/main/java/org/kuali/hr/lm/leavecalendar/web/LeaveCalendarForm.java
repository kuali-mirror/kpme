package org.kuali.hr.lm.leavecalendar.web;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;

import java.util.List;

public class LeaveCalendarForm extends TkForm {

    private String documentId;
    private LeaveCalendar leaveCalendar;
    private List<String> warnings;
    CalendarEntries calendarEntry;

    public LeaveCalendar getLeaveCalendar() {
        return leaveCalendar;
    }

    public void setLeaveCalendar(LeaveCalendar leaveCalendar) {
        this.leaveCalendar = leaveCalendar;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public CalendarEntries getCalendarEntry() {
        return calendarEntry;
    }

    public void setCalendarEntry(CalendarEntries calendarEntry) {
        this.calendarEntry = calendarEntry;
    }
}
