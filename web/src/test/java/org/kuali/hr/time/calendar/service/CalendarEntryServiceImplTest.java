/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.calendar.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.bo.calendar.entry.service.CalendarEntryService;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.common.TKUtils;

public class CalendarEntryServiceImplTest extends KPMETestCase {
	private CalendarEntryService ceService;
	@Before
	public void setUp() throws Exception{
		super.setUp();
		ceService = HrServiceLocator.getCalendarEntryService();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetAllCalendarEntriesForCalendarId() {
		List<CalendarEntry> ceList= ceService.getAllCalendarEntriesForCalendarId("2");
		Assert.assertTrue("Calendar entries not found for Calendar Id '2'", CollectionUtils.isNotEmpty(ceList));
	}
	@Test
	public void testGetAllCalendarEntriesForCalendarIdAndYear() {
		List<CalendarEntry> ceList= ceService.getAllCalendarEntriesForCalendarIdAndYear("2", "2012");
		Assert.assertTrue("Calendar entries not found for Calendar Id '2' and year '2012'", CollectionUtils.isNotEmpty(ceList));
		Assert.assertTrue("There should be 24 Calendar entries, not " + ceList.size(), ceList.size() == 24);
	}
	
	@Test
	public void testGetAllCalendarEntriesForCalendarIdUpToPlanningMonths() {
		List<CalendarEntry> ceList= ceService.getAllCalendarEntriesForCalendarIdUpToPlanningMonths("2", "admin");
		Assert.assertTrue("Calendar entries not found for Calendar Id '2' and principalId 'admin'", CollectionUtils.isNotEmpty(ceList));
	}
	
	@Test
	public void testGetAllCalendarEntriesForCalendarIdUpToCutOffTime() {
		DateTime aDate = new DateTime(2012,10,31,0,0,0,0, TKUtils.getSystemDateTimeZone());
		List<CalendarEntry> ceList= ceService.getAllCalendarEntriesForCalendarIdUpToCutOffTime("2", aDate);
		Assert.assertTrue("Calendar entries not found for Calendar Id '2' and date ", CollectionUtils.isNotEmpty(ceList));
		Assert.assertTrue("There should be 67 Calendar entries, not " + ceList.size(), ceList.size() == 67);
	}
}


