package org.kuali.hr.lm.leavecalendar.service;

import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.exception.WorkflowException;

public interface LeaveCalendarService {
    public LeaveCalendarDocument openLeaveCalendarDocument(String principalId, CalendarEntries calEntry) throws WorkflowException;
    public LeaveCalendarDocument getLeaveCalendarDocument(String documentId);
    public LeaveCalendarDocument getLeaveCalendarDocument(String principalId, CalendarEntries calendarEntries);

    /**
     * Route the given leaveCalendarDocument
     * @param principalId
     * @param leaveCalendarDocument
     */
    public void routeLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument);

    public void approveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument);

    public void disapproveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument);
}
