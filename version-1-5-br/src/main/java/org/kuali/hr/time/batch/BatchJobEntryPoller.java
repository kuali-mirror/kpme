package org.kuali.hr.time.batch;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

/**
 * Runs on each worker node, schedules jobs to run on the thread pool.
 */
public class BatchJobEntryPoller extends Thread  {
    private static final Logger LOG = Logger.getLogger(BatchJobEntryPoller.class);

    TkBatchManager manager;
    int secondsToSleep;
    int startupSleep;
    String ipAddress;

    public BatchJobEntryPoller(TkBatchManager manager, int sleepSeconds, int startupSleep) {
        this.manager = manager;
        this.secondsToSleep = sleepSeconds;
        this.startupSleep = startupSleep;
        this.ipAddress = TKUtils.getIPNumber();
    }

	@Override
	public void run() {

        try {
            Thread.sleep(1000 * startupSleep);
        } catch (Exception e) {
            LOG.error(e);
        }

        while(true) {
            LOG.debug("Looking for BatchJobEntries to run on '" + this.ipAddress + "'");
            try {
    		    //Find any jobs for this ip address that have a status of S
                List<BatchJobEntry> entries = TkServiceLocator.getBatchJobEntryService().getBatchJobEntries(ipAddress, TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);

	        	//Add jobs to the manager
                for (BatchJobEntry entry : entries) {
                    manager.pool.submit(getRunnable(entry));
                }
                Thread.sleep(1000 * secondsToSleep);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
	}

    private BatchJobEntryRunnable getRunnable(BatchJobEntry entry) {
        BatchJobEntryRunnable bjer = null;

        if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.APPROVE)) {
            LOG.debug("Creating EmployeeApprovalBatchJobRunnable.");
            bjer = new EmployeeApprovalBatchJobRunnable(entry);
        } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END)) {
            LOG.debug("Creating PayPeriodEndBatchJobRunnable.");
            bjer = new PayPeriodEndBatchJobRunnable(entry);
        } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL)) {
            LOG.debug("Creating SupervisorApprovalBatchJobRunnabble.");
            bjer = new SupervisorApprovalBatchJobRunnable(entry);
        } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.INITIATE)) {
            LOG.debug("Creating InitiateBatchJobRunnable.");
            bjer = new InitiateBatchJobRunnable(entry);
        } else if(StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.BATCH_APPROVE_MISSED_PUNCH)) {
        	LOG.debug("Creating BatchApproveMissedPunchJobRunnable.");
            bjer = new BatchApproveMissedPunchJobRunnable(entry);
        } else {
            LOG.warn("Unknown BatchJobEntryRunnable found in BatchJobEntry table. Unable to create Runnable.");
        }

        return bjer;
    }

}
