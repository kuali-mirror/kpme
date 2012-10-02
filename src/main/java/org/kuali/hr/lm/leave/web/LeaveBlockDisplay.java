package org.kuali.hr.lm.leave.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.BooleanUtils;
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
		return leaveBlock.getDescription();
	}
	
	public String getRequestStatus() {
		String requestStatus = null;
		
		if (StringUtils.isNotBlank(leaveBlock.getDocumentStatus())) {
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
		BigDecimal leaveAmount = leaveBlock.getLeaveAmount();
		
		if (StringUtils.isBlank(leaveBlock.getRequestStatus()) || StringUtils.equalsIgnoreCase(leaveBlock.getRequestStatus(), LMConstants.REQUEST_STATUS.USAGE)) {
			leaveAmount = leaveAmount.multiply(new BigDecimal(-1));
		}
		
		return leaveAmount;
	}
	
	public Timestamp getTimestamp() {
		return leaveBlock.getTimestamp();
	}
	
	public String getAssignmentTitle() {
		return leaveBlock.getAssignmentTitle();
	}
	
	public String getAccrualCategoryId() {
		return leaveBlock.getAccrualCategoryId();
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

}