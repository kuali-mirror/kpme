package org.kuali.hr.time.earncode.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

public class EarnCodeServiceImplTest extends TkTestCase {


	public static final String TEST_USER = "admin";
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER = 1L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_2 = 2L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_3 = 3L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_4 = 4L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(EarnCodeServiceImplTest.class);

	EarnCodeService earnCodeService = null;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		earnCodeService=TkServiceLocator.getEarnCodeService();
	}

	@Test
	public void getEarnCodes() throws Exception {
        Date asOfDate = TKUtils.getTimelessDate(null);
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TEST_USER, asOfDate);
		assertNotNull(assignments);
		assertTrue("Emtpy assignment list", !assignments.isEmpty());

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
		assertNotNull("Test assignment not found.", assignment1);
		assertNotNull("Test assignment not found.", assignment2);
		assertNotNull("Test assignment not found.", assignment3);
		assertNotNull("Test assignment not found.", assignment4);

		// Testing standard lookup.
		List<EarnCode> earnCodes = earnCodeService.getEarnCodes(assignment1,asOfDate);
		assertEquals("Wrong number of earn codes returned.", 8, earnCodes.size());

		// Wildcard on SalGroup
		earnCodes = earnCodeService.getEarnCodes(assignment2,asOfDate);
		assertEquals("Wrong number of earn codes returned.", 3, earnCodes.size());

		// Dual Wildcards
		earnCodes = earnCodeService.getEarnCodes(assignment3,asOfDate);
		assertEquals("Wrong number of earn codes returned.",2, earnCodes.size());
	}

	@Test
	public void testEarnCodeMaintenancePage() throws Exception{

		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_CODE_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		assertTrue("Page contains SDR entry", earnCodeLookUp.asText().contains("SDR"));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit","1");

		//Sai - confirm that the error is throw by not selecting a record type
		HtmlSelect inputText= maintPage.getHtmlElementById("document.newMaintainableObject.recordMethod");
		inputText.setDefaultValue("H");

		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
//		setFieldValue(maintPage, "document.newMaintainableObject.fractionalTimeAllowed", "99");
//		setFieldValue(maintPage, "document.newMaintainableObject.roundingOption", "T");
		
		HtmlRadioButtonInput hb = maintPage.getHtmlElementById("document.newMaintainableObject.fractionalTimeAllowed99");
		hb.setChecked(true);
		
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		System.out.println(resultantPageAfterEdit.asText());
//		assertTrue("Error message for not selecting any record type",
//				resultantPageAfterEdit.asText().contains("For this earn code you must specify Record Hours or Record Time or Record Amount"));

		//Sai - confirm that the error is thrown if more than one record type is selected
//		checkBox  = maintPage.getHtmlElementById("document.newMaintainableObject.recordTime");
//		checkBox.setChecked(true);
//		checkBox  = maintPage.getHtmlElementById("document.newMaintainableObject.recordHours");
//		checkBox.setChecked(true);

		inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
//		assertTrue("Error message for selecting more than one record type",
//				resultantPageAfterEdit.asText().contains("For this earn code you can only specify one of the Record types"));
	}
	
	@Test
	public void testGetEarnCodesForDisplay() throws Exception{
		Map<String, String> earnCodesDisplay = earnCodeService.getEarnCodesForDisplay("testUser");
		assertNotNull("earnCodesDisplay should not be null", earnCodesDisplay);
		assertTrue("There should be 1 earnCode found for principal_id 'testUser', not " + earnCodesDisplay.size(), earnCodesDisplay.size()== 1);
		assertTrue("earnCodesDisplay should contain Key '5000'", earnCodesDisplay.containsKey("5000"));
		assertTrue("earnCodesDisplay should contain Value 'EC1 : test1'", earnCodesDisplay.containsValue("EC1 : test1"));
		
	}
}
