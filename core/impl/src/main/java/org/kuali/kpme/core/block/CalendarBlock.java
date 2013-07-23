/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.block;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

import org.apache.log4j.Logger;

public class CalendarBlock extends CalendarBlockBase {

	private static final Logger LOG = Logger.getLogger(CalendarBlock.class);
	
	public CalendarBlock() {
		this.concreteBlockType = this.getClass().getName();
	}
	
	@Override
	public String getConcreteBlockType() {
		return concreteBlockType;
	}

	@Override
	public void setConcreteBlockType(String concreteBlockType) {
		this.concreteBlockType = concreteBlockType;
	}

	@Override
	public String getConcreteBlockId() {
		return concreteBlockId;
	}

	@Override
	public void setConcreteBlockId(String concreteBlockId) {
		this.concreteBlockId = concreteBlockId;
	}

	@Override
	public Long getWorkArea() {
		return workArea;
	}

	@Override
	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	@Override
	public Long getJobNumber() {
		return jobNumber;
	}

	@Override
	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	@Override
	public Long getTask() {
		return task;
	}

	@Override
	public void setTask(Long task) {
		this.task = task;
	}
	
	@Override
	public String getEarnCode() {
		return earnCode;
	}

	@Override
	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	@Override
	public String getDocumentId() {
		return documentId;
	}

	@Override
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public void setPrincipalIdModified(String principalIdModified) {
		this.principalIdModified = principalIdModified;
	}

	@Override
	public String getPrincipalId() {
		return principalId;
	}

	@Override
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	@Override
	public Timestamp getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public Date getBeginTimestamp() {
		return beginTimestamp;
	}

	@Override
	public void setBeginTimestamp(Date beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	@Override
	public Date getEndTimestamp() {
		return endTimestamp;
	}

	@Override
	public void setEndTimestamp(Date endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

}
