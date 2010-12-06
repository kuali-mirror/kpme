package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;

public class TkTimeBlockAggregateTest extends TkTestCase {

	
	@Test
	public void testGetFlsaWeeks() throws Exception {
		PayCalendar cal = new PayCalendar();
		cal.setFlsaBeginDay("mo");
		cal.setFlsaBeginTime(new Time((new DateTime(2012, 3, 1, 0, 0, 0, 0, DateTimeZone.forID("EST")).getMillis())));
		
		PayCalendarEntries pcd = new PayCalendarEntries();
		pcd.setBeginPeriodDateTime((new DateTime(2010, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("EST")).toDate()));
		pcd.setEndPeriodDateTime((new DateTime(2010, 1, 6, 0, 0, 0, 0, DateTimeZone.forID("EST")).toDate()));
	
		List<TimeBlock> blocks = getSomeTimeBlocks();
		TkTimeBlockAggregate tba = new TkTimeBlockAggregate(blocks, pcd, cal);
		List<FlsaWeek> weeks = tba.getFlsaWeeks();
	}
	

	
	
	private List<TimeBlock> getSomeTimeBlocks() {
		List<TimeBlock> blocks = new ArrayList<TimeBlock>();
		
		TimeBlock block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST")), 
				new DateTime(2010, 1, 1, 16, 0, 0, 0, DateTimeZone.forID("EST")), 
				new BigDecimal(1), 
				"REG");
		blocks.add(block);
		block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 2, 15, 0, 0, 0, DateTimeZone.forID("EST")), 
				new DateTime(2010, 1, 2, 16, 0, 0, 0, DateTimeZone.forID("EST")), 
				new BigDecimal(1), 
				"REG");
		blocks.add(block);
		block = TkTestUtils.createDummyTimeBlock(
				new DateTime(2010, 1, 5, 15, 0, 0, 0, DateTimeZone.forID("EST")), 
				new DateTime(2010, 1, 5, 16, 0, 0, 0, DateTimeZone.forID("EST")), 
				new BigDecimal(1), 
				"REG");
		blocks.add(block);
		
		return blocks;
	}
}
