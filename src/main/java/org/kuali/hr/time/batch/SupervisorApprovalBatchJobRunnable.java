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
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;

public class SupervisorApprovalBatchJobRunnable extends BatchJobEntryRunnable  {

    public SupervisorApprovalBatchJobRunnable(BatchJobEntry entry) {
		super(entry);
	}

	private static final Logger LOG = Logger.getLogger(SupervisorApprovalBatchJobRunnable.class);

    @Override
    public void doWork() throws Exception {
		String principalId = TkConstants.BATCH_JOB_USER_PRINCIPAL_ID;
		String documentId = batchJobEntry.getDocumentId();
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
		TkServiceLocator.getTimesheetService().approveTimesheet(principalId, timesheetDocument, TkConstants.BATCH_JOB_ACTIONS.BATCH_JOB_APPROVE);
    }
}
