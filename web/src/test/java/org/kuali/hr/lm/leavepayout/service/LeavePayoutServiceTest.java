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
package org.kuali.hr.lm.leavepayout.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.leave.payout.LeavePayout;
import org.kuali.hr.tklm.leave.summary.LeaveSummary;
import org.kuali.hr.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.hr.tklm.time.calendar.CalendarEntry;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeavePayoutServiceTest extends KPMETestCase {

	/**
	 * Leave Calendar Document Test data
	 */
	private final String USER_ID = "testUser1";
	
	private LeaveCalendarDocument janLCD;
	private CalendarEntry janEntry;
	private LeaveCalendarDocument decLCD;
	private CalendarEntry decEntry;
	
	private LocalDate janStart;
	private LocalDate decStart;
	
	private final String JAN_ID = "5001";
	private final String DEC_ID = "5000";
	
	/**
	 * Timesheet Document Test Data;
	 */
	
	private final String TS_USER_ID = "testUser2";	
	
	private TimesheetDocument endJanTSD;
	private CalendarEntry endJanTSDEntry;
	private TimesheetDocument midJanTSD;
	private CalendarEntry midJanTSDEntry;
	private TimesheetDocument endDecTSD;
	private CalendarEntry endDecTSDEntry;
	private TimesheetDocument midDecTSD;
	private CalendarEntry midDecTSDEntry;
	
	private final String TSD_MID_DEC_PERIOD_ID = "5000";
	private final String TSD_END_DEC_PERIOD_ID = "5001";
	private final String TSD_MID_JAN_PERIOD_ID = "5002";
	private final String TSD_END_JAN_PERIOD_ID = "5003";

	/**
	 *  Common data
	 */
	
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
	private final String YE_XFER_EO = "5012";
	private final LocalDate LM_FROM = TKUtils.formatDateString("11/01/2012");
	private final LocalDate LM_TO = TKUtils.formatDateString("02/01/2013");
	private final LocalDate TK_FROM = TKUtils.formatDateString("11/01/2011");
	private final LocalDate TK_TO = TKUtils.formatDateString("02/01/2012");
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		TkServiceLocator.getAccrualService().runAccrual(USER_ID,LM_FROM.toDateTimeAtStartOfDay(),LM_TO.toDateTimeAtStartOfDay(),true,USER_ID);
		janLCD = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(JAN_ID);
		janEntry = janLCD.getCalendarEntry();
		janStart = janEntry.getBeginPeriodFullDateTime().toLocalDate();
		decLCD = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(DEC_ID);
		decEntry = decLCD.getCalendarEntry();
		decStart = decEntry.getBeginPeriodFullDateTime().toLocalDate();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	/*****************************
	 * Use-case specific testing *
	 ****************************/
	
	//
	// ACTION_AT_MAX_BALANCE = TRANSFER
	//
	
	@Test
	public void testInitializePayoutNullAccrualRule() throws Exception {
		LeavePayout lp = new LeavePayout();

		LocalDate effectiveDate = decStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, null, BigDecimal.ZERO, effectiveDate);
		assertNull(lp);
	}
	
	@Test
	public void testInitializePayoutNullLeaveSummary() throws Exception {
		LeavePayout lp = new LeavePayout();

		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, OD_XFER, null, LocalDate.now());
		assertNull(lp);
	}
	
	@Test
	public void testInitializePayoutNullAccrualRuleNullLeaveSummary() {
		LeavePayout lp = new LeavePayout();
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, null, null, LocalDate.now());
		assertNull(lp);
	}
	
	@Test
	public void testInitializePayoutOnDemand() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(OD_XFER);
		LocalDate effectiveDate = decStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, OD_XFER, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(1)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount",(new BigDecimal(0)).longValue(), lp.getForfeitedAmount().longValue());
		////assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(0.5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnDemandWithForfeiture() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(OD_XFER);
		LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, OD_XFER, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(10)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount", (new BigDecimal(7)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnYearEnd() throws Exception {
		LeavePayout lp = new LeavePayout();
		TkServiceLocator.getLeaveBlockService().deleteLeaveBlocksForDocumentId(DEC_ID);
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(YE_XFER);
		LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_XFER, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(1)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount",(new BigDecimal(0)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(0.5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnYearEndWithForfeiture() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(YE_XFER);
		LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_XFER, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(10)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount", (new BigDecimal(7)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnLeaveApprove() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(LA_XFER);
		LocalDate effectiveDate = decStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, LA_XFER, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(1)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount",(new BigDecimal(0)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(0.5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnLeaveApproveWithForfeiture() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(LA_XFER);
		LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, LA_XFER, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(10)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount", (new BigDecimal(7)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnDemandMaxCarryOver() throws Exception {
		//N/A - Max Carry Over on Year End payouts.
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(OD_XFER_MAC);
		LocalDate effectiveDate = decStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, OD_XFER_MAC, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(1)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount",(new BigDecimal(0)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(0.5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnYearEndMaxCarryOver() throws Exception {
		/**
		 * decEntry is not the last calendar entry in the leave plan. Want to check amounts for this action & action frequency
		 * without exceeding the payout limit.
		 * 
		 * max payout amount = 10
		 * leave balance = 16
		 * max balance = 15
		 * max carry over = 10
		 * 
		 * all excess should be payoutrable. 1 unit of time for excess over max balance, 5 units of time for
		 * excess over max carry over.
		 * 
		 */
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(YE_XFER_MAC);
		LocalDate effectiveDate = decStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_XFER_MAC, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(6)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount", (new BigDecimal(0)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(3)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
