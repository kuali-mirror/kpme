/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.calendar.CalendarBo;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.utils.TkTestUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@IntegrationTest
public class TkTimeBlockAggregateTest extends TKLMIntegrationTestCase {


	@Test
	public void testGetFlsaWeeks() throws Exception {
		Calendar.Builder cal = Calendar.Builder.create();
		cal.setFlsaBeginDay("mo");
		cal.setFlsaBeginLocalTime((new DateTime(2012, 3, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).toLocalTime()));

		CalendarEntry.Builder pcd =  CalendarEntry.Builder.create();
		pcd.setBeginPeriodFullDateTime(new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		pcd.setEndPeriodFullDateTime(new DateTime(2010, 1, 6, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));

		List<TimeBlock> blocks = getSomeTimeBlocks();
		TkTimeBlockAggregate tba = new TkTimeBlockAggregate(blocks, pcd.build(), cal.build());
		List<FlsaWeek> weeks = tba.getFlsaWeeks(DateTimeZone.UTC, 0, false);
	}




	private List<TimeBlock> getSomeTimeBlocks() {
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();

		TimeBlockBo block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 1, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new DateTime(2010, 1, 1, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new BigDecimal(1),
				"REG");
		blocks.add(TimeBlockBo.to(block));
		block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 2, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new DateTime(2010, 1, 2, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new BigDecimal(1),
				"REG");
        blocks.add(TimeBlockBo.to(block));
		block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 5, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new DateTime(2010, 1, 5, 16, 0, 0, 0, TKUtils.getSystemDateTimeZone()),
				new BigDecimal(1),
				"REG");
        blocks.add(TimeBlockBo.to(block));

		return blocks;
	}
}
