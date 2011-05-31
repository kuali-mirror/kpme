package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class PayPeriodEndBatchJobRunnable extends BatchJobEntryRunnable {

    private static final Logger LOG = Logger.getLogger(PayPeriodEndBatchJobRunnable.class);

    public PayPeriodEndBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
        
    }

    @Override
    public void doWork() throws Exception {
		BatchJobEntry payPeriodEndBatchEntry = TkServiceLocator.getBatchJobEntryService().getBatchJobEntry(getTkBatchJobEntryId());
		String principalId = payPeriodEndBatchEntry.getPrincipalId();
		Long payCalendarId = payPeriodEndBatchEntry.getPayCalendarEntryId();
		
		//ClockLog currentClockLog = TkServiceLocator.getClockLogService().
		
		//TkServiceLocator.getClockLogService().buildClockLog(clockTimestamp, selectedAssign, timesheetDocument, clockAction, ip);
		//TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, beginTime, endTime, assignment, earnCode, hours, amount, isClockLogCreated)
    }
}
