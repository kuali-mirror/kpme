package org.kuali.hr.time.earngroup.service;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class EarnGroupServiceTest extends TkTestCase{
	@Test
	public void testEarnGroupFetch() throws Exception{
		EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroup("REG", new Date(System.currentTimeMillis()));
		assertTrue("Test Earn Group fetch failed", earnGroup!=null && StringUtils.equals("REG", earnGroup.getEarnGroup()));
		assertTrue("Test earn group def fetch failed", earnGroup.getEarnGroups()!=null && earnGroup.getEarnGroups().get(0).getTkEarnGroupId()==100);	
	}
	
	
}
