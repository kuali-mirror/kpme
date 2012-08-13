package org.kuali.hr.lm.leaveCalendar.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class LeaveCalendarServiceImplTest extends TkTestCase {
	
	@Test
	public void testGetLeaveSummary() throws Exception {
		// selected calendar entry is 03/15/2012 - 04/01/2012
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("50001");
		
		LeaveSummary ls = TkServiceLocator.getLeaveCalendarService().getLeaveSummary("testUser", ce);
		Assert.assertTrue("There ytd dates String should be '02/01/2012 - 03/14/2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("02/01/2012 - 03/14/2012"));
		Assert.assertTrue("There pending dates String should be '03/15/2012 - 03/31/2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("03/15/2012 - 03/31/2012"));
		
		List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
		Assert.assertTrue("There should be 1 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 1);
		LeaveSummaryRow aRow = rows.get(0);
		Assert.assertTrue("Accrual cateogry for Row should be 'testAC', not " + aRow.getAccrualCategory(), aRow.getAccrualCategory().equals("testAC"));
		Assert.assertNull("Carry over for Row should be null ", aRow.getCarryOver());
		Assert.assertTrue("YTD accrualed balance for Row should be '5', not " + aRow.getYtdAccruedBalance(), aRow.getYtdAccruedBalance().equals(new BigDecimal(5)));
		Assert.assertTrue("YTD approved usage for Row should be '2', not " + aRow.getYtdApprovedUsage(), aRow.getYtdApprovedUsage().equals(new BigDecimal(2)));
		Assert.assertTrue("Leave Balance for Row should be '3', not " + aRow.getLeaveBalance(), aRow.getLeaveBalance().equals(new BigDecimal(3)));
		Assert.assertTrue("Pending Leave Accrual for Row should be '0', not " + aRow.getPendingLeaveAccrual(), aRow.getPendingLeaveAccrual().equals(new BigDecimal(0)));
		Assert.assertTrue("Pending Leave requests for Row should be '0', not " + aRow.getPendingLeaveRequests(), aRow.getPendingLeaveRequests().equals(new BigDecimal(0)));
		Assert.assertTrue("Pending Leave Balance for Row should be '3', not " + aRow.getPendingLeaveBalance(), aRow.getPendingLeaveBalance().equals(new BigDecimal(3)));
		Assert.assertTrue("Pending Available usage for Row should be '198', not " + aRow.getPendingAvailableUsage(), aRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
		Assert.assertTrue("Usage Limit for Row should be '200', not " + aRow.getUsageLimit(), aRow.getUsageLimit().equals(new BigDecimal(200)));
		Assert.assertTrue("FMLA usage for Row should be '2', not " + aRow.getFmlaUsage(), aRow.getFmlaUsage().equals(new BigDecimal(2)));
		
		// selected calendar entry is 04/01/2012 - 04/30/2012
		ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("50002");
		ls = TkServiceLocator.getLeaveCalendarService().getLeaveSummary("testUser", ce);
		Assert.assertTrue("There ytd dates String should be '02/01/2012 - 03/14/2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("02/01/2012 - 03/14/2012"));
		Assert.assertTrue("There pending dates String should be '03/15/2012 - 04/30/2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("03/15/2012 - 04/30/2012"));
		
		rows = ls.getLeaveSummaryRows();
		Assert.assertTrue("There should be 1 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 1);
		aRow = rows.get(0);
		Assert.assertTrue("Accrual cateogry for Row should be 'testAC', not " + aRow.getAccrualCategory(), aRow.getAccrualCategory().equals("testAC"));
		Assert.assertNull("Carry over for Row should be null ", aRow.getCarryOver());
		Assert.assertTrue("YTD accrualed balance for Row should be '5', not " + aRow.getYtdAccruedBalance(), aRow.getYtdAccruedBalance().equals(new BigDecimal(5)));
		Assert.assertTrue("YTD approved usage for Row should be '2', not " + aRow.getYtdApprovedUsage(), aRow.getYtdApprovedUsage().equals(new BigDecimal(2)));
		Assert.assertTrue("Leave Balance for Row should be '3', not " + aRow.getLeaveBalance(), aRow.getLeaveBalance().equals(new BigDecimal(3)));
		Assert.assertTrue("Pending Leave Accrual for Row should be '10', not " + aRow.getPendingLeaveAccrual(), aRow.getPendingLeaveAccrual().equals(new BigDecimal(10)));
		Assert.assertTrue("Pending Leave requests for Row should be '0', not " + aRow.getPendingLeaveRequests(), aRow.getPendingLeaveRequests().equals(new BigDecimal(0)));
		Assert.assertTrue("Pending Leave Balance for Row should be '13', not " + aRow.getPendingLeaveBalance(), aRow.getPendingLeaveBalance().equals(new BigDecimal(13)));
		Assert.assertTrue("Pending Available usage for Row should be '198', not " + aRow.getPendingAvailableUsage(), aRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
		Assert.assertTrue("Usage Limit for Row should be '200', not " + aRow.getUsageLimit(), aRow.getUsageLimit().equals(new BigDecimal(200)));
		Assert.assertTrue("FMLA usage for Row should be '2', not " + aRow.getFmlaUsage(), aRow.getFmlaUsage().equals(new BigDecimal(2)));
		
		// selected calendar entry is 05/01/2012 - 05/31/2012
		ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("50003");
		ls = TkServiceLocator.getLeaveCalendarService().getLeaveSummary("testUser", ce);
		Assert.assertTrue("There ytd dates String should be '01/01/2012 - 03/14/2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("01/01/2012 - 03/14/2012"));
		Assert.assertTrue("There pending dates String should be '03/15/2012 - 05/31/2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("03/15/2012 - 05/31/2012"));
		
		rows = ls.getLeaveSummaryRows();
		Assert.assertTrue("There should be 2 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 2);
		for(LeaveSummaryRow lsRow : rows ) {
			if(lsRow.getAccrualCategory().equals("testAC")) {
				Assert.assertNull("Carry over for Row should be null ", lsRow.getCarryOver());
				Assert.assertTrue("YTD accrualed balance for Row should be '5', not " + lsRow.getYtdAccruedBalance(), lsRow.getYtdAccruedBalance().equals(new BigDecimal(5)));
				Assert.assertTrue("YTD approved usage for Row should be '2', not " + lsRow.getYtdApprovedUsage(), lsRow.getYtdApprovedUsage().equals(new BigDecimal(2)));
				Assert.assertTrue("Leave Balance for Row should be '3', not " + lsRow.getLeaveBalance(), lsRow.getLeaveBalance().equals(new BigDecimal(3)));
				Assert.assertTrue("Pending Leave Accrual for Row should be '10', not " + lsRow.getPendingLeaveAccrual(), lsRow.getPendingLeaveAccrual().equals(new BigDecimal(10)));
				Assert.assertTrue("Pending Leave requests for Row should be '0', not " + lsRow.getPendingLeaveRequests(), lsRow.getPendingLeaveRequests().equals(new BigDecimal(0)));
				Assert.assertTrue("Pending Leave Balance for Row should be '13', not " + lsRow.getPendingLeaveBalance(), lsRow.getPendingLeaveBalance().equals(new BigDecimal(13)));
				Assert.assertTrue("Pending Available usage for Row should be '198', not " + lsRow.getPendingAvailableUsage(), lsRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
				Assert.assertTrue("Usage Limit for Row should be '200', not " + lsRow.getUsageLimit(), lsRow.getUsageLimit().equals(new BigDecimal(200)));
				Assert.assertTrue("FMLA usage for Row should be '2', not " + lsRow.getFmlaUsage(), lsRow.getFmlaUsage().equals(new BigDecimal(2)));
			} else if(lsRow.getAccrualCategory().equals("testAC1")) {
				Assert.assertNull("Carry over for Row should be null ", lsRow.getCarryOver());
				Assert.assertTrue("YTD accrualed balance for Row should be '0', not " + lsRow.getYtdAccruedBalance(), lsRow.getYtdAccruedBalance().equals(new BigDecimal(0)));
				Assert.assertTrue("YTD approved usage for Row should be '0', not " + lsRow.getYtdApprovedUsage(), lsRow.getYtdApprovedUsage().equals(new BigDecimal(0)));
				Assert.assertTrue("Leave Balance for Row should be '0', not " + lsRow.getLeaveBalance(), lsRow.getLeaveBalance().equals(new BigDecimal(0)));
				Assert.assertTrue("Pending Leave Accrual for Row should be '8', not " + lsRow.getPendingLeaveAccrual(), lsRow.getPendingLeaveAccrual().equals(new BigDecimal(8)));
				Assert.assertTrue("Pending Leave requests for Row should be '0', not " + lsRow.getPendingLeaveRequests(), lsRow.getPendingLeaveRequests().equals(new BigDecimal(0)));
				Assert.assertTrue("Pending Leave Balance for Row should be '8', not " + lsRow.getPendingLeaveBalance(), lsRow.getPendingLeaveBalance().equals(new BigDecimal(8)));
				Assert.assertTrue("Pending Available usage for Row should be '300', not " + lsRow.getPendingAvailableUsage(), lsRow.getPendingAvailableUsage().equals(new BigDecimal(300)));
				Assert.assertTrue("Usage Limit for Row should be '300', not " + lsRow.getUsageLimit(), lsRow.getUsageLimit().equals(new BigDecimal(300)));
				Assert.assertTrue("FMLA usage for Row should be '0', not " + lsRow.getFmlaUsage(), lsRow.getFmlaUsage().equals(new BigDecimal(0)));
			} else {
				Assert.fail("Accrual category for Row should either be 'testAC' or 'testAC1', not " + lsRow.getAccrualCategory());
			}
		}
	}
	
	

}
