package org.kuali.hr.time.batch.web;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.util.TkConstants;

public class BatchJobActionForm extends TkForm {
    /**
     *
     */
    private static final long serialVersionUID = 385904747462568474L;
    private List<String> batchJobNames = new ArrayList<String>();
    private List<String> batchJobStatuses = new ArrayList<String>();
    private List<BatchJobEntry> batchJobEntries = new LinkedList<BatchJobEntry>();
    private String batchJobId;
    private String documentId;
    private String principalId;
    private String hrPyCalendarEntryId;
    private String ipAddress;
    private String batchJobName;
    private String batchJobEntryStatus;
    private String tkBatchJobEntryId;
    private String ipToChange;
    private String selectedBatchJob;

    public List<String> getBatchJobNames() {
        batchJobNames.add(TkConstants.BATCH_JOB_NAMES.INITIATE);
        batchJobNames.add(TkConstants.BATCH_JOB_NAMES.APPROVE);
        batchJobNames.add(TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END);
        batchJobNames.add(TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL);
        batchJobNames.add(TkConstants.BATCH_JOB_NAMES.BATCH_APPROVE_MISSED_PUNCH);

        return batchJobNames;
    }

    public void setBatchJobNames(List<String> batchJobs) {
        this.batchJobNames = batchJobs;
    }

    public List<String> getBatchJobStatuses() {
        batchJobStatuses.add(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING);
        batchJobStatuses.add(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
        batchJobStatuses.add(TkConstants.BATCH_JOB_ENTRY_STATUS.FINISHED);

        return batchJobStatuses;
    }

    public void setBatchJobStatuses(List<String> batchJobStatuses) {
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

    public String getHrPyCalendarEntryId() {
        return hrPyCalendarEntryId;
    }

    public void setHrPyCalendarEntryId(String hrPyCalendarEntryId) {
        this.hrPyCalendarEntryId = hrPyCalendarEntryId;
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

    public String getTkBatchJobEntryId() {
        return tkBatchJobEntryId;
    }

    public void setTkBatchJobEntryId(String tkBatchJobEntryId) {
        this.tkBatchJobEntryId = tkBatchJobEntryId;
    }

    public String getIpToChange() {
        return ipToChange;
    }

    public void setIpToChange(String ipToChange) {
        this.ipToChange = ipToChange;
    }

    public String getSelectedBatchJob() {
        return selectedBatchJob;
    }

    public void setSelectedBatchJob(String selectedBatchJob) {
        this.selectedBatchJob = selectedBatchJob;
    }
}
