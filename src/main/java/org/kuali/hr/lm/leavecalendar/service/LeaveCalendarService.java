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
package org.kuali.hr.lm.leavecalendar.service;

import java.math.BigDecimal;
import java.util.Date;

import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.rice.kew.api.exception.WorkflowException;

public interface LeaveCalendarService {
    public LeaveCalendarDocument openLeaveCalendarDocument(String principalId, CalendarEntry calEntry) throws WorkflowException;
    public LeaveCalendarDocument getLeaveCalendarDocument(String documentId);
    public LeaveCalendarDocument getLeaveCalendarDocument(String principalId, CalendarEntry calendarEntry);


    boolean isReadyToApprove(LeaveCalendarDocument leaveCalendarDocument);

    /**
     * Route the given leaveCalendarDocument
     * @param principalId
     * @param leaveCalendarDocument
     */
    public void routeLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument);

    public void approveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument);

    public void disapproveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument);
    
    /**
     * Determine if Leave Calendar document should be created for given principalId and calendar entry
     * Should only create leave calendar document if active jobs were found with flsa elig = no and ben elg = yes
     * @param principalId
     * @param calEntry
     * @return boolean  
     */
    public boolean shouldCreateLeaveDocument(String principalId, CalendarEntry calEntry);

    /**
     * Determine if Leave Calendar is planning or reporting calendar
     * @param principalId
     * @param beginDate
     * @param endDate
     * @return boolean
     */
    public boolean isLeavePlanningCalendar(String principalId, Date beginDate, Date endDate);
    
    void approveLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument, String action);

    void routeLeaveCalendar(String principalId, LeaveCalendarDocument leaveCalendarDocument, String action);
    
    public BigDecimal getCarryOverForCurrentCalendar(String principalId);
}
