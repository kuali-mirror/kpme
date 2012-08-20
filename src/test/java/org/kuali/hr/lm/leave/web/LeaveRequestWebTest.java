package org.kuali.hr.lm.leave.web;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveRequestWebTest extends KPMETestCase {

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
	public void testLeaveRequestPage() throws Exception {
		// get the page and Login
		HtmlPage leaveRequestPage = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_REQUEST_PAGE_URL);
		Assert.assertNotNull("Leave Request page not found" + leaveRequestPage);

		// Check planned requests
		Assert.assertTrue("Page does not contain planned leave ", leaveRequestPage
				.asText().contains("Send for Approval"));

		// check Approved Leaves
		Assert.assertTrue("Page does not contain approved leaves ", leaveRequestPage
				.asText().contains("Approved Leave"));

		// check disapproved Leaves
		Assert.assertTrue("Page does not contain approved leaves ", leaveRequestPage
				.asText().contains("Disapprove"));

		HtmlCheckBoxInput htmlElement = (HtmlCheckBoxInput) leaveRequestPage
				.getElementByName("plannedLeaves[0].submit");
		leaveRequestPage = (HtmlPage) htmlElement.setValueAttribute("true");

		HtmlCheckBoxInput checkbox = (HtmlCheckBoxInput) HtmlUnitUtil
				.getInputContainingText(leaveRequestPage,
						"plannedLeaves[0].submit");
		checkbox.setChecked(true);

		HtmlElement elementSubmit = leaveRequestPage.getElementByName("Submit");
		Assert.assertNotNull(elementSubmit);

		HtmlUnitUtil.createTempFile(leaveRequestPage);

		HtmlPage leaveRequestPage1 = elementSubmit.click();

		HtmlUnitUtil.createTempFile(leaveRequestPage1);

		// check if submitted pending leave comes in the section of Requested
		// leave
		Assert.assertTrue("Page does not contain Pending leave ", leaveRequestPage1
				.asText().contains("Send for Approval"));

	}

}
