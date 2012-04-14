package org.kuali.hr.lm.leaveblock;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class LeaveBlockHistory extends LeaveBlock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmLeaveBlockHistoryId;
	private String action;
	private String principalIdDeleted;
	private Timestamp timestampDeleted;
	
	public LeaveBlockHistory() {
	}

	public LeaveBlockHistory(LeaveBlock lb) {
		this.setLmLeaveBlockId(lb.getLmLeaveBlockId());
		this.setDocumentId(lb.getDocumentId());
		this.setAccrualCategoryId(lb.getAccrualCategoryId());
		this.setAccrualGenerated(lb.getAccrualGenerated());
		this.setApplyToYtdUsed(lb.getApplyToYtdUsed());
		this.setDescription(lb.getDescription());
		this.setLeaveAmount(lb.getLeaveAmount());
		this.setLeaveCodeId(lb.getLeaveCodeId());
		this.setLeaveCode(lb.getLeaveCode());
		this.setLeaveDate(lb.getLeaveDate());
		this.setPrincipalId(lb.getPrincipalId());
//		this.setPrincipalIdModified(lb.getPrincipalIdModified());
		this.setRequestStatus(lb.getRequestStatus());
		this.setTimestamp(lb.getTimestamp());
		this.setTkAssignmentId(lb.getTkAssignmentId());
		this.setScheduleTimeOffId(lb.getScheduleTimeOffId());
		
	}
	
	
	public String getLmLeaveBlockHistoryId() {
		return lmLeaveBlockHistoryId;
	}

	public void setLmLeaveBlockHistoryId(String lmLeaveBlockHistoryId) {
		this.lmLeaveBlockHistoryId = lmLeaveBlockHistoryId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPrincipalIdDeleted() {
		return principalIdDeleted;
	}

	public void setPrincipalIdDeleted(String principalIdDeleted) {
		this.principalIdDeleted = principalIdDeleted;
	}

	public Timestamp getTimestampDeleted() {
		return timestampDeleted;
	}

	public void setTimestampDeleted(Timestamp timestampDeleted) {
		this.timestampDeleted = timestampDeleted;
	}

//	@Override
//	protected LinkedHashMap toStringMapper() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
