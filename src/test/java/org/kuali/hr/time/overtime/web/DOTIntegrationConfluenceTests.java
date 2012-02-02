package org.kuali.hr.time.overtime.web;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetWebTestBase;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.time.util.TkConstants;

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
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ADMIN_URL);
        assertNotNull(page);
        page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ADMIN_URL + "?methodToCall=deleteTimesheet&deleteDocumentId="+tdocId);
        HtmlUnitUtil.createTempFile(page, "Deleted");
    }

    public String KPME788_789(ArrayList<Map<String, Object>> tb1ThdItems, HashMap<String, Object> tb1Items, ArrayList<Map<String, Object>> tb2ThdItems, HashMap<String, Object> tb2Items) throws Exception {
        Date asOfDate = new Date((new DateTime(2011, 3, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
        CalendarEntries pcd = TkServiceLocator.getCalendarSerivce().getCurrentCalendarDates(USER_PRINCIPAL_ID, asOfDate);
        assertNotNull("No PayCalendarDates", pcd);

        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();
        HtmlPage page = loginAndGetTimeDetailsHtmlPage("admin", tdocId, true);
        assertNotNull(page);
        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        assertNotNull(form);

        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(), JAN_AS_OF_DATE);
        Assignment assignment = assignments.get(0);
        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, JAN_AS_OF_DATE);
        EarnCode earnCode = earnCodes.get(0);
        assertEquals("There should be no existing time blocks.", 0, tdoc.getTimeBlocks().size());

        // 2. Set Timeblock Start and End time
        // 3/02/2011 - 8:00a to 6:00pm
        // OVT - 0 Hrs Expected
        DateTime start = new DateTime(2011, 3, 2, 8, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime end = new DateTime(2011, 3, 2, 13, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(tdoc, assignment, earnCode, start, end, null, false, null);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        assertEquals("There should be no errors in this time detail submission", 0, errors.size());
        page = TimeDetailTestUtils.submitTimeDetails(getTimesheetDocumentUrl(tdocId), tdaf);
        assertNotNull(page);

        start = new DateTime(2011, 3, 2, 13, 10, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        end = new DateTime(2011, 3, 2, 18, 10, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        tdaf = TimeDetailTestUtils.buildDetailActionForm(tdoc, assignment, earnCode, start, end, null, false, null);
        errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        assertEquals("There should be no errors in this time detail submission", 0, errors.size());
        page = TimeDetailTestUtils.submitTimeDetails(getTimesheetDocumentUrl(tdocId), tdaf);
        HtmlUnitUtil.createTempFile(page, "Hours");
        assertNotNull(page);

        // Verify block present on rendered page.
        String pageAsText = page.asText();
        // Grab the timeblock data from the text area. We can check specifics there
        // to be more fine grained in our validation.
        String dataText = page.getElementById("timeBlockString").getFirstChild().getNodeValue();
        JSONObject jsonData = (JSONObject) JSONValue.parse(dataText);
        assertTrue("TimeBlock #1 Data Missing.", checkJSONValues(jsonData, tb1ThdItems, tb1Items));
        assertTrue("TimeBlock #2 Data Missing.", checkJSONValues(jsonData, tb2ThdItems, tb2Items));

        return tdocId;
    }

    private DailyOvertimeRule createDailyOvertimeRule(String fromEarnGroup, String earnCode, String location, String paytype, String dept, Long workArea, Long task, BigDecimal minHours, BigDecimal maxGap, String overtimePref) {
        DailyOvertimeRule rule = new DailyOvertimeRule();
        rule.setEffectiveDate(JAN_AS_OF_DATE);
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
