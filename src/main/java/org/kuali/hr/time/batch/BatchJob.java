package org.kuali.hr.time.batch;

import java.sql.Timestamp;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.config.ConfigContext;

public class BatchJob {
	int lastPlace = 0;

	private Long tkBatchJobId;
	private String batchJobName;
	private String batchJobStatus;
	private Long payCalendarEntryId;
	private Long timeElapsed;
	private Timestamp timestamp;

    public BatchJob() {
        this.setBatchJobStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
        this.setTimestamp(new Timestamp(System.currentTimeMillis()));
    }

	public void runJob() {
        throw new UnsupportedOperationException("You must override this method in a subclass.");
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

	public Long getPayCalendarEntryId() {
		return payCalendarEntryId;
	}

	public void setPayCalendarEntryId(Long payCalendarEntryId) {
		this.payCalendarEntryId = payCalendarEntryId;
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

    BatchJobEntry createBatchJobEntry(String batchJobName, String ip, String principal, String documentId) {
        BatchJobEntry entry = new BatchJobEntry();

        entry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
        entry.setBatchJobName(batchJobName);
        entry.setIpAddress(ip);
        entry.setPayCalendarId(this.getPayCalendarEntryId());
        entry.setPrincipalId(principal);
        entry.setTkBatchJobId(this.getTkBatchJobId());
        entry.setDocumentId(documentId);

        return entry;
    }

}
