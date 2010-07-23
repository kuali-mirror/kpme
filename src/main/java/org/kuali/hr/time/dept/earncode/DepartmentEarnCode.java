package org.kuali.hr.time.dept.earncode;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class DepartmentEarnCode extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long deptEarnCodeId;
	private String deptId;
	private String tkSalGroup;
	private String earnCode;
	private boolean employee;
	private boolean approver;
	private boolean org_admin;
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}


	public Long getDeptEarnCodeId() {
		return deptEarnCodeId;
	}


	public void setDeptEarnCodeId(Long deptEarnCodeId) {
		this.deptEarnCodeId = deptEarnCodeId;
	}


	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getTkSalGroup() {
		return tkSalGroup;
	}


	public void setTkSalGroup(String tkSalGroup) {
		this.tkSalGroup = tkSalGroup;
	}


	public String getEarnCode() {
		return earnCode;
	}


	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}


	public boolean isEmployee() {
		return employee;
	}


	public void setEmployee(boolean employee) {
		this.employee = employee;
	}


	public boolean isApprover() {
		return approver;
	}


	public void setApprover(boolean approver) {
		this.approver = approver;
	}


	public boolean isOrg_admin() {
		return org_admin;
	}


	public void setOrg_admin(boolean orgAdmin) {
		org_admin = orgAdmin;
	}

}
