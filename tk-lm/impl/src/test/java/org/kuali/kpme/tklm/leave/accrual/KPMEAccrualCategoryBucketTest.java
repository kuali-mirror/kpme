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
package org.kuali.kpme.tklm.leave.accrual;

import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.leave.accrual.bucket.KPMEAccrualCategoryBucket;

@IntegrationTest
public class KPMEAccrualCategoryBucketTest extends TKLMIntegrationTestCase {

	KPMEAccrualCategoryBucket bucket;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		bucket = new KPMEAccrualCategoryBucket();//LmServiceLocator.getKPMEAccrualCategoryBucket();
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
/*	@Test
	public void testInitiate() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assertNotNull(bucket);
		PrincipalHRAttributes principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar("admin", DateTime.now().toLocalDate());
		principalCalendar.setLeavePlan("TST");
		bucket.initialize(principalCalendar);
		LinkedHashMap<String, List<LeaveBalance>> balances = bucket.getLeaveBalances();
		assertEquals("Map of balances should house balances for one accrual category, TEX", 1, balances.size());
		List<LeaveBalance> leaveBalances = balances.get("1");
		assertEquals("There should be 7 LeaveBalances in this list", 7, leaveBalances.size());
		for(LeaveBalance balance : leaveBalances) {
			assertTrue("This LeaveBalance should be one of the following instances",
					   balance instanceof AccruedLeaveBalance
					|| balance instanceof PendingLeaveBalance
					|| balance instanceof AvailableLeaveBalance
					|| balance instanceof YearToDateEarnedLeaveBalance
					|| balance instanceof YearToDateUsageLeaveBalance
					|| balance instanceof FmlaLeaveBalance
					|| balance instanceof CarryOverLeaveBalance);
		}
	}*/
	
	@Test
	public void testAddLeaveBlock() {
		assertNull(null);
	}
	
	@Test
	public void testRemoveLeaveBlock() {
		assertNull(null);
	}
	
	@Test
	public void testEditLeaveBlock() {
		assertNull(null);
	}
	
	@Test
	public void testGetLeaveBalance() {
		assertNull(null);
	}
	
	@Test
	@Ignore
	public void testGetLeaveBalanceForAccrualCategory() {
		assertNull(null);
	}
	
	@Test
	public void testCalculateLeaveBalanceForPreviousCalendar() {
		assertNull(null);
	}
	
	@Test
	public void testCalclulateLeaveBalanceForNextCalendar() {
		assertNull(null);
	}

}
