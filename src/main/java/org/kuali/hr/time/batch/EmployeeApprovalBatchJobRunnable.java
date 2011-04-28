package org.kuali.hr.time.batch;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class EmployeeApprovalBatchJobRunnable extends BatchJobEntryRunnable {

	@Override
	public void run() {
		//TODO Fetch the BatchJobEntry 
		BatchJobEntry employeeApprovalBatchJobEntry = null;
		
		String principalId = employeeApprovalBatchJobEntry.getPrincipalId();
		String documentId = employeeApprovalBatchJobEntry.getDocumentId();
		TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
		TkServiceLocator.getTimesheetService().routeTimesheet(principalId, timesheetDocument);
	}

}
