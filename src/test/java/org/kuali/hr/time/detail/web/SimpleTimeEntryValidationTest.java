package org.kuali.hr.time.detail.web;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.time.util.TkConstants;
import org.springframework.web.util.HtmlUtils;

import java.sql.Date;
import java.util.List;

/**
 * Test data should contain:
 *
 * insert into tk_document_header_t values ('2', 'admin', '2011-02-01 00:00:00', 'I', '2011-01-15 00:00:00', NULL, '1');
 */
public class SimpleTimeEntryValidationTest extends TkTestCase {

    public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());

    @Test
    /**
     * Example test to demonstrate adding time blocks to a time sheet using
     * HTMLUnit rather than JS calls.
     */
    public void testExample1ShouldBeValidationErrors() throws Exception {
        // Load document 2 -- an initiated document 1/15 - 2/01 // 2011
        String tdocId = "2"; // The timesheet to open.
        String baseUrl = TkTestConstants.Urls.TIME_DETAIL_URL + "?documentId=" + tdocId;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
        assertNotNull(page);
        String pageAsText = page.asText();
        
        assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
        assertTrue("Login info not present.", pageAsText.contains("admin, admin"));

        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        assertNotNull(form);

        // 1. Obtain User Data
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(), JAN_AS_OF_DATE);
        Assignment assignment = assignments.get(0);
        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, JAN_AS_OF_DATE);
        EarnCode earnCode = earnCodes.get(0);


        TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdocId);

        // 2. Set Timeblock Start and End time
        // Note - in this case, we're setting time that is outside of the valid
        // pay period for document 2.
        DateTime start = new DateTime(2010, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime end = new DateTime(2010, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(timesheetDocument, assignment, earnCode, start, end, null, false, null);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);

        // Check for errors
        assertEquals("There should be 1 error in this time detail submission", 1, errors.size());
        assertEquals("Error String Unexpected", "The start date/time is outside the pay period", errors.get(0));
    }


    @Test
    /**
     * Example test to demonstrate adding time blocks to a time sheet using
     * HTMLUnit rather than JS calls.
     */
    public void testExample2AddValidTimeBlock() throws Exception {
        // Load document 2 -- an initiated document 1/15 - 2/01 // 2011
        String tdocId = "2"; // The timesheet to open.
        String baseUrl = TkTestConstants.Urls.TIME_DETAIL_URL + "?documentId=" + tdocId;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
        assertNotNull(page);
        //HtmlUnitUtil.createTempFile(page, "SimpleTimeEntry");
        String pageAsText = page.asText();
        assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
        assertTrue("Login info not present.", pageAsText.contains("admin, admin"));

        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        assertNotNull(form);

        // 1. Obtain User Data
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(), JAN_AS_OF_DATE);
        Assignment assignment = assignments.get(0);
        List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, JAN_AS_OF_DATE);
        EarnCode earnCode = earnCodes.get(0);

        TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdocId);

        // 2. Set Timeblock Start and End time
        // 1/18/2011 - 8a to 10a
        DateTime start = new DateTime(2011, 1, 18, 8, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime end = new DateTime(2011, 1, 18, 10, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE);

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(timesheetDocument, assignment, earnCode, start, end, null, false, null);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);

        // Check for errors
        assertEquals("There should be no errors in this time detail submission", 0, errors.size());

        // Submit the Form to the Page.
        // Note - This currently uses a less than desirable method to accomplish this...
        page = TimeDetailTestUtils.submitTimeDetails(baseUrl, tdaf);
        assertNotNull(page);
        //HtmlUnitUtil.createTempFile(page, "TimeBlockPresent");

        // Verify block present on rendered page.
        pageAsText = page.asText();
        assertTrue("TimeBlock not Present.", pageAsText.contains("08:00 AM - 10:00 AM"));
        assertTrue("TimeBlock not Present.", pageAsText.contains("RGN - 2.00 hours"));
    }


}
