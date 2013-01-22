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
package org.kuali.hr.lm.balancetransfer.service;

import static org.junit.Assert.*;

import edu.emory.mathcs.backport.java.util.Collections;
import groovy.lang.Singleton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.cxf.service.invoker.SingletonFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlock.Builder;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferServiceTest extends KPMETestCase {

	private LeaveCalendarDocument janLCD;
	private CalendarEntries janEntry;
	private LeaveCalendarDocument decLCD;
	private CalendarEntries decEntry;
	
	private Date janStart;
	private Date janEnd;
	private Date decStart;
	private Date decEnd;
	
	private final String OD_XFER = "5000";
	private final String YE_XFER = "5001";
	private final String LA_XFER = "5002";
	private final String OD_XFER_MAC = "5003";
	private final String YE_XFER_MAC = "5004";
	private final String LA_XFER_MAC = "5005";
	private final String OD_LOSE = "5006";
	private final String YE_LOSE = "5007";
	private final String LA_LOSE = "5008";
	private final String OD_LOSE_MAC = "5009";
	private final String YE_LOSE_MAC = "5010";
	private final String LA_LOSE_MAC = "5011";
	
	private final String JAN_ID = "5001";
	private final String DEC_ID = "5000";
	
	private final String USER_ID = "testUser1";
	


	@Before
	public void setUp() throws Exception {
		super.setUp();
		java.sql.Date from = TKUtils.formatDateString("11/01/2012");
		java.sql.Date to = TKUtils.formatDateString("02/01/2013");
		TkServiceLocator.getAccrualService().runAccrual(USER_ID,from,to,true,USER_ID);
		janLCD = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(JAN_ID);
		janEntry = janLCD.getCalendarEntry();
		janStart = janEntry.getBeginPeriodDate();
		janEnd = janEntry.getEndPeriodDate();
		decLCD = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(DEC_ID);
		decEntry = decLCD.getCalendarEntry();
		decStart = decEntry.getBeginPeriodDate();
		decEnd = decEntry.getEndPeriodDate();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		TkServiceLocator.getLeaveBlockService().deleteLeaveBlocksForDocumentId(DEC_ID);
		TkServiceLocator.getLeaveBlockService().deleteLeaveBlocksForDocumentId(JAN_ID);
	}
	
	/*****************************
	 * Use-case specific testing *
	 ****************************/
	
	//
	// ACTION_AT_MAX_BALANCE = TRANSFER
	//
	@Test
	public void testInitializeTransferOnDemand() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(decStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, OD_XFER, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(1)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(0)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0.5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnDemandWithForfeiture() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, OD_XFER, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(10)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount", (new BigDecimal(7)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnYearEnd() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		TkServiceLocator.getLeaveBlockService().deleteLeaveBlocksForDocumentId(DEC_ID);
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_XFER, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(1)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(0)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0.5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnYearEndWithForfeiture() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_XFER, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(10)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount", (new BigDecimal(7)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnLeaveApprove() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(decStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, LA_XFER, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(1)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(0)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0.5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnLeaveApproveWithForfeiture() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, LA_XFER, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(10)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount", (new BigDecimal(7)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnDemandMaxCarryOver() throws Exception {
		//N/A - Max Carry Over on Year End transfers.
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(decStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, OD_XFER_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(1)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(0)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0.5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnYearEndMaxCarryOver() throws Exception {
		/**
		 * decEntry is not the last calendar entry in the leave plan. Want to check amounts for this action & action frequency
		 * without exceeding the transfer limit.
		 * 
		 * max transfer amount = 10
		 * leave balance = 16
		 * max balance = 15
		 * max carry over = 10
		 * 
		 * all excess should be transferrable. 1 unit of time for excess over max balance, 5 units of time for
		 * excess over max carry over.
		 * 
		 */
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(decStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_XFER_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(6)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount", (new BigDecimal(0)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(3)).longValue(), bt.getAmountTransferred().longValue());
	}
	
/*	@Test
	public void testInitializeTransferUnderMaxBalanceOnYearEndMaxCarryOver() throws Exception {
		//Create a leave block that will bring the available balance for january down to 14.
		//this balance would be under the max available balance (15), but over the max annual carry over amount.
		//i.o.w., this transfer would not due to max balance limit, but max annual carry over.
		//could also simply change the accrual amount.
		LeaveBlock usage = new LeaveBlock();
		usage.setAccrualCategory(YE_XFER_MAC);
		usage.setLeaveDate(new java.sql.Date(DateUtils.addDays(janStart,5).getTime()));
		usage.setLeaveAmount(new BigDecimal(-18));
		usage.setPrincipalId(USER_ID);
		usage.setAccrualGenerated(false);
		usage.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		usage.setDocumentId(janLCD.getDocumentId());
		usage.setLmLeaveBlockId("99999");
		usage.setEarnCode("EC5");
		usage.setBlockId(0L);
		usage.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR);
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
		leaveBlocks.add(usage);
		TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(leaveBlocks);
		
		BalanceTransfer bt = new BalanceTransfer();
		janLCD = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(JAN_ID);
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janLCD.getCalendarEntry());
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_XFER_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(4)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount", (new BigDecimal(0)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(2)).longValue(), bt.getAmountTransferred().longValue());
	}*/
	
	@Test
	public void testInitializeTransferOnYearEndMaxCarryOverWithForfeiture() throws Exception {
		//max bal limit reached and max annual carry over triggererd.
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_XFER_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(10)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount", (new BigDecimal(12)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeTransferOnLeaveApproveMaxCarryOver() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(decStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, LA_XFER_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(1)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(0)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0.5)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	//
	// ACTION_AT_MAX_BALANCE = LOSE
	//
	@Test
	public void testInitializeLoseOnDemand() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(decStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, OD_LOSE, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(0)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(1)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeLoseOnYearEnd() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_LOSE, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(0)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(17)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeLoseOnLeaveApprove() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, LA_LOSE, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(0)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(17)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeLoseOnDemandMaxCarryOver() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, OD_LOSE_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(0)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(17)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeLoseOnYearEndMaxCarryOver() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_LOSE_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(0)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(22)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0)).longValue(), bt.getAmountTransferred().longValue());
	}
	
	@Test
	public void testInitializeLoseOnLeaveApproveMaxCarryOver() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, LA_LOSE_MAC, summary, effectiveDate);
		assertEquals("transferOnDemand transfer amount", (new BigDecimal(0)).longValue(), bt.getTransferAmount().longValue());
		assertEquals("transferOnDemand forfeited amount",(new BigDecimal(17)).longValue(), bt.getForfeitedAmount().longValue());
		assertEquals("transferOnDemand amount transferred", (new BigDecimal(0)).longValue(), bt.getAmountTransferred().longValue());
	}
	/**
	 * End Use-case testing
	 */
	
	@Test
	public void testTransferNullBalanceTransfer() {
		BalanceTransfer balanceTransfer = null;
		try {
			balanceTransfer = TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
		} catch (RuntimeException re) {
			assertTrue(re.getMessage().contains("did not supply a valid BalanceTransfer object"));
		}
	}
	
	@Test
	public void testTransferWithZeroTransferAmount() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_LOSE, summary, effectiveDate);
		bt = TkServiceLocator.getBalanceTransferService().transfer(bt);
		LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getForfeitedLeaveBlockId());
		LeaveBlock accruedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getAccruedLeaveBlockId());
		LeaveBlock debitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getDebitedLeaveBlockId());
		assertEquals("forfeited leave block leave amount incorrect",forfeitedLeaveBlock.getLeaveAmount().longValue(), (new BigDecimal(-17)).longValue());
		assertTrue("accrued leave block should not exist",ObjectUtils.isNull(accruedLeaveBlock));
		assertTrue("debited leave block should not exist",ObjectUtils.isNull(debitedLeaveBlock));
	}
	
	@Test
	public void testTransferWithZeroForfeiture() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(decStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, OD_XFER, summary, effectiveDate);
		bt = TkServiceLocator.getBalanceTransferService().transfer(bt);
		System.out.println("finished transfer");
		LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getForfeitedLeaveBlockId());
		LeaveBlock accruedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getAccruedLeaveBlockId());
		LeaveBlock debitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getDebitedLeaveBlockId());
		assertEquals("accrued leave block leave amount incorrect",accruedLeaveBlock.getLeaveAmount().longValue(), (new BigDecimal(0.5)).longValue());
		assertTrue("forfeited leave block should not exist",ObjectUtils.isNull(forfeitedLeaveBlock));
		assertEquals("transfered leave block leave amount incorrect",debitedLeaveBlock.getLeaveAmount().longValue(), (new BigDecimal(-1)).longValue());
	}
	
	@Test
	public void testTransferWithThreeLeaveBlocks() throws Exception {
		BalanceTransfer bt = new BalanceTransfer();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		java.sql.Date effectiveDate = new java.sql.Date(DateUtils.addDays(janStart,3).getTime());
		bt = TkServiceLocator.getBalanceTransferService().initializeTransfer(USER_ID, YE_XFER, summary, effectiveDate);
		bt = TkServiceLocator.getBalanceTransferService().transfer(bt);
		LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getForfeitedLeaveBlockId());
		LeaveBlock accruedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getAccruedLeaveBlockId());
		LeaveBlock debitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(bt.getDebitedLeaveBlockId());
		assertEquals("forfeited leave block leave amount incorrect",forfeitedLeaveBlock.getLeaveAmount().longValue(), (new BigDecimal(-7)).longValue());
		assertEquals(accruedLeaveBlock.getLeaveAmount().longValue(), (new BigDecimal(5)).longValue());
		assertEquals(debitedLeaveBlock.getLeaveAmount().longValue(), (new BigDecimal(-10)).longValue());
	}
	
	@Test
	public void testGetEligibleTransfersLeaveApprove() throws Exception {
		List<String> eligibleTransfers = TkServiceLocator.getBalanceTransferService().getEligibleTransfers(janLCD, LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE);
		assertEquals("incorrect number of eligible transfers",eligibleTransfers.size(),4);
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(String eligibleTransfer : eligibleTransfers) {
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer));
		}
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			System.out.println("iterating");
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetEligibleTransfersYearEnd() throws Exception {
		List<String> eligibleTransfers = TkServiceLocator.getBalanceTransferService().getEligibleTransfers(janLCD, LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END);
		assertEquals("incorrect number of eligible transfers",eligibleTransfers.size(),4);
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(String eligibleTransfer : eligibleTransfers) {
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer));
		}
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			System.out.println("iterating");
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetEligibleTransfersOnDemand() throws Exception {
		List<String> eligibleTransfers = TkServiceLocator.getBalanceTransferService().getEligibleTransfers(janLCD, LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND);
		assertEquals("incorrect number of eligible transfers",eligibleTransfers.size(),4);
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(String eligibleTransfer : eligibleTransfers) {
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer));
		}
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			System.out.println("iterating");
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testSubmitToWorkflow() {
		assertNull(null);
	}
	
}
