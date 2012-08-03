package org.kuali.hr.time.paycalendar;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PayCalendarTest extends TkTestCase {
	
	@Test
	public void testPayCalendar() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.calendar.Calendar&returnLocation=" + HtmlUnitUtil.getBaseURL() + "/portal.do&hideReturnLink=true&docFormKey=88888888";
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit","1");
    	HtmlUnitUtil.createTempFile(page);
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("BW-CAL"));
	}
	
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
