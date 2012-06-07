package org.kuali.hr.time.accrual.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class AccrualServiceTest extends TkTestCase {
	 Date START_DATE = new Date((new DateTime(2012, 2, 20, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
	 Date END_DATE = new Date((new DateTime(2012, 5, 3, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
	
	 String PRINCIPAL_ID = "testUser";
	 String PRINCIPAL_ID_2 = "testUser2";
	 String PRINCIPAL_ID_3 = "testUser3";
	
	  
	@Test
	/*the employee has job A starts from 03/01/2012, ends on 04/01/2012 with standard hours of 40, so fte is 1.0
	 * job B starts on 04/01/2012, no end to it with standard hours of 20, fte is 0.5
	 * on 04/10/2012, there's a system scheduled time off of 8 hrs
	 */
	public void testRunAccrualForStatusChange() {
		
		 List<LeaveBlock> leaveBlockList = (List<LeaveBlock>) TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID, START_DATE, END_DATE);
		 assertTrue("There are leave blocks before runAccrual for princiapl id " + PRINCIPAL_ID, leaveBlockList.isEmpty());
		 
		 // first time to run accrual from 02/20/2012 to 05/03/2012
		 TkServiceLocator.getLeaveAccrualService().runAccrual(PRINCIPAL_ID, START_DATE, END_DATE);
		 verifyLeaveBlocksForStatusChange();
		 
		 // second time to run accrual from 02/20/2012 to 05/03/2012, should get the same results as first run
		 TkServiceLocator.getLeaveAccrualService().runAccrual(PRINCIPAL_ID, START_DATE, END_DATE);
		 verifyLeaveBlocksForStatusChange();
	}
	private void verifyLeaveBlocksForStatusChange() {
		List<LeaveBlock> leaveBlockList;
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID, START_DATE, END_DATE);
		assertFalse("No leave blocks created by runAccrual for princiapl id " + PRINCIPAL_ID, leaveBlockList.isEmpty());
		assertTrue("There should be 4 leave blocks for emplyee 'testUser', not " + leaveBlockList.size(), leaveBlockList.size()== 4);
		 
		// there should be one leave block of 16 hours on 03/31/2012
		Date intervalDate = new Date((new DateTime(2012, 3, 31, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID, intervalDate);
		assertTrue("There should be 1 leave block for date 03/31/2012.", leaveBlockList.size()==1);
		LeaveBlock lb = leaveBlockList.get(0);
		assertTrue("Hours of the leave block for date 03/31/2012 should be 16, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(16)));
		assertNull("lm_sys_schd_timeoff_id should be null for regular accrual leave block", lb.getScheduleTimeOffId());
		
		// employee changed status on 04/01, fte is changed from 1 to 0.5
		// there should be an empty leave block for status change on 04/01/2012
		intervalDate = new Date((new DateTime(2012, 4, 01, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID, intervalDate);
		assertTrue("There should be 1 leave block for date 04/01/2012.", leaveBlockList.size()==1);
		lb = leaveBlockList.get(0);
		assertTrue("Hours of the leave block for date 04/01/2012 should be 0, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(BigDecimal.ZERO));
		assertTrue("Leave Code of the leave block for date 04/01/2012 should be 'MSG', not " + lb.getLeaveCode(), lb.getLeaveCode().equals("MSG"));
		assertNull("lm_accrual_category_id should be null for empty status change leave block", lb.getAccrualCategoryId()); 
		
		// there should be a holiday leave block of 4 hours on 04/10/2012
		intervalDate = new Date((new DateTime(2012, 4, 10, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID, intervalDate);
		assertTrue("There should be 1 leave block for date 04/10/2012.", leaveBlockList.size()==1);
		lb = leaveBlockList.get(0);
		assertTrue("Hours of the leave block for date 04/10/2012 should be 4, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(4)));
		assertNotNull("lm_sys_schd_timeoff_id should NOT be null for holiday accrual leave block", lb.getScheduleTimeOffId());
		 
		// there should be 1 leave blocks on 04/30/2012, regular accrual of 8 hours
		intervalDate = new Date((new DateTime(2012, 4, 30, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID, intervalDate);
		assertTrue("There should be 1 leave block for date 04/30/2012.", leaveBlockList.size()==1);
		lb = leaveBlockList.get(0);
		assertTrue("Hours of the regular accrual leave block for date 04/30/2012 should be 8, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(8)));
		assertNull("lm_sys_schd_timeoff_id should be null for regular accrual leave block", lb.getScheduleTimeOffId());
	}
	
	@Test
	/* testUser's leavePlan "testLP" has planning month of 12
	 * after calculateFutureAccrualUsingPlanningMonth, try to get leaveBlock for 18 months in the future
	 * should still only get 12 leave blocks
	 */
	public void testCalculateFutureAccrualUsingPlanningMonth() {
		// the planning month of this leave plan is set to 12
		TkServiceLocator.getLeaveAccrualService().calculateFutureAccrualUsingPlanningMonth(PRINCIPAL_ID);
		Date currentDate = TKUtils.getCurrentDate();
		Calendar aCal = Calendar.getInstance();
		aCal.setTime(currentDate);
		aCal.add(Calendar.MONTH, 18);
		List<LeaveBlock> leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID, currentDate, aCal.getTime());
		assertFalse("No leave blocks created by calculateFutureAccrualUsingPlanningMonth for princiapl id " + PRINCIPAL_ID, leaveBlockList.isEmpty());
		assertTrue("There should be 12 leave blocks for emplyee 'testUser', not " + leaveBlockList.size(), leaveBlockList.size()== 12);
		
	}
	
	@Test
	/*
	  *testUser2 has two accrual category rules, rule 1 goes from 0 month to 5 with accrual rate of 16
	  *rule 2 goes from 5 to 900 with accrual rate of 24
	  *run accrual for testUser2 for 15 months
	 */
	public void testRunAccrualForRuleChanges() {
		
		 Calendar aCal = Calendar.getInstance();
		 aCal.setTime(START_DATE);
		 aCal.add(Calendar.MONTH, 15);
		 Date endDate = new java.sql.Date(aCal.getTime().getTime());
		 
		 List<LeaveBlock> leaveBlockList = (List<LeaveBlock>) TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID_2, START_DATE, endDate);
		 assertTrue("There are leave blocks before runAccrual for princiapl id " + PRINCIPAL_ID, leaveBlockList.isEmpty());
		
		 TkServiceLocator.getLeaveAccrualService().runAccrual(PRINCIPAL_ID_2, START_DATE, endDate);
		 
		 leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID_2, START_DATE, endDate);
		 assertTrue("There should be 14 leave blocks for emplyee " + PRINCIPAL_ID_2 + ", not " + leaveBlockList.size(), leaveBlockList.size()== 14);
		 
		 // July of 2012 is the 5th month of this user's employment, the accrual rate should be 16
		 Date intervalDate = new Date((new DateTime(2012, 7, 31, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID_2, intervalDate);
		 assertTrue("There should be 1 leave block for employee " + PRINCIPAL_ID_2 + " for date 07/31/2012.", leaveBlockList.size()==1);
		 LeaveBlock lb = leaveBlockList.get(0);
		 assertTrue("Hours of the leave block on date 07/31/2012 for employee " + PRINCIPAL_ID_2 + " should be 16, not " + lb.getLeaveAmount().toString()
				 , lb.getLeaveAmount().equals(new BigDecimal(16)));
		 
		 // August of 2012 is the 6th month of this user's employment, the accrual rate should be 24 from now on
		 intervalDate = new Date((new DateTime(2012, 8, 31, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID_2, intervalDate);
		 assertTrue("There should be 1 leave block for employee " + PRINCIPAL_ID_2 + " for date 08/31/2012.", leaveBlockList.size()==1);
		 lb = leaveBlockList.get(0);
		 assertTrue("Hours of the leave block for date 08/31/2012 for employee " + PRINCIPAL_ID_2 + " should be 24, not " + lb.getLeaveAmount().toString()
				 , lb.getLeaveAmount().equals(new BigDecimal(24)));
		 
	}
	@Test
	/* testUser3's leavePlan has 2 accrual categories with different accrual intervals
	 * testAC3 has accrual interval semi-monthly
	 * testAC4 has accrual interval monthly
	 */
	public void testRunAccrualWithDifferentAccrualIntervals() {
		 
		 List<LeaveBlock> leaveBlockList = (List<LeaveBlock>) TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID_3, START_DATE, END_DATE);
		 assertTrue("There are leave blocks before runAccrual for princiapl id " + PRINCIPAL_ID_3, leaveBlockList.isEmpty());
		 
		 TkServiceLocator.getLeaveAccrualService().runAccrual(PRINCIPAL_ID_3, START_DATE, END_DATE);
		 leaveBlockList = (List<LeaveBlock>) TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID_3, START_DATE, END_DATE);
		 assertTrue("There should be 6 leave blocks for emplyee " + PRINCIPAL_ID_3 + ", not " + leaveBlockList.size(), leaveBlockList.size()== 6);
		 
		 Date semiMonthlyDate = new Date((new DateTime(2012, 3, 15, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 Date monthlyDate = new Date((new DateTime(2012, 3, 31, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 this.verifyLeaveBlocksWithDifferentAccrualIntervals(semiMonthlyDate, monthlyDate);

		 semiMonthlyDate = new Date((new DateTime(2012, 4, 15, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 monthlyDate = new Date((new DateTime(2012, 4, 30, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 this.verifyLeaveBlocksWithDifferentAccrualIntervals(semiMonthlyDate, monthlyDate);
		 
	}
	public void verifyLeaveBlocksWithDifferentAccrualIntervals(Date semiMonthlyDate, Date monthlyDate) {
		 List<LeaveBlock> leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID_3, semiMonthlyDate);
		 assertTrue("There should be 1 leave block for date " + semiMonthlyDate.toString() + " for emplyee " + PRINCIPAL_ID_3, leaveBlockList.size()==1);
		 LeaveBlock lb = leaveBlockList.get(0);
		 assertTrue("Hours of the leave block on date " + semiMonthlyDate.toString() + " should be 4, not " + lb.getLeaveAmount().toString(), lb.getLeaveAmount().equals(new BigDecimal(4)));
		 assertTrue("Leave code of the leave block on date " + semiMonthlyDate.toString() + " should be testLC1, not " + lb.getLeaveCode(), lb.getLeaveCode().equals("testLC1"));
		 assertTrue("lm_accrual_category of the leave block on date " + semiMonthlyDate.toString() + " should be 5003, not " + lb.getAccrualCategoryId(), lb.getAccrualCategoryId().equals("5003"));
		
		
		 leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID_3, monthlyDate);
		 assertTrue("There should be 2 leave block for date " + monthlyDate.toString() + " for emplyee " + PRINCIPAL_ID_3, leaveBlockList.size()==2);
		 for(LeaveBlock aLeaveBlock : leaveBlockList) {
			 if(aLeaveBlock.getAccrualCategoryId().endsWith("5003")) {
				 assertTrue("Leave code of the leave block with accrualCategoryId 5003 on date " + monthlyDate.toString() + " should be testLC1, not " + aLeaveBlock.getLeaveCode()
						 , aLeaveBlock.getLeaveCode().equals("testLC1")); 
			 } else if(aLeaveBlock.getAccrualCategoryId().endsWith("5004")) {
				 assertTrue("Leave code of the leave block with accrualCategoryId 5003 on date " + monthlyDate.toString() + " should be testLC2, not " + aLeaveBlock.getLeaveCode()
						 , aLeaveBlock.getLeaveCode().equals("testLC2")); 
			 } else {
				 fail("Leave block on date " + monthlyDate.toString() + " should not have lm_accrual_category_id " + aLeaveBlock.getAccrualCategoryId());
			 }
		 }
	}
	
	@Test
	public void testRunAccrualForLeavePlanChanges() {
		
	}
	
}
