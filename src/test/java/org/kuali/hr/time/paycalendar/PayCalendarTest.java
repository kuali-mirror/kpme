package org.kuali.hr.time.paycalendar;

import java.sql.Date;
import java.sql.Time;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PayCalendarTest extends TkTestCase {


	@Override
	public void setUp() throws Exception {
		super.setUp();
		PayCalendar payCalendar = new PayCalendar();
		payCalendar.setCalendarGroup("test");
		payCalendar.setChart("BL");
		payCalendar.setBeginDate(new Date(System.currentTimeMillis()));
		payCalendar.setBeginTime(new Time(System.currentTimeMillis()));
		payCalendar.setEndDate(new Date(System.currentTimeMillis()));
		payCalendar.setEndTime(new Time(System.currentTimeMillis()));
		payCalendar.setFlsaBeginDay("Sun");
		payCalendar.setFlsaBeginTime(Time.valueOf("0:00:00"));
		payCalendar.setPayCalendarId(new Long(1000));
		
		KNSServiceLocator.getBusinessObjectService().save(payCalendar);
		PayCalendarDates payCalendarDates = new PayCalendarDates();
		payCalendarDates.setPayCalendarId(new Long(1000));
		payCalendarDates.setPayCalendarDatesId(new Long(1000));
		payCalendarDates.setBeginPeriodDateTime(new Date(System.currentTimeMillis()));
		payCalendarDates.setEndPeriodDateTime(new Date(System.currentTimeMillis()));
		KNSServiceLocator.getBusinessObjectService().save(payCalendarDates);
	}

	
	@Test
	public void testPayCalendar() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paycalendar.PayCalendar&returnLocation=http://localhost:8080/tk-dev/portal.do&hideReturnLink=true&docFormKey=88888888";	
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	HtmlUnitUtil.createTempFile(page);
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit","1000");
    	HtmlUnitUtil.createTempFile(page);
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("test"));
	}
	
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
