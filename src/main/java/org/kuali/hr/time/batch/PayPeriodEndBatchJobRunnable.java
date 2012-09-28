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

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
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

    private static final Logger LOG = Logger.getLogger(PayPeriodEndBatchJobRunnable.class);

    public PayPeriodEndBatchJobRunnable(BatchJobEntry entry) {
        super(entry);

    }

    @Override
    public void doWork() throws Exception {
        // For each batch job entry, grab the pay calendar entry id
        BatchJobEntry payPeriodEndBatchEntry = TkServiceLocator.getBatchJobEntryService().getBatchJobEntry(getTkBatchJobEntryId());
        // get pay calendar entry object by using id
//        TkServiceLocator.getPayCalendarEntriesService().getCurrentPayCalendarEntriesByPayCalendarId()
        CalendarEntries pe = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(payPeriodEndBatchEntry.getHrPyCalendarEntryId());
        // get open clock logs by pay calendar entry id
        List<ClockLog> openClockLogs = TkServiceLocator.getClockLogService().getOpenClockLogs(pe);
        // clock out at midnight based on user's timezone
        if (openClockLogs.size() > 0) {
            for (ClockLog cl : openClockLogs) {
                // clock out for each open clock log:
                // create a end period DateTime object at midnight based on user's timezone

                DateTime endPeriodDateTime = new DateTime(pe.getEndPeriodDateTime().getTime(), DateTimeZone.forID(cl.getClockTimestampTimezone()));
                TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(cl.getPrincipalId(), pe.getBeginPeriodDate(), pe.getEndPeriodDate());
                TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdh.getDocumentId());
                String assignmentKey = new AssignmentDescriptionKey(cl.getJobNumber(), cl.getWorkArea(), cl.getTask()).toAssignmentKeyString();
                Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(td, assignmentKey);

                TkServiceLocator.getClockLogService().processClockLog(new Timestamp(endPeriodDateTime.getMillis()), assignment, pe, cl.getIpAddress(),
                        new java.sql.Date(endPeriodDateTime.getMillis()), td, TkConstants.CLOCK_OUT, cl.getPrincipalId(), TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);

                // clock in
                TkServiceLocator.getClockLogService().processClockLog(new Timestamp(endPeriodDateTime.getMillis()), assignment, pe, cl.getIpAddress(),
                        new java.sql.Date(endPeriodDateTime.getMillis()), td, TkConstants.CLOCK_IN, cl.getPrincipalId(), TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);
            }

        }

    }
}
