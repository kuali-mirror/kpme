package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class SupervisorApprovalBatchJobRunnable extends BatchJobEntryRunnable  {

    public SupervisorApprovalBatchJobRunnable(BatchJobEntry entry) {
		super(entry);
	}

	private static final Logger LOG = Logger.getLogger(SupervisorApprovalBatchJobRunnable.class);

    @Override
    public void doWork() throws Exception {
		String principalId = batchJobEntry.getPrincipalId();
		String documentId = batchJobEntry.getDocumentId();
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
		TkServiceLocator.getTimesheetService().approveTimesheet(principalId, timesheetDocument);
    }
}
