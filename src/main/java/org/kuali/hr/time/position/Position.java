package org.kuali.hr.time.position;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.workarea.WorkArea;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class Position extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long hrPositionId;
	private String positionNumber;
	private String description;
	private String history;
	private Long workArea;
	private WorkArea workAreaObj;

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

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(String positionNumber) {
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

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Override
	public Long getId() {
		return getHrPositionId();
	}

	@Override
	public void setId(Long id) {
		setHrPositionId(id);
	}

	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}


}
