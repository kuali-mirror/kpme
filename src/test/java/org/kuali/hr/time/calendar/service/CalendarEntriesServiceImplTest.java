package org.kuali.hr.time.calendar.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class CalendarEntriesServiceImplTest extends KPMETestCase {
	private CalendarEntriesService ceService;
	@Before
	public void setUp() throws Exception{
		super.setUp();
		ceService = TkServiceLocator.getCalendarEntriesService();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetAllCalendarEntriesForCalendarId() {
		List<CalendarEntries> ceList= ceService.getAllCalendarEntriesForCalendarId("2");
		Assert.assertNotNull("Calendar entries not found for Calendar Id '2'" , ceList);
		Assert.assertTrue("Calendar entries not found for Calendar Id '2'", ceList.size() != 0);
	}
	@Test
	public void testGetAllCalendarEntriesForCalendarIdAndYear() {
		List<CalendarEntries> ceList= ceService.getAllCalendarEntriesForCalendarIdAndYear("2", "2012");
		Assert.assertNotNull("Calendar entries not found for Calendar Id '2' and year '2012'", ceList);
		Assert.assertTrue("Calendar entries not found for Calendar Id 2", ceList.size() != 0);
	}
}
