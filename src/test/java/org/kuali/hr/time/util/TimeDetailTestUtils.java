package org.kuali.hr.time.util;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.detail.validation.TimeDetailValidationService;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.math.BigDecimal;
import java.util.List;

public class TimeDetailTestUtils {

    /**
     * From the provided set of parameters, build an action form suitable for
     * submitting to the TimeDetailAction servlet. In our case, we are mostly
     * using it in a mock type of situation.
     *
     * @param timeshetDocument
     * @param assignment
     * @param earnCode
     * @param start
     * @param end
     * @param amount
     * @param acrossDays
     * @param timeblockId
     *
     * @return A populated TimeDetailActionFormBase object.
     */
    public static TimeDetailActionFormBase buildDetailActionForm(TimesheetDocument timeshetDocument, Assignment assignment, EarnCode earnCode, DateTime start, DateTime end, BigDecimal amount, boolean acrossDays, Long timeblockId) {
        TimeDetailActionFormBase tdaf = new TimeDetailActionFormBase();

        BigDecimal hours = null;
        String startTimeS = null;
        String endTimeS = null;
        String startDateS;
        String endDateS;
        String selectedEarnCode;
        String selectedAssignment;

        if (amount == null) {
            if (start != null && end != null) {
                Interval se_i = new Interval(start, end);
                hours = TKUtils.convertMillisToHours(se_i.toDurationMillis());
            }

            // the date/time format is defined in tk.calendar.js. For now, the format is 11/17/2010 8:0
            startTimeS = start.toString("H:mm");
            endTimeS = end.toString("H:mm");
        }

        startDateS = start.toString("MM/dd/YYYY");
        endDateS = end.toString("MM/dd/YYYY");

        AssignmentDescriptionKey adk = new AssignmentDescriptionKey(assignment);
        selectedAssignment = adk.toAssignmentKeyString();

        selectedEarnCode = earnCode.getEarnCode();

        tdaf.setAcrossDays(acrossDays ? "y" : "n");
        tdaf.setAmount(amount);
        tdaf.setHours(hours);
        tdaf.setStartTime(startTimeS);
        tdaf.setEndTime(endTimeS);
        tdaf.setStartDate(startDateS);
        tdaf.setEndDate(endDateS);
        tdaf.setTkTimeBlockId(timeblockId);
        tdaf.setTimesheetDocument(timeshetDocument);
        tdaf.setSelectedAssignment(selectedAssignment);
        tdaf.setSelectedEarnCode(selectedEarnCode);

        return tdaf;
    }

    /**
     * Set the attributes on the provided html form to the values found in the provided
     * ActionForm. Errors are returned in the List<String> object.
     *
     * @param form The HtmlForm to populate.
     * @param tdaf The ActionForm with values we will use to populate.
     *
     * @return A list of string error messages from the validation call.
     */
    public static List<String> setTimeBlockFormDetails(HtmlForm form, TimeDetailActionFormBase tdaf) {
        // Validation -- the same call the WS makes. (should already be valid...)
        List<String> errors = TimeDetailValidationService.validateTimeEntryDetails(tdaf);

        // If validation passes, we can add the time block.
        if (errors.size() == 0) {
            if (tdaf.getTkTimeBlockId() != null) {
                form.setAttribute("tkTimeBlockId", tdaf.getTkTimeBlockId().toString());
            }
            form.setAttribute("startDate", tdaf.getStartDate());
            form.setAttribute("endDate", tdaf.getEndDate());

            if (tdaf.getAmount() != null) {
                form.setAttribute("amount", tdaf.getAmount().toString());
            } else {
                form.setAttribute("startTime", tdaf.getStartTime());
                form.setAttribute("endTime", tdaf.getEndTime());
                form.setAttribute("hours", tdaf.getHours().toString());
            }

            form.setAttribute("selectedEarnCode", tdaf.getSelectedEarnCode());
            form.setAttribute("selectedAssignment", tdaf.getSelectedAssignment());
            form.setAttribute("acrossDays", tdaf.getAcrossDays());
        }

        return errors;
    }
}
