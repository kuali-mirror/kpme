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
package org.kuali.hr.time.overtime.web;

import java.math.BigDecimal;
import java.util.*;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetWebTestBase;
import org.kuali.hr.time.util.*;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * https://wiki.kuali.org/display/KPME/Test+Cases+-+Business+Logic+Daily+Overtime
 *
 * Implementations of tests from this page.
 */
public class DOTIntegrationConfluenceTests extends TimesheetWebTestBase {

    @Test
    /**
     * Daily OT - Shift hours met (in a single day) but Max Gap minutes exceeded (in a single gap)
     * Daily OT - Shift hours met (in a single day) but Max Gap matches exactly
     *
     * https://jira.kuali.org/browse/KPME-788
     * https://jira.kuali.org/browse/KPME-789
     *
     */
    public void testKPME788_789() throws Exception {
        Long jobNumber = 30L;
        Long workArea = 30L;
        Long task = 30L;
        DailyOvertimeRule rule = createDailyOvertimeRule("REG", "OVT", "SD1", "BW", "TEST-DEPT", workArea, task, new BigDecimal(8), new BigDecimal("9.00"), null);
        String tdocId = KPME788_789(
               new ArrayList<Map<String, Object>>() {{
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "RGN");
                        put("hours", "5.0");
                    }});
                }},
                new HashMap<String, Object>() {{
                    put("earnCode", "RGN");
                    put("startNoTz", "2011-03-02T08:00:00");
                    put("endNoTz", "2011-03-02T13:00:00");
                    put("title", "SDR1 Work Area");
                    put("assignment", "30_30_30");
                }},
                new ArrayList<Map<String, Object>>() {{
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "RGN");
                        put("hours", "5.0");
                    }});
                }},
                new HashMap<String, Object>() {{
                    put("earnCode", "RGN");
                    put("startNoTz", "2011-03-02T13:10:00");
                    put("endNoTz", "2011-03-02T18:10:00");
                    put("title", "SDR1 Work Area");
                    put("assignment", "30_30_30");
                }}
        );
        deleteTimesheet(tdocId);

        createDailyOvertimeRule("REG", "OVT", "SD1", "BW", "TEST-DEPT", workArea, task, new BigDecimal(8), new BigDecimal("10.00"), null);
        tdocId = KPME788_789(
                new ArrayList<Map<String, Object>>() {{
                     add(new HashMap<String, Object>() {{
                         put("earnCode", "RGN");
                         put("hours", "5.0");
                     }});
                 }},
                 new HashMap<String, Object>() {{
                     put("earnCode", "RGN");
                     put("startNoTz", "2011-03-02T08:00:00");
                     put("endNoTz", "2011-03-02T13:00:00");
                     put("title", "SDR1 Work Area");
                     put("assignment", "30_30_30");
                 }},
                 new ArrayList<Map<String, Object>>() {{
                     add(new HashMap<String, Object>() {{
                         put("earnCode", "RGN");
                         put("hours", "3.0");
                         put("earnCode", "OVT");
                         put("hours", "2.0");
                     }});
                 }},
                 new HashMap<String, Object>() {{
                     put("earnCode", "RGN");
                     put("startNoTz", "2011-03-02T13:10:00");
                     put("endNoTz", "2011-03-02T18:10:00");
                     put("title", "SDR1 Work Area");
                     put("assignment", "30_30_30");
                 }}
        );
        deleteTimesheet(tdocId);
    }


    public void deleteTimesheet(String tdocId) throws Exception {
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.PORTAL_URL);
        Assert.assertNotNull(page);
        page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DELETE_TIMESHEET_URL + "?deleteDocumentId="+tdocId);
        HtmlUnitUtil.createTempFile(page, "Deleted");
    }

    public String KPME788_789(ArrayList<Map<String, Object>> tb1ThdItems, HashMap<String, Object> tb1Items, ArrayList<Map<String, Object>> tb2ThdItems, HashMap<String, Object> tb2Items) throws Exception {
        DateTime asOfDate = new DateTime(2011, 3, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        CalendarEntry pcd = TkServiceLocator.getCalendarService().getCurrentCalendarDates(USER_PRINCIPAL_ID, asOfDate);
        Assert.assertNotNull("No PayCalendarDates", pcd);

        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();
        HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), "admin", tdocId, true);
        Assert.assertNotNull(page);
        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        Assert.assertNotNull(form);

        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(), JAN_AS_OF_DATE.toLocalDate());
        Assignment assignment = assignments.get(0);

        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodesForTime(assignment, JAN_AS_OF_DATE.toLocalDate());
        EarnCode earnCode = earnCodes.get(0);
        Assert.assertEquals("There should be no existing time blocks.", 0, tdoc.getTimeBlocks().size());

        // 2. Set Timeblock Start and End time
        // 3/02/2011 - 8:00a to 6:00pm
        // OVT - 0 Hrs Expected
        DateTime start = new DateTime(2011, 3, 2, 8, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime end = new DateTime(2011, 3, 2, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(tdoc, assignment, earnCode, start, end, null, false, null, true);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());
        page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), getTimesheetDocumentUrl(tdocId), tdaf);
        Assert.assertNotNull(page);

        start = new DateTime(2011, 3, 2, 13, 10, 0, 0, TKUtils.getSystemDateTimeZone());
        end = new DateTime(2011, 3, 2, 18, 10, 0, 0, TKUtils.getSystemDateTimeZone());
        tdaf = TimeDetailTestUtils.buildDetailActionForm(tdoc, assignment, earnCode, start, end, null, false, null, true);
        errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());
        page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), getTimesheetDocumentUrl(tdocId), tdaf);
        HtmlUnitUtil.createTempFile(page, "Hours");
        Assert.assertNotNull(page);

        // Verify block present on rendered page.
        String pageAsText = page.asText();
        // Grab the timeblock data from the text area. We can check specifics there
        // to be more fine grained in our validation.
        String dataText = page.getElementById("timeBlockString").getFirstChild().getNodeValue();
        JSONObject jsonData = (JSONObject) JSONValue.parse(dataText);
        Assert.assertTrue("TimeBlock #1 Data Missing.", checkJSONValues(jsonData, tb1ThdItems, tb1Items));
        Assert.assertTrue("TimeBlock #2 Data Missing.", checkJSONValues(jsonData, tb2ThdItems, tb2Items));

        return tdocId;
    }

    private DailyOvertimeRule createDailyOvertimeRule(String fromEarnGroup, String earnCode, String location, String paytype, String dept, Long workArea, Long task, BigDecimal minHours, BigDecimal maxGap, String overtimePref) {
        DailyOvertimeRule rule = new DailyOvertimeRule();
        rule.setEffectiveLocalDate(JAN_AS_OF_DATE.toLocalDate());
        rule.setFromEarnGroup(fromEarnGroup);
        rule.setEarnCode(earnCode);
        rule.setLocation(location);
        rule.setPaytype(paytype);
        rule.setDept(dept);
        rule.setWorkArea(workArea);
        rule.setMaxGap(maxGap);
        rule.setMinHours(minHours);
        rule.setActive(true);
        TkServiceLocator.getDailyOvertimeRuleService().saveOrUpdate(rule);
        return rule;
    }
}
