package org.kuali.hr.lm.leavecalendar.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.*;
import org.kuali.hr.lm.leave.web.LeaveCalendarWSForm;
import org.kuali.hr.lm.leavecalendar.web.LeaveCalendarForm;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LeaveCalendarValidationService {

    /**
     * Convenience method for handling validation directly from the form object.
     * @param tdaf The populated form.
     *
     * @return A list of error strings.
     */
    public static List<String> validateLaveEntryDetails(LeaveCalendarWSForm lcf) {
        return LeaveCalendarValidationService.validateLeaveEntryDetails(
        		lcf.getStartDate(), lcf.getEndDate(), lcf.getSpanningWeeks().equalsIgnoreCase("y")
        );
    }

    public static List<String> validateLeaveEntryDetails(String startDateS, String endDateS, boolean spanningWeeks) {
        List<String> errors = new ArrayList<String>();
        DateTime startTemp = new DateTime(TKUtils.convertDateStringToTimestamp(startDateS, "00:00:00"));
        DateTime endTemp = new DateTime(TKUtils.convertDateStringToTimestamp(endDateS, "00:00:00"));
                
        // KPME-1446 
        // -------------------------------
        // check if there is a weekend day when the include weekends flag is checked
        //--------------------------------
        errors.addAll(validateSpanningWeeks(spanningWeeks, startTemp, endTemp));
        if (errors.size() > 0) return errors;

        return errors;
    }

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
