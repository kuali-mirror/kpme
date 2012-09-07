package org.kuali.hr.job;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * This class needs refactored - the name job test implies that it should unit test on the Job object, especially considering it's package location.
 *
 *
 */
public class JobTest extends KPMETestCase {

	private static final String CALENDAR_GROUP = "BWN-CAL";
	private static Long jobId = 23L;//id entered in the bootstrap SQL
	private static Long jobNumber = 5L;//number entered in the bootstrap SQL
	public static final String TEST_USER = "admin";


	@Test
	public void testInsertPayCalendar() throws Exception {
		Calendar payCalendar = new Calendar();
		payCalendar.setHrCalendarId("1001");
		payCalendar.setCalendarName(CALENDAR_GROUP);

		payCalendar.setFlsaBeginDay("Sun");
		payCalendar.setFlsaBeginTime(Time.valueOf("0:00:00"));
        payCalendar.setCalendarDescriptions("Test Description");

		KRADServiceLocator.getBusinessObjectService().save(payCalendar);
		Assert.assertTrue(TkServiceLocator.getCalendarService().getCalendar(payCalendar.getHrCalendarId()) != null);

	}

	@Test
	public void testInsertPayCalendarDates() throws Exception {
		CalendarEntries payCalendarDates = new CalendarEntries();
		payCalendarDates.setHrCalendarEntriesId("1001");
		payCalendarDates.setHrCalendarId("1001");

		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(java.util.Calendar.MONTH, 7);
		cal.set(java.util.Calendar.DATE, 1);
		cal.set(java.util.Calendar.YEAR, 2010);

		payCalendarDates.setBeginPeriodDateTime(new java.sql.Date(cal.getTime().getTime()));
		payCalendarDates.setCalendarName(CALENDAR_GROUP);
		cal.set(java.util.Calendar.DATE, 14);
		payCalendarDates.setEndPeriodDateTime(new java.sql.Date(cal.getTime().getTime()));

		KRADServiceLocator.getBusinessObjectService().save(payCalendarDates);
		Assert.assertTrue(TkServiceLocator.getCalendarEntriesService().getCalendarEntries(payCalendarDates.getHrCalendarEntriesId()) != null);

	}

	@Test
	public void testInsertPayType() throws Exception {

		long currentTimestamp = java.util.Calendar.getInstance().getTime().getTime();

		PayType payType = new PayType();
		payType.setHrPayTypeId("1001");
		payType.setPayType("BW");
		payType.setRegEarnCode("RGN");
		payType.setEffectiveDate(new java.sql.Date(currentTimestamp));
		payType.setTimestamp(new Timestamp(currentTimestamp));

		KRADServiceLocator.getBusinessObjectService().save(payType);
		Assert.assertTrue(TkServiceLocator.getPayTypeService().getPayType(payType.getPayType(), payType.getEffectiveDate()) != null);
	}

	@Test
	public void jobMaintenancePage() throws Exception{
		HtmlPage lookupPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.JOB_MAINT_URL);
		lookupPage = HtmlUnitUtil.clickInputContainingText(lookupPage, "search");
		HtmlUnitUtil.createTempFile(lookupPage);
		Assert.assertTrue("Page contains admin entry", lookupPage.asText().contains("admin"));
		// xichen, changed to edit jobId 23, because clickAnchorContainingText() is a wild search. The test was editing jobId 1, it returned the first entry whose jobId starts with 1.
		HtmlPage editPage = HtmlUnitUtil.clickAnchorContainingText(lookupPage, "edit", jobId.toString());
		HtmlUnitUtil.createTempFile(editPage);
		Assert.assertTrue("Maintenance Page contains the correct job number", editPage.asText().contains(jobNumber.toString()));
	}

	@Test
	public void testGetJobs() {
		Date payPeriodEndDate = new Date((new DateTime(2010,7,30,1,0,0,0, TKUtils.getSystemDateTimeZone())).getMillis());
		List<Job> jobs = TkServiceLocator.getJobService().getJobs(TEST_USER, payPeriodEndDate);
		Assert.assertNotNull("Jobs was null", jobs);
		Assert.assertEquals("Incorrect number of jobs", 2, jobs.size());
	}


	@Test
	public void testSaveAndFetchObject() throws Exception{
		//Confirming the save and fetch
		//save an object and confirm that it can be fetched
	}

	@Test
	public void testMaintenancePageNew() throws Exception {
		//create new
		//input the data
		//confirm submit works
	}

	@Test
	public void testMaintenancePageEdit() throws Exception {
		HtmlPage lookupPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.JOB_MAINT_URL);
		lookupPage = HtmlUnitUtil.clickInputContainingText(lookupPage, "search");
		HtmlPage editPage = HtmlUnitUtil.clickAnchorContainingText(lookupPage, "edit", jobId.toString());
		//input bad dept, sal group, job location, pay type, pay grade
		//submit
		//confirm each error shows up

		//use each of the above lookups to populate the page
		//submit
		//confirm submit worked

	}
}

