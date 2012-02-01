package org.kuali.hr.time.shiftdiff.rule;

import org.kuali.hr.location.Location;
import org.kuali.hr.paygrade.PayGrade;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.rule.TkRule;
import org.kuali.hr.time.salgroup.SalGroup;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;

public class ShiftDifferentialRule extends TkRule {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tkShiftDiffRuleId;
	private String location;
	private String hrSalGroup;
	private String payGrade;
	private String earnCode;
	private Time beginTime;
	private Time endTime;
	private BigDecimal minHours;
	private boolean sunday;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private String fromEarnGroup;
	private String pyCalendarGroup;
	private BigDecimal maxGap; // Gap is in HOURS
	private String userPrincipalId;

	private String hrSalGroupId;
	private String hrLocationId;
	private String hrPayGradeId;	
	
	private boolean history;
	
	private EarnCode earnCodeObj;
	private SalGroup salGroupObj;
    private EarnGroup fromEarnGroupObj;
    private PayCalendar payCalendar;
    private Location locationObj;
    private PayGrade payGradeObj;

	@SuppressWarnings("rawtypes")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public String getTkShiftDiffRuleId() {
		return tkShiftDiffRuleId;
	}

	public void setTkShiftDiffRuleId(String tkShiftDiffRuleId) {
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

	public BigDecimal getMinHours() {
		return minHours;
	}

	public void setMinHours(BigDecimal minHours) {
		this.minHours = minHours;
	}

    /**
     * @return The maximum gap, in hours.
     */
	public BigDecimal getMaxGap() {
		return maxGap;
	}

    /**
     *
     * @param maxGap The number of hours that can be between one time block and another for the rule to consider it part of the same shift.
     */
	public void setMaxGap(BigDecimal maxGap) {
		this.maxGap = maxGap;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getHrSalGroup() {
		return hrSalGroup;
	}

	public void setHrSalGroup(String hrSalGroup) {
		this.hrSalGroup = hrSalGroup;
	}

	public String getPyCalendarGroup() {
		return pyCalendarGroup;
	}

	public void setPyCalendarGroup(String pyCalendarGroup) {
		this.pyCalendarGroup = pyCalendarGroup;
	}

	public Time getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getFromEarnGroup() {
		return fromEarnGroup;
	}

	public void setFromEarnGroup(String fromEarnGroup) {
		this.fromEarnGroup = fromEarnGroup;
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

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	public SalGroup getSalGroupObj() {
		return salGroupObj;
	}

	public void setSalGroupObj(SalGroup salGroupObj) {
		this.salGroupObj = salGroupObj;
	}

    public EarnGroup getFromEarnGroupObj() {
        return fromEarnGroupObj;
    }

    public void setFromEarnGroupObj(EarnGroup fromEarnGroupObj) {
        this.fromEarnGroupObj = fromEarnGroupObj;
    }

    public PayCalendar getPayCalendar() {
        return payCalendar;
    }

    public void setPayCalendar(PayCalendar payCalendar) {
        this.payCalendar = payCalendar;
    }

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	public PayGrade getPayGradeObj() {
		return payGradeObj;
	}

	public void setPayGradeObj(PayGrade payGradeObj) {
		this.payGradeObj = payGradeObj;
	}

	public String getHrSalGroupId() {
		return hrSalGroupId;
	}

	public void setHrSalGroupId(String hrSalGroupId) {
		this.hrSalGroupId = hrSalGroupId;
	}

	public String getHrLocationId() {
		return hrLocationId;
	}

	public void setHrLocationId(String hrLocationId) {
		this.hrLocationId = hrLocationId;
	}

	public String getHrPayGradeId() {
		return hrPayGradeId;
	}

	public void setHrPayGradeId(String hrPayGradeId) {
		this.hrPayGradeId = hrPayGradeId;
	}

	@Override
	public String getUniqueKey() {
		return location + "_" + hrSalGroup + "_" + payGrade;
	}

	@Override
	public String getId() {
		return getTkShiftDiffRuleId();
	}

	@Override
	public void setId(String id) {
		setTkShiftDiffRuleId(id);
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

}
