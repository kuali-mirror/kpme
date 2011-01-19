package org.kuali.hr.time.roles;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TkRole extends PersistableBusinessObjectBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkRolesId;
	private String principalId;
	private String roleName;
	private String userPrincipalId;
	private Long workArea;
	private String department;
	private Date effectiveDate;
	private Timestamp timestamp;
	private boolean active;
	private Long tkDeptId;
	private Long tkWorkAreaId;
	
	private Person person;
	
	public Long getTkRolesId() {
		return tkRolesId;
	}
	public void setTkRolesId(Long tkRolesId) {
		this.tkRolesId = tkRolesId;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserPrincipalId() {
		return userPrincipalId;
	}
	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}
	public Long getWorkArea() {
		return workArea;
	}
	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Long getTkDeptId() {
		return tkDeptId;
	}
	public void setTkDeptId(Long tkDeptId) {
		this.tkDeptId = tkDeptId;
	}
	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}
	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}
}
