package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.config.ConfigContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import java.sql.Timestamp;

public class BatchJob {
    private Logger LOG = Logger.getLogger(BatchJob.class);
	int lastPlace = 0;

	private Long tkBatchJobId;
	private String batchJobName;
	private String batchJobStatus;
	private String hrPyCalendarEntryId;
	private Long timeElapsed = 0L;
	private Timestamp timestamp;
    long startTime;
    long endTime;

    public BatchJob() {
        this.setBatchJobStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
        this.setTimestamp(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Setup logic goes here.
     */
    void doBeforeRun() {
        LOG.info("Starting batch job: " + this.getBatchJobName() + " for pay calendar entry: " + this.hrPyCalendarEntryId);
        startTime = System.currentTimeMillis();
        this.setBatchJobStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING);
        TkServiceLocator.getBatchJobService().saveBatchJob(this);
    }

    void runJob() {
    	TkServiceLocator.getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
		        doBeforeRun();
		        doWork();
		        doAfterRun();
			}
    	});
    }
    
	void doWork() {
        throw new UnsupportedOperationException("You must override this method in a subclass.");
	}

    /**
     * Cleanup logic goes here.
     */
    void doAfterRun() {
        endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        timeElapsed = (runtime > 0) ? runtime / 1000 : 0; // set to 0, and avoid div by 0.
        this.setBatchJobStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.FINISHED);
        TkServiceLocator.getBatchJobService().saveBatchJob(this);
        LOG.info("Batch job '" + this.getBatchJobName() + "' ("+this.getPayCalendarEntryId()+") complete after " + timeElapsed + " seconds.");
    }

	public String getNextIpAddressInCluster(){
		String clusterIps = ConfigContext.getCurrentContextConfig().getProperty("cluster.ips");
		String[] ips = StringUtils.split(clusterIps,",");
		if(ips != null){
			String ip = ips[lastPlace++];
			if(lastPlace >=ip.length()){
				lastPlace = 0;
			}
			return ip;
		}
		return "";
	}

	protected void populateBatchJobEntry(Object o){
        throw new UnsupportedOperationException("You must override this method in a subclass.");
	}

	public Long getTkBatchJobId() {
		return tkBatchJobId;
	}

	public void setTkBatchJobId(Long tkBatchJobId) {
		this.tkBatchJobId = tkBatchJobId;
	}

	public String getBatchJobName() {
		return batchJobName;
	}

	public void setBatchJobName(String batchJobName) {
		this.batchJobName = batchJobName;
	}

	public String getBatchJobStatus() {
		return batchJobStatus;
	}

	public void setBatchJobStatus(String batchJobStatus) {
		this.batchJobStatus = batchJobStatus;
	}

	public String getPayCalendarEntryId() {
		return hrPyCalendarEntryId;
	}

	public void setPayCalendarEntryId(String hrPyCalendarEntryId) {
		this.hrPyCalendarEntryId = hrPyCalendarEntryId;
	}

	public Long getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(Long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

    BatchJobEntry createBatchJobEntry(String batchJobName, String ip, String principal, String documentId, String clockLogId) {
        BatchJobEntry entry = new BatchJobEntry();

        entry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
        entry.setBatchJobName(batchJobName);
        entry.setIpAddress(ip);
        entry.setHrPyCalendarId(this.getPayCalendarEntryId());
        entry.setPrincipalId(principal);
        entry.setTkBatchJobId(this.getTkBatchJobId());
        entry.setDocumentId(documentId);
        entry.setClockLogId(clockLogId);

        return entry;
    }

}
