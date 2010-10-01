package org.kuali.hr.time.dept.earncode;

import java.sql.Date;
import java.sql.Timestamp;
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
	private Long tkDeptEarnCodeId;
	private String dept;
	private String tkSalGroup;
	private String earnCode;
	private boolean employee;
	private boolean approver;
	private boolean org_admin;
	private Date effectiveDate;
	private Timestamp timestamp;
	private Boolean active;
	
	
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
	public Long getTkDeptEarnCodeId() {
		return tkDeptEarnCodeId;
	}
	public void setTkDeptEarnCodeId(Long tkDeptEarnCodeId) {
		this.tkDeptEarnCodeId = tkDeptEarnCodeId;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

}
