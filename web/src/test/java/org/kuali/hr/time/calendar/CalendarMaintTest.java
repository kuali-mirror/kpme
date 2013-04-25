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
package org.kuali.hr.time.calendar;


import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CalendarMaintTest extends KPMETestCase {

	public static final String TEST_USER = "admin";
	private static final String CAL_NAME_REQUIRED = "Calendar Name (Calendar Name) is a required field.";
	private static final String CAL_DESP_REQUIRED = "Calendar Descriptions (Calendar Descriptions) is a required field.";
	private static final String CAL_TYPES_REQUIRED = "Calendar Types (Calendar Types) is a required field.";
	private static final String FLSA_DAY_REQUIRED = "FLSA Begin Day is a required field.";
	private static final String FLSA_TIME_REQUIRED = "FLSA Begin Time is a required field.";
	
	@Test
	public void testDisplayCalendarTypeRadioOptions() throws Exception {
		
		//verify the lookup page doesn't contain the both radio button
		HtmlPage calendarPage = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.CALENDAR_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(calendarPage, "search");
		HtmlUnitUtil.createTempFile(resultPage);
		Assert.assertTrue("Lookup page contains:\n" + "The both radio button is not present", resultPage.asText().contains("Both"));
		
		//verify the lookup page doesn't contain the both radio button
		HtmlPage calendarMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit"); //click on the first result
		HtmlUnitUtil.createTempFile(calendarMaintPage);
		Assert.assertFalse("Maintenance page contains:\n" + "The both radio button is present", calendarMaintPage.asText().contains("Both"));
		
	}
	
	@Test
	public void testRequiredFields() throws Exception {
	  	String baseUrl = TkTestConstants.Urls.CALENDAR_MAINT_NEW_URL;
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	
	  	HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	  	Assert.assertNotNull("Could not locate submit button", input);
	  	
	  	HtmlElement element = page.getElementByName("methodToCall.route");
	  	page = element.click();
	  	Assert.assertFalse("page text contains: Incident Report", page.asText().contains("Incident Report"));
	  	Assert.assertTrue("page text does not contain:\n" + CAL_NAME_REQUIRED, page.asText().contains(CAL_NAME_REQUIRED));
	  	Assert.assertTrue("page text does not contain:\n" + CAL_DESP_REQUIRED, page.asText().contains(CAL_DESP_REQUIRED));
	  	Assert.assertTrue("page text does not contain:\n" + CAL_TYPES_REQUIRED, page.asText().contains(CAL_TYPES_REQUIRED));
	  	Assert.assertFalse("page text contains:\n" + FLSA_DAY_REQUIRED, page.asText().contains(FLSA_DAY_REQUIRED));
	  	Assert.assertFalse("page text contains:\n" + FLSA_TIME_REQUIRED, page.asText().contains(FLSA_TIME_REQUIRED));
	    
	    setFieldValue(page, "document.newMaintainableObject.calendarName", "testCal");
	    setFieldValue(page, "document.newMaintainableObject.calendarDescriptions", "testDes");
	    // when calendar type is leave, flsa day and time are NOT required
	    setFieldValue(page, "document.newMaintainableObject.calendarTypesLeave", "on");
	  	page = ((HtmlElement)page.getElementByName("methodToCall.route")).click();
	  	Assert.assertFalse("page text contains:\n" + FLSA_DAY_REQUIRED, page.asText().contains(FLSA_DAY_REQUIRED));
	  	Assert.assertFalse("page text contains:\n" + FLSA_TIME_REQUIRED, page.asText().contains(FLSA_TIME_REQUIRED));
	    
	    // when calendar type is Pay, flsa day and time are required
	    setFieldValue(page, "document.newMaintainableObject.calendarTypesPay", "on");
	    page = ((HtmlElement)page.getElementByName("methodToCall.route")).click();
	    Assert.assertTrue("page text does not contain:\n" + FLSA_DAY_REQUIRED, page.asText().contains(FLSA_DAY_REQUIRED));
	    Assert.assertTrue("page text does not contain:\n" + FLSA_TIME_REQUIRED, page.asText().contains(FLSA_TIME_REQUIRED));
	}
}