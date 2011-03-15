package org.kuali.hr.time.workschedule;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class WorkSchedule extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleId;
	
	private String workScheduleDesc;
	private Date effectiveDate;
	private Timestamp timestamp;
	private boolean active;
	private String userPrincipalId;
	
	public String getUserPrincipalId() {
		return userPrincipalId;
	}


	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}


	private List<WorkScheduleEntry> workScheduleEntries = new  LinkedList<WorkScheduleEntry>();
	 
	

	public List<WorkScheduleEntry> getWorkScheduleEntries() {
		return workScheduleEntries;
	}


	public void setWorkScheduleEntries(List<WorkScheduleEntry> workScheduleEntries) {
		this.workScheduleEntries = workScheduleEntries;
	}

	public String getWorkScheduleDesc() {
		return workScheduleDesc;
	}

	public void setWorkScheduleDesc(String workScheduleDesc) {
		this.workScheduleDesc = workScheduleDesc;
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
	

	
	@SuppressWarnings("rawtypes")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		
		toStringMap.put("workScheduleId", hrWorkScheduleId);
		toStringMap.put("workScheduleDesc", workScheduleDesc);
		toStringMap.put("effectiveDate", effectiveDate);
		toStringMap.put("active", active); 	
		 
		return toStringMap;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public Long getHrWorkScheduleId() {
		return hrWorkScheduleId;
	}


	public void setHrWorkScheduleId(Long hrWorkScheduleId) {
		this.hrWorkScheduleId = hrWorkScheduleId;
	}
}
