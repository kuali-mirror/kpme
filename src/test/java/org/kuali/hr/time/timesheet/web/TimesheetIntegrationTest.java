package org.kuali.hr.time.timesheet.web;

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
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TimeDetailTestUtils;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

//@Ignore
public class TimesheetIntegrationTest extends TimesheetWebTestBase {

	public static final String USER_PRINCIPAL_ID = "admin";
	public static final Date TIME_SHEET_DATE = new Date(
			(new DateTime(2012, 2, 15, 0, 0, 0, 0, DateTimeZone
					.forID(TkConstants.SYSTEM_TIME_ZONE))).getMillis());
	public TimesheetDocument timeDoc;
	public List<Assignment> assignmentsOfUser;
	public CalendarEntries payCal;
	public String tdocId;

	/**
	 * @throws Exception
	 */
	public void setUp() throws Exception {

		super.setUp();

		payCal = TkServiceLocator.getCalendarService().getCurrentCalendarDates(
				USER_PRINCIPAL_ID, TIME_SHEET_DATE);
		assertNotNull("Pay calendar entries not found for admin", payCal);

		// retrieving time sheet for according to the pay calendar
		timeDoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(
				USER_PRINCIPAL_ID, payCal);
		tdocId = timeDoc.getDocumentId();

		// login
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(USER_PRINCIPAL_ID,
				tdocId, true);
		assertNotNull(page);

		assignmentsOfUser = TkServiceLocator.getAssignmentService()
				.getAssignments(USER_PRINCIPAL_ID, TIME_SHEET_DATE);
		assertNotNull("No Assignments found for the user ", assignmentsOfUser);

		// check if page contains calendar for February 2012
		assertTrue("Page could not find calendar for Feb 2012", page.asText()
				.contains("February 2012"));

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		assertNotNull(form);
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testAddTimeBlock() throws Exception {

		EarnCode earnCode = null;
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(USER_PRINCIPAL_ID,
				tdocId, true);

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		assertNotNull(form);

		// Assignment of user
		Assignment assToBeSelected = assignmentsOfUser.get(4);

		// retrieving earncode for the assignment
		List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService()
				.getEarnCodes(assToBeSelected, TIME_SHEET_DATE);
		if (earnCodes != null && !earnCodes.isEmpty()) {
			earnCode = earnCodes.get(0);
		}

		DateTime startTime = new DateTime(2012, 2, 15, 9, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime endTime = new DateTime(2012, 2, 15, 11, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);

		// Setup TimeDetailActionForm
		TimeDetailActionFormBase addTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, null, true);
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form,
				addTB);

		// Check for errors
		assertEquals(
				"There should be no errors in this time detail submission", 0,
				errors.size());

		// submit the details of Timeblock to be added.
		page = TimeDetailTestUtils.submitTimeDetails(
				TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), addTB);
		assertNotNull(page);

		// get Timeblocks objects from Timeblock string
		String dataText = page.getElementById("timeBlockString")
				.getFirstChild().getNodeValue();

