package org.kuali.hr.lm.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

public class LeaveBlockAggregateTest extends KPMETestCase {
	private String TEST_USER = "admin";
	
	@Test
	public void testGetLeaveBlockAggregate(){
		// 03/01/2012 to 03/15/2012
		Calendar cal = Calendar.getInstance();
		cal.setTime(TKUtils.getTimelessDate(null));
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DATE, 1);
		
		Date beginDate = cal.getTime();
		cal.add(Calendar.DATE,14);
		Date endDate = cal.getTime();
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("50001");
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
