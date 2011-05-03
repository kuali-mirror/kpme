package org.kuali.hr.time.batch;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.exception.WorkflowException;
import org.mortbay.log.Log;
import org.openqa.selenium.internal.seleniumemulation.GetBodyText;

public class InitiateBatchJobRunnable extends BatchJobEntryRunnable {

    public InitiateBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

	@Override
	public void run() {
		String principalId = batchJobEntry.getPrincipalId();
		Long payCalendarId = batchJobEntry.getPayCalendarId();
		PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(payCalendarId);
		try {
			TkServiceLocator.getTimesheetService().openTimesheetDocument(principalId, payCalendarEntry);
		} catch (WorkflowException e) {
			Log.info("Workflow error found"+ e);
		}

	}

}
