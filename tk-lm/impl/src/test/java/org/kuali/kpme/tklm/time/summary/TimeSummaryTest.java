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
package org.kuali.kpme.tklm.time.summary;

import java.math.BigDecimal;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.utils.TkTestUtils;

@IntegrationTest
public class TimeSummaryTest extends TKLMIntegrationTestCase {

	@Test
	public void testTimeBlockTimeHourDetailBreakup() throws Exception{
		DateTime beginTime = new DateTime(2010, 1, 2, 1, 0, 0, 0);
		DateTime endTime = new DateTime(2010, 1, 4, 2, 0, 0, 0);
		
		TimeBlockBo timeBlock = new TimeBlockBo();
		timeBlock.setBeginDateTime(beginTime);
		timeBlock.setEndDateTime(endTime);
		
		TimeHourDetailBo timeHourDetail = new TimeHourDetailBo();
		
		Map<DateTime, BigDecimal> timeToHrs = TkTestUtils.getDateToHoursMap(timeBlock, timeHourDetail);
		Assert.assertTrue(timeToHrs!=null);
	}
	
}
