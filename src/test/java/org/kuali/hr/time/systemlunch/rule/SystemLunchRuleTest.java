package org.kuali.hr.time.systemlunch.rule;

import java.sql.Date;

import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class SystemLunchRuleTest extends TkTestCase {
	@Test
	public void testSystemLunchRuleFetch() throws Exception{
		SystemLunchRule systemLunchRule = new SystemLunchRule();
		systemLunchRule.setActive(true);
		systemLunchRule.setShowLunchButton(true);
		systemLunchRule.setEffectiveDate(new Date(System.currentTimeMillis()));
		
		KNSServiceLocator.getBusinessObjectService().save(systemLunchRule);
		
		systemLunchRule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(new Date(System.currentTimeMillis()));
		assertTrue("System lunch rule is pulled back", systemLunchRule!=null);
	}
}
