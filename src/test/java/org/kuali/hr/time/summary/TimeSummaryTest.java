package org.kuali.hr.time.summary;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;

public class TimeSummaryTest extends TkTestCase {

	@Test
	public void testTimeBlockTimeHourDetailBreakup() throws Exception{
		DateTime beginTime = new DateTime(2010, 1, 2, 1, 0, 0, 0);
		DateTime endTime = new DateTime(2010, 1, 4, 2, 0, 0, 0);
		
		TimeBlock timeBlock = new TimeBlock();
		timeBlock.setBeginTimestamp(new Timestamp(beginTime.getMillis()));
		timeBlock.setEndTimestamp(new Timestamp(endTime.getMillis()));
		
		TimeHourDetail timeHourDetail = new TimeHourDetail();
		
		Map<Timestamp, BigDecimal> timeToHrs = TkTestUtils.getDateToHoursMap(timeBlock, timeHourDetail);
		Assert.assertTrue(timeToHrs!=null);
	}
	
}
