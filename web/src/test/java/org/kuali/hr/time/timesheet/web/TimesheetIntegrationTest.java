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
package org.kuali.hr.time.timesheet.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.assignment.AssignmentDescriptionKey;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.core.earncode.EarnCode;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.tklm.time.detail.web.TimeDetailActionFormBase;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.util.TKUtils;

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

	/**
	 * @throws Exception
	 */
	public void setUp() throws Exception {

		super.setUp();

		payCal = TkServiceLocator.getCalendarService().getCurrentCalendarDates(
				USER_PRINCIPAL_ID, new DateTime(TIME_SHEET_DATE));
		Assert.assertNotNull("Pay calendar entries not found for admin", payCal);

		// retrieving time sheet for according to the pay calendar
		timeDoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(
				USER_PRINCIPAL_ID, payCal);
		tdocId = timeDoc.getDocumentId();

		// login
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), USER_PRINCIPAL_ID,
				tdocId, true);
		Assert.assertNotNull(page);

		assignmentsOfUser = TkServiceLocator.getAssignmentService()
				.getAssignments(USER_PRINCIPAL_ID, TIME_SHEET_DATE.toLocalDate());
		Assert.assertNotNull("No Assignments found for the user ", assignmentsOfUser);

		// check if page contains calendar for February 2011
		Assert.assertTrue("Page could not find calendar for Feb 2011", page.asText()
				.contains("February 2011"));

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testAddTimeBlock() throws Exception {
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(getWebClient(), USER_PRINCIPAL_ID, tdocId, true);

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		Assert.assertNotNull(form);

		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, new AssignmentDescriptionKey("4_1234_1"), TIME_SHEET_DATE.toLocalDate());
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode("RGN", TIME_SHEET_DATE.toLocalDate());

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

		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, new AssignmentDescriptionKey("4_1234_1"), TIME_SHEET_DATE.toLocalDate());
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode("RGN", TIME_SHEET_DATE.toLocalDate());

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

		HtmlUnitUtil.createTempFile(page);

		Assignment newAssignment = TkServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, new AssignmentDescriptionKey("1_1234_1"), TIME_SHEET_DATE.toLocalDate());

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

		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(USER_PRINCIPAL_ID, new AssignmentDescriptionKey("4_1234_1"), TIME_SHEET_DATE.toLocalDate());
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode("RGN", TIME_SHEET_DATE.toLocalDate());

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
		List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodesForTime(assToBeSelected, TIME_SHEET_DATE.toLocalDate());
		if (earnCodes != null && !earnCodes.isEmpty()) {
			earnCode = earnCodes.get(0);
		}

		DateTime startTime = new DateTime(2011, 2, 17, 9, 0, 0, 0,
				TKUtils.getSystemDateTimeZone());  // Friday
		DateTime endTime = new DateTime(2011, 2, 20, 11, 0, 0, 0,
				TKUtils.getSystemDateTimeZone());  // Monday

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
