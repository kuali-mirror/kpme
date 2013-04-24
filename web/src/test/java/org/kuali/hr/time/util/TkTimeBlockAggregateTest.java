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
package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.tklm.time.calendar.Calendar;
import org.kuali.hr.tklm.time.calendar.CalendarEntry;
import org.kuali.hr.tklm.time.flsa.FlsaWeek;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.hr.tklm.time.util.TkTimeBlockAggregate;

public class TkTimeBlockAggregateTest extends KPMETestCase {


	@Test
	public void testGetFlsaWeeks() throws Exception {
		Calendar cal = new Calendar();
		cal.setFlsaBeginDay("mo");
		cal.setFlsaBeginTime(new Time((new DateTime(2012, 3, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis())));

		CalendarEntry pcd = new CalendarEntry();
		pcd.setBeginPeriodDateTime((new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).toDate()));
		pcd.setEndPeriodDateTime((new DateTime(2010, 1, 6, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).toDate()));

		List<TimeBlock> blocks = getSomeTimeBlocks();
		TkTimeBlockAggregate tba = new TkTimeBlockAggregate(blocks, pcd, cal);
		List<FlsaWeek> weeks = tba.getFlsaWeeks(DateTimeZone.forID(TkServiceLocator.getTimezoneService().getUserTimezone()));
	}




	private List<TimeBlock> getSomeTimeBlocks() {
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();

		TimeBlock block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 1, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new DateTime(2010, 1, 1, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new BigDecimal(1),
				"REG");
		blocks.add(block);
		block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 2, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new DateTime(2010, 1, 2, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new BigDecimal(1),
				"REG");
		blocks.add(block);
		block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 5, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new DateTime(2010, 1, 5, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new BigDecimal(1),
				"REG");
		blocks.add(block);

		return blocks;
	}
}
