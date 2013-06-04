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
package org.kuali.kpme.tklm.time.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.utils.TkTestUtils;

public class TkTimeBlockAggregateTest extends KPMETestCase {


	@Test
	public void testGetFlsaWeeks() throws Exception {
		Calendar cal = new Calendar();
		cal.setFlsaBeginDay("mo");
		cal.setFlsaBeginTime(new Time((new DateTime(2012, 3, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).getMillis())));

		CalendarEntry pcd = new CalendarEntry();
		pcd.setBeginPeriodFullDateTime(new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		pcd.setEndPeriodFullDateTime(new DateTime(2010, 1, 6, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));

		List<TimeBlock> blocks = getSomeTimeBlocks();
		TkTimeBlockAggregate tba = new TkTimeBlockAggregate(blocks, pcd, cal);
		List<FlsaWeek> weeks = tba.getFlsaWeeks(DateTimeZone.UTC);
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
