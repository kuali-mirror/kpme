package org.kuali.hr.time.shiftdiff.rule;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.rule.TkRuleContext;

public class ShiftDifferentialRule extends TkRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long tkShiftDiffRuleId;
	private String location;
	private String salGroup;
	private String payGrade;
	private String effectiveDate;
	private String earnCode;
	private Timestamp beginTimestamp;
	private Timestamp endTimestamp;
	private BigDecimal minHours;
	private boolean sunday;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private BigDecimal maxGap;
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

	public String getSalGroup() {
		return salGroup;
	}

	public void setSalGroup(String salGroup) {
		this.salGroup = salGroup;
	}

	public String getPayGrade() {
		return payGrade;
	}

	public void setPayGrade(String payGrade) {
		this.payGrade = payGrade;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public Timestamp getBeginTimestamp() {
		return beginTimestamp;
	}

	public void setBeginTimestamp(Timestamp beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	public Timestamp getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Timestamp endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public BigDecimal getMinHours() {
		return minHours;
	}

	public void setMinHours(BigDecimal minHours) {
		this.minHours = minHours;
	}

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
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

}
