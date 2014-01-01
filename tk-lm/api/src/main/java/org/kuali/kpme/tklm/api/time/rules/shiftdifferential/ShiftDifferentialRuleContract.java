/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.api.time.rules.shiftdifferential;

import java.math.BigDecimal;
import java.sql.Time;

import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.paygrade.PayGradeContract;
import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.kuali.kpme.tklm.api.time.rules.TkRuleContract;

/**
 * <p>ShiftDifferentialRuleContract interface</p>
 *
 */
public interface ShiftDifferentialRuleContract extends TkRuleContract {
	
	/**
	 * The primary key of a ShiftDifferentialRule entry saved in a database
	 * 
	 * <p>
	 * tkShiftDiffRuleId of a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return tkShiftDiffRuleId for ShiftDifferentialRule
	 */
	public String getTkShiftDiffRuleId();

	/**
	 * The location associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * If a location is defined, only entries associated with a job in this location will be subject to the shift rule.
	 * <p>
	 * 
	 * @return location for ShiftDifferentialRule
	 */
	public String getLocation();

	/**
	 * The pay grade associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * If a pay grade is defined, only entries associated with a job in this pay grade will be subject to the shift rule.
	 * <p>
	 * 
	 * @return payGrade for ShiftDifferentialRule
	 */
	public String getPayGrade();
	
	/**
	 * The earn code associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * The earn code that will be applied to the eligible shift and result in additional earnings.
	 * <p>
	 * 
	 * @return earnCode for ShiftDifferentialRule
	 */
	public String getEarnCode();

	/**
	 * The minimum hours associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * The minimum number of hours a shift must be in order to qualify for shift differential. 
	 * <p>
	 * 
	 * @return minHours for ShiftDifferentialRule
	 */
	public BigDecimal getMinHours();

	/**
	 * The max gap associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * The maximum number of minutes which can separate time blocks, and still qualify as an eligible shift. 
	 * <p>
	 * 
	 * @return maxGap for ShiftDifferentialRule
	 */
	public BigDecimal getMaxGap();
  
	/**
	 * The user that sets up the ShiftDifferentialRule
	 * 
	 * <p>
	 * userPrincipalId of a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return userPrincipalId for ShiftDifferentialRule
	 */
	public String getUserPrincipalId();
	
	/**
	 * The salary group associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * If a salary group is defined, only entries associated with a job in this salary group will be subject to the shift rule.
	 * <p>
	 * 
	 * @return hrSalGroup for ShiftDifferentialRule
	 */
	public String getHrSalGroup();
	
	/**
	 * Pay Calendar record that defines the FLSA period
	 * 
	 * <p>
	 * pyCalendarGroup of a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return pyCalendarGroup for ShiftDifferentialRule
	 */
	public String getPyCalendarGroup();

	/**
	 * The beginning time of the eligible shift
	 * 
	 * <p>
	 * beginTime of a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return beginTime for ShiftDifferentialRule
	 */
	public Time getBeginTime();

	/**
	 * The end time of the eligible shift. 
	 * 
	 * <p>
	 * This could be on the following day if the eligible shift is overnight.
	 * <p>
	 * 
	 * @return endTime for ShiftDifferentialRule
	 */
	public Time getEndTime();

	/**
	 * The earn group associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * The earn group defined (using the Earn Group maint doc) to represent the earn codes to be converted to Shift Differential Earn Code.
	 * <p>
	 * 
	 * @return fromEarnGroup for ShiftDifferentialRule
	 */
	public String getFromEarnGroup();
	
	/**
	 * The flag that indicates if Sunday is eligible for shift differential
	 * 
	 * <p>
	 * sunday flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if Sunday is eligible, N if not
	 */
	public boolean isSunday();

	/**
	 * The flag that indicates if Monday is eligible for shift differential.
	 * 
	 * <p>
	 * monday flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if Monday is eligible, N if not
	 */
	public boolean isMonday();

	/**
	 * The flag that indicates if Tuesday is eligible for shift differential.
	 * 
	 * <p>
	 * tuesday flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if Tuesday is eligible, N if not
	 */
	public boolean isTuesday();

	/**
	 * The flag that indicates if Wednesday is eligible for shift differential.
	 * 
	 * <p>
	 * wednesday flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if Wednesday is eligible, N if not
	 */
	public boolean isWednesday();
	
	/**
	 * The flag that indicates if Thursday is eligible for shift differential.
	 * 
	 * <p>
	 * thursday flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if Thursday is eligible, N if not
	 */
	public boolean isThursday();

	/**
	 * The flag that indicates if Friday is eligible for shift differential.
	 * 
	 * <p>
	 * friday flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if Friday is eligible, N if not
	 */
	public boolean isFriday();

	/**
	 * The flag that indicates if Saturday is eligible for shift differential.
	 * 
	 * <p>
	 * saturday flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if Saturday is eligible, N if not
	 */
	public boolean isSaturday();

	/**
	 * The EarnCode object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * The earn code that will be applied to the eligible shift and result in additional earnings.
	 * <p>
	 * 
	 * @return earnCodeObj for ShiftDifferentialRule
	 */
	public EarnCodeContract getEarnCodeObj();

	/**
	 * The SalaryGroup object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * If a salary group is defined, only entries associated with a job in this salary group will be subject to the shift rule.
	 * <p>
	 * 
	 * @return salaryGroupObj for ShiftDifferentialRule
	 */
	public SalaryGroupContract getSalaryGroupObj();

	/**
	 * The EarnCodeGroup object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * The earn group defined (using the Earn Group maint doc) to represent the earn codes to be converted to Shift Differential Earn Code.
	 * <p>
	 * 
	 * @return fromEarnGroupObj for ShiftDifferentialRule
	 */
    public EarnCodeGroupContract getFromEarnGroupObj();
   
    /**
	 * The Calendar object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * payCalendar for a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return payCalendar for ShiftDifferentialRule
	 */
    public CalendarContract getPayCalendar();
    
    /**
	 * The Location object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * If a location is defined, only entries associated with a job in this location will be subject to the shift rule. 
	 * <p>
	 * 
	 * @return locationObj for a ShiftDifferentialRule
	 */
	public LocationContract getLocationObj();

	/**
	 * The PayGrade object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * If a pay grade is defined, only entries associated with a job in this pay grade will be subject to the shift rule.
	 * <p>
	 * 
	 * @return payGradeObj for a ShiftDifferentialRule
	 */
	public PayGradeContract getPayGradeObj();
	
	/**
	 * The id of the SalaryGroup object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * hrSalGroupId for a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return hrSalGroupId for ShiftDifferentialRule
	 */
	public String getHrSalGroupId();

	/**
	 * The id of the Location object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * hrLocationId for a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return hrLocationId for ShiftDifferentialRule
	 */
	public String getHrLocationId();

	/**
	 * The id of the PayGrade object associated with the ShiftDifferentialRule
	 * 
	 * <p>
	 * hrPayGradeId for a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return hrPayGradeId for ShiftDifferentialRule
	 */
	public String getHrPayGradeId();

	/**
	 * The history flag of the ShiftDifferentialRule
	 * 
	 * <p>
	 * history flag of a ShiftDifferentialRule
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public boolean isHistory();
}
