/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.batch.web;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.util.TkConstants;

public class BatchJobActionForm extends TkForm {

    private static final long serialVersionUID = 385904747462568474L;
    
    private static final List<String> BATCH_JOB_NAMES = new ArrayList<String>();
    private static final List<String> BATCH_JOB_STATUSES = new ArrayList<String>();
    
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
    
    static {
    	BATCH_JOB_NAMES.add(TkConstants.BATCH_JOB_NAMES.INITIATE);
    	BATCH_JOB_NAMES.add(TkConstants.BATCH_JOB_NAMES.APPROVE);
    	BATCH_JOB_NAMES.add(TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END);
    	BATCH_JOB_NAMES.add(TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL);
    	BATCH_JOB_NAMES.add(TkConstants.BATCH_JOB_NAMES.BATCH_APPROVE_MISSED_PUNCH);
    	
    	BATCH_JOB_STATUSES.add(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING);
    	BATCH_JOB_STATUSES.add(TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);
    	BATCH_JOB_STATUSES.add(TkConstants.BATCH_JOB_ENTRY_STATUS.FINISHED);
    }

    public List<String> getBatchJobNames() {
        return BATCH_JOB_NAMES;
    }

    public List<String> getBatchJobStatuses() {
        return BATCH_JOB_STATUSES;
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
