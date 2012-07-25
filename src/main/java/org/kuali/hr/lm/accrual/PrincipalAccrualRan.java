package org.kuali.hr.lm.accrual;

import java.sql.Timestamp;


public class PrincipalAccrualRan extends Object {

	private static final long serialVersionUID = 1L;
	private String principalId;
	private Timestamp lastRanTs;

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Timestamp getLastRanTs() {
		return lastRanTs;
	}

	public void setLastRanTs(Timestamp lastRanTs) {
		this.lastRanTs = lastRanTs;
	}


}
