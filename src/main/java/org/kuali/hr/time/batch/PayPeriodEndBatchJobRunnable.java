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
package org.kuali.hr.time.batch;

import java.sql.Date;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public class PayPeriodEndBatchJobRunnable extends BatchJobEntryRunnable {

    public PayPeriodEndBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

    @Override
    public void doWork() throws Exception {
        BatchJobEntry payPeriodEndBatchEntry = TkServiceLocator.getBatchJobEntryService().getBatchJobEntry(getTkBatchJobEntryId());
        CalendarEntries calendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(payPeriodEndBatchEntry.getHrPyCalendarEntryId());
        Date beginDate = calendarEntry.getBeginPeriodDate();
        Date endDate = calendarEntry.getEndPeriodDate();
        ClockLog openClockLog = TkServiceLocator.getClockLogService().getClockLog(payPeriodEndBatchEntry.getClockLogId());
        String ipAddress = openClockLog.getIpAddress();
    	String principalId = openClockLog.getPrincipalId();
        DateTime endPeriodDateTime = new DateTime(calendarEntry.getEndPeriodDateTime().getTime(), DateTimeZone.forID(openClockLog.getClockTimestampTimezone()));
        Date endPeriodDate = new Date(endPeriodDateTime.getMillis());
        Timestamp endPeriodTimestamp = new Timestamp(endPeriodDateTime.getMillis());
        
        TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
        if (timesheetDocumentHeader != null) {
            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentHeader.getDocumentId());
            String assignmentKey = new AssignmentDescriptionKey(openClockLog.getJobNumber(), openClockLog.getWorkArea(), openClockLog.getTask()).toAssignmentKeyString();
            Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument, assignmentKey);

            TkServiceLocator.getClockLogService().processClockLog(endPeriodTimestamp, assignment, calendarEntry, ipAddress, endPeriodDate, timesheetDocument, 
            		TkConstants.CLOCK_OUT, principalId, TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);
            TkServiceLocator.getClockLogService().processClockLog(endPeriodTimestamp, assignment, calendarEntry, ipAddress, endPeriodDate, timesheetDocument, 
            		TkConstants.CLOCK_IN, principalId, TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);
        }
    }
    
}