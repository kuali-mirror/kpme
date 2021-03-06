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
package org.kuali.kpme.tklm.time.detail.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.*;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.summary.LeaveSummaryContract;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.common.CalendarValidationUtil;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.detail.web.TimeDetailActionFormBase;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TimeDetailValidationUtil extends CalendarValidationUtil {

    public static List<String> validateLeaveEntry(TimeDetailActionFormBase tdaf) throws Exception {
    	// This validator could be shared between LeaveCalendarValidationUtil and TimeDetailValidationUtil
    	// if the common parameters required by both are moved to a shared parent of TimeDetailActionFormBase and LeaveCalendarForm.
    	List<String> errorMsgList = new ArrayList<String>();
        CalendarEntry payCalendarEntry = tdaf.getCalendarEntry();
    	if(ObjectUtils.isNotNull(payCalendarEntry)) {
			LeaveBlock lb = null;
			if(StringUtils.isNotEmpty(tdaf.getLmLeaveBlockId())) {
				lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(tdaf.getLmLeaveBlockId());
			}
			errorMsgList.addAll(CalendarValidationUtil.validateEarnCode(tdaf.getSelectedEarnCode(),tdaf.getStartDate(),tdaf.getEndDate()));
			if(errorMsgList.isEmpty()) {
				LeaveSummaryContract ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(HrContext.getTargetPrincipalId(), TKUtils.formatDateString(tdaf.getEndDate()));
				
				  BigDecimal leaveAmount = tdaf.getLeaveAmount();
                  if(leaveAmount == null) {
	                  Long startTime = TKUtils.convertDateStringToDateTimeWithoutZone(tdaf.getStartDate(), tdaf.getStartTime()).getMillis();
	                  Long endTime = TKUtils.convertDateStringToDateTimeWithoutZone(tdaf.getEndDate(), tdaf.getEndTime()).getMillis();
	                  leaveAmount = TKUtils.getHoursBetween(startTime, endTime);
                  }
				
				// Validate LeaveBlock timings and all that
				/**
				 * In all cases, TIME, AMOUNT, HOUR, DAY, we should validate usage and balance limits. But depending on earn code record method, the derivation of requested
				 * leave amount varies. It can be derived from endTime - startTime, or vary by units.
				 * 
				 * TimeDetailValidationUtil.validateLeaveParametersByEarnCodeRecordMethod offers a form of organization for these validations.
				 */
				errorMsgList.addAll(TimeDetailValidationUtil.validateLeaveParametersByEarnCodeRecordMethod(tdaf));
				errorMsgList.addAll(LeaveCalendarValidationUtil.validateAvailableLeaveBalanceForUsage(tdaf.getSelectedEarnCode(), 
						tdaf.getStartDate(), tdaf.getEndDate(), leaveAmount, lb));
				//Validate leave block does not exceed max usage. Leave Calendar Validators at this point rely on a leave summary.
		        errorMsgList.addAll(LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, tdaf.getSelectedEarnCode(),
		                tdaf.getStartDate(), tdaf.getEndDate(), leaveAmount, lb));
		        errorMsgList.addAll(LeaveCalendarValidationUtil.validateHoursUnderTwentyFour(tdaf.getSelectedEarnCode(),
		        		tdaf.getStartDate(), tdaf.getEndDate(),leaveAmount));
			}
		}
		return errorMsgList;
    }
    
    public static List<String> validateLeaveParametersByEarnCodeRecordMethod(TimeDetailActionFormBase lcf) {
    	List<String> errors = new ArrayList<String>();
    	if (StringUtils.isNotBlank(lcf.getSelectedEarnCode()) &&  lcf.getCalendarEntry() != null) {
    		//earn code is validate through the span of the leave entry, could the earn code's record method change between then and the leave period end date?
    		//Why not use endDateS to retrieve the earn code?
            CalendarEntry calendarEntry = lcf.getCalendarEntry();
    		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(lcf.getSelectedEarnCode(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
    		if(earnCode != null) {
    			if(earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_TIME)) {
    		    	return LeaveCalendarValidationUtil.validateTimeParametersForLeaveEntry(earnCode, lcf.getCalendarEntry(), lcf.getStartDate(), lcf.getEndDate(), lcf.getStartTime(), lcf.getEndTime(), lcf.getSelectedAssignment(), lcf.getLmLeaveBlockId(), null);
    			}
    			// we should not have any leave earn codes with amount recording method
//    			else if (earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_AMOUNT)) {
//    		    	return validateAmountParametersForLeaveEntry(earnCode, lcf.getCalendarEntry(), lcf.getStartDate(), lcf.getEndDate(), lcf.getSelectedAssignment(), lcf.getLmLeaveBlockId());
//    			}
    			else if (earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_HOUR)) {
    		    	return validateHourParametersForLeaveEntry(earnCode, lcf.getCalendarEntry(), lcf.getStartDate(), lcf.getEndDate(), lcf.getLeaveAmount());
    			}
    			else if (earnCode.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_DAY)) {
    		    	return validateDayParametersForLeaveEntry(earnCode, lcf.getCalendarEntry(), lcf.getStartDate(), lcf.getEndDate(), lcf.getLeaveAmount());
    			}
    		}
    	}
    	return errors;
    }
	 /**
     * Validate the earn code exists on every day within the date rage
     * @param earnCode
     * @param startDateString
     * @param endDateString
     *
     * @return A list of error strings.
     */
    @Deprecated
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
	
    /**
     * Convenience method for handling validation directly from the form object.
     * @param tdaf The populated form.
     *
     * @return A list of error strings.
     */
    public static List<String> validateTimeEntryDetails(TimeDetailActionFormBase tdaf) {
    	boolean spanningWeeks = false;
    	boolean acrossDays = false;
    	
    	if(tdaf.getAcrossDays() != null) {
    		acrossDays = tdaf.getAcrossDays().equalsIgnoreCase("y");
    	}
    	
        return validateTimeEntryDetails(
                tdaf.getHours(), tdaf.getAmount(), tdaf.getStartTime(), tdaf.getEndTime(),
                tdaf.getStartDate(), tdaf.getEndDate(), tdaf.getTimesheetDocument(),
                tdaf.getSelectedEarnCode(), tdaf.getSelectedAssignment(),
                acrossDays, tdaf.getTkTimeBlockId(), tdaf.getOvertimePref()
        );
    }

    public static List<String> validateTimeEntryDetails(BigDecimal hours, BigDecimal amount, String startTimeS, String endTimeS, String startDateS, String endDateS, TimesheetDocument timesheetDocument, String selectedEarnCode, String selectedAssignment, boolean acrossDays, String timeblockId, String overtimePref) {
        List<String> errors = new ArrayList<String>();
        LocalDate savedStartDate = TKUtils.formatDateString(startDateS);
        LocalDate savedEndDate = TKUtils.formatDateString(endDateS);

        if (timesheetDocument == null) {
            errors.add("No timesheet document found.");
        }
        if (errors.size() > 0) return errors;

        CalendarEntry payCalEntry = timesheetDocument.getCalendarEntry();
        EarnCode earnCode = null;
        if (StringUtils.isNotBlank(selectedEarnCode)) {
            earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, TKUtils.formatDateTimeStringNoTimezone(endDateS).toLocalDate());
        }
        boolean isTimeRecordMethod = earnCode != null && StringUtils.equalsIgnoreCase(earnCode.getRecordMethod(), HrConstants.EARN_CODE_TIME);

        errors.addAll(CalendarValidationUtil.validateDates(startDateS, endDateS));

        if (isTimeRecordMethod) {
            errors.addAll(CalendarValidationUtil.validateTimes(startTimeS, endTimeS));
        }
        if (errors.size() > 0) return errors;

        Long startTime;
        Long endTime;

        if (!isTimeRecordMethod) {
            startTimeS = "0:0";
            endTimeS = "0:0";
        }

        if (acrossDays && !endTimeS.equals("0:00")) { endDateS = startDateS;}

        startTime = TKUtils.convertDateStringToDateTimeWithoutZone(startDateS, startTimeS).getMillis();
        endTime = TKUtils.convertDateStringToDateTimeWithoutZone(endDateS, endTimeS).getMillis();

        errors.addAll(CalendarValidationUtil.validateInterval(payCalEntry, startTime, endTime));
        if (errors.size() > 0) return errors;

        if (isTimeRecordMethod) {
            if (startTimeS == null) errors.add("The start time is blank.");
            if (endTimeS == null) errors.add("The end time is blank.");
            if (startTime - endTime == 0) errors.add("Start time and end time cannot be equivalent");
        }
        if (errors.size() > 0) return errors;

        DateTime startTemp = new DateTime(startTime);
        DateTime endTemp = new DateTime(endTime);
