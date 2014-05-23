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
package org.kuali.kpme.tklm.time.clock.location;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;

@IntegrationTest
public class ClockLocationRuleServiceImplTest extends TKLMIntegrationTestCase {
	
	@Test
	public void testSearchClockLocationRules() throws Exception {
//		List<ClockLocationRule> allResults = TkServiceLocator.getClockLocationRuleService().getClockLocationRules("admin","IU-IN");
//		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
//		
//		List<ClockLocationRule> restrictedResults = TkServiceLocator.getClockLocationRuleService().getClockLocationRules("fran", "IU-IN");
//		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}

}
