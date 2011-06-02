package org.kuali.hr.time.position;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class Position extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long hrPositionId;
	private Long positionNumber;
	private Date effectiveDate;
	private String description;
	private boolean active;
	private Timestamp timestamp;

	@SuppressWarnings("rawtypes")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(Long hrPositionId) {
		this.hrPositionId = hrPositionId;
	}

	public Long getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(Long positionNumber) {
		this.positionNumber = positionNumber;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
