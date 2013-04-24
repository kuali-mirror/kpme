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
package org.kuali.hr.time.detail.web;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.assignment.AssignmentDescriptionKey;
import org.kuali.hr.core.earncode.EarnCode;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.tklm.time.calendar.CalendarEntry;
import org.kuali.hr.tklm.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.util.*;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Test data should contain:
 *
 * insert into tk_document_header_t values ('2', 'admin', '2011-02-01 00:00:00', 'I', '2011-01-15 00:00:00', NULL, '1');
 */
public class SimpleTimeEntryValidationTest extends KPMETestCase {

    public static final String USER_PRINCIPAL_ID = "admin";
	private DateTime JAN_AS_OF_DATE = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	private String documentId;
	
    @Override
    public void setUp() throws Exception {
        super.setUp();

        CalendarEntry calendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry("5000");
        TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, calendarEntry);
        documentId = timesheetDocument.getDocumentId();
    }
    
    @Test
    /**
     * Example test to demonstrate adding time blocks to a time sheet using
     * HTMLUnit rather than JS calls.
     */
    public void testExample1ShouldBeValidationErrors() throws Exception {
        String baseUrl = TkTestConstants.Urls.TIME_DETAIL_URL + "?documentId=" + documentId;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
        Assert.assertNotNull(page);
        String pageAsText = page.asText();
        
        Assert.assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
        Assert.assertTrue("Login info not present.", pageAsText.contains("admin, admin"));

        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        Assert.assertNotNull(form);

        // 1. Obtain User Data
        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, new AssignmentDescriptionKey("30_30_30"), JAN_AS_OF_DATE.toLocalDate());
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode("RGN", JAN_AS_OF_DATE.toLocalDate());

        TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);

        // 2. Set Timeblock Start and End time
        // Note - in this case, we're setting time that is outside of the valid
        // pay period for document 2.
        DateTime start = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime end = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(timesheetDocument, assignment, earnCode, start, end, null, false, null, true);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);

        // Check for errors
        Assert.assertEquals("There should be 1 error in this time detail submission", 1, errors.size());
        Assert.assertEquals("Error String Unexpected", "The start date/time is outside the pay period", errors.get(0));
    }


    @Test
    /**
     * Example test to demonstrate adding time blocks to a time sheet using
     * HTMLUnit rather than JS calls.
     */
    public void testExample2AddValidTimeBlock() throws Exception {
        String baseUrl = TkTestConstants.Urls.TIME_DETAIL_URL + "?documentId=" + documentId;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
        Assert.assertNotNull(page);
        //HtmlUnitUtil.createTempFile(page, "SimpleTimeEntry");
        String pageAsText = page.asText();
        Assert.assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
        Assert.assertTrue("Login info not present.", pageAsText.contains("admin, admin"));

        HtmlForm form = page.getFormByName("TimeDetailActionForm");
        Assert.assertNotNull(form);

        // 1. Obtain User Data
        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, new AssignmentDescriptionKey("30_30_30"), JAN_AS_OF_DATE.toLocalDate());
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode("RGN", JAN_AS_OF_DATE.toLocalDate());

        TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);

        // 2. Set Timeblock Start and End time
        // 1/18/2011 - 8a to 10a
        DateTime start = new DateTime(2011, 1, 18, 8, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime end = new DateTime(2011, 1, 18, 10, 0, 0, 0, TKUtils.getSystemDateTimeZone());

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        TimeDetailActionFormBase tdaf = TimeDetailTestUtils.buildDetailActionForm(timesheetDocument, assignment, earnCode, start, end, null, false, null, true);
        List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, tdaf);

        // Check for errors
        Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());

        // Submit the Form to the Page.
        // Note - This currently uses a less than desirable method to accomplish this...
        page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), baseUrl, tdaf);
        Assert.assertNotNull(page);
        //HtmlUnitUtil.createTempFile(page, "TimeBlockPresent");

        // Verify block present on rendered page.
        pageAsText = page.asText();
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("08:00 AM - 10:00 AM"));
        Assert.assertTrue("TimeBlock not Present.", pageAsText.contains("RGN - 2.00 hours"));
    }


}
