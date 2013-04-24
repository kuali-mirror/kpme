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
package org.kuali.hr.time.summary;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeHourDetail;

public class TimeSummaryTest extends KPMETestCase {

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
