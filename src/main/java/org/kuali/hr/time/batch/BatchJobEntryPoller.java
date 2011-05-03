package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.util.List;

/**
 * Runs on each worker node, schedules jobs to run on the thread pool.
 */
public class BatchJobEntryPoller extends Thread  {
    private static final Logger LOG = Logger.getLogger(BatchJobEntryPoller.class);

    TkBatchManager manager;
    int secondsToSleep;
    String ipAddress;

    public BatchJobEntryPoller(TkBatchManager manager, int sleepSeconds) {
        this.manager = manager;
        this.secondsToSleep = sleepSeconds;
        this.ipAddress = TKUtils.getIPNumber();
    }

	@Override
	public void run() {
        while(true) {
            LOG.debug("Looking for BatchJobEntries to run on '" + this.ipAddress + "'");
            try {
    		    //Find any jobs for this ip address that have a status of S
                List<BatchJobEntry> entries = TkServiceLocator.getBatchJobEntryService().getBatchJobEntries(ipAddress, TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);

	        	//Add jobs ot the manager, Update Status
                for (BatchJobEntry entry : entries) {
                    entry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING);
                    TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
                    // Add to thread pool of manager.
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
            // TODO : Create this Runnable
            LOG.warn("Runnable does not yet exist.");
        } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL)) {
            // TODO : Create this Runnable.
            LOG.warn("Runnable does not yet exist.");
        } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.INITIATE)) {
            LOG.debug("Creating InitiateBatchJobRunnable.");
            bjer = new InitiateBatchJobRunnable(entry);
        } else {
            LOG.warn("Unknown BatchJobEntryRunnable found in BatchJobEntry table. Unable to create Runnable.");
        }

        return bjer;
    }

}
