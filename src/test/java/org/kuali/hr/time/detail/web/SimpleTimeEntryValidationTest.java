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

import java.sql.Date;
import java.util.List;

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
        assertEquals("There should be no errors in this time detail submission", 1, errors.size());
        assertEquals("Error String Unexpected", "The start date/time is outside the pay period", errors.get(0));

        // In other tests, when there are no errors:

        // Click Submit
        // TODO : Get the button from the form, and .click() it.

        // Check That results appear on the Page
        // TODO : Fill in this example.
    }
}
