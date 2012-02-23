package org.kuali.hr.time.calandar;


import org.junit.Test;

import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;

public class CalendarMaintTest extends TkTestCase {

	public static final String TEST_USER = "admin";
	
	@Test
	public void testDisplayCalendarTypeRadioOptions() throws Exception {
		
		//verify the lookup page doesn't contain the both radio button
		HtmlPage calendarPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.CALENDAR_MAINT_URL);
		HtmlPage resultPage = HtmlUnitUtil.clickInputContainingText(calendarPage, "search");
		HtmlUnitUtil.createTempFile(resultPage);
		assertFalse("Lookup page contains:\n" + "The both radio button is not present", resultPage.asText().contains("Both"));
		
		//verify the lookup page doesn't contain the both radio button
		HtmlPage calendarMaintPage = HtmlUnitUtil.clickAnchorContainingText(resultPage, "edit"); //click on the first result
		HtmlUnitUtil.createTempFile(calendarMaintPage);
		assertFalse("Maintenance page contains:\n" + "The both radio button is not present", calendarMaintPage.asText().contains("Both"));
		
	}
}