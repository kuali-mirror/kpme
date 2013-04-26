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
package org.kuali.hr.lm.permission.service;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.core.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class LMPermissionServiceTest extends KPMETestCase {
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		// change taget person to a non-admin
	    TKContext.setTargetPrincipalId("eric");
	    PrincipalHRAttributes phra = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes("2");
	    phra.setLeaveCalendar("BWS-LM");
	    phra.setLeaveCalObj(HrServiceLocator.getCalendarService().getCalendarByGroup("BWS-LM"));
	    KRADServiceLocator.getBusinessObjectService().save(phra);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	    TKContext.setTargetPrincipalId("admin");
	    PrincipalHRAttributes phra = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes("2");
	    phra.setLeaveCalendar(null);
	    phra.setLeaveCalObj(null);
	    KRADServiceLocator.getBusinessObjectService().save(phra);
	}
	
	@Test
	// added this brand new test for kpme-2109 so this test does not cover all existing scenarios,
	// only system scheduled time off usage leave blocks that can be banked or transferred
	public void testCanDeleteLeaveBlockForSSTOUsageLB() {
		// leave block 6000 is a bankable ssto usage block that is on current leave calendar, 
		// ssto 2000's unused time is "B"
		LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock("6000");
		lb.setLeaveDate(LocalDate.now().toDate());
		boolean deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock(TKContext.getPrincipalId(), lb);
		Assert.assertTrue("Leave Block 6000 should be deletable", deleteFlag);
		
		// leave block 6001 is a ssto usage block that can be transferred, ssto 2001's unused time is "T"
		lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock("6001");
		lb.setLeaveDate(LocalDate.now().toDate());
		deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock(TKContext.getPrincipalId(), lb);
		Assert.assertTrue("Leave Block 6001 should be deletable", deleteFlag);
		
		// leave block 6002 is a ssto usage block that is not on current leave calendar
		lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock("6002");
		deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock(TKContext.getPrincipalId(), lb);
		Assert.assertFalse("Leave Block 6002 should NOT be deletable", deleteFlag);
		
		// leave block 6003 is a ssto accrual block, not a usage, it's leave amount is 8
		lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock("6003");
		lb.setLeaveDate(LocalDate.now().toDate());
		deleteFlag = LmServiceLocator.getLMPermissionService().canDeleteLeaveBlock(TKContext.getPrincipalId(), lb);
		Assert.assertFalse("Leave Block 6003 should NOT be deletable", deleteFlag);
		
		
	}
}
