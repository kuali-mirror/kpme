/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.rules.shiftdifferential;

import java.math.BigDecimal;
import java.sql.Time;

import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.bo.location.Location;
import org.kuali.kpme.core.bo.paygrade.PayGrade;
import org.kuali.kpme.core.bo.salarygroup.SalaryGroup;
import org.kuali.kpme.tklm.time.rules.TkRule;

import com.google.common.collect.ImmutableList;

public class ShiftDifferentialRule extends TkRule {

	private static final long serialVersionUID = -3990672795815968915L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "ShiftDifferentialRule";
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("location")
            .build();
	
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
	private SalaryGroup salaryGroupObj;
    private EarnCodeGroup fromEarnGroupObj;
    private Calendar payCalendar;
    private Location locationObj;
    private PayGrade payGradeObj;
    
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

	public SalaryGroup getSalaryGroupObj() {
		return salaryGroupObj;
	}

	public void setSalaryGroupObj(SalaryGroup salaryGroupObj) {
		this.salaryGroupObj = salaryGroupObj;
	}

    public EarnCodeGroup getFromEarnGroupObj() {
        return fromEarnGroupObj;
    }

    public void setFromEarnGroupObj(EarnCodeGroup fromEarnGroupObj) {
        this.fromEarnGroupObj = fromEarnGroupObj;
    }

    public Calendar getPayCalendar() {
        return payCalendar;
    }

    public void setPayCalendar(Calendar payCalendar) {
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
		return location + "_" + hrSalGroup + "_" + payGrade + "_" + earnCode;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((earnCode == null) ? 0 : earnCode.hashCode());
		result = prime * result
				+ ((hrSalGroup == null) ? 0 : hrSalGroup.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((payGrade == null) ? 0 : payGrade.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiftDifferentialRule other = (ShiftDifferentialRule) obj;
		if (earnCode == null) {
			if (other.earnCode != null)
				return false;
		} else if (!earnCode.equals(other.earnCode))
			return false;
		if (hrSalGroup == null) {
			if (other.hrSalGroup != null)
				return false;
		} else if (!hrSalGroup.equals(other.hrSalGroup))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (payGrade == null) {
			if (other.payGrade != null)
				return false;
		} else if (!payGrade.equals(other.payGrade))
			return false;
		return true;
	}
	
	

}
