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
package org.kuali.hr.time.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.earncode.service.EarnCodeService;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.detail.web.TimeDetailActionFormBase;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.service.TimesheetService;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

//@Ignore
public class TimesheetIntegrationTest extends TimesheetWebTestBase {

	public static final String USER_PRINCIPAL_ID = "admin";
	public static final DateTime TIME_SHEET_DATE = new DateTime(2011, 2, 15, 0, 0, 0, 0, DateTimeZone
					.forID(TKUtils.getSystemTimeZone()));
	public TimesheetDocument timeDoc;
	public List<Assignment> assignmentsOfUser;
	public CalendarEntry payCal;
	public String tdocId;

	public static final String TEST_USER = "admin";
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER = 1L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_2 = 2L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_3 = 3L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_4 = 4L;

	EarnCodeService earnCodeService = null;
	TimesheetService timesheetService = null;

	@Test
	public void getEarnCodes() throws Exception {
        LocalDate asOfDate = LocalDate.now();
		List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(TEST_USER, asOfDate);
		Assert.assertNotNull(assignments);
		Assert.assertTrue("Emtpy assignment list", !assignments.isEmpty());

		Assignment assignment1 = null;
		Assignment assignment2 = null;
		Assignment assignment3 = null;
		Assignment assignment4 = null;
		for (Assignment a : assignments) {
			if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER)) {
				assignment1 = a;
			} else if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER_2)) {
				assignment2 = a;
			} else if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER_3)) {
				assignment3 = a;
			} else if (a.getJobNumber().equals(TEST_ASSIGNMENT_JOB_NUMBER_4)) {
				assignment4 = a;
			}
		}

		// one for each test scenario involving wildcards at least...
		Assert.assertNotNull("Test assignment not found.", assignment1);
		Assert.assertNotNull("Test assignment not found.", assignment2);
		Assert.assertNotNull("Test assignment not found.", assignment3);
		Assert.assertNotNull("Test assignment not found.", assignment4);

        //  Testing getEarnCodes* - these routines are separated among Leave and Time calendars. Run both, then run a combined routine that may not get used in practice.
        //  As the testing data gets better, the Time and Leave results should have little to no overlap, and the assertions will need to be correspondingly updated.
        // Testing standard lookup.
		List<EarnCode> earnCodes1t = timesheetService.getEarnCodesForTime(assignment1, asOfDate);
		Assert.assertEquals("Wrong number of earn codes returned.", 7, earnCodes1t.size());
        List<EarnCode> earnCodes1l = earnCodeService.getEarnCodesForLeave(assignment1, asOfDate, false);
        Assert.assertEquals("Wrong number of earn codes returned.", 0, earnCodes1l.size());

        // Wildcard on SalaryGroup
        List<EarnCode> earnCodes2t = timesheetService.getEarnCodesForTime(assignment2, asOfDate);
		Assert.assertEquals("Wrong number of earn codes returned.", 2, earnCodes2t.size());
        List<EarnCode> earnCodes2l = earnCodeService.getEarnCodesForLeave(assignment2, asOfDate, false);
        Assert.assertEquals("Wrong number of earn codes returned.", 0, earnCodes2l.size());

        // Dual Wildcards
        List<EarnCode> earnCodes3t = timesheetService.getEarnCodesForTime(assignment3, asOfDate);
		Assert.assertEquals("Wrong number of earn codes returned.",1, earnCodes3t.size());
        List<EarnCode> earnCodes3l = earnCodeService.getEarnCodesForLeave(assignment3, asOfDate, false);
        Assert.assertEquals("Wrong number of earn codes returned.",0, earnCodes3l.size());
    }
	
	/**
	 * @throws Exception
	 */
	public void setUp() throws Exception {

		super.setUp();

		payCal = HrServiceLocator.getCalendarService().getCurrentCalendarDates(
				USER_PRINCIPAL_ID, TIME_SHEET_DATE);
		Assert.assertNotNull("Pay calendar entries not found for admin", payCal);

		// retrieving time sheet for according to the pay calendar
		timeDoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(
				USER_PRINCIPAL_ID, payCal);
		tdocId = timeDoc.getDocumentId();

		// login
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), USER_PRINCIPAL_ID,
				tdocId, true);
		Assert.assertNotNull(page);

		assignmentsOfUser = HrServiceLocator.getAssignmentService()
				.getAssignments(USER_PRINCIPAL_ID, TIME_SHEET_DATE.toLocalDate());
		Assert.assertNotNull("No Assignments found for the user ", assignmentsOfUser);

		// check if page contains calendar for February 2011
		Assert.assertTrue("Page could not find calendar for Feb 2011", page.asText()
				.contains("February 2011"));

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);
		
		timesheetService = TkServiceLocator.getTimesheetService();
		earnCodeService = HrServiceLocator.getEarnCodeService();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testAddTimeBlock() throws Exception {
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), USER_PRINCIPAL_ID, tdocId, true);

		Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, AssignmentDescriptionKey.get("4_1234_1"), TIME_SHEET_DATE.toLocalDate());
		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);

		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode("RGN", TIME_SHEET_DATE.toLocalDate());

		DateTime startTime = new DateTime(2011, 2, 15, 9, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endTime = new DateTime(2011, 2, 15, 11, 0, 0, 0, TKUtils.getSystemDateTimeZone());

		// Setup TimeDetailActionForm
		TimeDetailActionFormBase addTB = TimeDetailTestUtils.buildDetailActionForm(timeDoc, assignment, earnCode, startTime, endTime, null, true, null, true);
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, addTB);

		// Check for errors
		Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());

		// submit the details of Timeblock to be added.
		page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), addTB);
		Assert.assertNotNull(page);

		// get Timeblocks objects from Timeblock string
		String dataText = page.getElementById("timeBlockString").getFirstChild().getNodeValue();

		// check the values in timeblockString
		JSONArray jsonData = (JSONArray) JSONValue.parse(dataText);
		final JSONObject jsonDataObject = (JSONObject) jsonData.get(0);
		final String assignmentKey = assignment.getAssignmentKey();
		Assert.assertTrue("TimeBlock Data Missing.", checkJSONValues(new JSONObject() {
			{
				put("outer", jsonDataObject);
			}
		}, new ArrayList<Map<String, Object>>() {
			{
				add(new HashMap<String, Object>() {
					{
						put("earnCode", "RGN");
						put("hours", "2.0");
						put("amount", null);
					}
				});
			}
		}, new HashMap<String, Object>() {
			{
				put("earnCode", "RGN");
				put("startNoTz", "2011-02-15T09:00:00");
				put("endNoTz", "2011-02-15T11:00:00");
				put("assignment", assignmentKey);
			}
		}));


		// check value ....
		Assert.assertTrue("TimeBlock did not created successfully.", page.asText()
				.contains("work area description-description 1"));
		Assert.assertTrue("TimeBlock did not created successfully.", page.asText()
				.contains("RGN - 2.00 hours"));

	}

	@Test
	public void testEditTimeBlock() throws Exception {
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), USER_PRINCIPAL_ID, tdocId, true);

		Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, AssignmentDescriptionKey.get("4_1234_1"), TIME_SHEET_DATE.toLocalDate());
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode("RGN", TIME_SHEET_DATE.toLocalDate());

		DateTime startTime = new DateTime(2011, 2, 15, 9, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endTime = new DateTime(2011, 2, 15, 11, 0, 0, 0, TKUtils.getSystemDateTimeZone());

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);

		// Setup TimeDetailActionForm for adding time block
		TimeDetailActionFormBase addTB = TimeDetailTestUtils.buildDetailActionForm(timeDoc, assignment, earnCode, startTime, endTime, null, true, null, true);
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, addTB);

		// Check for errors
		Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());

		page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), addTB);
		Assert.assertNotNull(page);

		// chk if the page contains the created time block.
		Assert.assertTrue("TimeBlock not Present.", page.asText().contains("work area description-description 1"));
		Assert.assertTrue("TimeBlock not Present.", page.asText().contains("RGN - 2.00 hours"));

		// now updating the time block
		timeDoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, payCal);

		String createdTBId = timeDoc.getTimeBlocks().get(0).getTkTimeBlockId();

		Assignment newAssignment = HrServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, AssignmentDescriptionKey.get("1_1234_1"), TIME_SHEET_DATE.toLocalDate());
		HtmlUnitUtil.createTempFile(page);


		DateTime startTime1 = new DateTime(2011, 2, 15, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endTime1 = new DateTime(2011, 2, 15, 17, 0, 0, 0, TKUtils.getSystemDateTimeZone());

		form = page.getFormByName("TimeDetailActionForm");

		TimeDetailActionFormBase updateTB = TimeDetailTestUtils.buildDetailActionForm(timeDoc, newAssignment, earnCode, startTime1, endTime1, null, true, createdTBId, true);

		// validation of time block
		errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, updateTB);
		Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());

		// update it
		page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), updateTB);
		Assert.assertNotNull(page);

		// chk the timesheet contains the changes done with the time block
		Assert.assertTrue("TimeBlock did not updated properly.", page.asText().contains("work area description-description 1"));
		Assert.assertTrue("TimeBlock did not updated properly.", page.asText().contains("RGN - 3.00 hours"));

	}

	@Test
	public void testDeleteTimeBlock() throws Exception {
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), USER_PRINCIPAL_ID,tdocId, true);

		Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, AssignmentDescriptionKey.get("4_1234_1"), TIME_SHEET_DATE.toLocalDate());
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode("RGN", TIME_SHEET_DATE.toLocalDate());

		DateTime startTime = new DateTime(2011, 2, 15, 9, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endTime = new DateTime(2011, 2, 15, 11, 0, 0, 0, TKUtils.getSystemDateTimeZone());

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);

		// Setup TimeDetailActionForm
		TimeDetailActionFormBase addTB = TimeDetailTestUtils.buildDetailActionForm(timeDoc, assignment, earnCode, startTime, endTime, null, true, null, true);
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, addTB);

		// Check for errors
		Assert.assertEquals("There should be no errors in this time detail submission", 0, errors.size());

		page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), addTB);
		Assert.assertNotNull(page);

		// chk the page must contain the created time block
		Assert.assertTrue("TimeBlock did not created successfully.", page.asText().contains("work area description-description 1"));
		Assert.assertTrue("TimeBlock did not created successfully.", page.asText().contains("RGN - 2.00 hours"));

		timeDoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(USER_PRINCIPAL_ID, payCal);

		// Delete the timeblock
		String createTBId = timeDoc.getTimeBlocks().get(0).getTkTimeBlockId();
		HtmlUnitUtil.createTempFile(page);
		form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);

		// set detail for deleting time block
		TimeDetailActionFormBase deleteTB = TimeDetailTestUtils.buildDetailActionForm(timeDoc, assignment, earnCode, startTime, endTime, null, true, createTBId, true);
		deleteTB.setMethodToCall("deleteTimeBlock");

		// submitting the page
		page = TimeDetailTestUtils.submitTimeDetails(getWebClient(), TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), deleteTB);
		Assert.assertNotNull(page);

		// chk the timesheet does not contain the time block
		Assert.assertTrue("TimeBlock did not deleted successfully.", !page.asText().contains("work area description-description 1"));
		Assert.assertTrue("TimeBlock did not deleted successfully.", !page.asText().contains("RGN - 2.00 hours"));
	}
	
	// KPME-1446
	@Test
	public void testValidateTimeBlock() throws Exception {

		EarnCode earnCode = null;
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), USER_PRINCIPAL_ID,
				tdocId, true);

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);

		// Assignment of user
		Assignment assToBeSelected = assignmentsOfUser.get(4);

        // retrieving earncode for the assignment
		List<EarnCode> earnCodes = TkServiceLocator.getTimesheetService().getEarnCodesForTime(assToBeSelected, TIME_SHEET_DATE.toLocalDate());
		if (earnCodes != null && !earnCodes.isEmpty()) {
			earnCode = earnCodes.get(0);
		}

		DateTime startTime = new DateTime(2011, 2, 17, 9, 0, 0, 0,
				TKUtils.getSystemDateTimeZone());  // Thursday
		DateTime endTime = new DateTime(2011, 2, 20, 11, 0, 0, 0,
				TKUtils.getSystemDateTimeZone());  // Sunday

		// Setup TimeDetailActionForm1
		TimeDetailActionFormBase addTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, null, true); // last argument true = include weekends
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form,
				addTB);
		// Check for errors - spanning weeks includes weekends, and include weekends box is checked - should give no error
		Assert.assertEquals(
				"There should no error in this time detail submission", 0,
				errors.size());

		startTime = new DateTime(2011, 2, 19, 9, 0, 0, 0,
				TKUtils.getSystemDateTimeZone());  // Saturday
		endTime = new DateTime(2011, 2, 20, 11, 0, 0, 0,
				TKUtils.getSystemDateTimeZone());  // Sunday

		// Setup TimeDetailActionForm2
		addTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, null, false); // last argument false = do not include weekends
		errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, addTB);
		// Check for errors - spanning weeks includes weekends, and include weekends box is not not checked - should give an error
		Assert.assertEquals(
				"There should an error in this time detail submission", 1,
				errors.size());
	}

}
