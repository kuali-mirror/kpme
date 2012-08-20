package org.kuali.hr.lm.leavecalendar.service;

import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.rice.kew.api.exception.WorkflowException;

public interface LeaveCalendarService {
    public LeaveCalendarDocument openLeaveCalendarDocument(String principalId, CalendarEntries calEntry) throws WorkflowException;
    public LeaveCalendarDocument getLeaveCalendarDocument(String documentId);
    public LeaveCalendarDocument getLeaveCalendarDocument(String principalId, CalendarEntries calendarEntries);
}
