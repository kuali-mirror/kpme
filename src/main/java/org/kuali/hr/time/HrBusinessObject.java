package org.kuali.hr.time;

import java.sql.Date;
import java.sql.Timestamp;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public abstract class HrBusinessObject extends PersistableBusinessObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected abstract String getUniqueKey();
	protected Date effectiveDate;
	protected boolean active;
	protected Timestamp timestamp;
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

}
