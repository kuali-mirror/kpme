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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public class SupervisorApprovalBatchJob extends BatchJob {
    private Logger LOG = Logger.getLogger(PayPeriodEndBatchJob.class);
    private CalendarEntries calendarEntry;

    public SupervisorApprovalBatchJob(CalendarEntries calendarEntry) {
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL);
        this.setHrPyCalendarEntryId(calendarEntry.getHrCalendarEntriesId());
        this.calendarEntry = calendarEntry;
    }

    @Override
    public void doWork() {
        List<TimesheetDocumentHeader> documentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(calendarEntry.getBatchEmployeeApprovalDate());
        for(TimesheetDocumentHeader timesheetDocumentHeader : documentHeaders){
            populateBatchJobEntry(timesheetDocumentHeader);
        }
    }


    @Override
    protected void populateBatchJobEntry(Object o) {
    	TimesheetDocumentHeader tdh = (TimesheetDocumentHeader)o;
        String ip = this.getNextIpAddressInCluster();
        if(StringUtils.isNotBlank(ip)){
            //insert a batch job entry here
            BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, tdh.getPrincipalId(), tdh.getDocumentId(),null);
            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
        } else {
            LOG.info("No ip found in cluster to assign batch jobs");
        }
    }

}
