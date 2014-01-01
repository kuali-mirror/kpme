/**
 * Copyright 2004-2014 The Kuali Foundation
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class CalendarBlock extends CalendarBlockBase {

	private static final long serialVersionUID = -4680437059186586309L;
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

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public BigDecimal getHours() {
		return hours;
	}

	@Override
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	@Override
	public String getOvertimePref() {
		return overtimePref;
	}

	@Override
	public void setOvertimePref(String overtimePref) {
		this.overtimePref = overtimePref;
	}

	@Override
	public void setLunchDeleted(boolean lunchDeleted) {
		this.lunchDeleted = lunchDeleted;
	}

	@Override
	public boolean getLunchDeleted() {
		return lunchDeleted;
	}

}
