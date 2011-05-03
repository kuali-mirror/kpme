package org.kuali.hr.time.batch;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class EmployeeApprovalBatchJobRunnable extends BatchJobEntryRunnable {

    public EmployeeApprovalBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

	@Override
	public void doWork() {
		String principalId = batchJobEntry.getPrincipalId();
		String documentId = batchJobEntry.getDocumentId();
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
		TkServiceLocator.getTimesheetService().routeTimesheet(principalId, timesheetDocument);
	}

}
