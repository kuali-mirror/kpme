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
package org.kuali.kpme.tklm.time.rule.differential;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMEUnitTestCase;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;

public class ShiftDifferentialRuleServiceTest extends KPMEUnitTestCase {
	
	@Test
	public void testSearchShiftDifferentialRules() throws Exception {
		List<ShiftDifferentialRule> allResults = TkServiceLocator.getShiftDifferentialRuleService().getShiftDifferentialRules("admin", null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<ShiftDifferentialRule> restrictedResults = TkServiceLocator.getShiftDifferentialRuleService().getShiftDifferentialRules("fran", null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}
	
}