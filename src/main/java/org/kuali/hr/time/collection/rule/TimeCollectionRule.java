package org.kuali.hr.time.collection.rule;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.authorization.DepartmentalRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.workarea.WorkArea;

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
	
	private Boolean history;
	
	// chen, 11/07/11, KPME-1152
	private String payType; 
	private String hrPayTypeId; 
	private PayType payTypeObj;
	// KPME-1152

	
	public Department getDepartmentObj() {
		return departmentObj;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getHrPayTypeId() {
		return hrPayTypeId;
	}

	public void setHrPayTypeId(String hrPayTypeId) {
		this.hrPayTypeId = hrPayTypeId;
	}

	public PayType getPayTypeObj() {
		return payTypeObj;
	}

	public void setPayTypeObj(PayType payTypeObj) {
		this.payTypeObj = payTypeObj;
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
	public String getUniqueKey() {
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

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}


}
