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
package org.kuali.kpme.core.earncode;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.earncode.service.EarnCodeService;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.kpme.core.util.HtmlUnitUtil;
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

	@Before
	public void setUp() throws Exception {
		super.setUp();
		earnCodeService=HrServiceLocator.getEarnCodeService();
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
