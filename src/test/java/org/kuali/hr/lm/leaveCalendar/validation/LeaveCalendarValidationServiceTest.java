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
package org.kuali.hr.lm.leaveCalendar.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.util.TKUtils;

public class LeaveCalendarValidationServiceTest extends KPMETestCase {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testValidateAvailableLeaveBalance() throws Exception {
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setLeaveBalance(new BigDecimal(5));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		
		// adding brand new leave blocks
		// earn code "EC" does not allow negative accrual balance
		List<String> errors = LeaveCalendarValidationUtil.validateAvailableLeaveBalance(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should be 1 error message" , errors.size()== 1);
		String anError = errors.get(0);
		Assert.assertTrue("error message not correct" , anError.equals("Requested leave amount is greater than available leave balance."));
		
		// earn code "EC1" allows negative accrual balance
		errors = LeaveCalendarValidationUtil.validateAvailableLeaveBalance(ls, "EC1", "02/15/2012", "02/15/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should NOT be error message(s)" , errors.isEmpty());
		
		//updating an existing leave block
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setEarnCode("EC");
		aLeaveBlock.setLeaveAmount(new BigDecimal(-10));
		
		errors = LeaveCalendarValidationUtil.validateAvailableLeaveBalance(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(3), aLeaveBlock);
		Assert.assertTrue("There should NOT be error message(s)" , errors.isEmpty());
		
		aLeaveBlock.setLeaveAmount(new BigDecimal(-2));
		errors = LeaveCalendarValidationUtil.validateAvailableLeaveBalance(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(10), aLeaveBlock);
		Assert.assertTrue("error message not correct" , anError.equals("Requested leave amount is greater than available leave balance."));
	}
	
	@Test
	public void testValidateLeaveSpanOverMaxUsageRule() throws Exception {
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(39));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		// adding brand new leave blocks
		List<String> errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/19/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should be 1 error message" , errors.size()== 1);
		String anError = errors.get(0);
		Assert.assertTrue("error message not correct" , anError.equals("This leave request would exceed the usage limit for " + lsr.getAccrualCategory()));
	}
	
