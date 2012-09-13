package org.kuali.hr.time.timesheet.web;

import java.sql.Date;
import java.util.*;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.*;
import org.kuali.hr.time.web.TkLoginFilter;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.kuali.hr.util.filter.TestAutoLoginFilter;

public class TimesheetWorkflowIntegrationTest extends TimesheetWebTestBase {

    public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());


    /**
     * @throws Exception
     */
    public void setUp() throws Exception {
        super.setUp();
        // Data is loaded as part of database loading lifecycle
        // See: tk-test-data.sql
        // See: TimesheetWorkflowIntegrationTest.sql
        // See: TkTestCase.java
        //
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    /**
     * - create timesheet
     * - add two 8 hour time blocks
     * - submit timesheet for routing
     * - ## login as approver
     * - look for approval button
     * - approve timeblock
     * - verify approval button gone
     * - ## login as original user
     * - verify submit for routing button gone
     */
    public void testTimesheetSubmissionIntegration() throws Exception {
        Date asOfDate = new Date((new DateTime(2011, 3, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
        CalendarEntries pcd = TkServiceLocator.getCalendarService().getCurrentCalendarDates(USER_PRINCIPAL_ID, asOfDate);
        Assert.assertNotNull("No PayCalendarDates", pcd);
        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();
        HtmlPage page = loginAndGetTimeDetailsHtmlPage("admin", tdocId, true);

        // 1. Obtain User Data
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(), JAN_AS_OF_DATE);
        Assignment assignment = assignments.get(0);

        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodesForTime(assignment, JAN_AS_OF_DATE);
        EarnCode earnCode = earnCodes.get(0);

        // 2. Set Timeblock Start and End time
        // 3/02/2011 - 8:00a to 4:00pm
        DateTime start = new DateTime(2011, 3, 2, 8, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime end = new DateTime(2011, 3, 3, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone());

        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        Assert.assertNotNull(form);

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(tdoc, assignment, earnCode, start, end, null, true, null, true);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        // Check for errors
        Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());

        page = TimeDetailTestUtils.submitTimeDetails(TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), tdaf);
        Assert.assertNotNull(page);
        //HtmlUnitUtil.createTempFile(page, "TimeBlockPresent");

        // Verify block present on rendered page.
        String pageAsText = page.asText();

        // JSON
        //
        //
        // Grab the timeblock data from the text area. We can check specifics there
        // to be more fine grained in our validation.
        String dataText = page.getElementById("timeBlockString").getFirstChild().getNodeValue();
        JSONArray jsonData = (JSONArray)JSONValue.parse(dataText);
        final JSONObject jsonDataObject = (JSONObject) jsonData.get(0);
        Assert.assertTrue("TimeBlock Data Missing.", checkJSONValues(new JSONObject() {{ put("outer", jsonDataObject); }},
                new ArrayList<Map<String, Object>>() {{
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "RGN");
                        put("hours", "8.0");
                        put("amount", null);
                    }});
                }},
                new HashMap<String, Object>() {{
                    put("earnCode", "RGN");
                    put("startNoTz", "2011-03-02T08:00:00");
                    put("endNoTz", "2011-03-02T16:00:00");
                    put("title", "SDR1 Work Area");
                    put("assignment", "30_30_30");
                }}
        ));

        // Check the Display Rendered Text for Time Block, Quick Check
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("08:00 AM - 04:00 PM"));
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("RGN - 8.00 hours"));

        //
        // Route Timesheet
        //
        // Routing is initiated via javascript, we need to extract the routing
        // action from the button element to perform this action.
        HtmlElement routeButton = page.getElementById("ts-route-button");
        String routeHref = TkTestUtils.getOnClickHref(routeButton);
        // The 'only' way to do the button click.
        page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.BASE_URL + "/" + routeHref);
        //HtmlUnitUtil.createTempFile(page, "RouteClicked");
        pageAsText = page.asText();
        // Verify Route Status via UI
        Assert.assertTrue("Wrong Document Loaded.", pageAsText.contains(tdocId));
        Assert.assertTrue("Document not routed.", pageAsText.contains("Enroute"));
        routeButton = page.getElementById("ts-route-button");
        Assert.assertNull("Route button should not be present.", routeButton);
        HtmlElement approveButton = page.getElementById("ts-approve-button");
        Assert.assertNull("Approval button should not be present.", approveButton);

        //
        // Login as Approver, who is not 'admin'
        page = TimesheetWebTestBase.loginAndGetTimeDetailsHtmlPage("eric", tdocId, true);
        //HtmlUnitUtil.createTempFile(page, "2ndLogin");
        pageAsText = page.asText();
        Assert.assertTrue("Document not routed.", pageAsText.contains("Enroute"));
        approveButton = page.getElementById("ts-approve-button");
        Assert.assertNotNull("No approval button present.", approveButton);

        // Click Approve
        // And Verify
        //
        routeHref = TkTestUtils.getOnClickHref(approveButton);
        TestAutoLoginFilter.OVERRIDE_ID = "eric";
        page = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.BASE_URL + "/" + routeHref);
        TestAutoLoginFilter.OVERRIDE_ID = "";
        //HtmlUnitUtil.createTempFile(page, "ApproveClicked");
        pageAsText = page.asText();
        Assert.assertTrue("Wrong Document Loaded.", pageAsText.contains(tdocId));
        Assert.assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
        Assert.assertTrue("Login info not present.", pageAsText.contains("eric, eric"));
        Assert.assertTrue("Document not routed.", pageAsText.contains("Final"));
        approveButton = page.getElementById("ts-approve-button");
        Assert.assertNull("Approval button should not be present.", approveButton);
    }

}
