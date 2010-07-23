package org.kuali.hr.time.workschedule;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class WorkSchedule extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleId;
	private String workScheduleDesc;
	private Date effectiveDate;
	private String deptId;
	private Long workAreaId;
	private String principalId;
	private boolean active;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}


	public Long getHrWorkScheduleId() {
		return hrWorkScheduleId;
	}


	public void setHrWorkScheduleId(Long hrWorkScheduleId) {
		this.hrWorkScheduleId = hrWorkScheduleId;
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


	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public Long getWorkAreaId() {
		return workAreaId;
	}


	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}


	public String getPrincipalId() {
		return principalId;
	}


	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

}
