package org.kuali.hr.time.salgroup;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class SalGroup extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkSalGroupId;
	private String tkSalGroup;
	private Date effectiveDate;
	private Timestamp timestamp;
	private boolean active;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
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

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public Long getTkSalGroupId() {
		return tkSalGroupId;
	}

	public void setTkSalGroupId(Long tkSalGroupId) {
		this.tkSalGroupId = tkSalGroupId;
	}

	public String getTkSalGroup() {
		return tkSalGroup;
	}

	public void setTkSalGroup(String tkSalGroup) {
		this.tkSalGroup = tkSalGroup;
	}

}
