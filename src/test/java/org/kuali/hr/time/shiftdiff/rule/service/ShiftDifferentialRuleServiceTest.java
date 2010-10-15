package org.kuali.hr.time.shiftdiff.rule.service;

import java.util.List;

import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;

public class ShiftDifferentialRuleServiceTest extends TkTestCase {

	@Test
	public void testProcessShiftDifferentialRules() throws Exception {
		ShiftDifferentialRuleService sdrs = TkServiceLocator.getShiftDifferentialRuleService();
		assertNotNull("No service found.", sdrs);
		
		List<ShiftDifferentialRule> rules = sdrs.getShiftDifferentalRules("NOWHERE", "NOTHING", "NOTHING", TKUtils.getCurrentDate());
		assertEquals("List should be empty", 0, rules.size());
		// TODO :: TDD This Rule
	}
}