		// check the values in timeblockString
		JSONArray jsonData = (JSONArray) JSONValue.parse(dataText);
		final JSONObject jsonDataObject = (JSONObject) jsonData.get(0);
		final String assignmentKey = assToBeSelected.getAssignmentKey();
		assertTrue("TimeBlock Data Missing.", checkJSONValues(new JSONObject() {
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
				put("startNoTz", "2012-02-15T09:00:00");
				put("endNoTz", "2012-02-15T11:00:00");
				put("assignment", assignmentKey);
			}
		}));


		// check value ....
		assertTrue("TimeBlock did not created successfully.", page.asText()
				.contains("09:00 AM - 11:00 AM"));
		assertTrue("TimeBlock did not created successfully.", page.asText()
				.contains("RGN - 2.00 hours"));

	}

	@Test
	public void testEditTimeBlock() throws Exception {
		EarnCode earnCode = null;

		// login
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(USER_PRINCIPAL_ID,
				tdocId, true);

		// Assignment
		Assignment assToBeSelected = assignmentsOfUser.get(4);

		// retrieving earncode for the assignment
		List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService()
				.getEarnCodes(assToBeSelected, TIME_SHEET_DATE);
		if (earnCodes != null && !earnCodes.isEmpty()) {
			earnCode = earnCodes.get(0);
		}

		DateTime startTime = new DateTime(2012, 2, 15, 9, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime endTime = new DateTime(2012, 2, 15, 11, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		assertNotNull(form);

		// Setup TimeDetailActionForm for adding time block
		TimeDetailActionFormBase addTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, null, true);
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form,
				addTB);

		// Check for errors
		assertEquals(
				"There should be no errors in this time detail submission", 0,
				errors.size());

		page = TimeDetailTestUtils.submitTimeDetails(
				TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), addTB);
		assertNotNull(page);

		// chk if the page contains the created time block.
		assertTrue("TimeBlock not Present.",
				page.asText().contains("09:00 AM - 11:00 AM"));
		assertTrue("TimeBlock not Present.",
				page.asText().contains("RGN - 2.00 hours"));

		// now updating the time block
		timeDoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(
				USER_PRINCIPAL_ID, payCal);

		String createdTBId = timeDoc.getTimeBlocks().get(0).getTkTimeBlockId();

		HtmlUnitUtil.createTempFile(page);

		// change assignment and time
		assToBeSelected = assignmentsOfUser.get(3);

		// earn codes relatedd to the assignment
		earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(
				assToBeSelected, TIME_SHEET_DATE);
		if (earnCodes != null && !earnCodes.isEmpty()) {
			earnCode = earnCodes.get(0);
		}

		DateTime startTime1 = new DateTime(2012, 2, 15, 14, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime endTime1 = new DateTime(2012, 2, 15, 17, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);

		form = page.getFormByName("TimeDetailActionForm");

		TimeDetailActionFormBase updateTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime1, endTime1, null, true, createdTBId, true);

		// validation of time block
		errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, updateTB);
		assertEquals(
				"There should be no errors in this time detail submission", 0,
				errors.size());

		// update it
		page = TimeDetailTestUtils.submitTimeDetails(
				TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), updateTB);
		assertNotNull(page);

		// chk the timesheet contains the changes done with the time block
		assertTrue("TimeBlock did not updated properly.", page.asText()
				.contains("02:00 PM - 05:00 PM"));
		assertTrue("TimeBlock did not updated properly.", page.asText()
				.contains("RGN - 3.00 hours"));

	}

	@Test
	public void testDeleteTimeBlock() throws Exception {

		EarnCode earnCode = null;

		// login
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(USER_PRINCIPAL_ID,
				tdocId, true);

		// Assignment of user
		Assignment assToBeSelected = assignmentsOfUser.get(4);

		// retrieving earncode for the assignment
		List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService()
				.getEarnCodes(assToBeSelected, TIME_SHEET_DATE);
		if (earnCodes != null && !earnCodes.isEmpty()) {
			earnCode = earnCodes.get(0);
		}

		DateTime startTime = new DateTime(2012, 2, 15, 9, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime endTime = new DateTime(2012, 2, 15, 11, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		assertNotNull(form);

		// Setup TimeDetailActionForm
		TimeDetailActionFormBase addTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, null, true);
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form,
				addTB);

		// Check for errors
		assertEquals(
				"There should be no errors in this time detail submission", 0,
				errors.size());

		page = TimeDetailTestUtils.submitTimeDetails(
				TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), addTB);
		assertNotNull(page);

		// chk the page must contain the created time block
		assertTrue("TimeBlock did not created successfully.", page.asText()
				.contains("09:00 AM - 11:00 AM"));
		assertTrue("TimeBlock did not created successfully.", page.asText()
				.contains("RGN - 2.00 hours"));

		timeDoc = TkServiceLocator.getTimesheetService().openTimesheetDocument(
				USER_PRINCIPAL_ID, payCal);

		// Delete the timeblock
		String createTBId = timeDoc.getTimeBlocks().get(0).getTkTimeBlockId();
		HtmlUnitUtil.createTempFile(page);
		form = page.getFormByName("TimeDetailActionForm");
		assertNotNull(form);

		// set detail for deleting time block
		TimeDetailActionFormBase deleteTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, createTBId, true);
		deleteTB.setMethodToCall("deleteTimeBlock");

		// submitting the page
		page = TimeDetailTestUtils.submitTimeDetails(
				TimesheetWebTestBase.getTimesheetDocumentUrl(tdocId), deleteTB);
		assertNotNull(page);

		// chk the timesheet does not contain the time block
		assertTrue("TimeBlock did not deleted successfully.", !page.asText()
				.contains("09:00 AM - 11:00 AM"));
		assertTrue("TimeBlock did not deleted successfully.", !page.asText()
				.contains("RGN - 2.00 hours"));

	}
	
	// KPME-1446
	@Test
	public void testValidateTimeBlock() throws Exception {

		EarnCode earnCode = null;
		HtmlPage page = loginAndGetTimeDetailsHtmlPage(USER_PRINCIPAL_ID,
				tdocId, true);

		HtmlForm form = page.getFormByName("TimeDetailActionForm");
		assertNotNull(form);

		// Assignment of user
		Assignment assToBeSelected = assignmentsOfUser.get(4);

		// retrieving earncode for the assignment
		List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService()
				.getEarnCodes(assToBeSelected, TIME_SHEET_DATE);
		if (earnCodes != null && !earnCodes.isEmpty()) {
			earnCode = earnCodes.get(0);
		}

		DateTime startTime = new DateTime(2012, 2, 17, 9, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);  // Friday
		DateTime endTime = new DateTime(2012, 2, 20, 11, 0, 0, 0,
				TkConstants.SYSTEM_DATE_TIME_ZONE);  // Monday

		// Setup TimeDetailActionForm1
		TimeDetailActionFormBase addTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, null, true); // last argument true = include weekends
		List<String> errors = TimeDetailTestUtils.setTimeBlockFormDetails(form,
				addTB);
		// Check for errors - spanning weeks includes weekends, and include weekends box is checked - should give no error
		assertEquals(
				"There should no error in this time detail submission", 0,
				errors.size());


		// Setup TimeDetailActionForm2
		addTB = TimeDetailTestUtils
				.buildDetailActionForm(timeDoc, assToBeSelected, earnCode,
						startTime, endTime, null, true, null, false); // last argument false = do not include weekends
		errors = TimeDetailTestUtils.setTimeBlockFormDetails(form, addTB);
		// Check for errors - spanning weeks includes weekends, and include weekends box is not not checked - should give an error
		assertEquals(
				"There should an error in this time detail submission", 1,
				errors.size());
	}

}
