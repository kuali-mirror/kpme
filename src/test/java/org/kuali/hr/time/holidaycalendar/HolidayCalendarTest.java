package org.kuali.hr.time.holidaycalendar;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HolidayCalendarTest extends TkTestCase {
	
	private static Long holidayGroupId = 1L;//id entered in the bootstrap SQL
	
	@Test
	public void testHolidayCalendarMaintenancePage() throws Exception{	
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.HOLIDAY_CALENDAR_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		assertTrue("Page contains REG entry", earnCodeLookUp.asText().contains("REG"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit",holidayGroupId.toString());		
		assertTrue("Maintenance Page contains REG entry",maintPage.asText().contains("REG"));
	}
	
}