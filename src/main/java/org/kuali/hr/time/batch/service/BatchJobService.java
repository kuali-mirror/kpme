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
package org.kuali.hr.time.batch.service;

import java.util.Date;

import org.kuali.hr.time.calendar.CalendarEntries;
import org.quartz.SchedulerException;

public interface BatchJobService {
    
    void scheduleInitiateJobs(CalendarEntries calendarEntry) throws SchedulerException;
    
    void scheduleInitiateJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException;
    
    void scheduleEndReportingPeriodJobs(CalendarEntries calendarEntry) throws SchedulerException;
    
    void scheduleEndReportingPeriodJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException;
    
    void scheduleEndPayPeriodJobs(CalendarEntries calendarEntry) throws SchedulerException;
    
    void scheduleEndPayPeriodJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException;

    void scheduleEmployeeApprovalJobs(CalendarEntries calendarEntry) throws SchedulerException;
    
    void scheduleEmployeeApprovalJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException;
    
    void scheduleMissedPunchApprovalJobs(CalendarEntries calendarEntry) throws SchedulerException;
    
    void scheduleMissedPunchApprovalJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException;

    void scheduleSupervisorApprovalJobs(CalendarEntries calendarEntry) throws SchedulerException;
    
    void scheduleSupervisorApprovalJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException;
    
}