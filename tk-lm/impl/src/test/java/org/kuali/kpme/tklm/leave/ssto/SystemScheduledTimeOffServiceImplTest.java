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
package org.kuali.kpme.tklm.leave.ssto;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.api.leave.timeoff.SystemScheduledTimeOffContract;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;

@IntegrationTest
public class SystemScheduledTimeOffServiceImplTest extends TKLMIntegrationTestCase {
	
	@Test
	public void testSearchSystemScheduledTimeOffs() throws Exception {
		List<? extends SystemScheduledTimeOffContract> allResults = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffs("admin", null, null, null, null, null, null, null,null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<? extends SystemScheduledTimeOffContract> restrictedResults = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffs("testuser6", null, null, null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}

}
