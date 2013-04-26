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
package org.kuali.hr.lm.accrual.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TKUtils;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.util.LMConstants;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

public class AccrualCategoryMaxBalanceServiceTest extends KPMETestCase {


	/**
	 * Leave Calendar Document Test data
	 */
	private final String USER_ID = "testUser1";
	
	private LeaveCalendarDocument janLCD;
	private CalendarEntry janEntry;
	private LeaveCalendarDocument decLCD;
	private CalendarEntry decEntry;
	
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
		LmServiceLocator.getAccrualService().runAccrual(USER_ID,LM_FROM.toDateTimeAtStartOfDay(),LM_TO.toDateTimeAtStartOfDay(),true,USER_ID);
		janLCD = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(JAN_ID);
		janEntry = janLCD.getCalendarEntry();
		decLCD = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(DEC_ID);
		decEntry = decLCD.getCalendarEntry();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetMaxBalanceViolationsLeaveApprove() throws Exception {
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
		Interval interval = new Interval(janEntry.getBeginPeriodDate().getTime(),janEntry.getEndPeriodDate().getTime());
		
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
			if(interval.contains(violation.getLeaveDate().getTime()))
				eligibleTransfers.add(violation);
		}
		// 6 accrual categories over max balance * 3 leave blocks ( 2 created on leave period end date + 1 created on calendar
		// year end date )
		assertEquals(6, eligibleTransfers.size());
		//assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
	}

	@Test
	public void testGetMaxBalanceViolationsYearEnd() throws Exception {
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END))
			rules.add(HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));
		
		// Set should contain an accrual category whose rule's max balance is trumped by an employee override.
		// Comparing accrued balance to a rule's defined max balance is insufficient for testing
		// whether or not an accrual category is indeed over it's balance limit. Same can be said for FTE-proration.
		LeaveSummary summary = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			BigDecimal maxBalance = aRule.getMaxBalance();
			EmployeeOverride mbOverride = LmServiceLocator.getEmployeeOverrideService().getEmployeeOverride(USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MB",
					janEntry.getBeginPeriodFullDateTime().toLocalDate());
			if(ObjectUtils.isNotNull(mbOverride))
				maxBalance = new BigDecimal(mbOverride.getOverrideValue());
			assertNotNull("eligible accrual category has no balance limit",maxBalance);
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(maxBalance) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnDemand() throws Exception {
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());

		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			rules.add(HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnYearEndCaseOne() throws Exception {
		//calendar entry is not the last calendar entry of the leave plan's calendar year.
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(decEntry, USER_ID);
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		
		LeaveBlock usage = new LeaveBlock();
		
		usage.setAccrualCategory("ye-xfer");
		usage.setAccrualGenerated(true);
		usage.setLeaveAmount(new BigDecimal(-34));
		usage.setLeaveLocalDate(TKUtils.formatDateString("12/28/2012"));
		usage.setDocumentId(DEC_ID);
		usage.setPrincipalId(USER_ID);
		usage.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		usage.setEarnCode("EC2");
		usage.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
		usage.setBlockId(0L);
		
		KRADServiceLocator.getBusinessObjectService().save(usage);
		
		maxBalanceViolations = eligibilityTestHelper(decEntry, USER_ID);

		//The above leave block should reduce balance of ye-xfer under max limit, reducing the number of violations by 1.
		assertEquals(7, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		
		// adding an accrual block beyond the underlying leave calendar end date, but still within the
		// bounds of the time period, should not re-mark this accrual category over max balance.
/*		usage = new LeaveBlock();
		
		usage.setAccrualCategory("la-xfer");
		usage.setAccrualGenerated(false);
		usage.setLeaveAmount(new BigDecimal(10));
		usage.setLeaveDate(TKUtils.formatDateString("01/01/2012"));
		usage.setDocumentId(TSD_END_DEC_PERIOD_ID);
		usage.setPrincipalId(TS_USER_ID);
		usage.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		usage.setEarnCode("EC1");
		usage.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
		usage.setBlockId(0L);
		
		KRADServiceLocator.getBusinessObjectService().save(usage);
		
		assertEquals(5, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());*/
		
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnYearEndCaseTwo() throws Exception {
		//calendar entry is the last calendar entry of the leave plan's calendar year.
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
	}
	
	/**
	 * 
	 * TIMESHEET ELIGIBLE TESTS
	 * 
	 */
	
	@Test
	public void testGetMaxBalanceViolationsLeaveApproveForTimesheetCaseOne() throws Exception {
		//Timesheet contains leave period ending 12/11/2011
		//accruals occur on 12/01/2011
		LmServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM.toDateTimeAtStartOfDay(),TK_TO.toDateTimeAtStartOfDay(),true,TS_USER_ID);
		midDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_MID_DEC_PERIOD_ID);
		midDecTSDEntry = midDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(midDecTSDEntry, TS_USER_ID);
		
		assertEquals(12, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		//Assert correct number of transfer eligible for frequency
		List<LeaveBlock> eligibleLeaveApproveActions = new ArrayList<LeaveBlock>();
		Interval interval = new Interval(midDecTSDEntry.getBeginPeriodDate().getTime(),midDecTSDEntry.getEndPeriodDate().getTime());
		List<CalendarEntry> entries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate("3", interval.getStart(), interval.getEnd());
		assertEquals("There should be one leave entry ending within this timesheet", 1, entries.size());
		Interval leaveInterval = new Interval(midDecTSDEntry.getBeginPeriodDate().getTime(),entries.get(0).getEndPeriodDate().getTime());
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())) {
				eligibleLeaveApproveActions.add(violation);
			}
		}
		assertEquals(6, eligibleLeaveApproveActions.size());
	}
	
	@Test
	public void testGetMaxBalanceViolationsYearEndForTimesheetCaseOne() throws Exception {
		//Timesheet contains leave period ending 12/11/2011
		//this is not the last leave period of the leave plan's calendar year.
		LmServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM.toDateTimeAtStartOfDay(),TK_TO.toDateTimeAtStartOfDay(),true,TS_USER_ID);
		midDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_MID_DEC_PERIOD_ID);
		midDecTSDEntry = midDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(midDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		List<LeaveBlock> eligibleYearEndActions = new ArrayList<LeaveBlock>();
		Interval interval = new Interval(midDecTSDEntry.getBeginPeriodDate().getTime(),midDecTSDEntry.getEndPeriodDate().getTime());
		List<CalendarEntry> entries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate("3", interval.getStart(), interval.getEnd());
		assertEquals("There should be one leave entry ending within this timesheet", 1, entries.size());
		Interval leaveInterval = new Interval(midDecTSDEntry.getBeginPeriodDate().getTime(),entries.get(0).getEndPeriodDate().getTime());
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())
				&& HrServiceLocator.getLeavePlanService().isLastCalendarPeriodOfLeavePlan(entries.get(0), "testLP", TK_TO)) {
				eligibleYearEndActions.add(violation);
			}
		}
		assertEquals(0, eligibleYearEndActions.size());
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnDemandForTimesheetCaseOne() throws Exception {
		//Timesheet contains leave period ending 12/11/2011
		LmServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM.toDateTimeAtStartOfDay(),TK_TO.toDateTimeAtStartOfDay(),true,TS_USER_ID);
		midDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_MID_DEC_PERIOD_ID);
		midDecTSDEntry = midDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(midDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
	}
	
	@Test
	public void testGetMaxBalanceViolationsLeaveApproveForTimesheetCaseThree() throws Exception {
		//Timesheet contains leave period ending 12/25/2011
		LmServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM.toDateTimeAtStartOfDay(),TK_TO.toDateTimeAtStartOfDay(),true,TS_USER_ID);
		endDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_DEC_PERIOD_ID);
		endDecTSDEntry = endDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);

		assertEquals("Incorrect number of max balance violations", 18, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		//Assert correct base number of transfer eligible for frequency
		List<LeaveBlock> eligibleLeaveApproveActions = new ArrayList<LeaveBlock>();
		Interval interval = new Interval(endDecTSDEntry.getBeginPeriodDate().getTime(),endDecTSDEntry.getEndPeriodDate().getTime());
		List<CalendarEntry> entries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate("3", interval.getStart(), interval.getEnd());
		assertTrue("There should only be one leave period that ends within any given time calendar", entries.size() == 1);
		CalendarEntry leaveEntry = entries.get(0);
		Interval leaveInterval = new Interval(endDecTSDEntry.getBeginPeriodDate().getTime(),leaveEntry.getEndPeriodDate().getTime());
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())) {
				eligibleLeaveApproveActions.add(violation);
			}
		}
		assertEquals("Incorrect number of eligible leave approve actions", 6, eligibleLeaveApproveActions.size());

		LeaveBlock usage = new LeaveBlock();
		
		usage.setAccrualCategory("la-xfer");
		usage.setAccrualGenerated(false);
		usage.setLeaveAmount(new BigDecimal(-30));
		// 12/15/2011 <= leave date < 12/25/2011
		usage.setLeaveLocalDate(TKUtils.formatDateString("12/23/2011"));
		usage.setDocumentId(TSD_END_DEC_PERIOD_ID);
		usage.setPrincipalId(TS_USER_ID);
		usage.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		usage.setEarnCode("EC1");
		usage.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
		usage.setBlockId(0L);
		
		KRADServiceLocator.getBusinessObjectService().save(usage);
		
		maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);
		assertEquals("Incorrect number of max balance violation", 15, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());

		//The above leave block should remove la-xfer from eligibility, reducing the number of eligibilities by 1.
		List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
		
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())) {
				eligibleTransfers.add(violation);
			}
		}
		//actual reduction is by 2...
		assertEquals(5, eligibleTransfers.size());
		
		// adding an accrual block beyond the underlying leave calendar end date, but still within the
		// bounds of the time period, should re-mark this accrual category over max balance, but should not be eligible for action
		usage = new LeaveBlock();
		
		usage.setAccrualCategory("la-xfer");
		usage.setAccrualGenerated(false);
		usage.setLeaveAmount(new BigDecimal(30));
		usage.setLeaveLocalDate(TKUtils.formatDateString("12/28/2011"));
		usage.setDocumentId(TSD_END_DEC_PERIOD_ID);
		usage.setPrincipalId(TS_USER_ID);
		usage.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		usage.setEarnCode("EC1");
		usage.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
		usage.setBlockId(0L);
		
		KRADServiceLocator.getBusinessObjectService().save(usage);
		
		maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);
		assertEquals("Incorrect number of leave approve violations", 18, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		eligibleTransfers = new ArrayList<LeaveBlock>();
		
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())) {
				eligibleTransfers.add(violation);
			}
		}
		assertEquals(5, eligibleTransfers.size());
	}
	
	@Test
	public void testGetMaxBalanceViolationsLeaveApproveForTimesheetCaseTwo() throws Exception {
		//Timesheet includes the leave calendar end period, but does not include the leave plan's start date.
		LmServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM.toDateTimeAtStartOfDay(),TK_TO.toDateTimeAtStartOfDay(),true,TS_USER_ID);
		endDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_DEC_PERIOD_ID);
		endDecTSDEntry = endDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);
		//Assert correct number of violations
		assertEquals("Incorrect number of leave approve violations", 18, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());

		List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
		Interval interval = new Interval(endDecTSDEntry.getBeginPeriodDate().getTime(),endDecTSDEntry.getEndPeriodDate().getTime());
		List<CalendarEntry> entries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate("3", interval.getStart(), interval.getEnd());
		assertTrue("There should only be one leave period that ends within any given time calendar", entries.size() == 1);
		CalendarEntry leaveEntry = entries.get(0);
		Interval leaveInterval = new Interval(endDecTSDEntry.getBeginPeriodDate().getTime(),leaveEntry.getEndPeriodDate().getTime());
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())) {
				eligibleTransfers.add(violation);
			}
		}
		//Assert correct number of actions eligible for frequency
		assertEquals(6, eligibleTransfers.size());
	}
	
	@Test
	public void testGetMaxBalanceViolationsYearEndForTimesheetCaseTwo() throws Exception {
		//Timesheet includes the leave calendar end period, but does not include the leave plan's start date.
		LmServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM.toDateTimeAtStartOfDay(),TK_TO.toDateTimeAtStartOfDay(),true,TS_USER_ID);
		endDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_DEC_PERIOD_ID);
		endDecTSDEntry = endDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		
		List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
		Interval interval = new Interval(endDecTSDEntry.getBeginPeriodDate().getTime(),endDecTSDEntry.getEndPeriodDate().getTime());
		List<CalendarEntry> entries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate("3", interval.getStart(), interval.getEnd());
		assertTrue("There should only be one leave period that ends within any given time calendar", entries.size() == 1);
		CalendarEntry leaveEntry = entries.get(0);
		Interval leaveInterval = new Interval(endDecTSDEntry.getBeginPeriodDate().getTime(),leaveEntry.getEndPeriodDate().getTime());
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())) {
				eligibleTransfers.add(violation);
			}
		}
		
		assertEquals("There should be no eligible max balance actions", 0, eligibleTransfers.size());
	}
	
	@Test
	public void testGetMaxBalanceViolationsYearEndForTimesheetCaseThree() throws Exception {
		//Timesheet includes the leave calendar end period, which is the leave plan's final period.
		LmServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM.toDateTimeAtStartOfDay(),TK_TO.toDateTimeAtStartOfDay(),true,TS_USER_ID);
		endJanTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_JAN_PERIOD_ID);
		endJanTSDEntry = endJanTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endJanTSDEntry, TS_USER_ID);

		assertEquals(24, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());

		//Assert correct number of transfer eligible for frequency
		List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
		Interval interval = new Interval(endJanTSDEntry.getBeginPeriodDate().getTime(),endJanTSDEntry.getEndPeriodDate().getTime());
		List<CalendarEntry> entries = HrServiceLocator.getCalendarEntryService().getCalendarEntriesEndingBetweenBeginAndEndDate("3", interval.getStart(), interval.getEnd());
		assertTrue("There should only be one leave period that ends within any given time calendar", entries.size() == 1);
		CalendarEntry leaveEntry = entries.get(0);
		Interval leaveInterval = new Interval(endJanTSDEntry.getBeginPeriodDate().getTime(),leaveEntry.getEndPeriodDate().getTime());
		for(LeaveBlock violation : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
			if(leaveInterval.contains(violation.getLeaveDate().getTime())) {
				eligibleTransfers.add(violation);
			}
		}
		
		assertEquals(8, eligibleTransfers.size());
	}
	
	
	private Map<String, Set<LeaveBlock>> eligibilityTestHelper(
			CalendarEntry entry, String principalId) throws Exception {
		return LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(entry, principalId);
	}
}
