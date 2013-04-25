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
package org.kuali.hr.lm.leave.web;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveBlockDisplayWebTest extends KPMETestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void updateWebClient() {
        WebClient webClient = getWebClient();
		webClient.setJavaScriptEnabled(true);
		webClient.setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScript(10000);
	}

	@Test
	public void testLeaveBlockDisplayPage() throws Exception {
		// get the page and Login
		Calendar currentCalendar = Calendar.getInstance();
		HtmlPage leaveBlockDisplayPage = HtmlUnitUtil
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.LEAVE_BLOCK_DISPLAY_URL);
		Assert.assertNotNull("Leave Request page not found" + leaveBlockDisplayPage);

		updateWebClient();

		// check page contains the current year
		Assert.assertTrue("Page is not current year ",
				leaveBlockDisplayPage.asText().contains(Integer.toString(currentCalendar.get(Calendar.YEAR))));

		// Check Main section
		// depends on injection of test data for current year.
		Assert.assertTrue("Page does not contain planned leave ",
				leaveBlockDisplayPage.asText().contains("Send for Approval"));

		// check Days Removed in correction section
		Assert.assertTrue("Page does not contain approved leaves ",
				leaveBlockDisplayPage.asText().contains("Updated by others"));
		
		// check Inactive Leave entries
		Assert.assertTrue("Page does not contain approved leaves ",
				leaveBlockDisplayPage.asText().contains("Updated by self"));
		
		// check page contains Document Status column
		Assert.assertTrue("Page does not contain Document Status ",
				leaveBlockDisplayPage.asText().contains("Document Status"));
		Assert.assertTrue("Page does not contain 'FINAL' Document Status ",
				leaveBlockDisplayPage.asText().contains("FINAL"));
		
		// check page contains Planning Status column
		Assert.assertTrue("Page does not contain Planning Status ",
				leaveBlockDisplayPage.asText().contains("Planning Status"));
		
		// Check for next year
		HtmlButton nextButton = (HtmlButton) leaveBlockDisplayPage
				.getElementById("nav_lb_next");
		leaveBlockDisplayPage = nextButton.click();

		// check page contains the next year
		Assert.assertTrue("Page does not contain next year ",
				leaveBlockDisplayPage.asText().contains(Integer.toString(currentCalendar.get(Calendar.YEAR)+1)));
	}

}
