package org.kuali.hr.time.batch;

public abstract class BatchJobEntryRunnable implements Runnable{
	Long tkBatchJobEntryId;
	Long tkBatchJobId;
    BatchJobEntry batchJobEntry;

    public BatchJobEntryRunnable(BatchJobEntry entry) {
        this.batchJobEntry = entry;
        this.tkBatchJobEntryId = entry.getTkBatchJobEntryId();
        this.tkBatchJobId = entry.getTkBatchJobId();
    }

	@Override
	public void run() {
        throw new UnsupportedOperationException("You must implement this method in a subclass.");
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
