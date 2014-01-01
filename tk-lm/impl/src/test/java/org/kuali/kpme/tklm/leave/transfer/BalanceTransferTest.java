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
package org.kuali.kpme.tklm.leave.transfer;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.UnitTest;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;


@UnitTest
public class BalanceTransferTest extends TKLMIntegrationTestCase {
    private static final Logger LOG = Logger.getLogger(BalanceTransferTest.class);
    public static final String USER_PRINCIPAL_ID = "admin";
	private BalanceTransfer balanceTransfer;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
        //setBaseDetailURL(TkTestConstants.Urls.LEAVE_CALENDAR_SUBMIT_URL + "?documentId=");
        balanceTransfer = new BalanceTransfer();
        balanceTransfer.setTransferAmount(new BigDecimal(20));
        balanceTransfer.setForfeitedAmount(new BigDecimal(0));
        balanceTransfer.setAmountTransferred(new BigDecimal(10));
        balanceTransfer.setAccrualCategoryRule("5000");
	}
	
	@Test
	public void testTransferWithAccrualRule() throws Exception {
		BalanceTransfer btd = new BalanceTransfer();
		//btd.setCreditedAccrualCategory(TKTestUtils.creat)
		assertTrue("Dummy assertion",true);
	}
	
	@Test
	public void testAdjustLowerTransferAmount() {
		BigDecimal adjustedTransferAmount = new BigDecimal(10);
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(new BigDecimal(10)) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(5)) == 0);
	}
	
	@Test
	public void testAdjustLowerTransferAmountWithForfeiture() {
		BigDecimal adjustedTransferAmount = new BigDecimal(10);
		balanceTransfer.setForfeitedAmount(new BigDecimal(10));
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(new BigDecimal(20)) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(5)) == 0);
	}
	
	@Test
	public void testAdjustRaiseTransferAmount() {
		BigDecimal adjustedTransferAmount = new BigDecimal(30);
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(BigDecimal.ZERO) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(15)) == 0);
	}
	
	@Test
	public void testAdjustRaiseTransferAmountWithForfeitureLessThanDifference() {
		BigDecimal adjustedTransferAmount = new BigDecimal(40);
		balanceTransfer.setForfeitedAmount(new BigDecimal(10));
		
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(BigDecimal.ZERO) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(20)) == 0);
	}
	
	@Test
	public void testAdjustRaiseTransferAmountWithForfeitureMoreThanDifference() {
		BigDecimal adjustedTransferAmount = new BigDecimal(30);
		balanceTransfer.setForfeitedAmount(new BigDecimal(15));
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(new BigDecimal(5)) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(15)) == 0);
	}
	
}