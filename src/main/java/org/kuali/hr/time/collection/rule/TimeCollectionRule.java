package org.kuali.hr.time.collection.rule;

import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class TimeCollectionRule extends PersistableBusinessObjectBase implements DepartmentalRule {

	private static final long serialVersionUID = 1L;

	private Long tkTimeCollectionRuleId;
	private String dept;
	private Long workArea;
	private Date effDate;
	private boolean clockUserFl;
	private boolean hrsDistributionF;
	private String userPrincipalId;
	private Timestamp timeStamp;
	private boolean active;


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

	public Date getEffDate() {
		return effDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
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

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}


	@Override
	protected LinkedHashMap<String,Object> toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String,Object>();

		toStringMap.put("tkTimeCollectionRuleId", tkTimeCollectionRuleId);
		toStringMap.put("department", departmentObj);
		toStringMap.put("dept", dept);
		toStringMap.put("workAreaObj", workAreaObj);
		toStringMap.put("workArea", workArea);
		toStringMap.put("effDate", effDate);
		toStringMap.put("clockUserFl", clockUserFl);
		toStringMap.put("hrsDistributionF", hrsDistributionF);
		toStringMap.put("userPrincipalId", userPrincipalId);
		toStringMap.put("timeStamp", timeStamp);

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


}
