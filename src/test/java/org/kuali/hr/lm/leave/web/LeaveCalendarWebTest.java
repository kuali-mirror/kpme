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
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

public class LeaveCalendarWebTest extends TkTestCase {

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
	public void testLeaveCalendarPage() throws Exception {
		// get the page and Login
		HtmlPage leaveCalendarPage = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_CALENDAR_URL);
		assertNotNull("Leave Request page not found" ,leaveCalendarPage);

		this.setWebClient(leaveCalendarPage.getWebClient());

		
		assertTrue("Page does not have Current calendar ", leaveCalendarPage
				.asText().contains("April 2012"));

		// Check for previous document
		HtmlButton prevButton = (HtmlButton) leaveCalendarPage
				.getElementById("nav_prev_lc");
		assertNotNull(prevButton);
	
		
		// Check for next document
		HtmlButton nextButton = (HtmlButton) leaveCalendarPage
				.getElementById("nav_next_lc");
		assertNotNull(nextButton);
		HtmlPage page = nextButton.click();
		
		assertNotNull(page);

	}

}
