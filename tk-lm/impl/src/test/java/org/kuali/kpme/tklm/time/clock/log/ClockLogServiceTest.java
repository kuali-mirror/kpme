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
package org.kuali.kpme.tklm.time.clock.log;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;

@IntegrationTest
public class ClockLogServiceTest extends TKLMIntegrationTestCase {
	
	@Test
	public void testGetUnapprovedIPWarning() throws Exception {
		List<TimeBlock> tbLists = new ArrayList<TimeBlock>();
		TimeBlockBo timeBlock = new TimeBlockBo();
        timeBlock.setUserPrincipalId("testUser");
        timeBlock.setClockLogCreated(true);
        timeBlock.setClockLogEndId("5000");
        tbLists.add(TimeBlockBo.to(timeBlock));
		
		List<String> warningList = TkServiceLocator.getClockLogService().getUnapprovedIPWarning(tbLists);
		Assert.assertTrue("There should be 1 warning message ", warningList.size()== 1);
		String warning = warningList.get(0);
		Assert.assertTrue("Warning message should be 'Warning: Action 'Clock Out' taken at 03/01/2012 08:08:08.000 was from an unapproved IP address - TEST', not " + warning,
				warning.equals("Warning: Action 'Clock Out' taken at 03/01/2012 08:08:08.000 was from an unapproved IP address - TEST"));
		
	}
	
	@Test
	public void testIsClockLogCreatedByMissedPunch() throws Exception {
		boolean isMissedPunch = TkServiceLocator.getClockLogService().isClockLogCreatedByMissedPunch("5000");
		Assert.assertTrue("Clock Log 5000 is created by Missed Punch", isMissedPunch);
		
		isMissedPunch = TkServiceLocator.getClockLogService().isClockLogCreatedByMissedPunch("5001");
		Assert.assertFalse("Clock Log 5001 is NOT created by Missed Punch", isMissedPunch);
	}
}
