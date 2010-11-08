package org.kuali.hr.job;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

/**
 * This class needs refactored - the name job test implies that it should unit test on the Job object, especially considering it's package location.
 * 
 *
 */
public class JobTest extends TkTestCase {

	private static final String TEST_USER_ID = "eric";
	private static final String CALENDAR_GROUP = "BW-CAL";

	@Test
	public void testInsertPayCalendar() throws Exception {
		PayCalendar payCalendar = new PayCalendar();
		payCalendar.setPayCalendarId(1L);
		payCalendar.setCalendarGroup(CALENDAR_GROUP);
		payCalendar.setChart("Test");
		payCalendar.setBeginDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		payCalendar.setEndDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		payCalendar.setBeginTime(new java.sql.Time(Calendar.getInstance().getTimeInMillis()));
		payCalendar.setEndTime(new java.sql.Time(Calendar.getInstance().getTimeInMillis()));

		KNSServiceLocator.getBusinessObjectService().save(payCalendar);
		assertTrue(TkServiceLocator.getPayCalendarSerivce().getPayCalendar(payCalendar.getPayCalendarId()) != null);

	}

	@Test
	public void testInsertPayCalendarDates() throws Exception {
		PayCalendarDates payCalendarDates = new PayCalendarDates();
		payCalendarDates.setPayCalendarDatesId(1L);
		payCalendarDates.setPayCalendarId(1L);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.YEAR, 2010);

		payCalendarDates.setBeginPeriodDateTime(new java.sql.Date(cal.getTime().getTime()));

		cal.set(Calendar.DATE, 14);
		payCalendarDates.setEndPeriodDateTime(new java.sql.Date(cal.getTime().getTime()));

		KNSServiceLocator.getBusinessObjectService().save(payCalendarDates);
		assertTrue(TkServiceLocator.getPayCalendarDatesSerivce().getPayCalendarDates(payCalendarDates.getPayCalendarDatesId()) != null);

	}

	@Test
	public void testInsertPayType() throws Exception {

		long currentTimestamp = Calendar.getInstance().getTime().getTime();

		PayType payType = new PayType();
		payType.setHrPayTypeId(1L);
		payType.setPayType("BW");
		payType.setCalendarGroup(CALENDAR_GROUP);
		payType.setRegEarnCode("RGN");
		payType.setEffectiveDate(new java.sql.Date(currentTimestamp));
		payType.setTimestamp(new Timestamp(currentTimestamp));
		payType.setHolidayCalendarGroup("HOL");

		KNSServiceLocator.getBusinessObjectService().save(payType);
		assertTrue(TkServiceLocator.getPayTypeSerivce().getPayType(payType.getPayType(), payType.getEffectiveDate()) != null);
	}
}
