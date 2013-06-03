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
package org.kuali.kpme.tklm.utils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.util.HtmlUnitUtil;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.util.TestAutoLoginFilter;
import org.kuali.kpme.tklm.time.detail.validation.TimeDetailValidationUtil;
import org.kuali.kpme.tklm.time.detail.web.TimeDetailActionFormBase;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TimeDetailTestUtils {

    private static final Logger LOG = Logger.getLogger(TimeDetailTestUtils.class);

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
    public static TimeDetailActionFormBase buildDetailActionForm(TimesheetDocument timeshetDocument, Assignment assignment, EarnCode earnCode, DateTime start, DateTime end, BigDecimal amount, boolean acrossDays, String timeblockId, boolean spanningWeeks) {
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
        tdaf.setSpanningWeeks(spanningWeeks ? "y" : "n"); // KPME-1446
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
        tdaf.setMethodToCall("addTimeBlock");

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
        List<String> errors = TimeDetailValidationUtil.validateTimeEntryDetails(tdaf);

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
            form.setAttribute("methodToCall", tdaf.getMethodToCall());
        }

        return errors;
    }

    /**
     * This is a 'hacker' method to get around the fact that in HtmlUnit you
     * can no longer directly submit forms if there are no buttons. We
     * simply add a button to the form, and click it!
     *
     * @param page The HtmlPage the form came from.
     * @param form The HtmlForm you wish to submit.
     * @return The return results from clicking .submit()
     */
    private static HtmlPage submitTimeDetailsDep(HtmlPage page, HtmlForm form) {
        HtmlButton submitButton = null;

        //ScriptResult sr = page.executeJavaScript("document.forms[\"TimeDetailActionForm\"].submit();");

        if (submitButton == null) {
            submitButton = (HtmlButton)page.createElement("button");
            submitButton.setAttribute("type", "submit");
            form.appendChild(submitButton);
        }

        HtmlPage newPage = null;
        try {
            submitButton.click();
        } catch (Exception e) {
            LOG.error("While submitting time detail form", e);
        }

        return newPage;
    }


    /**
     * A method to wrap the submission of the time details.
     * @param baseUrl
     * @param tdaf
     * @return
     */
    public static HtmlPage submitTimeDetails(WebClient webClient, String baseUrl, TimeDetailActionFormBase tdaf) {
        // For now, until a more HtmlUnit based click method can be found
        // workable, we're building a url-encoded string to directly
        // post to the servlet.

        String url = baseUrl + buildPostFromFormParams(tdaf);
        HtmlPage page = null;

        try {
            page = HtmlUnitUtil.gotoPageAndLogin(webClient, url);
        } catch (Exception e) {
            LOG.error("Error while submitting form", e);
        }

        return page;
    }

    /**
     * A method to wrap the submission of the time details.
     * @param baseUrl
     * @param tdaf
     * @return
     */
    public static HtmlPage submitTimeDetails(WebClient webClient, String principalId, String baseUrl, TimeDetailActionFormBase tdaf) {
        // For now, until a more HtmlUnit based click method can be found
        // workable, we're building a url-encoded string to directly
        // post to the servlet.

        String url = baseUrl + buildPostFromFormParams(tdaf);
        HtmlPage page = null;

        try {
            TestAutoLoginFilter.OVERRIDE_ID = principalId;
            page = HtmlUnitUtil.gotoPageAndLogin(webClient, url);
            TestAutoLoginFilter.OVERRIDE_ID = "";
        } catch (Exception e) {
            LOG.error("Error while submitting form", e);
        }

        return page;
    }

    private static String buildPostFromFormParams(TimeDetailActionFormBase tdaf) {
        StringBuilder builder = new StringBuilder();

        try {
            builder.append("&methodToCall=").append(URLEncoder.encode(tdaf.getMethodToCall(), "UTF-8"));
            builder.append("&acrossDays=").append(URLEncoder.encode(tdaf.getAcrossDays(), "UTF-8"));
            if (tdaf.getAmount() != null) {
                builder.append("&amount=").append(URLEncoder.encode(tdaf.getAmount().toString(), "UTF-8"));
            } else {
                builder.append("&hours=").append(URLEncoder.encode(tdaf.getHours().toString(), "UTF-8"));
                builder.append("&startTime=").append(URLEncoder.encode(tdaf.getStartTime(), "UTF-8"));
                builder.append("&endTime=").append(URLEncoder.encode(tdaf.getEndTime(), "UTF-8"));
            }
            builder.append("&startDate=").append(URLEncoder.encode(tdaf.getStartDate(), "UTF-8"));
            builder.append("&endDate=").append(URLEncoder.encode(tdaf.getEndDate(), "UTF-8"));
            builder.append("&selectedAssignment=").append(URLEncoder.encode(tdaf.getSelectedAssignment(), "UTF-8"));
            builder.append("&selectedEarnCode=").append(URLEncoder.encode(tdaf.getSelectedEarnCode(), "UTF-8"));
            if (tdaf.getTkTimeBlockId() != null) {
                builder.append("&tkTimeBlockId=").append(URLEncoder.encode(tdaf.getTkTimeBlockId().toString(), "UTF-8"));
            }
        } catch (Exception e) {
            LOG.error("Exception building Post String", e);
        }

        return builder.toString();
    }
}
