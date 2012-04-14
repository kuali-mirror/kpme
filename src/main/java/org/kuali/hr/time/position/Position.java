package org.kuali.hr.time.position;

import java.sql.Timestamp;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.workarea.WorkArea;

public class Position extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hrPositionId;
	private String positionNumber;
	private String description;
	private String history;
	private Long workArea;
	private WorkArea workAreaObj;

	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
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
	public String getUniqueKey() {
		return positionNumber + "";
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@Override
	public String getId() {
		return getHrPositionId();
	}

	@Override
	public void setId(String id) {
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
