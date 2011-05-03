package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public abstract class BatchJobEntryRunnable implements Runnable{

    private Logger LOG = Logger.getLogger(BatchJobEntryRunnable.class);

    long startTime;
    long endTime;
	Long tkBatchJobEntryId;
	Long tkBatchJobId;
    BatchJobEntry batchJobEntry;

    public BatchJobEntryRunnable(BatchJobEntry entry) {
        this.batchJobEntry = entry;
        this.tkBatchJobEntryId = entry.getTkBatchJobEntryId();
        this.tkBatchJobId = entry.getTkBatchJobId();
    }

    /**
     * Method that is called before the user function doWork() is called. Any
     * pre-work maintenance can be done in this method.
     */
    void doBeforeRun() {
        startTime = System.currentTimeMillis();
        LOG.debug("Before run.");
        batchJobEntry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING);
        TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(batchJobEntry);
    }

	@Override
	public final void run() {
        doBeforeRun();
        doWork();
        doAfterRun();
	}

    /**
     * Implement this method in your subclass. Place business logic here.
     */
    public abstract void doWork();

    /**
     * Method that is called after the user function doWork() is called. Any
     * post-work maintenance can be done in this method.
     */
    void doAfterRun() {
        endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        runtime = (runtime > 0) ? runtime : 1; // hack around 0 length job... just in case.
        LOG.debug("Job finished in " + runtime / 1000 + " seconds.");
        batchJobEntry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.FINISHED);
        TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(batchJobEntry);
    }

	public Long getTkBatchJobEntryId() {
		return tkBatchJobEntryId;
	}

	public Long getTkBatchJobId() {
		return tkBatchJobId;
	}

    public BatchJobEntry getBatchJobEntry() {
        return batchJobEntry;
    }
}
