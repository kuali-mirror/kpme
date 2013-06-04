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
package org.kuali.hr.time.overtime.daily;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.time.workflow.TimesheetWebTestBase;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.detail.web.TimeDetailActionFormBase;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeWebIntegrationTest extends TimesheetWebTestBase {

    public static final String USER_PRINCIPAL_ID = "admin";
	private DateTime JAN_AS_OF_DATE = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());

    @Test
    /**
     * Tests daily overtime for:
     *
     * 3/2/2011 10hrs; 8 RGN, 2 OVT
     * 3/3/2011 10hrs; 8 RGN, 2 OVT
     */
    public void testSimpleDOTCalculationIntegration() throws Exception {
        DateTime asOfDate = new DateTime(2011, 3, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());

        CalendarEntry pcd = HrServiceLocator.getCalendarService().getCurrentCalendarDates(USER_PRINCIPAL_ID, asOfDate);
        Assert.assertNotNull("No PayCalendarDates", pcd);

        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();
        HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), "admin", tdocId,true);
        Assert.assertNotNull(page);
        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        Assert.assertNotNull(form);

        // 1. Obtain User Data
        Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(HrContext.getPrincipalId(), AssignmentDescriptionKey.get("30_30_30"), JAN_AS_OF_DATE.toLocalDate());
        EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode("RGN", JAN_AS_OF_DATE.toLocalDate());
        Assert.assertEquals("There should be no existing time blocks.", 0, tdoc.getTimeBlocks().size());

        // 2. Set Timeblock Start and End time
        // 3/02/2011 - 8:00a to 6:00pm
        // OVT - 2 Hrs Expected
        DateTime start = new DateTime(2011, 3, 2, 8, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime end = new DateTime(2011, 3, 3, 18, 0, 0, 0, TKUtils.getSystemDateTimeZone());

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(tdoc, assignment, earnCode, start, end, null, true, null, true);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        // Check for errors
        Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());

        page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), getTimesheetDocumentUrl(tdocId), tdaf);
        Assert.assertNotNull(page);
        //HtmlUnitUtil.createTempFile(page, "TimeBlockPresent");

        // Verify block present on rendered page.
        String pageAsText = page.asText();
        //HtmlUnitUtil.createTempFile(page, "Hours");
        // JSON
        //
        //
        // Grab the timeblock data from the text area. We can check specifics there
        // to be more fine grained in our validation.
        String dataText = page.getElementById("timeBlockString").getFirstChild().getNodeValue();
        JSONArray jsonData = (JSONArray) JSONValue.parse(dataText);
        final JSONObject jsonDataObject = (JSONObject) jsonData.get(0);
        Assert.assertTrue("TimeBlock #1 Data Missing.", checkJSONValues(new JSONObject() {{ put("outer", jsonDataObject); }},
                new ArrayList<Map<String, Object>>() {{
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "RGN");
                        put("hours", "8.0");
                    }});
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "OVT");
                        put("hours", "2.0");
                    }});
                }},
                new HashMap<String, Object>() {{
                    put("earnCode", "RGN");
                    put("startNoTz", "2011-03-02T08:00:00");
                    put("endNoTz", "2011-03-02T18:00:00");
                    put("title", "SDR1 Work Area");
                    put("assignment", "30_30_30");
                }}
        ));
        final JSONObject jsonDataObject2 = (JSONObject) jsonData.get(1);
        Assert.assertTrue("TimeBlock #2 Data Missing.", checkJSONValues(new JSONObject() {{ put("outer", jsonDataObject2); }},
                new ArrayList<Map<String, Object>>() {{
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "RGN");
                        put("hours", "8.0");
                    }});
                    add(new HashMap<String, Object>() {{
                        put("earnCode", "OVT");
                        put("hours", "2.0");
                    }});
                }},
                new HashMap<String, Object>() {{
                    put("earnCode", "RGN");
                    put("startNoTz", "2011-03-03T08:00:00");
                    put("endNoTz", "2011-03-03T18:00:00");
                    put("title", "SDR1 Work Area");
                    put("assignment", "30_30_30");
                }}
        ));


        // Check the Display Rendered Text for Time Block, Quick Check
        // Not as accurate as teh checkJSONValues tests above.
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("08:00 AM - 06:00 PM"));
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("RGN - 8.00 hours"));
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("OVT - 2.00 hours"));
    }

}
