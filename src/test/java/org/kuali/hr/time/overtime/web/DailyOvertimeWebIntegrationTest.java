package org.kuali.hr.time.overtime.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetWebTestBase;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DailyOvertimeWebIntegrationTest extends TimesheetWebTestBase {

    public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());

    @Test
    /**
     * Tests daily overtime for:
     *
     * 3/2/2011 10hrs; 8 RGN, 2 OVT
     * 3/3/2011 10hrs; 8 RGN, 2 OVT
     */
    public void testSimpleDOTCalculationIntegration() throws Exception {
        Date asOfDate = new Date((new DateTime(2011, 3, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());

        CalendarEntries pcd = TkServiceLocator.getCalendarService().getCurrentCalendarDates(USER_PRINCIPAL_ID, asOfDate);
        assertNotNull("No PayCalendarDates", pcd);

        TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();
        HtmlPage page = loginAndGetTimeDetailsHtmlPage("admin", tdocId,true);
        assertNotNull(page);
        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        assertNotNull(form);

        // 1. Obtain User Data
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(), JAN_AS_OF_DATE);
        Assignment assignment = assignments.get(0);
        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, JAN_AS_OF_DATE);
        EarnCode earnCode = earnCodes.get(0);
        assertEquals("There should be no existing time blocks.", 0, tdoc.getTimeBlocks().size());

        // 2. Set Timeblock Start and End time
        // 3/02/2011 - 8:00a to 6:00pm
        // OVT - 2 Hrs Expected
        DateTime start = new DateTime(2011, 3, 2, 8, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime end = new DateTime(2011, 3, 3, 18, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(tdoc, assignment, earnCode, start, end, null, true, null, true);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        // Check for errors
        assertEquals("There should be no errors in this time detail submission", 0, errors.size());

        page = TimeDetailTestUtils.submitTimeDetails(getTimesheetDocumentUrl(tdocId), tdaf);
        assertNotNull(page);
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
        assertTrue("TimeBlock #1 Data Missing.", checkJSONValues(new JSONObject() {{ put("outer", jsonDataObject); }},
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
        assertTrue("TimeBlock #2 Data Missing.", checkJSONValues(new JSONObject() {{ put("outer", jsonDataObject2); }},
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
        assertTrue("TimeBlock not Present.", pageAsText.contains("08:00 AM - 06:00 PM"));
        assertTrue("TimeBlock not Present.", pageAsText.contains("RGN - 8.00 hours"));
        assertTrue("TimeBlock not Present.", pageAsText.contains("OVT - 2.00 hours"));
    }

}