/*
 * KPME-2687:
 *
 * Removed 24 hour limitation. System creates continuous sequence of time blocks when !accrossDays,
 * hours between startTemp and endTemp may be over 24 hours.
 *
        if (errors.size() == 0 && !acrossDays && !StringUtils.equals(TkConstants.EARN_CODE_CPE, overtimePref)) {
            Hours hrs = Hours.hoursBetween(startTemp, endTemp);
            if (hrs.getHours() > 24) errors.add("One timeblock cannot exceed 24 hours");
        }
        if (errors.size() > 0) return errors;

 */

        //Check that assignment is valid within the timeblock span. 
        AssignmentDescriptionKey assignKey = HrServiceLocator.getAssignmentService().getAssignmentDescriptionKey(selectedAssignment);
        Assignment assign = HrServiceLocator.getAssignmentService().getAssignmentForTargetPrincipal(assignKey, startTemp.toLocalDate());
        if (assign == null) errors.add("Assignment is not valid for start date " + TKUtils.formatDate(new LocalDate(startTime)));
        assign = HrServiceLocator.getAssignmentService().getAssignmentForTargetPrincipal(assignKey, endTemp.toLocalDate());
        if (assign == null) errors.add("Assignment is not valid for end date " + TKUtils.formatDate(new LocalDate(endTime)));
        if (errors.size() > 0) return errors;

        //------------------------
        // some of the simple validations are in the js side in order to reduce the server calls
        // 1. check if the begin / end time is empty - tk.calenadr.js
        // 2. check the time format - timeparse.js
        // 3. only allows decimals to be entered in the hour field
        //------------------------

        //------------------------
        // check if the overnight shift is across days
        //------------------------
        if (acrossDays && hours == null && amount == null) {
            if (savedEndDate.isAfter(savedStartDate)
                    && startTemp.getHourOfDay() > endTemp.getHourOfDay()
                    && !(endTemp.getDayOfYear() - startTemp.getDayOfYear() <= 1
                    && endTemp.getHourOfDay() == 0)) {
                errors.add("The \"apply to each day\" box should not be checked.");
            }
        }
        if (errors.size() > 0) return errors;

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
       
        //------------------------
        // Amount cannot be zero
        //------------------------
        if (amount != null && earnCode != null && StringUtils.equals(earnCode.getEarnCodeType(), HrConstants.EARN_CODE_AMOUNT)) {
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
        if (hours != null && earnCode != null && StringUtils.equals(earnCode.getEarnCodeType(), HrConstants.EARN_CODE_HOUR)) {
            if (hours.equals(BigDecimal.ZERO)) {
                errors.add("Hours cannot be zero.");
            }
            if (hours.scale() > 2) {
                errors.add("Hours cannot have more than two digits after decimal point.");
            }
/*
 * KPME-2671:
 * 
 * Replacing this conditional with the one below. Shouldn't matter if the date range spans more than one day,
 * hours shouldn't exceed 24.
 * 
 *          int dayDiff = endTemp.getDayOfYear() - startTemp.getDayOfYear() + 1;
            if (hours.compareTo(new BigDecimal(dayDiff * 24)) == 1) {
                errors.add("Cannot enter more than 24 hours per day.");
            }
 */
        }
        if (errors.size() > 0) return errors;

        /**
         * KPME-2671:
         * 
         * Generalize 24 limit to hour field on time entry form.
         * 
         */
    	if(hours != null && hours.compareTo(new BigDecimal(24.0)) > 0) {
    		errors.add("Hours cannot exceed 24.");
    	}
    	//------------------------
        // check if time blocks overlap with each other. Note that the tkTimeBlockId is used to
        // determine is it's updating an existing time block or adding a new one
        //------------------------

        boolean isRegularEarnCode = StringUtils.equals(assign.getJob().getPayTypeObj().getRegEarnCode(),selectedEarnCode);
        startTime = TKUtils.convertDateStringToDateTime(startDateS, startTimeS).getMillis();
        endTime = TKUtils.convertDateStringToDateTime(endDateS, endTimeS).getMillis();

        errors.addAll(validateOverlap(startTime, endTime, acrossDays, startDateS, endTimeS,startTemp, endTemp, timesheetDocument, timeblockId, isRegularEarnCode, selectedEarnCode));
        if (errors.size() > 0) return errors;

        // Accrual Hour Limits Validation
        //errors.addAll(TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimitByEarnCode(timesheetDocument, selectedEarnCode));

        return errors;
    }

    public static List<String> validateOverlap(Long startTime, Long endTime, boolean acrossDays, String startDateS, String endTimeS, DateTime startTemp, DateTime endTemp, TimesheetDocument timesheetDocument, String timeblockId, boolean isRegularEarnCode, String selectedEarnCode) {
        List<String> errors = new ArrayList<String>();
        Interval addedTimeblockInterval = new Interval(startTime, endTime);
        List<Interval> dayInt = new ArrayList<Interval>();

        //if the user is clocked in, check if this time block overlaps with the clock action
        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(HrContext.getTargetPrincipalId());
        if(lastClockLog != null &&
        		(lastClockLog.getClockAction().equals(TkConstants.CLOCK_IN) 
        				|| lastClockLog.getClockAction().equals(TkConstants.LUNCH_IN))) {
        	 DateTime lastClockDateTime = lastClockLog.getClockDateTime();
             //String lastClockZone = lastClockLog.getClockTimestampTimezone();
             //if (StringUtils.isEmpty(lastClockZone)) {
             //    lastClockZone = TKUtils.getSystemTimeZone();
             //}
             //DateTimeZone zone = DateTimeZone.forID(lastClockZone);
             //DateTime clockWithZone = lastClockDateTime.withZone(zone);
             //DateTime currentTime = new DateTime(System.currentTimeMillis(), zone);
            DateTime currentTime = DateTime.now();
            if (lastClockDateTime.getMillis() > currentTime.getMillis()) {
                currentTime = new DateTime(lastClockDateTime.getMillis());
            }
            
            Interval currentClockInInterval = new Interval(lastClockDateTime, currentTime);
            if (isRegularEarnCode && addedTimeblockInterval.overlaps(currentClockInInterval)) {
                 errors.add("The time block you are trying to add overlaps with the current clock action.");
                 return errors;
             }
        }
       
        if (acrossDays) {
        	DateTime start = new DateTime(startTime);
        	// KPME-2720
        	// The line below is necessary because "end" needs to have start date to construct the right end datetime to 
        	// create an interval.  For example, if user selects from 8a 8/19 to 10a 8/21 and checks across days check box, 
        	// startTime would have 8a 8/19 and endTime would have 10a 8/21.  With the line below, "end" would have 10a 8/19, and
        	// an interval from 8a 8/19 to 10a 8/19 would be created.  However, since it was using convertDateStringToDateTime,
        	// which takes into account user time zone, "end" was off.  Use convertDateStringToDateTimeWithoutZone isntead
        	// so that "end" would have the right time in default time zone.
        	//        	DateTime end = TKUtils.convertDateStringToDateTimeWithoutZone(startDateS, endTimeS);
        	
        	// kpme-3181
        	// revert the following line of change from kpme-2720 since it's using timezone for starttime but not endtime
        	// that's causing interval not being build correctly issue
            DateTime end = TKUtils.convertDateStringToDateTime(startDateS, endTimeS);
        	
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
            if (errors.size() == 0 && StringUtils.equals(timeBlock.getEarnCodeType(), HrConstants.EARN_CODE_TIME)) {
            	// allow regular time blocks to be added with overlapping non-regular time blocks
            	JobContract aJob = HrServiceLocator.getJobService().getJob(timeBlock.getPrincipalId(), timeBlock.getJobNumber(), timeBlock.getBeginDateTime().toLocalDate());
            	if(aJob != null && aJob.getPayTypeObj() != null && isRegularEarnCode && !StringUtils.equals(aJob.getPayTypeObj().getRegEarnCode(),timeBlock.getEarnCode())) {
            		continue;
            	}
            	
                Interval timeBlockInterval = new Interval(timeBlock.getBeginDateTime(), timeBlock.getEndDateTime());
                for (Interval intv : dayInt) {
                	// KPME-2720
                	// timeblockInterval above seems to have the server timezone (America/New York), for example, if you log in as iadetail1 (America/chicago) 
                	// and see a timeblock from 8a to 10a on the screen, that time block is actually stored as 9a to 11a in the table because 
                	// there is an hour difference.  So, timeblockInterval intervale above is 9a to 11a in America/New York timezone.  
                	// However, intv interval seems to have local time with the server timezone.  For example, if you create a timeblock from 10a to 12p
                	// as iadetail1, intv interval is 10a to 12p in America/New York timezone.  This is why it was giving the error below because it was
                	// comparing a time block with 9a-11a to a time block with 10a-12p (overlapping).  To fix this, we will create
                	// an interval with the right time in the server timezone.
//                    String start_datetime = TKUtils.formatDateTimeLong(intv.getStart());
//                	String start_date = TKUtils.formatDateTimeShort(intv.getStart());
//                	String start_time = TKUtils.formatTimeShort(start_datetime);
//                	String end_datetime = TKUtils.formatDateTimeLong(intv.getEnd());                	
//                	String end_date = TKUtils.formatDateTimeShort(intv.getEnd());
//                	String end_time =  TKUtils.formatTimeShort(end_datetime);
                	DateTime start_dt_timezone = new DateTime(startTime);
                	DateTime end_dt_timezone = new DateTime(endTime);
                	Interval converted_intv = new Interval(start_dt_timezone.getMillis(), end_dt_timezone.getMillis()); // interval with start/end datetime in server timezone
                    List<Interval> intervals = new ArrayList<Interval>();
                    if (acrossDays) {
                        List<LocalDate> localDates = new ArrayList<LocalDate>();
                        LocalDate startDay = new LocalDate(start_dt_timezone);
                        DateTimeZone userTimeZone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(timesheetDocument.getPrincipalId()));
                        if (userTimeZone==null) {
                            userTimeZone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
                        }


                        int days = end_dt_timezone.withZone(userTimeZone).toLocalTime().equals(new LocalTime(0,0,0)) ? Days.daysBetween(startDay, new LocalDate(end_dt_timezone)).getDays() : Days.daysBetween(startDay, new LocalDate(end_dt_timezone)).getDays()+1;
                        for (int i=0; i < days; i++) {
                            LocalDate d = startDay.withFieldAdded(DurationFieldType.days(), i);
                            localDates.add(d);
                        }
                        for (LocalDate localDate : localDates) {
                            DateTime startDateTime = localDate.toDateTime(start_dt_timezone.toLocalTime());
                            DateTime endDateTime = localDate.toDateTime(end_dt_timezone.toLocalTime());
                            endDateTime = endDateTime.isBefore(startDateTime) ? endDateTime.plusDays(1) : endDateTime;

                            intervals.add(new Interval(startDateTime,endDateTime));
                        }

                    } else {
                        intervals.add(converted_intv);
                    }

                    for (Interval interval : intervals) {
                        if (isRegularEarnCode && timeBlockInterval.overlaps(interval) && (timeblockId == null || timeblockId.compareTo(timeBlock.getTkTimeBlockId()) != 0)) {
                        	errors.add("The time block you are trying to add overlaps with an existing time block.");
                            return errors;
                        }else if(timeBlockInterval.overlaps(interval) && (timeblockId == null || timeblockId.compareTo(timeBlock.getTkTimeBlockId()) != 0)){
                        	errors.add("The time block you are trying to add overlaps with an existing time block.");
                        	return errors;
                        }
                    }
                }
            }
        }

        return errors;
    }
    
    /*
     * Moving to CalendarValidationUtil
     * @param startDateS
     * @param endDateS
     * @return
     */
    @Deprecated
    public static List<String> validateDates(String startDateS, String endDateS) {
        List<String> errors = new ArrayList<String>();
        if (errors.size() == 0 && StringUtils.isEmpty(startDateS)) errors.add("The start date is blank.");
        if (errors.size() == 0 && StringUtils.isEmpty(endDateS)) errors.add("The end date is blank.");
        return errors;
    }
    
    /*
     * Moving to CalendarValidationUtil
     * @param startTimeS
     * @param endTimeS
     * @return
     */
    @Deprecated
    public static List<String> validateTimes(String startTimeS, String endTimeS) {
        List<String> errors = new ArrayList<String>();
        if (errors.size() == 0 && startTimeS == null) errors.add("The start time is blank.");
        if (errors.size() == 0 && endTimeS == null) errors.add("The end time is blank.");
        return errors;
    }
    
    /*
     * Moving to CalendarValidationUtil
     * @param payCalEntry
     * @param startTime
     * @param endTime
     * @return
     */
    @Deprecated
    public static List<String> validateInterval(CalendarEntryBo payCalEntry, Long startTime, Long endTime) {
        List<String> errors = new ArrayList<String>();
        LocalDateTime pcb_ldt = payCalEntry.getBeginPeriodLocalDateTime();
        LocalDateTime pce_ldt = payCalEntry.getEndPeriodLocalDateTime();

        DateTime p_cal_b_dt = pcb_ldt.toDateTime();
        DateTime p_cal_e_dt = pce_ldt.toDateTime();

        Interval payInterval = new Interval(p_cal_b_dt, p_cal_e_dt);
        if (errors.size() == 0 && !payInterval.contains(startTime)) {
            errors.add("The start date/time is outside the pay period");
        }
        if (errors.size() == 0 && !payInterval.contains(endTime) && p_cal_e_dt.getMillis() != endTime) {
            errors.add("The end date/time is outside the pay period");
        }
        return errors;
    }

}
