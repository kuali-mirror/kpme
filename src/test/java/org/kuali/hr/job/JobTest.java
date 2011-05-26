package org.kuali.hr.job;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * This class needs refactored - the name job test implies that it should unit test on the Job object, especially considering it's package location.
 * 
 *
 */
public class JobTest extends TkTestCase {

	private static final String TEST_USER_ID = "eric";
	private static final String CALENDAR_GROUP = "BW-CAL";
	private static Long jobId = 23L;//id entered in the bootstrap SQL
	private static Long jobNumber = 5L;//number entered in the bootstrap SQL
	
	@Test
	public void testInsertPayCalendar() throws Exception {
		PayCalendar payCalendar = new PayCalendar();
		payCalendar.setPayCalendarId(1L);
		payCalendar.setCalendarGroup(CALENDAR_GROUP);

		payCalendar.setFlsaBeginDay("Sun");
		payCalendar.setFlsaBeginTime(Time.valueOf("0:00:00"));
		KNSServiceLocator.getBusinessObjectService().save(payCalendar);
		assertTrue(TkServiceLocator.getPayCalendarSerivce().getPayCalendar(payCalendar.getPayCalendarId()) != null);

	}

	@Test
	public void testInsertPayCalendarDates() throws Exception {
		PayCalendarEntries payCalendarDates = new PayCalendarEntries();
		payCalendarDates.setPayCalendarEntriesId(1001L);
		payCalendarDates.setPayCalendarId(1001L);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.YEAR, 2010);

		payCalendarDates.setBeginPeriodDateTime(new java.sql.Date(cal.getTime().getTime()));
		payCalendarDates.setCalendarGroup(CALENDAR_GROUP);
		cal.set(Calendar.DATE, 14);
		payCalendarDates.setEndPeriodDateTime(new java.sql.Date(cal.getTime().getTime()));

		KNSServiceLocator.getBusinessObjectService().save(payCalendarDates);
		assertTrue(TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(payCalendarDates.getPayCalendarEntriesId()) != null);

	}

	@Test
	public void testInsertPayType() throws Exception {

		long currentTimestamp = Calendar.getInstance().getTime().getTime();

		PayType payType = new PayType();
		payType.setHrPayTypeId(1001L);
		payType.setPayType("BW");
		payType.setRegEarnCode("RGN");
		payType.setEffectiveDate(new java.sql.Date(currentTimestamp));
		payType.setTimestamp(new Timestamp(currentTimestamp));

		KNSServiceLocator.getBusinessObjectService().save(payType);
		assertTrue(TkServiceLocator.getPayTypeSerivce().getPayType(payType.getPayType(), payType.getEffectiveDate()) != null);
	}
	
	@Test
	public void jobMaintenancePage() throws Exception{
		HtmlPage lookupPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.JOB_MAINT_URL);
		lookupPage = HtmlUnitUtil.clickInputContainingText(lookupPage, "search");
		HtmlUnitUtil.createTempFile(lookupPage);
		assertTrue("Page contains admin entry", lookupPage.asText().contains("admin"));
		// xichen, changed to edit jobId 23, because clickAnchorContainingText() is a wild search. The test was editing jobId 1, it returned the first entry whose jobId starts with 1.
		HtmlPage editPage = HtmlUnitUtil.clickAnchorContainingText(lookupPage, "edit", jobId.toString());	
		HtmlUnitUtil.createTempFile(editPage);
		assertTrue("Maintenance Page contains the correct job number", editPage.asText().contains(jobNumber.toString()));				
	}
}
