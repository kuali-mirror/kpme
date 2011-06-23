package org.kuali.hr.time.position;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;

public class Position extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long hrPositionId;
	private Long positionNumber;
	private String description;

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

	@Override
	protected String getUniqueKey() {
		return positionNumber + "";
	}

}
