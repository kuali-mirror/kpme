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
package org.kuali.hr.core.earncode.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.core.HrTestConstants;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.earncode.service.EarnCodeService;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.service.TimesheetService;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

public class EarnCodeServiceImplTest extends KPMETestCase {


	public static final String TEST_USER = "admin";
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER = 1L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_2 = 2L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_3 = 3L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_4 = 4L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(EarnCodeServiceImplTest.class);

	EarnCodeService earnCodeService = null;
	TimesheetService timesheetService = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		earnCodeService=HrServiceLocator.getEarnCodeService();
		timesheetService=TkServiceLocator.getTimesheetService();
	}

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

	@Test
	public void testEarnCodeMaintenancePage() throws Exception{

		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		Assert.assertTrue("Page contains SDR entry", earnCodeLookUp.asText().contains("SDR"));
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
		LOG.debug(resultantPageAfterEdit.asText());

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
        //create the testPrincipal object for the earn code service parm, from the TEST_USER string
        Principal testPrincipal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName("testUser");
        Map<String, String> earnCodesDisplay = earnCodeService.getEarnCodesForDisplay(testPrincipal.getPrincipalId(), false);
//  assertions commented out until earn code service finished. 20120918tv
//		Assert.assertNotNull("earnCodesDisplay should not be null", earnCodesDisplay);
//		Assert.assertEquals("There should be 2 earnCode found for principal_id 'testUser', not " + earnCodesDisplay.size(), earnCodesDisplay.size(), 2);
//		Assert.assertTrue("earnCodesDisplay should contain Value 'EC1 : test1'", earnCodesDisplay.containsValue("EC1 : test1"));
	}
}
