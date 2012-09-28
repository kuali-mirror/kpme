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

import org.apache.log4j.Logger;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;

public class InitiateBatchJobRunnable extends BatchJobEntryRunnable {

	private Logger LOG = Logger.getLogger(InitiateBatchJobRunnable.class);
	
    public InitiateBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

	@Override
	public void doWork() throws Exception {
		BatchJobEntry initiateBatchJobEntry = TkServiceLocator.getBatchJobEntryService().getBatchJobEntry(getTkBatchJobEntryId());
		String principalId = initiateBatchJobEntry.getPrincipalId();
		String hrPyCalendarId = initiateBatchJobEntry.getHrPyCalendarEntryId();
		CalendarEntries payCalendarEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(hrPyCalendarId);
		try {
			TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, payCalendarEntry);
		} catch (WorkflowException e) {
			LOG.info("Workflow error found"+ e);
            throw e;
		}

	}

}
