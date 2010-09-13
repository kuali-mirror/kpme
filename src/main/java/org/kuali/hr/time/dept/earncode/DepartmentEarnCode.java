package org.kuali.hr.time.dept.earncode;

import java.util.LinkedHashMap;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class DepartmentEarnCode extends PersistableBusinessObjectBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long deptEarnCodeId;
	private String deptId;
	private Long tkSalGroupId;
	private Long earnCodeId;
	private boolean employee;
	private boolean approver;
	private boolean org_admin;
	private SalGroup  salGroupObj;
	private Department departmentObj;
	private EarnCode earnCodeObj;
	

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}
	public SalGroup getSalGroupObj() {
		return salGroupObj;
	}


	public void setSalGroupObj(SalGroup salGroupObj) {
		this.salGroupObj = salGroupObj;
	}


	public Department getDepartmentObj() {
		return departmentObj;
	}


	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
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


	public Long getTkSalGroupId() {
		return tkSalGroupId;
	}


	public void setTkSalGroupId(Long tkSalGroupId) {
		this.tkSalGroupId = tkSalGroupId;
	}


	public Long getEarnCodeId() {
		return earnCodeId;
	}


	public void setEarnCodeId(Long earnCodeId) {
		this.earnCodeId = earnCodeId;
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


	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}


	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

}
