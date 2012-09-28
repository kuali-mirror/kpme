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