/*	@Test
	public void testInitializePayoutUnderMaxBalanceOnYearEndMaxCarryOver() throws Exception {
		//Create a leave block that will bring the available balance for january down to 14.
		//this balance would be under the max available balance (15), but over the max annual carry over amount.
		//i.o.w., this payout would not due to max balance limit, but max annual carry over.
		//could also simply change the accrual amount.
		LeaveBlock usage = new LeaveBlock();
		usage.setAccrualCategory(YE_XFER_MAC);
		usage.setLeaveLocalDate(LocalDate effectiveDate = janStart.plusDays(5););
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
		
		LeavePayout lp = new LeavePayout();
		janLCD = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(JAN_ID);
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janLCD.getCalendarEntry());
		LocalDate effectiveDate = LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_XFER_MAC, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(4)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount", (new BigDecimal(0)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(2)).longValue(), lp.getAmountPayoutred().longValue());
	}*/
	
	@Test
	public void testInitializePayoutOnYearEndMaxCarryOverWithForfeiture() throws Exception {
		//max bal limit reached and max annual carry over triggererd.
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(YE_XFER_MAC);
		LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_XFER_MAC, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(10)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount", (new BigDecimal(12)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutOnLeaveApproveMaxCarryOver() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(LA_XFER_MAC);
		LocalDate effectiveDate = decStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, LA_XFER_MAC, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(1)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount",(new BigDecimal(0)).longValue(), lp.getForfeitedAmount().longValue());
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(0.5)).longValue(), lp.getAmountPayoutred().longValue());
	}
	
	@Test
	public void testInitializePayoutWithOverrides() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(YE_XFER_EO);
		LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_XFER_EO, aRow.getAccruedBalance(), effectiveDate);
		assertEquals("payoutOnDemand payout amount", (new BigDecimal(7)).longValue(), lp.getPayoutAmount().longValue());
		assertEquals("payoutOnDemand forfeited amount",(new BigDecimal(20)).longValue(), lp.getForfeitedAmount().longValue());
		// max balance payout conversion factor is undefined for YE_XFER_EO
		//assertEquals("payoutOnDemand amount payoutred", (new BigDecimal(7)).longValue(), lp.getAmountPayoutred().longValue());
	}
	/**
	 * End Use-case testing
	 */
	
	@Test
	public void testPayoutNullLeavePayout() {
		LeavePayout LeavePayout = null;
		try {
			LeavePayout = TkServiceLocator.getLeavePayoutService().payout(LeavePayout);
		} catch (RuntimeException re) {
			assertTrue(re.getMessage().contains("did not supply a valid LeavePayout object"));
		}
	}
	
	@Test
	public void testPayoutWithZeroPayoutAmount() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(YE_LOSE);
		LocalDate effectiveDate = janStart.plusDays(3);
		//lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_LOSE, aRow.getAccruedBalance(), effectiveDate);
		lp.setPayoutAmount(BigDecimal.ZERO);
		lp = TkServiceLocator.getLeavePayoutService().payout(lp);
		LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getForfeitedLeaveBlockId());
		LeaveBlock payoutLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getPayoutLeaveBlockId());
		LeaveBlock payoutFromLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getPayoutFromLeaveBlockId());
		assertTrue("forfeited leave block should not exist", ObjectUtils.isNull(forfeitedLeaveBlock));
		assertTrue("accrued leave block should not exist",ObjectUtils.isNull(payoutLeaveBlock));
		assertTrue("debited leave block should not exist",ObjectUtils.isNull(payoutFromLeaveBlock));
	}
	
	@Test
	public void testPayoutWithZeroForfeiture() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(OD_XFER);
		LocalDate effectiveDate = decStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, OD_XFER, aRow.getAccruedBalance(), effectiveDate);
		lp = TkServiceLocator.getLeavePayoutService().payout(lp);
		LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getForfeitedLeaveBlockId());
		LeaveBlock payoutLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getPayoutLeaveBlockId());
		LeaveBlock payoutFromLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getPayoutFromLeaveBlockId());
		assertEquals("accrued leave block leave amount incorrect", (new BigDecimal(1)).longValue(), payoutLeaveBlock.getLeaveAmount().longValue());
		assertTrue("forfeited leave block should not exist",ObjectUtils.isNull(forfeitedLeaveBlock));
		assertEquals("payouted leave block leave amount incorrect", (new BigDecimal(-1)).longValue(), payoutFromLeaveBlock.getLeaveAmount().longValue());
	}
	
	@Test
	public void testPayoutWithThreeLeaveBlocks() throws Exception {
		LeavePayout lp = new LeavePayout();
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		LeaveSummaryRow aRow = summary.getLeaveSummaryRowForAccrualCategory(YE_XFER);
		LocalDate effectiveDate = janStart.plusDays(3);
		lp = TkServiceLocator.getLeavePayoutService().initializePayout(USER_ID, YE_XFER, aRow.getAccruedBalance(), effectiveDate);
		lp = TkServiceLocator.getLeavePayoutService().payout(lp);
		LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getForfeitedLeaveBlockId());
		LeaveBlock payoutLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getPayoutLeaveBlockId());
		LeaveBlock payoutFromLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lp.getPayoutFromLeaveBlockId());
		assertEquals("forfeited leave block leave amount incorrect", (new BigDecimal(-7)).longValue(), forfeitedLeaveBlock.getLeaveAmount().longValue());
		assertEquals((new BigDecimal(10)).longValue(), payoutLeaveBlock.getLeaveAmount().longValue());
		assertEquals((new BigDecimal(-10)).longValue(), payoutFromLeaveBlock.getLeaveAmount().longValue());
	}
	
	//TODO: write tests for adjusted max balance cases - i.e. FTE < 1, employee override's w/ type MAX_BALANCE
	
	@Test
	public void testSubmitToWorkflow() {
		assertNull(null);
	}

}
