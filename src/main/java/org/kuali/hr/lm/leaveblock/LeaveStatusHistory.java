package org.kuali.hr.lm.leaveblock;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class LeaveStatusHistory extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmLeaveStatusHistoryId;
	private String lmLeaveBlockId;
	private String requestStatus;
	private Timestamp timestamp;
	private String principalIdModified;
	private String reason;

	public String getLmLeaveStatusHistoryId() {
		return lmLeaveStatusHistoryId;
	}

	public void setLmLeaveStatusHistoryId(String lmLeaveStatusHistoryId) {
		this.lmLeaveStatusHistoryId = lmLeaveStatusHistoryId;
	}

	public String getLmLeaveBlockId() {
		return lmLeaveBlockId;
	}

	public void setLmLeaveBlockId(String lmLeaveBlockId) {
		this.lmLeaveBlockId = lmLeaveBlockId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getPrincipalIdModified() {
		return principalIdModified;
	}

	public void setPrincipalIdModified(String principalIdModified) {
		this.principalIdModified = principalIdModified;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

}
