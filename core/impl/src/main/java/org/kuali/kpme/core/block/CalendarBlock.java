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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.service.HrServiceLocator;

public class CalendarBlock extends CalendarBlockBase {

	private static final long serialVersionUID = -4680437059186586309L;
	private static final Logger LOG = Logger.getLogger(CalendarBlock.class);
    protected String groupKeyCode;
    protected HrGroupKey groupKey;
	
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

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	@Override
	public Long getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	@Override
	public Long getTask() {
		return task;
	}

	public void setTask(Long task) {
		this.task = task;
	}
	
	@Override
	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	@Override
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Override
	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getBeginTimestamp() {
		return beginTimestamp;
	}

    @Override
    public DateTime getBeginDateTime() {
        return beginTimestamp == null ? null : new DateTime(beginTimestamp.getTime());
    }

	public void setBeginTimestamp(Timestamp beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	public Timestamp getEndTimestamp() {
		return endTimestamp;
	}

    @Override
    public DateTime getEndDateTime() {
        return endTimestamp == null ? null : new DateTime(endTimestamp.getTime());
    }

	public void setEndTimestamp(Timestamp endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public BigDecimal getHours() {
		return hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	@Override
	public String getOvertimePref() {
		return overtimePref;
	}

	public void setOvertimePref(String overtimePref) {
		this.overtimePref = overtimePref;
	}

	public void setLunchDeleted(boolean lunchDeleted) {
		this.lunchDeleted = lunchDeleted;
	}

	@Override
	public boolean isLunchDeleted() {
		return lunchDeleted;
	}

    @Override
    public String getGroupKeyCode() {
        return groupKeyCode;
    }

    public void setGroupKeyCode(String groupKeyCode) {
        this.groupKeyCode = groupKeyCode;
    }

    @Override
    public HrGroupKey getGroupKey() {
        if (groupKey == null
                && getGroupKeyCode() != null
                && getBeginDateTime() != null) {
            setGroupKey(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), this.getBeginDateTime().toLocalDate()));
        }
        return groupKey;
    }

    public void setGroupKey(HrGroupKey groupKey) {
        this.groupKey = groupKey;
    }
}
