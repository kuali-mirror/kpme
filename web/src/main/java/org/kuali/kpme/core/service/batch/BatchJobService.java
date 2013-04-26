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
package org.kuali.kpme.core.service.batch;

import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.leaveplan.LeavePlan;
import org.quartz.SchedulerException;

public interface BatchJobService {
    
    void scheduleInitiateJobs(CalendarEntry calendarEntry) throws SchedulerException;
    
    void scheduleInitiateJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;
    
    void scheduleEndReportingPeriodJobs(CalendarEntry calendarEntry) throws SchedulerException;
    
    void scheduleEndReportingPeriodJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;
    
    void scheduleEndPayPeriodJobs(CalendarEntry calendarEntry) throws SchedulerException;
    
    void scheduleEndPayPeriodJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;

    void scheduleEmployeeApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException;
    
    void scheduleEmployeeApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;
    
    void scheduleMissedPunchApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException;
    
    void scheduleMissedPunchApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;

    void scheduleSupervisorApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException;
    
    void scheduleSupervisorApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;
    
    void scheduleLeaveCarryOverJobs(LeavePlan leavePlan) throws SchedulerException;
    
    void scheduleLeaveCarryOverJobs(LeavePlan leavePlan, DateTime scheduleDate) throws SchedulerException;
    
}