/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.detail.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.*;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TimeDetailValidationUtil {

    /**
     * Convenience method for handling validation directly from the form object.
     * @param tdaf The populated form.
     *
     * @return A list of error strings.
     */
    public static List<String> validateTimeEntryDetails(TimeDetailActionFormBase tdaf) {
        return TimeDetailValidationUtil.validateTimeEntryDetails(
                tdaf.getHours(), tdaf.getAmount(), tdaf.getStartTime(), tdaf.getEndTime(),
                tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getTimesheetDocument(),
                tdaf.getSelectedEarnCode(), tdaf.getSelectedAssignment(),
                tdaf.getAcrossDays().equalsIgnoreCase("y"), tdaf.getTkTimeBlockId(), tdaf.getOvertimePref(), tdaf.getSpanningWeeks().equalsIgnoreCase("y")
        );
    }

    public static List<String> validateTimeEntryDetails(BigDecimal hours, BigDecimal amount, String startTimeS, String endTimeS, String startDateS, String endDateS, TimesheetDocument timesheetDocument, String selectedEarnCode, String selectedAssignment, boolean acrossDays, String timeblockId, String overtimePref, boolean spanningWeeks) {
        List<String> errors = new ArrayList<String>();

        if (timesheetDocument == null) {
            errors.add("No timesheet document found.");
        }
        if (errors.size() > 0) return errors;

        CalendarEntries payCalEntry = timesheetDocument.getCalendarEntry();
        java.sql.Date asOfDate = payCalEntry.getEndPeriodDate();

        errors.addAll(TimeDetailValidationUtil.validateDates(startDateS, endDateS));
        errors.addAll(TimeDetailValidationUtil.validateTimes(startTimeS, endTimeS));
        if (errors.size() > 0) return errors;
        // These methods use the UserTimeZone.
        Long startTime = TKUtils.convertDateStringToTimestamp(startDateS, startTimeS).getTime();
        Long endTime = TKUtils.convertDateStringToTimestamp(endDateS, endTimeS).getTime();

        errors.addAll(validateInterval(payCalEntry, startTime, endTime));
        if (errors.size() > 0) return errors;

        EarnCode earnCode = new EarnCode();
        if (StringUtils.isNotBlank(selectedEarnCode)) {
            earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, asOfDate);
            if (earnCode != null && earnCode.getRecordMethod()!= null && earnCode.getRecordMethod().equalsIgnoreCase(TkConstants.EARN_CODE_TIME)) {
                if (startTimeS == null) errors.add("The start time is blank.");
                if (endTimeS == null) errors.add("The end time is blank.");
                if (startTime - endTime == 0) errors.add("Start time and end time cannot be equivalent");
            }
        }
        if (errors.size() > 0) return errors;

        DateTime startTemp = new DateTime(startTime);
        DateTime endTemp = new DateTime(endTime);

        if (errors.size() == 0 && !acrossDays && !StringUtils.equals(TkConstants.EARN_CODE_CPE, overtimePref)) {
            Hours hrs = Hours.hoursBetween(startTemp, endTemp);
            if (hrs.getHours() >= 24) errors.add("One timeblock cannot exceed 24 hours");
        }
        if (errors.size() > 0) return errors;

        //Check that assignment is valid for both days
        AssignmentDescriptionKey assignKey = TkServiceLocator.getAssignmentService().getAssignmentDescriptionKey(selectedAssignment);
        Assignment assign = TkServiceLocator.getAssignmentService().getAssignment(assignKey, new Date(startTime));
        if (assign == null) errors.add("Assignment is not valid for start date " + TKUtils.formatDate(new Date(startTime)));
        assign = TkServiceLocator.getAssignmentService().getAssignment(assignKey, new Date(endTime));
        if (assign == null) errors.add("Assignment is not valid for end date " + TKUtils.formatDate(new Date(endTime)));
        if (errors.size() > 0) return errors;

        //------------------------
        // some of the simple validations are in the js side in order to reduce the server calls
        // 1. check if the begin / end time is empty - tk.calenadr.js
        // 2. check the time format - timeparse.js
        // 3. only allows decimals to be entered in the hour field
        //------------------------

        //------------------------
        // check if the begin / end time are valid
        //------------------------
        if ((startTime.compareTo(endTime) > 0 || endTime.compareTo(startTime) < 0)) {
            errors.add("The time or date is not valid.");
        }
        if (errors.size() > 0) return errors;
        
        // KPME-1446 
        // -------------------------------
        // check if there is a weekend day when the include weekends flag is checked
        //--------------------------------
        errors.addAll(validateSpanningWeeks(spanningWeeks, startTemp, endTemp));
        if (errors.size() > 0) return errors;

        //------------------------
        // check if the overnight shift is across days
        //------------------------
        if (acrossDays && hours == null && amount == null) {
            if (startTemp.getHourOfDay() >= endTemp.getHourOfDay()
                    && !(endTemp.getDayOfYear() - startTemp.getDayOfYear() <= 1
                    && endTemp.getHourOfDay() == 0)) {
                errors.add("The \"apply to each day\" box should not be checked.");
            }
        }
        if (errors.size() > 0) return errors;

        //------------------------
        // Amount cannot be zero
        //------------------------
        if (amount != null && earnCode != null && StringUtils.equals(earnCode.getEarnCodeType(), TkConstants.EARN_CODE_AMOUNT)) {
            if (amount.equals(BigDecimal.ZERO)) {
                errors.add("Amount cannot be zero.");
            }
            if (amount.scale() > 2) {
                errors.add("Amount cannot have more than two digits after decimal point.");
            }
        }
        if (errors.size() > 0) return errors;

        //------------------------
        // check if the hours entered for hourly earn code is greater than 24 hours per day
        // Hours cannot be zero
        //------------------------
        if (hours != null && earnCode != null && StringUtils.equals(earnCode.getEarnCodeType(), TkConstants.EARN_CODE_HOUR)) {
            if (hours.equals(BigDecimal.ZERO)) {
                errors.add("Hours cannot be zero.");
            }
            if (hours.scale() > 2) {
                errors.add("Hours cannot have more than two digits after decimal point.");
            }
            int dayDiff = endTemp.getDayOfYear() - startTemp.getDayOfYear() + 1;
            if (hours.compareTo(new BigDecimal(dayDiff * 24)) == 1) {
                errors.add("Cannot enter more than 24 hours per day.");
            }
        }
        if (errors.size() > 0) return errors;



        //------------------------
        // check if time blocks overlap with each other. Note that the tkTimeBlockId is used to
        // determine is it's updating an existing time block or adding a new one
        //------------------------

        boolean isRegularEarnCode = StringUtils.equals(assign.getJob().getPayTypeObj().getRegEarnCode(),selectedEarnCode);
        errors.addAll(validateOverlap(startTime, endTime, acrossDays, startDateS, endTimeS,startTemp, endTemp, timesheetDocument, timeblockId, isRegularEarnCode));
        if (errors.size() > 0) return errors;

        // Accrual Hour Limits Validation
        //errors.addAll(TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimitByEarnCode(timesheetDocument, selectedEarnCode));

        return errors;
    }

    public static List<String> validateOverlap(Long startTime, Long endTime, boolean acrossDays, String startDateS, String endTimeS, DateTime startTemp, DateTime endTemp, TimesheetDocument timesheetDocument, String timeblockId, boolean isRegularEarnCode) {
        List<String> errors = new ArrayList<String>();
        Interval addedTimeblockInterval = new Interval(startTime, endTime);
        List<Interval> dayInt = new ArrayList<Interval>();

        //if the user is clocked in, check if this time block overlaps with the clock action
        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(TKUser.getCurrentTargetPerson().getPrincipalId());
        if(lastClockLog != null &&
        		(lastClockLog.getClockAction().equals(TkConstants.CLOCK_IN) 
        				|| lastClockLog.getClockAction().equals(TkConstants.LUNCH_IN))) {
        	 Timestamp lastClockTimestamp = lastClockLog.getClockTimestamp();
             String lastClockZone = lastClockLog.getClockTimestampTimezone();
             if (StringUtils.isEmpty(lastClockZone)) {
                 lastClockZone = TKUtils.getSystemTimeZone();
             }
             DateTimeZone zone = DateTimeZone.forID(lastClockZone);
             DateTime clockWithZone = new DateTime(lastClockTimestamp, zone);
             DateTime currentTime = new DateTime(System.currentTimeMillis(), zone);
             Interval currentClockInInterval = new Interval(clockWithZone.getMillis(), currentTime.getMillis());
       
            if (isRegularEarnCode && addedTimeblockInterval.overlaps(currentClockInInterval)) {
                 errors.add("The time block you are trying to add overlaps with the current clock action.");
                 return errors;
             }
        }
       
        if (acrossDays) {
            DateTime start = new DateTime(startTime);
            DateTime end = new DateTime(TKUtils.convertDateStringToTimestamp(startDateS, endTimeS).getTime());
            if (endTemp.getDayOfYear() - startTemp.getDayOfYear() < 1) {
                end = new DateTime(endTime);
            }
            DateTime groupEnd = new DateTime(endTime);
            Long startLong = start.getMillis();
            Long endLong = end.getMillis();
            //create interval span if start is before the end and the end is after the start except
            //for when the end is midnight ..that converts to midnight of next day
            DateMidnight midNight = new DateMidnight(endLong);
            while (start.isBefore(groupEnd.getMillis()) && ((endLong >= startLong) || end.isEqual(midNight))) {
                Interval tempInt = null;
                if (end.isEqual(midNight)) {
                    tempInt = addedTimeblockInterval;
                } else {
                    tempInt = new Interval(startLong, endLong);
                }
                dayInt.add(tempInt);
                start = start.plusDays(1);
                end = end.plusDays(1);
                startLong = start.getMillis();
                endLong = end.getMillis();
            }
        } else {
            dayInt.add(addedTimeblockInterval);
        }

        for (TimeBlock timeBlock : timesheetDocument.getTimeBlocks()) {
            if (errors.size() == 0 && StringUtils.equals(timeBlock.getEarnCodeType(), TkConstants.EARN_CODE_TIME)) {
                Interval timeBlockInterval = new Interval(timeBlock.getBeginTimestamp().getTime(), timeBlock.getEndTimestamp().getTime());
                for (Interval intv : dayInt) {
                    if (isRegularEarnCode && timeBlockInterval.overlaps(intv) && (timeblockId == null || timeblockId.compareTo(timeBlock.getTkTimeBlockId()) != 0)) {
                        errors.add("The time block you are trying to add overlaps with an existing time block.");
                    }
                }
            }
        }

        return errors;
    }

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

    public static List<String> validateInterval(CalendarEntries payCalEntry, Long startTime, Long endTime) {
        List<String> errors = new ArrayList<String>();
        LocalDateTime pcb_ldt = payCalEntry.getBeginLocalDateTime();
        LocalDateTime pce_ldt = payCalEntry.getEndLocalDateTime();
        DateTimeZone utz = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime p_cal_b_dt = pcb_ldt.toDateTime(utz);
        DateTime p_cal_e_dt = pce_ldt.toDateTime(utz);

        Interval payInterval = new Interval(p_cal_b_dt, p_cal_e_dt);
        if (errors.size() == 0 && !payInterval.contains(startTime)) {
            errors.add("The start date/time is outside the pay period");
        }
        if (errors.size() == 0 && !payInterval.contains(endTime) && p_cal_e_dt.getMillis() != endTime) {
            errors.add("The end date/time is outside the pay period");
        }
        return errors;
    }
    
    // KPME-1446
    public static List<String> validateSpanningWeeks(boolean spanningWeeks, DateTime startTemp, DateTime endTemp) {
    	List<String> errors = new ArrayList<String>();
    	boolean valid = true;
        while ((startTemp.isBefore(endTemp) || startTemp.isEqual(endTemp)) && valid) {
        	if (!spanningWeeks && 
        		(startTemp.getDayOfWeek() == DateTimeConstants.SATURDAY || startTemp.getDayOfWeek() == DateTimeConstants.SUNDAY)) {
        		valid = false;
        	}
        	startTemp = startTemp.plusDays(1);
        }
        if (!valid) {
        	errors.add("Weekend day is selected, but include weekends checkbox is not checked");
        }
    	return errors;
    }
}
