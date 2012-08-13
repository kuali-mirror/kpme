package org.kuali.hr.lm.leaveCalendar.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationService;
import org.kuali.hr.time.test.TkTestCase;

public class LeaveCalendarValidationServiceTest extends TkTestCase {
	
	@Test
	public void testValidateAvailableLeaveBalance() throws Exception {
		LeaveSummary ls = new LeaveSummary();
		LeaveSummaryRow lsr = new LeaveSummaryRow();
		lsr.setAccrualCategory("testAC");
		lsr.setPendingAvailableUsage(new BigDecimal(5));
		List<LeaveSummaryRow> lsrList = new ArrayList<LeaveSummaryRow>();
		lsrList.add(lsr);
		ls.setLeaveSummaryRows(lsrList);
		
		// adding brand new leave blocks
		// earn code id "5000" does not allow negative accrual balance
		List<String> errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "5000", "02/15/2012", new BigDecimal(8), null);
		assertTrue("There should be 1 error message" , errors.size()== 1);
		String anError = errors.get(0);
		assertTrue("error message not correct" , anError.equals("Requested leave amount is greater than pending available usage."));
		
		// earn code id "5001" allows negative accrual balance
		errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "5001", "02/15/2012", new BigDecimal(8), null);
		assertTrue("There should NOT be error message(s)" , errors.isEmpty());
		
		//updating an existing leave block
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setEarnCodeId("5000");
		aLeaveBlock.setLeaveAmount(new BigDecimal(-10));
		
		errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "5000", "02/15/2012", new BigDecimal(3), aLeaveBlock);
		assertTrue("There should NOT be error message(s)" , errors.isEmpty());
		
		aLeaveBlock.setLeaveAmount(new BigDecimal(-2));
		errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "5000", "02/15/2012", new BigDecimal(10), aLeaveBlock);
		assertTrue("error message not correct" , anError.equals("Requested leave amount is greater than pending available usage."));
	}

		
}
