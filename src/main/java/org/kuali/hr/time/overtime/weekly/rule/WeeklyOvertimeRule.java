package org.kuali.hr.time.overtime.weekly.rule;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.rule.TkRuleContext;

public class WeeklyOvertimeRule extends TkRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkWeeklyOvertimeRuleId;
	private String maxHoursEarnGroup;
	private String convertFromEarnGroup;
	private String convertToEarnGroup;
	private Date effectiveDate;
	private BigDecimal step;
	private BigDecimal maxHours;
	private String userPrincipalId;
	private Timestamp timeStamp;
	private boolean active;
	
	
	@Override
	public boolean isValid(TkRuleContext tkRuleContext) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getTkWeeklyOvertimeRuleId() {
		return tkWeeklyOvertimeRuleId;
	}

	public void setTkWeeklyOvertimeRuleId(Long tkWeeklyOvertimeRuleId) {
		this.tkWeeklyOvertimeRuleId = tkWeeklyOvertimeRuleId;
	}

	public String getMaxHoursEarnGroup() {
		return maxHoursEarnGroup;
	}

	public void setMaxHoursEarnGroup(String maxHoursEarnGroup) {
		this.maxHoursEarnGroup = maxHoursEarnGroup;
	}

	public String getConvertFromEarnGroup() {
		return convertFromEarnGroup;
	}

	public void setConvertFromEarnGroup(String convertFromEarnGroup) {
		this.convertFromEarnGroup = convertFromEarnGroup;
	}

	public String getConvertToEarnGroup() {
		return convertToEarnGroup;
	}

	public void setConvertToEarnGroup(String convertToEarnGroup) {
		this.convertToEarnGroup = convertToEarnGroup;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getStep() {
		return step;
	}

	public void setStep(BigDecimal step) {
		this.step = step;
	}

	public BigDecimal getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(BigDecimal maxHours) {
		this.maxHours = maxHours;
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
