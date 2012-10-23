/**
 * Copyright 2004-2012 The Kuali Foundation
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

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationService;
import org.kuali.hr.test.KPMETestCase;

public class LeaveCalendarValidationServiceTest extends KPMETestCase {
	
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
		List<String> errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "EC", "02/15/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should be 1 error message" , errors.size()== 1);
		String anError = errors.get(0);
		Assert.assertTrue("error message not correct" , anError.equals("Requested leave amount is greater than pending available usage."));
		
		// earn code "EC1" allows negative accrual balance
		errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "EC1", "02/15/2012", new BigDecimal(8), null);
		Assert.assertTrue("There should NOT be error message(s)" , errors.isEmpty());
		
		//updating an existing leave block
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setEarnCode("EC");
		aLeaveBlock.setLeaveAmount(new BigDecimal(-10));
		
		errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "EC", "02/15/2012", new BigDecimal(3), aLeaveBlock);
		Assert.assertTrue("There should NOT be error message(s)" , errors.isEmpty());
		
		aLeaveBlock.setLeaveAmount(new BigDecimal(-2));
		errors = LeaveCalendarValidationService.validateAvailableLeaveBalance(ls, "EC", "02/15/2012", new BigDecimal(10), aLeaveBlock);
		Assert.assertTrue("error message not correct" , anError.equals("Requested leave amount is greater than pending available usage."));
	}

		
}
