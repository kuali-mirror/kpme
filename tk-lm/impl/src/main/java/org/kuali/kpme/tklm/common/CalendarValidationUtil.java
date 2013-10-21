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
package org.kuali.kpme.tklm.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.util.ValidationUtils;

public class CalendarValidationUtil {

    public static List<String> validateDates(String startDateS, String endDateS) {
        List<String> errors = new ArrayList<String>();
        if (errors.size() == 0 && StringUtils.isEmpty(startDateS)) errors.add("The start date is blank.");
        if (errors.size() == 0 && StringUtils.isEmpty(endDateS)) errors.add("The end date is blank.");
        return errors;
    }
	
    public static List<String> validateTimes(String startTimeS, String endTimeS) {
        List<String> errors = new ArrayList<String>();
        if (errors.size() == 0 && startTimeS == null) errors.add("The start time is blank.");
        if (errors.size() == 0 && endTimeS == null) errors.add("The end time is blank.");
        return errors;
    }
    
	 /**
     * Validate the earn code exists on every day within the date rage. This method is moving to CalendarValidationUtil
     * @param earnCode
     * @param startDateString
     * @param endDateString
     *
     * @return A list of error strings.
     */
    public static List<String> validateEarnCode(String earnCode, String startDateString, String endDateString) {
    	List<String> errors = new ArrayList<String>();

    	LocalDate tempDate = TKUtils.formatDateTimeStringNoTimezone(startDateString).toLocalDate();
    	LocalDate localEnd = TKUtils.formatDateTimeStringNoTimezone(endDateString).toLocalDate();
		// tempDate and localEnd could be the same day
    	while(!localEnd.isBefore(tempDate)) {
    		if(!ValidationUtils.validateEarnCode(earnCode, tempDate)) {
    			 errors.add("Earn Code " + earnCode + " is not available for " + tempDate);
    			 break;
    		}
    		tempDate = tempDate.plusDays(1);
    	}
    	
    	return errors;
    }
    
    public static List<String> validateInterval(CalendarEntry payCalEntry, Long startTime, Long endTime) {
        List<String> errors = new ArrayList<String>();
        LocalDateTime pcb_ldt = payCalEntry.getBeginPeriodLocalDateTime();
        LocalDateTime pce_ldt = payCalEntry.getEndPeriodLocalDateTime();
        /*
         * LeaveCalendarValidationUtil uses DateTimeZone... TimeDetailValidation does not...
         * Does one require a non converted DateTime and the other not?
         */
        //DateTimeZone utz = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        //DateTime p_cal_b_dt = pcb_ldt.toDateTime(utz);
        //DateTime p_cal_e_dt = pce_ldt.toDateTime(utz);
        DateTime p_cal_b_dt = pcb_ldt.toDateTime();
        DateTime p_cal_e_dt = pce_ldt.toDateTime();

        Interval payInterval = new Interval(p_cal_b_dt, p_cal_e_dt);
        if (errors.size() == 0 && !payInterval.contains(startTime)) {
            errors.add("The start date/time is outside the calendar period");
        }
        if (errors.size() == 0 && !payInterval.contains(endTime) && p_cal_e_dt.getMillis() != endTime) {
            errors.add("The end date/time is outside the calendar period");
        }
        return errors;
    }

	protected static List<String> validateDayParametersForLeaveEntry(EarnCode earnCode,
			CalendarEntry calendarEntry, String startDate, String endDate, BigDecimal leaveAmount) {
		List<String> errors = new ArrayList<String>();
		if(leaveAmount == null) {
			 errors.add("The Day field should not be empty.");
			 return errors;
		}
    	errors.addAll(validateDateTimeParametersForCalendarEntry(earnCode, calendarEntry, startDate, endDate));
		return errors;
	}

	public static List<String> validateHourParametersForLeaveEntry(EarnCode earnCode,
			CalendarEntry calendarEntry, String startDate, String endDate, BigDecimal leaveAmount) {
		List<String> errors = new ArrayList<String>();
		if(leaveAmount == null) {
			 errors.add("The Hour field should not be empty.");
			 return errors;
		}
    	errors.addAll(validateDateTimeParametersForCalendarEntry(earnCode, calendarEntry, startDate, endDate));
		return errors;
	}
	
	/*
	 * Validates if the state/end dates is within the range of the calendar entry
	 */
	public static List<String> validateDateTimeParametersForCalendarEntry(EarnCode earnCode,
			CalendarEntry calendarEntry, String startDate, String endDate) {
		if(!(earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_HOUR)
				|| earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_AMOUNT) ))
			return new ArrayList<String>();
		
		List<String> errors = new ArrayList<String>();
    	errors.addAll(CalendarValidationUtil.validateDates(startDate, endDate));
        if (errors.size() > 0) 
        	return errors;
        // use beginning hour of the start date and ending hour of the end date to fake the time to validate intervals 
        Long startTime= TKUtils.convertDateStringToDateTimeWithoutZone(startDate, "00:00:00").getMillis();
        Long endTime= TKUtils.convertDateStringToDateTimeWithoutZone(endDate, "11:59:59").getMillis();
        errors.addAll(CalendarValidationUtil.validateInterval(calendarEntry, startTime, endTime));
		return errors;
	}

	public static List<String> validateSpanningWeeks(DateTime startDate, DateTime endDate) {
		List<String> errors = new ArrayList<String>();
		
	    boolean isOnlyWeekendSpan = true;
	    while ((startDate.isBefore(endDate) || startDate.isEqual(endDate)) && isOnlyWeekendSpan) {
	    	if (startDate.getDayOfWeek() != DateTimeConstants.SATURDAY && startDate.getDayOfWeek() != DateTimeConstants.SUNDAY) {
	    		isOnlyWeekendSpan = false;
	    	}
	    	startDate = startDate.plusDays(1);
	    }
	    if (isOnlyWeekendSpan) {
	    	errors.add("Weekend day is selected, but include weekends checkbox is not checked");            //errorMessages
	    }
	    
	    return errors;
	}

}