	@Test
	public void testValidateLeaveSpanUnderMaxUsageRule() throws Exception {
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(41));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		// adding brand new leave blocks
		List<String> errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/19/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should be no error message" , errors.size()== 0);
	}
	
	@Test
	public void testValidateLeaveSpanEqualMaxUsageRule() throws Exception {
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(40));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		// adding brand new leave blocks
		List<String> errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/19/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should be no error message" , errors.size()== 0);
	}
	
	@Test
	public void testValidateLeaveNonSpanOverMaxUsageRule() throws Exception {
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(5));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		// adding brand new leave blocks
		List<String> errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should be 1 error message" , errors.size()== 1);
		String anError = errors.get(0);
		Assert.assertTrue("error message not correct" , anError.equals("This leave request would exceed the usage limit for " + lsr.getAccrualCategory()));
	}
	
	@Test
	public void testValidateLeaveNonSpanEqualsMaxUsageRule() throws Exception {
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(5));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);

		List<String> errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(5), null);
		Assert.assertTrue("There should be no error message" , errors.size()== 0);

	}
	
	@Test
	public void testValidateEditLeaveBlockMaxUsageRuleCaseOne() throws Exception {
		//Leave Amount increases, Earn Code unchanged
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(50));
		lsr.setPendingLeaveRequests(new BigDecimal(25));
		lsr.setYtdApprovedUsage(new BigDecimal(15));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		
		//updating an existing leave block
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setEarnCode("EC");
		aLeaveBlock.setLeaveAmount(new BigDecimal(-10)); //this amount, multiplied by the days in the span, is considered to be part of the pending leave requests.
		List<String> errors = new ArrayList<String>();

		// EC1 belongs to the accrual category testAC
		// should still be under 50 effective difference is +9, over 1 days = 9 -> 40+12 < 50
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(19), aLeaveBlock);
		Assert.assertTrue("There should be no error message test 1" , errors.size()== 0);
		
		// should be right at 50 effective difference is +10, over 1 days = 10 -> 40+10 = 50
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(20), aLeaveBlock);
		Assert.assertTrue("There should be no error message test 2" , errors.size()== 0);
		
		// should be over 50 effective difference is +11, over 1 day = 11 -> 40+11 > 50
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(21), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message test 3" , errors.size()== 1);
		
		// should be over 50 effective difference is +2, over 6 days = 12 -> 40+12 > 50
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/20/2012", new BigDecimal(12), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message test 5" , errors.size()== 1);
		
		// should be under effective difference is +2, over 4 days = 8 -> 40+8 < 50
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/18/2012", new BigDecimal(12), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message test 6" , errors.size()== 1);
	}
	
	@Test
	public void testValidateEditLeaveBlockMaxUsageRuleCaseTwo() throws Exception {
		//Leave Amount decreases, earn code remains the same.
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(50));
		lsr.setPendingLeaveRequests(new BigDecimal(25));
		lsr.setYtdApprovedUsage(new BigDecimal(30));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		
		//updating an existing leave block
		//Somehow a block enters the system that exceeds max_usage. The only way for it to be saved
		//is if the net change drops below the usage limit.
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setEarnCode("EC");
		aLeaveBlock.setLeaveAmount(new BigDecimal(-10));
		List<String> errors = new ArrayList<String>();

		// effective difference is (-2), over 1 days = -2 -> 55+(-2) > 50
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/15/2012", new BigDecimal(8), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message" , errors.size()== 1);
		
		// should be equal effective difference is (-0.5), over 5 days = -2.5 -> 55+(-2.5) > 50
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC", "02/15/2012", "02/19/2012", new BigDecimal(9.5), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message" , errors.size()== 1);
	}

	@Test
	public void testValidateEditLeaveBlockMaxUsageRuleCaseThree() throws Exception {
		//Leave Amount static, earn code changes.
		LeaveSummary ls = new LeaveSummary(); 
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setUsageLimit(new BigDecimal(50));
		lsr.setPendingLeaveRequests(new BigDecimal(25));
		lsr.setYtdApprovedUsage(new BigDecimal(15));

		LeaveSummaryRow lsr2 = new LeaveSummaryRow();
		lsr2.setAccrualCategory("testAC2");
		lsr2.setUsageLimit(new BigDecimal(15));
		lsr2.setPendingLeaveRequests(new BigDecimal(5));
		lsr2.setYtdApprovedUsage(new BigDecimal(4));
		
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		lsrList.add(lsr2);
		ls.setLeaveSummaryRows(lsrList);
		
		//updating an existing leave block
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setEarnCode("EC");
		aLeaveBlock.setAccrualCategory("testAC");
		aLeaveBlock.setLeaveAmount(new BigDecimal(-10));
		List<String> errors = new ArrayList<String>();

		//Changing to an earn code with different accrual category, testAC2
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC2", "02/15/2012", "02/15/2012", new BigDecimal(6), aLeaveBlock);
		Assert.assertTrue("There should be no error message. reached usage limit." , errors.size()== 0);
		
		//Changing to an earn code with different accrual category, testAC2
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC2", "02/15/2012", "02/15/2012", new BigDecimal(7), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message, there were " + errors.size() + " errors" , errors.size()== 1);
		
		//Changing to an earn code with different accrual category, testAC2 with spanning days.
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC2", "02/15/2012", "02/19/2012", new BigDecimal(1), aLeaveBlock);
		Assert.assertTrue("There should be no error message, there were " + errors.size() + " errors" , errors.size()== 0);
		
		//Changing to an earn code with different accrual category, testAC2 with spanning days.
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC2", "02/15/2012", "02/20/2012", new BigDecimal(1), aLeaveBlock);
		Assert.assertTrue("There should be no error message, there were " + errors.size() + " errors" , errors.size()== 0);
		
		//Changing to an earn code with different accrual category, testAC2 with spanning days.
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC2", "02/15/2012", "02/21/2012", new BigDecimal(1), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message, there were " + errors.size() + " errors" , errors.size()== 1);
		
		//Changing to an earn code within same accrual category, testAC
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC1", "02/15/2012", "02/15/2012", new BigDecimal(10), aLeaveBlock);
		Assert.assertTrue("There should be no error message, there were " + errors.size() + " errors" , errors.size()== 0);
		
		//Changing to an earn code within same accrual category, testAC with spanning days.
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC1", "02/15/2012", "02/19/2012", new BigDecimal(2), aLeaveBlock);
		Assert.assertTrue("There should be 0 error message, there were " + errors.size() + " errors" , errors.size()== 0);
		
		//Changing to an earn code within same accrual category, testAC with spanning days.
		errors = LeaveCalendarValidationUtil.validateLeaveAccrualRuleMaxUsage(ls, "EC2", "02/15/2012", "02/25/2012", new BigDecimal(1), aLeaveBlock);
		Assert.assertTrue("There should be 1 error message, there were " + errors.size() + " errors" , errors.size()== 1);
				
	}
	@Test
	public void testGetWarningTextForLeaveBlocks() throws Exception {
		// create two leave blocks with two different earn codes
		// earn code "ECA" has fmla=Y, has earn code group with warning messages
		// earn Code "ECB" has fmla = N, has earn code group with warning messages
		// earn code "ECC" does not have earn code group with warning messages		
		
		List<LeaveBlock> leaveBlocs = new ArrayList<LeaveBlock>();
		LeaveBlock lbA = new LeaveBlock();
		lbA.setEarnCode("ECA");
		lbA.setLeaveDate(TKUtils.getCurrentDate());
		leaveBlocs.add(lbA);
		
		LeaveBlock lbB = new LeaveBlock();
		lbB.setEarnCode("ECB");
		lbB.setLeaveDate(TKUtils.getCurrentDate());
		leaveBlocs.add(lbB);
		
		LeaveBlock lbC = new LeaveBlock();
		lbC.setEarnCode("ECC");
		lbC.setLeaveDate(TKUtils.getCurrentDate());
		leaveBlocs.add(lbC);
		
		List<String> warningMess = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocs);
		Assert.assertTrue("There should be 2 warning messages, not " + warningMess.size(), warningMess.size()== 2);
		for(String warning : warningMess) {
			Assert.assertTrue("Warning message should be 'Test Message' or 'Test Message1'", warning.equals("Test Message") || warning.equals("Test Message1"));
		}
	}
		
}
