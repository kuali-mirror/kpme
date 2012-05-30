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

	@Test
	public void testRunAccrual() {
		 Date startDate = new Date((new DateTime(2012, 2, 20, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 Date endDate = new Date((new DateTime(2012, 5, 3, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 String principalId = "testUser";
		 List<LeaveBlock> leaveBlockList = (List<LeaveBlock>) TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, startDate, endDate);
		 
		 assertTrue("There are leave blocks before runAccrual for princiapl id " + principalId, leaveBlockList.isEmpty());
		 TkServiceLocator.getLeaveAccrualService().runAccrual(principalId, startDate, endDate);
		 
		 leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, startDate, endDate);
		 assertFalse("No leave blocks created by runAccrual for princiapl id " + principalId, leaveBlockList.isEmpty());
		 
		 // there should be leave block of 16 hours on 03/31/2012
		 Date intervalDate = new Date((new DateTime(2012, 3, 31, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(principalId, intervalDate);
		 assertTrue("There should be 1 leave block for date 03/31/2012.", leaveBlockList.size()==1);
		 LeaveBlock lb = leaveBlockList.get(0);
		 assertTrue("Hours of the leave block for date 03/31/2012 should be 16.", lb.getLeaveAmount().equals(new BigDecimal(16)));
		 
		 // there should be leave block of 24 hours on 04/30/2012, it's regular accual of 16 + 8 hours of system scheduled time off
		 intervalDate = new Date((new DateTime(2012, 4, 30, 5, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
		 leaveBlockList = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDate(principalId, intervalDate);
		 assertTrue("There should be 1 leave block for date 04/30/2012.", leaveBlockList.size()==1);
		 lb = leaveBlockList.get(0);
		 assertTrue("Hours of the leave block for date 04/30/2012 should be 24.", lb.getLeaveAmount().equals(new BigDecimal(24)));
	}
}
