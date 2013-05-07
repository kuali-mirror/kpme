package org.kuali.hr.lm.timeoff.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;

public class SystemScheduledTimeOffServiceImplTest extends KPMETestCase {
	
	@Test
	public void testSearchSystemScheduledTimeOffs() throws Exception {
		List<SystemScheduledTimeOff> allResults = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffs("admin", null, null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<SystemScheduledTimeOff> restrictedResults = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffs("testuser6", null, null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}

}
