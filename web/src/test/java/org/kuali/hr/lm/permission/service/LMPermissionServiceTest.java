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
package org.kuali.hr.lm.permission.service;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.calendar.CalendarBo;
import org.kuali.kpme.core.principal.PrincipalHRAttributesBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

@FunctionalTest
public class LMPermissionServiceTest extends KPMEWebTestCase {
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		// change taget person to a non-admin
	    //HrContext.setTargetPrincipalId("eric");
	    PrincipalHRAttributesBo phra = PrincipalHRAttributesBo.from(HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes("2"));
	    phra.setLeaveCalendar("BWS-LM");
	    phra.setLeaveCalObj(CalendarBo.from(HrServiceLocator.getCalendarService().getCalendarByGroup("BWS-LM")));
	    KRADServiceLocatorWeb.getLegacyDataAdapter().save(phra);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	    //HrContext.setTargetPrincipalId("admin");
	    PrincipalHRAttributesBo phra = PrincipalHRAttributesBo.from(HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes("2"));
	    phra.setLeaveCalendar(null);
	    phra.setLeaveCalObj(null);
	    KRADServiceLocatorWeb.getLegacyDataAdapter().save(phra);
	}
	
	@Test
	// added this brand new test for kpme-2109 so this test does not cover all existing scenarios,
	// only system scheduled time off usage leave blocks that can be banked or transferred
	public void testCanDeleteLeaveBlockForSSTOUsageLB() {
		// leave block 6000 is a bankable ssto usage block that is on current leave calendar, 
		// ssto 2000's unused time is "B"
		LeaveBlock.Builder lb = LeaveBlock.Builder.create(LmServiceLocator.getLeaveBlockService().getLeaveBlock("6000"));
		lb.setLeaveDateTime(LocalDate.now().toDateTimeAtStartOfDay());
		boolean deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock("eric", lb.build());
		Assert.assertTrue("Leave Block 6000 should be deletable", deleteFlag);
		
		// leave block 6001 is a ssto usage block that can be transferred, ssto 2001's unused time is "T"
        lb = LeaveBlock.Builder.create(LmServiceLocator.getLeaveBlockService().getLeaveBlock("6001"));
		lb.setLeaveDateTime(LocalDate.now().toDateTimeAtStartOfDay());
		deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock("eric", lb.build());
		Assert.assertTrue("Leave Block 6001 should be deletable", deleteFlag);
		
		// leave block 6002 is a ssto usage block that is not on current leave calendar
        lb = LeaveBlock.Builder.create(LmServiceLocator.getLeaveBlockService().getLeaveBlock("6002"));
		deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock("eric", lb.build());
		Assert.assertFalse("Leave Block 6002 should NOT be deletable", deleteFlag);
		
		// leave block 6003 is a ssto accrual block, not a usage, it's leave amount is 8
        lb = LeaveBlock.Builder.create(LmServiceLocator.getLeaveBlockService().getLeaveBlock("6003"));
        lb.setLeaveDateTime(LocalDate.now().toDateTimeAtStartOfDay());
		deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock("eric", lb.build());
		Assert.assertFalse("Leave Block 6003 should NOT be deletable", deleteFlag);
		
		
	}
}
