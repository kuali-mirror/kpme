package org.kuali.hr.time.clock.location.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;

public class ClockLocationRuleServiceImplTest extends KPMETestCase {
	
	@Test
	public void testSearchClockLocationRules() throws Exception {
		List<ClockLocationRule> allResults = TkServiceLocator.getClockLocationRuleService().getClockLocationRules("admin", null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<ClockLocationRule> restrictedResults = TkServiceLocator.getClockLocationRuleService().getClockLocationRules("fran", null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}

}
