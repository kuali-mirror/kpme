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
