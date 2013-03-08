package org.kuali.hr.lm.accrual.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.util.ObjectUtils;

public class AccrualCategoryMaxBalanceServiceTest extends KPMETestCase {


	/**
	 * Leave Calendar Document Test data
	 */
	private final String USER_ID = "testUser1";
	
	private LeaveCalendarDocument janLCD;
	private CalendarEntries janEntry;
	private LeaveCalendarDocument decLCD;
	private CalendarEntries decEntry;
	
	private Date janStart;
	private Date janEnd;
	private Date decStart;
	private Date decEnd;
	
	private final String JAN_ID = "5001";
	private final String DEC_ID = "5000";
	
	/**
	 * Timesheet Document Test Data;
	 */
	
	private final String TS_USER_ID = "testUser2";	
	
	private TimesheetDocument endJanTSD;
	private CalendarEntries endJanTSDEntry;
	private TimesheetDocument midJanTSD;
	private CalendarEntries midJanTSDEntry;
	private TimesheetDocument endDecTSD;
	private CalendarEntries endDecTSDEntry;
	private TimesheetDocument midDecTSD;
	private CalendarEntries midDecTSDEntry;
	
	private Date midJanStart;
	private Date midJanEnd;
	private Date endDecStart;
	private Date endDecEnd;
	private Date midDecStart;
	private Date midDecEnd;
	
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
	private final java.sql.Date LM_FROM = TKUtils.formatDateString("11/01/2012");
	private final java.sql.Date LM_TO = TKUtils.formatDateString("02/01/2013");
	private final java.sql.Date TK_FROM = TKUtils.formatDateString("11/01/2011");
	private final java.sql.Date TK_TO = TKUtils.formatDateString("02/01/2012");
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		TkServiceLocator.getAccrualService().runAccrual(USER_ID,LM_FROM,LM_TO,true,USER_ID);
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
	}
	
	@Test
	public void testGetMaxBalanceViolationsLeaveApprove() throws Exception {
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		//assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));
			
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}

	@Test
	public void testGetMaxBalanceViolationsYearEnd() throws Exception {
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));
		
		// Set should contain an accrual category whose rule's max balance is trumped by an employee override.
		// Comparing accrued balance to a rule's defined max balance is insufficient for testing
		// whether or not an accrual category is indeed over it's balance limit. Same can be said for FTE-proration.
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			BigDecimal maxBalance = aRule.getMaxBalance();
			EmployeeOverride mbOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MB",
					janEntry.getBeginPeriodDate());
			if(ObjectUtils.isNotNull(mbOverride))
				maxBalance = new BigDecimal(mbOverride.getOverrideValue());
			assertNotNull("eligible accrual category has no balance limit",maxBalance);
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(maxBalance) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnDemand() throws Exception {
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		//assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
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
		assertEquals(0, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, decEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnYearEndCaseTwo() throws Exception {
		//calendar entry is the last calendar entry of the leave plan's calendar year.
		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(janEntry, USER_ID);
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		//assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_ID, janEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			BigDecimal maxBalance = aRule.getMaxBalance();
			EmployeeOverride mbOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MB",
					janEntry.getBeginPeriodDate());
			EmployeeOverride macOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MAC",
					janEntry.getBeginPeriodDate());
			if(ObjectUtils.isNotNull(mbOverride) && ObjectUtils.isNotNull(macOverride))
				maxBalance = new BigDecimal(Math.min(mbOverride.getOverrideValue(), macOverride.getOverrideValue()));
			else {
				if(ObjectUtils.isNotNull(macOverride))
					maxBalance = new BigDecimal(macOverride.getOverrideValue());
				if(ObjectUtils.isNotNull(mbOverride))
					maxBalance = new BigDecimal(mbOverride.getOverrideValue());
			}
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(maxBalance));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(maxBalance) > 0);
		}
	}
	
	/**
	 * 
	 * TIMESHEET ELIGIBLE TESTS
	 * 
	 */
	
	@Test
	public void testGetMaxBalanceViolationsLeaveApproveForTimesheetCaseOne() throws Exception {
		//Timesheet does not contain the leave calendar end period
		TkServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM,TK_TO,true,TS_USER_ID);
		midDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_MID_DEC_PERIOD_ID);
		midDecTSDEntry = midDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(midDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());

		//Assert that the accrual categories returned by BT service are in fact over their balance limit,
		//according to their rules. - does not consider FTE.
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TS_USER_ID, midDecTSDEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsYearEndForTimesheetCaseOne() throws Exception {
		//Timesheet does not include the leave calendar end period
		TkServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM,TK_TO,true,TS_USER_ID);
		midDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_MID_DEC_PERIOD_ID);
		midDecTSDEntry = midDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(midDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(0, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		
		/**
		 * No eligible transfers to test balance limit.
		 */
/*		//Assert that the accrual categories returned by BT service are in fact over their balance limit,
		//according to their rules.
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));
			
		// Set should contain an accrual category whose rule's max balance is trumped by an employee override.
		// Comparing accrued balance to a rule's defined max balance is insufficient for testing
		// whether or not an accrual category is indeed over it's balance limit. Same can be said for FTE-proration.
		// However, in this case, using an employee override will 
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TS_USER_ID, midDecTSDEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			BigDecimal maxBalance = aRule.getMaxBalance();
			EmployeeOverride mbOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(TS_USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MB",
					janEntry.getBeginPeriodDate());
			if(ObjectUtils.isNotNull(mbOverride))
				maxBalance = new BigDecimal(mbOverride.getOverrideValue());
			//Don't care about employee override existence, this is not the leave plan's roll-over period.
			assertNotNull("eligible accrual category has no balance limit",maxBalance);
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(maxBalance) > 0);
		}*/
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnDemandForTimesheetCaseOne() throws Exception {
		//Timesheet does not include the leave calendar end period
		TkServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM,TK_TO,true,TS_USER_ID);
		midDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_MID_DEC_PERIOD_ID);
		midDecTSDEntry = midDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(midDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());

		//Assert that the accrual categories returned by BT service are in fact over their balance limit,
		//according to their rules. - does not consider FTE.
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TS_USER_ID, midDecTSDEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsLeaveApproveForTimesheetCaseTwo() throws Exception {
		//Timesheet includes the leave calendar end period, but does not include the leave plan's start date.
		TkServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM,TK_TO,true,TS_USER_ID);
		endDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_DEC_PERIOD_ID);
		endDecTSDEntry = endDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE).size());
		
		//Assert that the accrual categories returned by BT service are in fact over their balance limit,
		//according to their rules. - does not consider FTE.
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TS_USER_ID, endDecTSDEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsYearEndForTimesheetCaseTwo() throws Exception {
		//Timesheet includes the leave calendar end period, but does not include the leave plan's start date.
		TkServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM,TK_TO,true,TS_USER_ID);
		endDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_DEC_PERIOD_ID);
		endDecTSDEntry = endDecTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(0, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		
		//Assert that the accrual categories returned by BT service are in fact over their balance limit,
		//according to their rules. - does not consider FTE.
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		// Set should contain an accrual category whose rule's max balance is trumped by an employee override.
		// Comparing accrued balance to a rule's defined max balance is insufficient for testing
		// whether or not an accrual category is indeed over it's balance limit. Same can be said for FTE-proration.
		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TS_USER_ID, endDecTSDEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			BigDecimal maxBalance = aRule.getMaxBalance();
			EmployeeOverride mbOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(TS_USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MB",
					janEntry.getBeginPeriodDate());
			if(ObjectUtils.isNotNull(mbOverride))
				maxBalance = new BigDecimal(mbOverride.getOverrideValue());
			assertNotNull("eligible accrual category has no balance limit",maxBalance);
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(maxBalance) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsOnDemandForTimesheetCaseTwo() throws Exception {
		//Timesheet includes the leave calendar end period, but does not include the leave plan's start date.
		TkServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM,TK_TO,true,TS_USER_ID);
		endDecTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_DEC_PERIOD_ID);
		endDecTSDEntry = endDecTSD.getCalendarEntry();


		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endDecTSDEntry, TS_USER_ID);
		//Assert correct number of transfer eligible for frequency
		assertEquals(6, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND).size());
		
		//Assert that the accrual categories returned by BT service are in fact over their balance limit,
		//according to their rules. - does not consider FTE.
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TS_USER_ID,endDecTSDEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(aRule.getMaxBalance()));
			assertTrue("accrual category not eligible for transfer",row.getAccruedBalance().compareTo(aRule.getMaxBalance()) > 0);
		}
	}
	
	@Test
	public void testGetMaxBalanceViolationsYearEndForTimesheetCaseThree() throws Exception {
		//Timesheet includes the leave calendar end period, which is the leave plan's roll-over date.
		TkServiceLocator.getAccrualService().runAccrual(TS_USER_ID,TK_FROM,TK_TO,true,TS_USER_ID);
		endJanTSD = TkServiceLocator.getTimesheetService().getTimesheetDocument(TSD_END_JAN_PERIOD_ID);
		endJanTSDEntry = endJanTSD.getCalendarEntry();

		Map<String, Set<LeaveBlock>> maxBalanceViolations = eligibilityTestHelper(endJanTSDEntry, TS_USER_ID);

		//Assert correct number of transfer eligible for frequency
		assertEquals(8, maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END).size());
		
		//Assert that the accrual categories returned by BT service are in fact over their balance limit,
		//according to their rules. - does not consider FTE.
		List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		for(LeaveBlock eligibleTransfer : maxBalanceViolations.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
			rules.add(TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(eligibleTransfer.getAccrualCategoryRuleId()));
        }

		LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(TS_USER_ID,endJanTSDEntry);
		for(AccrualCategoryRule aRule : rules) {
			LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			BigDecimal maxBalance = aRule.getMaxBalance();
			EmployeeOverride mbOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(TS_USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MB",
					janEntry.getBeginPeriodDate());
			EmployeeOverride macOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(TS_USER_ID,
					"testLP",
					row.getAccrualCategory(),
					"MAC",
					janEntry.getBeginPeriodDate());
			if(ObjectUtils.isNotNull(mbOverride) && ObjectUtils.isNotNull(macOverride)) {
				maxBalance = new BigDecimal(Math.min(mbOverride.getOverrideValue(), macOverride.getOverrideValue()));
            } else {
				if(ObjectUtils.isNotNull(macOverride)) {
					maxBalance = new BigDecimal(macOverride.getOverrideValue());
                }
				if(ObjectUtils.isNotNull(mbOverride)) {
					maxBalance = new BigDecimal(mbOverride.getOverrideValue());
                }
			}
			assertNotNull("eligible accrual category has no balance limit",ObjectUtils.isNotNull(maxBalance));
			assertTrue("accrual category " + aRule.getLmAccrualCategoryId() + " not eligible for transfer",row.getAccruedBalance().compareTo(maxBalance) > 0);
		}
	}
	
	
	private Map<String, Set<LeaveBlock>> eligibilityTestHelper(
			CalendarEntries entry, String principalId) throws Exception {
		return TkServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(entry, principalId);
	}
}
