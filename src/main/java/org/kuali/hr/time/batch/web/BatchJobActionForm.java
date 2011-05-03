package org.kuali.hr.time.batch.web;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.util.TkConstants;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BatchJobActionForm extends TkForm {
    /**
     *
     */
    private static final long serialVersionUID = 385904747462568474L;
    private Map<String, String> batchJobNames = new LinkedHashMap<String, String>();
    private Map<String, String> batchJobStatuses = new LinkedHashMap<String, String>();
    private List<BatchJobEntry> batchJobEntries = new LinkedList<BatchJobEntry>();
    private String batchJobId;
    private String documentId;
    private String principalId;
    private String payCalendarEntryId;
    private String ipAddress;
    private String batchJobName;
    private String batchJobEntryStatus;



    public Map<String, String> getBatchJobNames() {
        batchJobNames.put(TkConstants.BATCH_JOB_NAMES.INITIATE, "initiate");
        batchJobNames.put(TkConstants.BATCH_JOB_NAMES.APPROVE, "approve");
        batchJobNames.put(TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END, "payPeriodEnd");
        batchJobNames.put(TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL, "supervisorApproval");

        return batchJobNames;
    }

    public void setBatchJobNames(Map<String, String> batchJobs) {
        this.batchJobNames = batchJobs;
    }

    public Map<String, String> getBatchJobStatuses() {
    	batchJobStatuses.put(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING, "running");
    	batchJobStatuses.put(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED, "scheduled");
    	batchJobStatuses.put(TkConstants.BATCH_JOB_ENTRY_STATUS.FINISHED, "finished");

        return batchJobStatuses;
    }

    public void setBatchJobStatuses(Map<String, String> batchJobStatuses) {
        this.batchJobStatuses = batchJobStatuses;
    }

	public String getBatchJobId() {
		return batchJobId;
	}

	public void setBatchJobId(String batchJobId) {
		this.batchJobId = batchJobId;
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

	public String getPayCalendarEntryId() {
		return payCalendarEntryId;
	}

	public void setPayCalendarEntryId(String payCalendarEntryId) {
		this.payCalendarEntryId = payCalendarEntryId;
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

	public String getBatchJobEntryStatus() {
		return batchJobEntryStatus;
	}

	public void setBatchJobEntryStatus(String batchJobEntryStatus) {
		this.batchJobEntryStatus = batchJobEntryStatus;
	}

	public List<BatchJobEntry> getBatchJobEntries() {
		return batchJobEntries;
	}

	public void setBatchJobEntries(List<BatchJobEntry> batchJobEntries) {
		this.batchJobEntries = batchJobEntries;
	}

}
