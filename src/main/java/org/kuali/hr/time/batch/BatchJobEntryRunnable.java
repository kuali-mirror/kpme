package org.kuali.hr.time.batch;

public abstract class BatchJobEntryRunnable implements Runnable{
	private Long tkBatchJobEntryId;
	private Long tkBatchJobId;
	
	
	@Override
	public void run() {
	}


	public Long getTkBatchJobEntryId() {
		return tkBatchJobEntryId;
	}


	public void setTkBatchJobEntryId(Long tkBatchJobEntryId) {
		this.tkBatchJobEntryId = tkBatchJobEntryId;
	}


	public void setTkBatchJobId(Long tkBatchJobId) {
		this.tkBatchJobId = tkBatchJobId;
	}


	public Long getTkBatchJobId() {
		return tkBatchJobId;
	}

}
