package org.kuali.hr.time.overtime.daily.rule;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.rule.TkRuleContext;


public class DailyOvertimeRule extends TkRule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkDailyOvertimeRuleId;
	private String location;
	private String hrPaytype;
	private String deptId;
	private Long workAreaId;
	private Long taskId;
	private BigDecimal maxGap;
	private BigDecimal shiftHours;
	private String overtimePref;
	private Date effectiveDate;
	private String userPrincipalId;
	private Timestamp timeStamp;
	private boolean active;
	
	@Override
	public boolean isValid(TkRuleContext tkRuleContext) {
		return false;
	}

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

	public String getHrPaytype() {
		return hrPaytype;
	}

	public void setHrPaytype(String hrPaytype) {
		this.hrPaytype = hrPaytype;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Long getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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

}
