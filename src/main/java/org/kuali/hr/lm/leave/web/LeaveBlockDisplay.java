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
package org.kuali.hr.lm.leave.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.api.KewApiConstants;

public class LeaveBlockDisplay {
	
	private LeaveBlock leaveBlock;
	private SortedMap<String, BigDecimal> accrualBalances = new TreeMap<String, BigDecimal>();

	public LeaveBlockDisplay(LeaveBlock leaveBlock) {
		this.leaveBlock = leaveBlock;
	}
	
	public Date getLeaveDate() {
		return leaveBlock.getLeaveDate();
	}
	
	public String getDocumentId() {
		return leaveBlock.getDocumentId();
	}
	
	public String getCalendarId() {
		return leaveBlock.getCalendarId();
	}
	
	public String getEarnCode() {
		return leaveBlock.getEarnCode();
	}
	
	public String getDescription() {
		if(leaveBlock.getDescription() == null || leaveBlock.getDescription().trim().isEmpty()) {
			return retrieveDescriptionAccordingToLeaveType(leaveBlock.getLeaveBlockType());
		}
		return leaveBlock.getDescription();
	}
	
	public String getRequestStatus() {
		String requestStatus = null;
		
		if (StringUtils.isNotBlank(leaveBlock.getRequestStatus())) {
			requestStatus = LMConstants.REQUEST_STATUS_STRINGS.get(leaveBlock.getRequestStatus());
		}
		
		return requestStatus;
	}
	
	public String getDocumentStatus() {
		String documentStatus = null;
		
		if (StringUtils.isNotBlank(leaveBlock.getDocumentId())) {
			LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(leaveBlock.getDocumentId());
			if (lcdh != null ) {
				documentStatus = KewApiConstants.DOCUMENT_STATUSES.get(lcdh.getDocumentStatus());
			}
		}
		
		return documentStatus;
	}
	
	public BigDecimal getLeaveAmount() {
		return leaveBlock.getLeaveAmount();
	}
	
	public Timestamp getTimestamp() {
		return leaveBlock.getTimestamp();
	}
	
	public String getAssignmentTitle() {
		return leaveBlock.getAssignmentTitle();
	}
	
	public String getAccrualCategory() {
		return leaveBlock.getAccrualCategory();
	}
	
	public Collection<BigDecimal> getAccrualBalances() {
		return accrualBalances.values();
	}

	public BigDecimal getAccrualBalance(String accrualCategory) {
		return accrualBalances.get(accrualCategory);
	}

	public void setAccrualBalance(String accrualCategory, BigDecimal accrualBalance) {
		this.accrualBalances.put(accrualCategory, accrualBalance);
	}
	
	private String retrieveDescriptionAccordingToLeaveType(String leaveType) {
		String description = null;
		description = LMConstants.LEAVE_BLOCK_TYPE_MAP.get(leaveType);
		return description;
	}

}