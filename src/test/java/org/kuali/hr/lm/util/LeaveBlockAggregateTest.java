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
package org.kuali.hr.lm.util;

import java.util.List;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

public class LeaveBlockAggregateTest extends KPMETestCase {
	private String TEST_USER = "admin";
	
	@Test
	public void testGetLeaveBlockAggregate(){
		// 03/01/2012 to 03/15/2012
		LocalDate beginDate = new LocalDate(2012, 3, 1);
		LocalDate endDate = beginDate.plusDays(14);
		CalendarEntry ce = TkServiceLocator.getCalendarEntryService().getCalendarEntry("55");
		List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(TEST_USER, beginDate, endDate);
		
		// get leaveBlockAggaregate with leaveBlocks, calendarEntry and intervals
		LeaveBlockAggregate lbAgg = new LeaveBlockAggregate(leaveBlocks, ce, TKUtils.getFullWeekDaySpanForCalendarEntry(ce));
		Assert.assertNotNull("LeaveBlockAggregate should not be null.", lbAgg);
		Assert.assertTrue("LeaveBlockAggregate should have 21 days, not " + lbAgg.getDayLeaveBlockList().size(), lbAgg.getDayLeaveBlockList().size() == 21);
		Assert.assertTrue("There should be 1 leave block on 03/01.", lbAgg.getDayLeaveBlockList().get(4).size() == 1);
		Assert.assertTrue("There should be 1 leave block on 03/02.", lbAgg.getDayLeaveBlockList().get(5).size() == 1);
		
		// get leaveBlockAggaregate with leaveBlocks, calendarEntry
		lbAgg = new LeaveBlockAggregate(leaveBlocks, ce);
		Assert.assertNotNull("LeaveBlockAggregate should not be null.", lbAgg);
		Assert.assertTrue("LeaveBlockAggregate should have 14 days, not " + lbAgg.getDayLeaveBlockList().size(), lbAgg.getDayLeaveBlockList().size() == 14);
		Assert.assertTrue("There should be 1 leave block on 03/01.", lbAgg.getDayLeaveBlockList().get(0).size() == 1);
		Assert.assertTrue("There should be 1 leave block on 03/02.", lbAgg.getDayLeaveBlockList().get(1).size() == 1);
	}
}
