package org.kuali.hr.time.roles.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetWebTestBase;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.web.util.HtmlUtils;

/**
 * See: https://wiki.kuali.org/display/KPME/Role+Security+Grid
 */
public class RoleTimesheetWebIntegrationTest extends TimesheetWebTestBase {

    private static final Logger LOG = Logger.getLogger(RoleTimesheetWebIntegrationTest.class);

    // Non Time Entry users (for this test) who have some access to 'fred's'
    // Time Sheet.
    private List<String> VALID_NON_ENTRY_USERS = new ArrayList<String>() {{
        add("testuser6"); add("frank"); add("fran"); add("edna"); }};

    // Users with incorrect Department or Work Area for Time Sheet privilege.
    private List<String> INVALID_NON_ENTRY_USERS = new ArrayList<String>(){{
        add("testuser1"); add("testuser2"); add("testuser3"); add("testuser4"); }};

    private TimesheetDocument fredsDocument = null;

    @Override
    /**
     * This code is called before each test below - allows us to control how far
     * into the routing process we are for any given level of testing.
     */
    public void setUp() throws Exception {
        super.setUp();

        String userId = "fred";
        Date asOfDate = new Date((new DateTime(2011, 3, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
        CalendarEntries pcd = TkServiceLocator.getCalendarSerivce().getCurrentCalendarDates(userId, asOfDate);
        assertNotNull("No PayCalendarDates", pcd);
        fredsDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(userId, pcd);
        String tdocId = fredsDocument.getDocumentId();

        // Verify the non time entry logins.
        verifyLogins(fredsDocument);

        // Verify Fred, and Add Timeblocks
        HtmlPage page = loginAndGetTimeDetailsHtmlPage(userId, tdocId, true);
        assertTrue("Calendar not loaded.", page.asText().contains("March 2011"));

        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        assertNotNull(form);
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(userId, JAN_AS_OF_DATE);
        Assignment assignment = assignments.get(0);
        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, JAN_AS_OF_DATE);
        EarnCode earnCode = earnCodes.get(0);
        assertEquals("There should be no existing time blocks.", 0, fredsDocument.getTimeBlocks().size());

        // 2. Set Timeblock Start and End time
        // 3/02/2011 - 8:00a to 6:00pm
        // OVT - 0 Hrs Expected
        DateTime start = new DateTime(2011, 3, 2, 8, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime end = new DateTime(2011, 3, 2, 13, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(fredsDocument, assignment, earnCode, start, end, null, false, null);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        assertEquals("There should be no errors in this time detail submission", 0, errors.size());
        page = TimeDetailTestUtils.submitTimeDetails(getTimesheetDocumentUrl(tdocId), tdaf);
        assertNotNull(page);
    }

    /*
     * Tests while Timesheet is in INITIATED state.
     */

    @Test
    public void testInitiatedTimesheetIsVisibleByAll() throws Exception {
        // test valid users
        for (String uid : VALID_NON_ENTRY_USERS) {
            LOG.info("Testing visibility for " + uid);
            HtmlPage page = loginAndGetTimeDetailsHtmlPage(uid, fredsDocument.getDocumentId(), true);
            assertTrue("Calendar not loaded.", page.asText().contains("March 2011"));
        }
    }

    @Test
    public void testInitiatedTimesheetIsNotVisible() throws Exception {
        for (String uid : INVALID_NON_ENTRY_USERS) {
            LOG.info("Testing visibility for " + uid);
            HtmlPage page = loginAndGetTimeDetailsHtmlPage(uid, fredsDocument.getDocumentId(), false);
            //HtmlUnitUtil.createTempFile(page, "badlogin");
            assertTrue("Should not have access", page.asText().contains("You are not authorized to access this portion of the application."));
        }
    }

    @Test
    public void testInitiatedTimesheetIsEditableByAdmin() throws Exception {
        // admin, add one timeblock
        String userId = "admin";
        String tdocId = fredsDocument.getDocumentId();
        HtmlPage page = loginAndGetTimeDetailsHtmlPage(userId, tdocId, true);
        HtmlUnitUtil.createTempFile(page, "loggedin");
        assertTrue("Calendar not loaded.", page.asText().contains("March 2011"));

        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        assertNotNull(form);
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments("fred", JAN_AS_OF_DATE);
        Assignment assignment = assignments.get(0);
        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, JAN_AS_OF_DATE);
        EarnCode earnCode = earnCodes.get(0);

        // TODO: Why is this not returning 1
        //assertEquals("There should be one existing time block.", 1, fredsDocument.getTimeBlocks().size());

        DateTime start = new DateTime(2011, 3, 4, 8, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime end = new DateTime(2011, 3, 4, 13, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(fredsDocument, assignment, earnCode, start, end, null, false, null);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);
        assertEquals("There should be no errors in this time detail submission", 0, errors.size());
        page = TimeDetailTestUtils.submitTimeDetails(getTimesheetDocumentUrl(tdocId), tdaf);
        assertNotNull(page);
        HtmlUnitUtil.createTempFile(page, "initiatetest");

        // TODO: Fill out block verification
    }

    @Test
    public void testInitiatedTimesheetIsEditableByApprover() throws Exception {
        // approver, add one timeblock
    }

    @Test
    public void testInitiatedTimesheetIsEditableByReviewer() throws Exception {
        // reviewer add one timeblock.
    }

    @Test
    public void testInitiatedTimesheetIs_NOT_EditableByViewOnly() throws Exception {
    }

    @Test
    public void testInitiatedTimesheetIs_NOT_EditableByDeptAdmin() throws Exception {
    }


    @Test
    public void testInitiatedTimesheetSubmitUser() throws Exception {
        // User,
        // Check for submit button.
        // Click Button
    }

    @Test
    public void testInitiatedTimesheetSubmitAdmin() throws Exception {
        // Admin
        // Check for submit button.
        // Click Button
    }

    @Test
    public void testInitiatedTimesheetSubmitApprover() throws Exception {
        // Approver
        // Check for submit button.
        // Click Button
    }


    @Test
    public void testInitiatedTimesheetIs_NOT_SubmittableByUsers() throws Exception {
        // DeptAdmin, View Only, Reviewer
        // Check that submit button is not present.
    }

    /*
     * Test for ENROUTE state.
     */

    @Test
    public void testEnrouteTimesheetIsVisibleByAll() throws Exception {
        // test valid users
    }

    @Test
    public void testEnrouteTimesheetIsNotVisible() throws Exception {
        // make sure invalid users do not have access
    }

    @Test
    public void testEnrouteTimesheetIsEditableByAdmin() throws Exception {
        // admin, add one timeblock
    }

    @Test
    public void testEnrouteTimesheetIsEditableByApprover() throws Exception {
        // approver, add one timeblock
    }

    @Test
    public void testEnrouteTimesheetIsEditableByReviewer() throws Exception {
        // reviewer add one timeblock.
    }

    @Test
    public void testEnrouteTimesheetIs_NOT_EditableByViewOnly() throws Exception {
    }

    @Test
    public void testEnrouteTimesheetIs_NOT_EditableByDeptAdmin() throws Exception {
    }


    @Test
    public void testEnrouteTimesheet_NOT_Approvable() throws Exception {
        // User, Reviewer, View Only, Dept Admin
        // Check for approve button
    }

    @Test
    public void testEnrouteTimesheetApproveAdmin() throws Exception {
        // Admin
        // Check for approve button.
        // Click Button
    }

    @Test
    public void testEnrouteTimesheetApproveApprover() throws Exception {
        // Approver
        // Check for approve button.
        // Click Button
    }

    @Test
    public void testEnrouteTimesheetIs_NOT_SubmittableByUsers() throws Exception {
        // DeptAdmin, View Only, Reviewer
        // Check that submit button is not present.
    }


    /*
     * Final State
     */

    @Test
    public void testFinalTimesheetIsVisibleByAll() throws Exception {
        // test valid users
    }

    @Test
    public void testFinalTimesheetIsNotVisible() throws Exception {
        // make sure invalid users do not have access
    }

    @Test
    public void testFinalTimesheetIsNotEditable() throws Exception {
        // by everyone but admin
    }

    @Test
    public void testFinalTimesheetIsAdminEditable() throws Exception {
        // admin, add timeblock.
    }

    /**
     * Verifies that each admin/approver/reviewer login is active for this test.
     */
    private void verifyLogins(TimesheetDocument tdoc) throws Exception {
        for (String userId : VALID_NON_ENTRY_USERS) {
            String tdocId = tdoc.getDocumentId();
            HtmlPage page = loginAndGetTimeDetailsHtmlPage(userId, tdocId, true);
            assertTrue("Calendar not loaded.", page.asText().contains("March 2011"));
        }
    }

}