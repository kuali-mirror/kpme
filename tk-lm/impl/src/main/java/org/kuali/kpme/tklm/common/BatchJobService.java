/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.common;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.tklm.time.batch.BatchJobStatus;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

public interface BatchJobService {
    
    public static final String PENDING_JOB_STATUS_CODE = "Pending";
    public static final String SCHEDULED_JOB_STATUS_CODE = "Scheduled";
    public static final String RUNNING_JOB_STATUS_CODE = "Running";
    public static final String SUCCEEDED_JOB_STATUS_CODE = "Succeeded";
    public static final String FAILED_JOB_STATUS_CODE = "Failed";
    public static final String CANCELLED_JOB_STATUS_CODE = "Cancelled";
    
    public static final String SCHEDULED_GROUP = "scheduled";
	
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

	void schedulePayrollApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException;
	
	void schedulePayrollApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;
	
	void scheduleLeaveCalendarDelinquencyJobs(CalendarEntry calendarEntry) throws SchedulerException;
	
	void scheduleLeaveCalendarDelinquencyJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException;

    void scheduleClockedInEmployeeJob(DateTime scheduleDate) throws SchedulerException;
    
    List<BatchJobStatus> getJobs(String jobNm, String jobStatus, String hrCalendarEntryId, Date startDate, Date endDate) throws SchedulerException;
    
    public void updateStatus(JobDetail jobDetail, String jobStatus);

    public String getStatus(JobDetail jobDetail);
    
    public List<String> getJobStatuses();
    
    public boolean isJobRunning(String jobName);
}
