package org.kuali.hr.time.dept.earncode;

import org.kuali.hr.job.Job;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.salgroup.SalGroup;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class DepartmentEarnCode extends HrBusinessObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long hrDeptEarnCodeId;
	private String dept;
	private String hrSalGroup;
	private String earnCode;
	private boolean employee;
	private boolean approver;
	private String location;
	
	private Long hrDeptId;
	private Long hrSalGroupId;
	private Long hrEarnCodeId;
	private Long hrLocationId;
	
	private SalGroup  salGroupObj;
	private Department departmentObj;
	private EarnCode earnCodeObj;
    private Job jobObj;
    private Location locationObj;
    private String history;


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


	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}


	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
	public Long getHrDeptEarnCodeId() {
		return hrDeptEarnCodeId;
	}
	public void setHrDeptEarnCodeId(Long hrDeptEarnCodeId) {
		this.hrDeptEarnCodeId = hrDeptEarnCodeId;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getHrSalGroup() {
		return hrSalGroup;
	}
	public void setHrSalGroup(String hrSalGroup) {
		this.hrSalGroup = hrSalGroup;
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
		public Job getJobObj() {
		return jobObj;
	}
	public void setJobObj(Job jobObj) {
		this.jobObj = jobObj;
	}
	
	public Location getLocationObj() {
		return locationObj;
	}
	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getHrDeptId() {
		return hrDeptId;
	}
	public void setHrDeptId(Long hrDeptId) {
		this.hrDeptId = hrDeptId;
	}
	public Long getHrSalGroupId() {
		return hrSalGroupId;
	}
	public void setHrSalGroupId(Long hrSalGroupId) {
		this.hrSalGroupId = hrSalGroupId;
	}
	public Long getHrEarnCodeId() {
		return hrEarnCodeId;
	}
	public void setHrEarnCodeId(Long hrEarnCodeId) {
		this.hrEarnCodeId = hrEarnCodeId;
	}
	public Long getHrLocationId() {
		return hrLocationId;
	}
	public void setHrLocationId(Long hrLocationId) {
		this.hrLocationId = hrLocationId;
	}
	@Override
	protected String getUniqueKey() {
		return dept + "_" + hrSalGroup + "_" + earnCode;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	@Override
	public Long getId() {
		return getHrDeptEarnCodeId();
	}
	@Override
	public void setId(Long id) {
		setHrDeptEarnCodeId(id);
	}


}
