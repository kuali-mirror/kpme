package org.kuali.hr.lm.leave.web;

import junit.framework.Assert;

import org.joda.time.DateTime;
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

import java.util.Calendar;

public class LeaveCalendarWebTest extends KPMETestCase {

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
				.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_CALENDAR_URL, true);
		Assert.assertNotNull("Leave Request page not found" ,leaveCalendarPage);

		this.setWebClient(leaveCalendarPage.getWebClient());

        DateTime dt = new DateTime();
		Assert.assertTrue("Page does not have Current calendar ", leaveCalendarPage
				.asText().contains(dt.monthOfYear().getAsText() + dt.toString(" YYYY")));

        // Check for next document
        HtmlButton nextButton = (HtmlButton) leaveCalendarPage
                .getElementById("nav_next_lc");
        Assert.assertNotNull(nextButton);
        //TODO: click not working.  Not even getting to the 'execute' method in LeaveCalendarAction
        HtmlPage page = nextButton.click();
        Assert.assertNotNull(page);

		// Check for previous document
		HtmlButton prevButton = (HtmlButton) page
				.getElementById("nav_prev_lc");
		Assert.assertNotNull(prevButton);
        page = prevButton.click();
        Assert.assertNotNull(page);

	}

}
