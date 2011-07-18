package org.kuali.hr.time.collection.rule;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.workarea.WorkArea;

public class TimeCollectionRule extends HrBusinessObject implements DepartmentalRule {

	private static final long serialVersionUID = 1L;

	private Long tkTimeCollectionRuleId;
	private String dept;
	private Long workArea;
	private boolean clockUserFl;
	private boolean hrsDistributionF;
	private String userPrincipalId;

	private Long tkWorkAreaId;
	private Long tkDeptId;

	private Department departmentObj;
	private WorkArea workAreaObj;

	public Department getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isClockUserFl() {
		return clockUserFl;
	}

	public void setClockUserFl(boolean clockUserFl) {
		this.clockUserFl = clockUserFl;
	}

	public boolean isHrsDistributionF() {
		return hrsDistributionF;
	}

	public void setHrsDistributionF(boolean hrsDistributionF) {
		this.hrsDistributionF = hrsDistributionF;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}


	@Override
	protected LinkedHashMap<String,Object> toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();

		toStringMap.put("tkTimeCollectionRuleId", tkTimeCollectionRuleId);
		toStringMap.put("department", departmentObj);
		toStringMap.put("dept", dept);
		toStringMap.put("workAreaObj", workAreaObj);
		toStringMap.put("workArea", workArea);
		toStringMap.put("effDate", effectiveDate);
		toStringMap.put("clockUserFl", clockUserFl);
		toStringMap.put("hrsDistributionF", hrsDistributionF);
		toStringMap.put("userPrincipalId", userPrincipalId);
		toStringMap.put("timestamp", timestamp);

		return toStringMap;
	}

	public Long getTkTimeCollectionRuleId() {
		return tkTimeCollectionRuleId;
	}

	public void setTkTimeCollectionRuleId(Long tkTimeCollectionRuleId) {
		this.tkTimeCollectionRuleId = tkTimeCollectionRuleId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public Long getTkDeptId() {
		return tkDeptId;
	}

	public void setTkDeptId(Long tkDeptId) {
		this.tkDeptId = tkDeptId;
	}

	@Override
	protected String getUniqueKey() {
		String timeCollKey = getDept()+"_"+isClockUserFl()+"_"+isHrsDistributionF()+"_"+
		(getWorkArea() !=null ? getWorkArea().toString() : "");

		return timeCollKey;
	}


}
