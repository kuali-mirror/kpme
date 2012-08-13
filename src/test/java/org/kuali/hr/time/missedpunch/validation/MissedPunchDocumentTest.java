package org.kuali.hr.time.missedpunch.validation;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.google.common.collect.Lists;

public class MissedPunchDocumentTest extends KPMETestCase {

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
	public void testMissedPunch() throws Exception {

		String baseUrl = TkTestConstants.Urls.CLOCK_URL;
		HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
		Assert.assertNotNull(page);
		Assert.assertTrue("Clock Page contains Missed Punch Button", page.asText()
				.contains("Missed Punch"));
		this.setWebClient(page.getWebClient());

		System.out.println("Page is : " + page.asText());

		// getting Assignment options
		HtmlSelect assOption = (HtmlSelect) page
				.getElementByName("selectedAssignment");
		Iterable<HtmlElement> it = assOption.getChildElements();
		List<HtmlElement> assList = Lists.newLinkedList(it);
		
		System.out.println("Second ele is : "+assList.get(2));

		// set Assignment value
		HtmlOption ho = null;
		if(assList.size() > 2) {
			ho = (HtmlOption) assList.get(2);
		} else {
			ho= (HtmlOption) assList.get(0);
		}
		System.out.println("ho is : " + ho.asText());
		assOption.setSelectedAttribute(ho, true);

				
		// get tdocid 
		HtmlTableDataCell htmlTable = (HtmlTableDataCell) page.getByXPath(
				"//tbody//tr//td").get(7);
		System.out.println("htnlTable.getTextContent();"
				+ htmlTable.getTextContent());

		String docId = htmlTable.getTextContent();
		
		// redirect to missed punch page
		HtmlPage mPunchPage = HtmlUnitUtil
				.gotoPageAndLogin(HtmlUnitUtil.getBaseURL()
						+ "/missedPunch.do?methodToCall=docHandler&command=initiate&docTypeName=MissedPunchDocumentType&tdocid="
						+ docId);
		Assert.assertNotNull(mPunchPage);
		
		// clock action and assignment are drop down lists that are not readonly
		HtmlElement element = mPunchPage.getElementById("document.clockAction");
		Assert.assertNotNull(element);
		element = mPunchPage.getElementById("document.assignment");
		Assert.assertNotNull(element);

		System.out.println("Page1 is : " + mPunchPage.asText());

		// set Invalid action time
		setFieldValue(mPunchPage, "document.documentHeader.documentDescription",
				"Missed Punch- test");
		setFieldValue(mPunchPage, "document.clockAction", "CO");
		setFieldValue(mPunchPage, "document.actionDate", "01/16/2012");
		setFieldValue(mPunchPage, "document.actionTime", "5:5");
		setFieldValue(mPunchPage, "document.assignment", "2_1234_2");

		HtmlElement elementSubmit = mPunchPage
				.getElementByName("methodToCall.route");
		mPunchPage = elementSubmit.click();

		System.out.println("After first click >>>>> page is : "
				+ mPunchPage.asText());

		Assert.assertTrue("page text:\n" + mPunchPage.asText() + "\n does not contain:\n",
				mPunchPage.asText().contains("5:5 is not a valid date."));

		// set Future time 
		setFieldValue(mPunchPage, "document.actionDate", "01/20/2020");
		setFieldValue(mPunchPage, "document.actionTime", "5:5 AM");

		elementSubmit = mPunchPage.getElementByName("methodToCall.route");
		mPunchPage = elementSubmit.click();

		Assert.assertTrue(
				"page text:\n" + mPunchPage.asText() + "\n does not contain:\n",
				mPunchPage.asText().contains(
						"Missed Punch Action Date cannot be a future date"));

		System.out.println("After second click >>>>> page is : "
				+ mPunchPage.asText());

		// before last clock in time
		setFieldValue(mPunchPage, "document.clockAction", "CI");
		setFieldValue(mPunchPage, "document.actionDate", "12/20/2009");
		setFieldValue(mPunchPage, "document.actionTime", "5:5 AM");
		setFieldValue(mPunchPage, "document.assignment", "2_1234_2");

		elementSubmit = mPunchPage.getElementByName("methodToCall.route");

		mPunchPage = elementSubmit.click();
		System.out.println("After third click >>>>> page is : "
				+ mPunchPage.asText());

		Assert.assertTrue(
				"page text:\n" + mPunchPage.asText() + "\n does not contain:\n",
				mPunchPage.asText()
						.contains(
								"The date/time is not valid. Can not be before previous clock action, or 24 hours beyond a clock in."));

	
		// set proper value
		setFieldValue(mPunchPage, "document.actionDate", "01/19/2012");
		setFieldValue(mPunchPage, "document.actionTime", "5:5 AM");
		setFieldValue(mPunchPage, "document.clockAction", "CI");
		setFieldValue(mPunchPage, "document.assignment", "2_1234_2");

		HtmlElement elementSubmit1 = mPunchPage.getElementByName("methodToCall.route");
		mPunchPage = elementSubmit1.click();

		System.out.println("After forth click >>>>> page is : "
				+ mPunchPage.asText());

		Assert.assertTrue("page text:\n" + mPunchPage.asText() + "\n does not contain:\n",
				mPunchPage.asText().contains("Document was successfully submitted."));
		
		// open another missed punch doc for clock out
		mPunchPage = HtmlUnitUtil.gotoPageAndLogin(HtmlUnitUtil.getBaseURL()
				+ "/missedPunch.do?methodToCall=docHandler&command=initiate&docTypeName=MissedPunchDocumentType&tdocid="
				+ docId);
		Assert.assertNotNull(mPunchPage);
		element = mPunchPage.getElementById("document.clockAction");
		Assert.assertNotNull(element);
		// element not found for assignment since it is a readonly field now
		element = mPunchPage.getElementById("document.assignment");
		Assert.assertNull(element);
		Assert.assertTrue("page text:does not contain: \n", 
				mPunchPage.asText().contains("Assignment: 	 work area description : $0.00 Rcd 2 TEST-DEPT description 2"));
		
	}
}
