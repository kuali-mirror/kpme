package org.kuali.hr.time.salgroup;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class SalGroup extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long salGroupId;
	private String salGroup;
	private Date effectiveDate;
	private Time timestamp;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setTimestamp(Time timestamp) {
		this.timestamp = timestamp;
	}

	public Time getTimestamp() {
		return timestamp;
	}

}
