package org.kuali.hr.time.overtime.daily.rule;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;


public class DailyOvertimeRule extends TkRule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkDailyOvertimeRuleId;
	private String location;
	private String paytype;
	private String dept;
	private Long workArea;
	private Long task;
	private BigDecimal maxGap;
	private BigDecimal shiftHours;
	private String overtimePref;
	private Date effectiveDate;
	private String userPrincipalId;
	private Timestamp timeStamp;
	private boolean active;	
	
	private Task taskObj;
	private WorkArea workAreaObj;
	private Department departmentObj;
	private PayType payTypeObj;
	
//	@Override
//	public boolean isValid(TkRuleContext tkRuleContext) {
//		return false;
//	}

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public Long getTkDailyOvertimeRuleId() {
		return tkDailyOvertimeRuleId;
	}

	public void setTkDailyOvertimeRuleId(Long tkDailyOvertimeRuleId) {
		this.tkDailyOvertimeRuleId = tkDailyOvertimeRuleId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getMaxGap() {
		return maxGap;
	}

	public void setMaxGap(BigDecimal maxGap) {
		this.maxGap = maxGap;
	}

	public BigDecimal getShiftHours() {
		return shiftHours;
	}

	public void setShiftHours(BigDecimal shiftHours) {
		this.shiftHours = shiftHours;
	}

	public String getOvertimePref() {
		return overtimePref;
	}

	public void setOvertimePref(String overtimePref) {
		this.overtimePref = overtimePref;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Department getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Task getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(Task taskObj) {
		this.taskObj = taskObj;
	}

	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public void setTask(Long task) {
		this.task = task;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public Long getTask() {
		return task;
	}

	public PayType getPayTypeObj() {
		return payTypeObj;
	}

	public void setPayTypeObj(PayType payTypeObj) {
		this.payTypeObj = payTypeObj;
	}

}
