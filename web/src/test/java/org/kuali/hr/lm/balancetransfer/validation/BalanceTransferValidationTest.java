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
package org.kuali.hr.lm.balancetransfer.validation;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.leave.transfer.BalanceTransfer;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class BalanceTransferValidationTest extends KPMETestCase {

	private BusinessObjectService boService;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
    	boService = KRADServiceLocator.getBusinessObjectService();
    	clearBusinessObjects(BalanceTransfer.class);
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testOverMaxCarryOver() throws Exception{
		assertTrue("Dummy assertion", true);
	}
	
	/*
	@Test
	public void testAtMaxCarryOver() throws Exception{
		assertTrue(true);
	}
	
	@Test
	public void testUnderMaxBalanceBalanceTransfer() throws Exception{
		//ensure that balance transfer isn't triggered by accrual categories whose balance is
		//less than the max balance.
		//Exception could be balance transfers for termination/retirement where max balance
		//is not yet reached.
		assertTrue(true);
	}
	
	@Test
	public void testOverMaxBalanceBalanceTransfer() throws Exception{
		assertTrue(true);
	}
	
	@Test
	public void testAtMaxBalanceBalanceTransfer() throws Exception{
		assertTrue(true);
	}
	
	@Test
	public void testBalanceTransferOfPartialFTE() throws Exception{
		//TODO: Balance transfer test of employee having more than one leave eligible job
		// but whose FTE is less than one.
		assertTrue(true);
	}
	
	@Test
	public void testBalanceTransferOfPartialFTECase2() throws Exception{
		//TODO: Balance transfer test of employee having more than one leave eligible job
		// and whose FTE is 1
		assertTrue(true);
	}
	
	@Test
	public void testBalanceTransferOfNonFTEEmployee() throws Exception{
		//TODO: Balance transfer should not be available for employee having no leave
		// eligible job (FTE=0)
		assertTrue(true);
	}
	*/
	
//	/**
//	 * Following tests should be moved to leave calendar to test on-demand balance transfers and payouts.
//	 */
//	@Test
//	public void testOnDemandBalanceTransfer() throws Exception{
//		//TODO: Check to make sure that the "Transfer" button appears when max balance
//		// is reached for a transfer with action frequency "On-Demand" and action "Transfer"
//		// is in effect for accrual category.
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testOnDemandBalancePayout() throws Exception{
//		//TODO: Check to make sure that the "Payout" button appears when max balance
//		// is reached for a transfer with action frequency "On-Demand" and action "payout"
//		// is in effect for accrual category.
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testOnDemandBalanceTransferForMultipleAccrualCategories() throws Exception{
//		//TODO: Test that the "Transfer" button correctly displays when more than one accrual
//		// category reach max balance simultaneously.
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testOnDemandBalancePayoutForMultipleAccrualCategories() throws Exception{
//		//TODO: Test that the "Payout" button displays correctly when more than one accrual
//		// category reach max balance simultaneously.
//		assertTrue(true);
//	}
//	
//	/**
//	 * The following tests require setting up scenarios that occur on the set
//	 * of possible max balance action frequencies. The result of each test should
//	 * be dependent upon a balance transfer document correctly being created for each
//	 * scenario.
//	 */
//	@Test
//	public void testLeaveApproveBalanceTransferService() throws Exception{
//		/**
//		 * TODO: Create the scenario where a balance TRANSFER should occur
//		 * on max balance action frequency of "leave approval".
//		 * Upon submitting a leave calendar document for approval,
//		 * the user should be prompted for approval of balance TRANSFER
//		 * according to the accrual categories max balance rules.
//		 * A successful test would check that, upon affirmative action by the
//		 * user, a balance transfer document should be created, and the
//		 * balances of the respective accrual categories should reflect
//		 * values in-line with the transfer AND accrual category rules.
//		 * (i.e. the max balance conversion factor ).
//		 */
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testLeaveApprovalBalancePayoutService() throws Exception{
//		/**
//		 * TODO: Create the scenario where a balance PAYOUT should occur
//		 * on max balance action frequency of "leave approval".
//		 * Upon submitting a leave calendar document for approval,
//		 * the user should be prompted for approval of balance PAYOUT
//		 * according to the accrual categories max balance rules.
//		 * A successful test would check that, upon affirmative action by the
//		 * user, a balance transfer document is created, and the
//		 * balances of the respective accrual categories should reflect
//		 * values in-line with the transfer AND accrual category rules.
//		 * (i.e. the max balance conversion factor ).
//		 */	
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testYearEndBalanceTransferService() throws Exception{
//		/**
//		 * TODO: Create the scenario where a balance transfer should occur
//		 * on max balance action frequency of "YEAR END".
//		 * Upon submitting the final leave calendar document for approval,
//		 * the user should be prompted for approval of balance transfer
//		 * according to the accrual categories max balance rules.
//		 * A successful test would check that, upon affirmative action by the
//		 * user, a balance transfer document should be created, and the
//		 * balances of the respective accrual categories should reflect
//		 * values in-line with the transfer AND accrual category rules.
//		 * (i.e. the max balance conversion factor ).
//		 */
//		assertTrue(true);
//	}
//	
//	@Test
//	public void testYearEndBalancePayoutService() throws Exception {
//		/**
//		 * TODO: Create the scenario where a balance PAYOUT should occur
//		 * on max balance action frequency of "YEAR END".
//		 * Upon submitting a leave calendar document for approval,
//		 * the user should be prompted for approval of balance PAYOUT
//		 * according to the accrual categories max balance rules.
//		 * A successful test would check that, upon affirmative action by the
//		 * user, a balance transfer document should be created, and the
//		 * balances of the respective accrual categories should reflect
//		 * values in-line with the transfer AND accrual category rules.
//		 * (i.e. the max balance conversion factor ).
//		 * For successful payout, the test must check that a new, previously defined earn code
//		 * should exist for the user, and that this earn code is available for use.
//		 */
//		assertTrue(true);
//	}
	
    @SuppressWarnings("unchecked")
    public void clearBusinessObjects(Class clazz) {
    	boService.deleteMatching(clazz, new HashMap());
    }
}
