package org.kuali.hr.time.collection.rule;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;

import java.util.LinkedHashMap;

public class TimeCollectionRule extends HrBusinessObject implements DepartmentalRule {

	private static final long serialVersionUID = 1L;

	private String tkTimeCollectionRuleId;
	private String dept;
	private Long workArea;
	private boolean clockUserFl;
	private boolean hrsDistributionF;
	private String userPrincipalId;

	private String tkWorkAreaId;
	private String hrDeptId;

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

	public String getTkTimeCollectionRuleId() {
		return tkTimeCollectionRuleId;
	}

	public void setTkTimeCollectionRuleId(String tkTimeCollectionRuleId) {
		this.tkTimeCollectionRuleId = tkTimeCollectionRuleId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(String tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	@Override
	protected String getUniqueKey() {
		String timeCollKey = getDept()+"_"+isClockUserFl()+"_"+isHrsDistributionF()+"_"+
		(getWorkArea() !=null ? getWorkArea().toString() : "");

		return timeCollKey;
	}

	@Override
	public String getId() {
		return getTkTimeCollectionRuleId();
	}

	@Override
	public void setId(String id) {
		setTkTimeCollectionRuleId(id);
	}


}
