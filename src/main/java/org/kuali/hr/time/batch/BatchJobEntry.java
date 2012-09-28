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
	private String hrPyCalendarEntryId;
	private String clockLogId;
	
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
	public void setHrPyCalendarEntryId(String hrPyCalendarEntryId) {
		this.hrPyCalendarEntryId = hrPyCalendarEntryId;
	}
	public String getHrPyCalendarEntryId() {
		return hrPyCalendarEntryId;
	}
	public void setClockLogId(String clockLogId) {
		this.clockLogId = clockLogId;
	}
	public String getClockLogId() {
		return clockLogId;
	}
}
