package org.kuali.hr.time.batch;

public class BatchJobEntry {
	private Long tkBatchJobEntryId;
	private Long tkBatchJobId;
	private String documentId;
	private String principalId;
	private String batchJobEntryStatus;
	private String batchJobException;
	private String ipAddress;
	private String batchJobName;
	public Long getTkBatchJobEntryId() {
		return tkBatchJobEntryId;
	}
	public void setTkBatchJobEntryId(Long tkBatchJobEntryId) {
		this.tkBatchJobEntryId = tkBatchJobEntryId;
	}
	public Long getTkBatchJobId() {
		return tkBatchJobId;
	}
	public void setTkBatchJobId(Long tkBatchJobId) {
		this.tkBatchJobId = tkBatchJobId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getBatchJobEntryStatus() {
		return batchJobEntryStatus;
	}
	public void setBatchJobEntryStatus(String batchJobEntryStatus) {
		this.batchJobEntryStatus = batchJobEntryStatus;
	}
	public String getBatchJobException() {
		return batchJobException;
	}
	public void setBatchJobException(String batchJobException) {
		this.batchJobException = batchJobException;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getBatchJobName() {
		return batchJobName;
	}
	public void setBatchJobName(String batchJobName) {
		this.batchJobName = batchJobName;
	}
}
