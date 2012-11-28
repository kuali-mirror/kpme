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
package org.kuali.hr.lm.leaveCalendar;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leave.web.LeaveCalendarWSForm;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.util.LeaveCalendarTestUtils;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.util.filter.TestAutoLoginFilter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaveCalendarWorkflowIntegrationTest extends LeaveCalendarWebTestBase {

    public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());

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
    public void testLeaveCalendarSubmissionIntegration() throws Exception {
        Date asOfDate = new Date((new DateTime(2011, 3, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
        CalendarEntries pcd = TkServiceLocator.getCalendarService().getCurrentCalendarDatesForLeaveCalendar(USER_PRINCIPAL_ID, asOfDate);
        Assert.assertNotNull("No CalendarEntries", pcd);
        LeaveCalendarDocument tdoc = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();
        HtmlPage page = loginAndGetLeaveCalendarHtmlPage("admin", tdocId, true);

        // 1. Obtain User Data
        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(TKContext.getPrincipalId(), new AssignmentDescriptionKey("30_30_30"), JAN_AS_OF_DATE);
        EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode("VAC", JAN_AS_OF_DATE);

        // 2. Set Timeblock Start and End time
        // 3/02/2011 - 8:00a to 4:00pm
        DateTime start = new DateTime(2011, 3, 2, 8, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime end = new DateTime(2011, 3, 2, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone());

        HtmlForm form = page.getFormByName("LeaveCalendarForm");
        Assert.assertNotNull(form);

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        LeaveCalendarWSForm tdaf = LeaveCalendarTestUtils.buildLeaveCalendarForm(tdoc, assignment, earnCode, start, end, null, true);
        LeaveCalendarTestUtils.setTimeBlockFormDetails(form, tdaf);
        page = LeaveCalendarTestUtils.submitLeaveCalendar(getLeaveCalendarUrl(tdocId), tdaf);
        Assert.assertNotNull(page);
        HtmlUnitUtil.createTempFile(page, "LeaveBlockPresent");

        // Verify block present on rendered page.
        String pageAsText = page.asText();

        // JSON
        //
        //
        // Grab the leaveBlock data from the text area. We can check specifics there
        // to be more fine grained in our validation.
        String dataText = page.getElementById("leaveBlockString").getFirstChild().getNodeValue();
        JSONArray jsonData = (JSONArray)JSONValue.parse(dataText);
        final JSONObject jsonDataObject = (JSONObject) jsonData.get(0);
        Assert.assertTrue("leaveBlock Data Missing.", checkJSONValues(new JSONObject() {{ put("outer", jsonDataObject); }},
                new ArrayList<Map<String, Object>>() {{
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "VAC");
                        put("leaveAmount", "-8");
                        put("earnCode", "VAC");
                        put("leaveDate", "03/02/2011");
                        //put("endNoTz", "2011-03-02T16:00:00");
                        put("title", "SDR1 Work Area");
                        put("assignment", "30_30_30");
                    }});
                }},
                new HashMap<String, Object>() {{  }}
        ));

        // Check the Display Rendered Text for Time Block, Quick Check
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("SDR1 Work Area-SDR1 task"));
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("VAC (-8)"));

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
        page = loginAndGetLeaveCalendarHtmlPage("eric", tdocId, true);
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


    /**
     * Examines the JSON structure that is written to each output TimeDetails
     * page.
     * @param json The JSON Object to examine
     * @param thdList The (optional) list of Time Hour Details values
     * @param checkValues The list of values to check for in the JSON object
     * @return true if the JSON object contains the required values, false otherwise.
     */
    public static boolean checkJSONValues(JSONObject json, List<Map<String,Object>> thdList, Map<String,Object> checkValues) {
        boolean contains = false;

        for(Object eso : json.entrySet()) {
            // This is the outer TimeBlock ID -> Time Block Data mapping
            Map.Entry e = (Map.Entry)eso;
            JSONObject innerMap = (JSONObject)e.getValue(); // the values we actually care about.
            boolean localContains = true;
            for (Map.Entry<String,Object> cve : checkValues.entrySet()) {
                Object joValue = innerMap.get(cve.getKey());
                Object cvValue = cve.getValue();

                // Null Checks..
                if (cvValue == null && joValue == null)
                    localContains &= true;
                else if (joValue == null || cvValue == null)
                    localContains = false;
                else
                    localContains &= StringUtils.equals(joValue.toString(), cvValue.toString());
            }

            contains |= localContains;
        }

        return contains;
    }

    public static boolean checkJSONValues(String json, List<Map<String,Object>> thdList, Map<String,Object> checkValues) {
        return checkJSONValues((JSONObject)JSONValue.parse(json), thdList, checkValues);
    }
}
