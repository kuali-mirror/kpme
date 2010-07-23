package org.kuali.hr.time.salgroup;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TkSalGroup extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long salGroupId;
	private String salGroup;
	private Date effectiveDate;
	private Timestamp timestamp;
	private boolean active;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public Long getSalGroupId() {
		return salGroupId;
	}


	public void setSalGroupId(Long salGroupId) {
		this.salGroupId = salGroupId;
	}


	public String getSalGroup() {
		return salGroup;
	}


	public void setSalGroup(String salGroup) {
		this.salGroup = salGroup;
	}


	public Date getEffectiveDate() {
		return effectiveDate;
	}


	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

}
