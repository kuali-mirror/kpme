package org.kuali.hr.time.accrual.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TkConstants;

public class AccrualServiceTest extends TkTestCase {
	 Date START_DATE = new Date((new DateTime(2012, 2, 20, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
	 Date END_DATE = new Date((new DateTime(2012, 5, 3, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
	 String PRINCIPAL_ID = "testUser";
	 
	@Test
	public void testRunAccrual() {
		 List<LeaveBlock> leaveBlockList = (List<LeaveBlock>) TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID, START_DATE, END_DATE);
		 assertTrue("There are leave blocks before runAccrual for princiapl id " + PRINCIPAL_ID, leaveBlockList.isEmpty());
		 
		 // first time to run accrual
		 TkServiceLocator.getLeaveAccrualService().runAccrual(PRINCIPAL_ID, START_DATE, END_DATE);
		 verifyLeaveBlocks();
		 
		 // second time to run accrual, should get the same results as first run
		 TkServiceLocator.getLeaveAccrualService().runAccrual(PRINCIPAL_ID, START_DATE, END_DATE);
		 verifyLeaveBlocks();
		 
	}

	private void verifyLeaveBlocks() {
		List<LeaveBlock> leaveBlockList;
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(PRINCIPAL_ID, START_DATE, END_DATE);
		assertFalse("No leave blocks created by runAccrual for princiapl id " + PRINCIPAL_ID, leaveBlockList.isEmpty());
		assertTrue("There should be 3 leave blocks for emplyee 'testUser'.", leaveBlockList.size()==3);
		 
		// there should be leave block of 16 hours on 03/31/2012
		Date intervalDate = new Date((new DateTime(2012, 3, 31, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID, intervalDate);
		assertTrue("There should be 1 leave block for date 03/31/2012.", leaveBlockList.size()==1);
		LeaveBlock lb = leaveBlockList.get(0);
		assertTrue("Hours of the leave block for date 03/31/2012 should be 16.", lb.getLeaveAmount().equals(new BigDecimal(16)));
		assertNull("lm_sys_schd_timeoff_id should be null for regular accrual leave block", lb.getScheduleTimeOffId());
		 
		// there should be a holiday leave block of 8 hours on 04/10/2012
		intervalDate = new Date((new DateTime(2012, 4, 10, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID, intervalDate);
		assertTrue("There should be 1 leave block for date 04/10/2012.", leaveBlockList.size()==1);
		lb = leaveBlockList.get(0);
		assertTrue("Hours of the leave block for date 04/10/2012 should be 8.", lb.getLeaveAmount().equals(new BigDecimal(8)));
		assertNotNull("lm_sys_schd_timeoff_id should NOT be null for holiday accrual leave block", lb.getScheduleTimeOffId());
		 
		// there should be 1 leave blocks on 04/30/2012, regular accrual of 16 hours
		intervalDate = new Date((new DateTime(2012, 4, 30, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(PRINCIPAL_ID, intervalDate);
		assertTrue("There should be 1 leave block for date 04/30/2012.", leaveBlockList.size()==1);
		lb = leaveBlockList.get(0);
		assertTrue("Hours of the regular accrual leave block for date 04/30/2012 should be 16.", lb.getLeaveAmount().equals(new BigDecimal(16)));
		assertNull("lm_sys_schd_timeoff_id should be null for regular accrual leave block", lb.getScheduleTimeOffId());
	}
}
