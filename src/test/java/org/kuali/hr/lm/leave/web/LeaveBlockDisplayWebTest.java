package org.kuali.hr.lm.leave.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveBlockDisplayWebTest extends TkTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void setWebClient(WebClient webClient) {
		webClient.setJavaScriptEnabled(true);
		webClient.setThrowExceptionOnScriptError(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScript(10000);
	}

	@Test
	public void testLeaveBlockDisplayPage() throws Exception {
		// get the page and Login
		HtmlPage leaveBlockDisplayPage = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_BLOCK_DISPLAY_URL);
		assertNotNull("Leave Request page not found" + leaveBlockDisplayPage);

		this.setWebClient(leaveBlockDisplayPage.getWebClient());

		// check page contains the current year
		assertTrue("Page does not contain planned leave ",
				leaveBlockDisplayPage.asText().contains("2012"));

		// Check Main section
		assertTrue("Page does not contain planned leave ",
				leaveBlockDisplayPage.asText().contains("Send for Approval"));

		// check Days Removed in correction section
		assertTrue("Page does not contain approved leaves ",
				leaveBlockDisplayPage.asText().contains("Updated by others"));
		
		// check Inactive Leave entries
		assertTrue("Page does not contain approved leaves ",
				leaveBlockDisplayPage.asText().contains("Updated by self"));

		// Check for next year
		HtmlButton nextButton = (HtmlButton) leaveBlockDisplayPage
				.getElementById("nav_lb_next");
		leaveBlockDisplayPage = nextButton.click();

		// check page contains the next year
		assertTrue("Page does not contain planned leave ",
				leaveBlockDisplayPage.asText().contains("2013"));

	}

}
