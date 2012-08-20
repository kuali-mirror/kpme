package org.kuali.hr.time.workschedule;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;



public class WorkScheduleAssignment extends PersistableBusinessObjectBase {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long hrWorkScheduleAssignmentId;
	private Long hrWorkSchedule;
	private String dept;
	private Long workArea;
	private String principalId;
	private Date effectiveDate;
	private Boolean active;
	private Timestamp timestamp;
	private String userPrincipalId;

	public Long getHrWorkScheduleAssignmentId() {
		return hrWorkScheduleAssignmentId;
	}


	public void setHrWorkScheduleAssignmentId(Long hrWorkScheduleAssignmentId) {
		this.hrWorkScheduleAssignmentId = hrWorkScheduleAssignmentId;
	}


	public Long getHrWorkSchedule() {
		return hrWorkSchedule;
	}


	public void setHrWorkSchedule(Long hrWorkSchedule) {
		this.hrWorkSchedule = hrWorkSchedule;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public Long getWorkArea() {
		return workArea;
	}


	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}


	public String getPrincipalId() {
		return principalId;
	}


	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public String getUserPrincipalId() {
		return userPrincipalId;
	}


	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}


	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public Date getEffectiveDate() {
		return effectiveDate;
	}

}
