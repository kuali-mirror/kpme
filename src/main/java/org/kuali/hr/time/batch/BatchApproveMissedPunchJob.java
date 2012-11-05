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
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.document.DocumentStatus;

public class BatchApproveMissedPunchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(BatchApproveMissedPunchJob.class);
	private CalendarEntries calendarEntry;
	
   public BatchApproveMissedPunchJob(CalendarEntries calendarEntry) {
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.BATCH_APPROVE_MISSED_PUNCH);
        this.setHrPyCalendarEntryId(calendarEntry.getHrCalendarEntriesId());
        this.calendarEntry = calendarEntry;
    }
   
   @Override
   public void doWork() {
	   List<TimesheetDocumentHeader> timesheetDocumentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(calendarEntry.getBeginPeriodDateTime(), calendarEntry.getEndPeriodDateTime());
       for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
		   List<MissedPunchDocument> missedPunchDocuments = TkServiceLocator.getMissedPunchService().getMissedPunchDocsByTimesheetDocumentId(timesheetDocumentHeader.getDocumentId());
	       for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
	    	   if (StringUtils.equals(missedPunchDocument.getDocumentStatus(), DocumentStatus.ENROUTE.getCode())) {
	    		   populateBatchJobEntry(missedPunchDocument);
	    	   }
	       }
       }
   }
   
   @Override
   protected void populateBatchJobEntry(Object o) {
       MissedPunchDocument mp = (MissedPunchDocument)o;
       String ip = this.getNextIpAddressInCluster();
       if(StringUtils.isNotBlank(ip)){
           //insert a batch job entry here
           BatchJobEntry entry = this.createBatchJobEntry(this.getBatchJobName(), ip, mp.getPrincipalId(), null, null);
           TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
       } else {
           LOG.info("No ip found in cluster to assign batch jobs");
       }
   }

}
