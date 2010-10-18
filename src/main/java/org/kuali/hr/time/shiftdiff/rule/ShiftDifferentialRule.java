package org.kuali.hr.time.shiftdiff.rule;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.rule.TkRule;

public class ShiftDifferentialRule extends TkRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkShiftDiffRuleId;
	private String location;
	private String tkSalGroup;
	private String payGrade;
	private Date effectiveDate;
	private String earnCode;
	private String beginTime;
	private String endTime;
	private BigDecimal minHours;
	private boolean day0;
	private boolean day1;
	private boolean day2;
	private boolean day3;
	private boolean day4;
	private boolean day5;
	private boolean day6;
	private String calendarGroup;
	private BigDecimal maxGap;
	private String userPrincipalId;
	private Timestamp timeStamp;
	private boolean active;
	
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public Long getTkShiftDiffRuleId() {
		return tkShiftDiffRuleId;
	}

	public void setTkShiftDiffRuleId(Long tkShiftDiffRuleId) {
		this.tkShiftDiffRuleId = tkShiftDiffRuleId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	 
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getMinHours() {
		return minHours;
	}

	public void setMinHours(BigDecimal minHours) {
		this.minHours = minHours;
	}

	public BigDecimal getMaxGap() {
		return maxGap;
	}

	public void setMaxGap(BigDecimal maxGap) {
		this.maxGap = maxGap;
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

	public String getTkSalGroup() {
		return tkSalGroup;
	}

	public void setTkSalGroup(String tkSalGroup) {
		this.tkSalGroup = tkSalGroup;
	}

	public boolean isDay0() {
		return day0;
	}

	public void setDay0(boolean day0) {
		this.day0 = day0;
	}

	public boolean isDay1() {
		return day1;
	}

	public void setDay1(boolean day1) {
		this.day1 = day1;
	}

	public boolean isDay2() {
		return day2;
	}

	public void setDay2(boolean day2) {
		this.day2 = day2;
	}

	public boolean isDay3() {
		return day3;
	}

	public void setDay3(boolean day3) {
		this.day3 = day3;
	}

	public boolean isDay4() {
		return day4;
	}

	public void setDay4(boolean day4) {
		this.day4 = day4;
	}

	public boolean isDay5() {
		return day5;
	}

	public void setDay5(boolean day5) {
		this.day5 = day5;
	}

	public boolean isDay6() {
		return day6;
	}

	public void setDay6(boolean day6) {
		this.day6 = day6;
	}

	public String getCalendarGroup() {
		return calendarGroup;
	}

	public void setCalendarGroup(String calendarGroup) {
		this.calendarGroup = calendarGroup;
	}

}
