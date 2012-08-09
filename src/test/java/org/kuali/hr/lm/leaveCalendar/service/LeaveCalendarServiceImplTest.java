package org.kuali.hr.lm.leaveCalendar.service;

import java.math.BigDecimal;
import java.util.List;

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
		assertTrue("There ytd dates String should be '02/01/2012 - 03/15/2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("02/01/2012 - 03/15/2012"));
		assertTrue("There pending dates String should be '03/15/2012 - 04/01/2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("03/15/2012 - 04/01/2012"));
		
		List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
		assertTrue("There should be 1 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 1);
		LeaveSummaryRow aRow = rows.get(0);
		assertTrue("Accrual cateogry for Row should be 'testAC', not " + aRow.getAccrualCategory(), aRow.getAccrualCategory().equals("testAC"));
		assertNull("Carry over for Row should be null ", aRow.getCarryOver());
		assertTrue("YTD accrualed balance for Row should be '5', not " + aRow.getYtdAccruedBalance(), aRow.getYtdAccruedBalance().equals(new BigDecimal(5)));
		assertTrue("YTD approved usage for Row should be '2', not " + aRow.getYtdApprovedUsage(), aRow.getYtdApprovedUsage().equals(new BigDecimal(2)));
		assertTrue("Leave Balance for Row should be '3', not " + aRow.getLeaveBalance(), aRow.getLeaveBalance().equals(new BigDecimal(3)));
		assertTrue("Pending Leave Accrual for Row should be '0', not " + aRow.getPendingLeaveAccrual(), aRow.getPendingLeaveAccrual().equals(new BigDecimal(0)));
		assertTrue("Pending Leave requests for Row should be '0', not " + aRow.getPendingLeaveRequests(), aRow.getPendingLeaveRequests().equals(new BigDecimal(0)));
		assertTrue("Pending Leave Balance for Row should be '3', not " + aRow.getPendingLeaveBalance(), aRow.getPendingLeaveBalance().equals(new BigDecimal(3)));
		assertTrue("Pending Available usage for Row should be '198', not " + aRow.getPendingAvailableUsage(), aRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
		assertTrue("Usage Limit for Row should be '200', not " + aRow.getUsageLimit(), aRow.getUsageLimit().equals(new BigDecimal(200)));
		assertTrue("FMLA usage for Row should be '2', not " + aRow.getFmlaUsage(), aRow.getFmlaUsage().equals(new BigDecimal(2)));
		
		// selected calendar entry is 04/01/2012 - 04/15/2012
		ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("50002");
		ls = TkServiceLocator.getLeaveCalendarService().getLeaveSummary("testUser", ce);
		assertTrue("There ytd dates String should be '02/01/2012 - 03/15/2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("02/01/2012 - 03/15/2012"));
		assertTrue("There pending dates String should be '03/15/2012 - 04/15/2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("03/15/2012 - 04/15/2012"));
		
		rows = ls.getLeaveSummaryRows();
		assertTrue("There should be 1 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 1);
		aRow = rows.get(0);
		assertTrue("Accrual cateogry for Row should be 'testAC', not " + aRow.getAccrualCategory(), aRow.getAccrualCategory().equals("testAC"));
		assertNull("Carry over for Row should be null ", aRow.getCarryOver());
		assertTrue("YTD accrualed balance for Row should be '5', not " + aRow.getYtdAccruedBalance(), aRow.getYtdAccruedBalance().equals(new BigDecimal(5)));
		assertTrue("YTD approved usage for Row should be '2', not " + aRow.getYtdApprovedUsage(), aRow.getYtdApprovedUsage().equals(new BigDecimal(2)));
		assertTrue("Leave Balance for Row should be '3', not " + aRow.getLeaveBalance(), aRow.getLeaveBalance().equals(new BigDecimal(3)));
		assertTrue("Pending Leave Accrual for Row should be '10', not " + aRow.getPendingLeaveAccrual(), aRow.getPendingLeaveAccrual().equals(new BigDecimal(10)));
		assertTrue("Pending Leave requests for Row should be '0', not " + aRow.getPendingLeaveRequests(), aRow.getPendingLeaveRequests().equals(new BigDecimal(0)));
		assertTrue("Pending Leave Balance for Row should be '13', not " + aRow.getPendingLeaveBalance(), aRow.getPendingLeaveBalance().equals(new BigDecimal(13)));
		assertTrue("Pending Available usage for Row should be '198', not " + aRow.getPendingAvailableUsage(), aRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
		assertTrue("Usage Limit for Row should be '200', not " + aRow.getUsageLimit(), aRow.getUsageLimit().equals(new BigDecimal(200)));
		assertTrue("FMLA usage for Row should be '2', not " + aRow.getFmlaUsage(), aRow.getFmlaUsage().equals(new BigDecimal(2)));
	}

}
