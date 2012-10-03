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
package org.kuali.hr.lm.leaveSummary.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class LeaveSummaryServiceImplTest extends KPMETestCase {
	
	@Test
	public void testGetLeaveSummary() throws Exception {
		// selected calendar entry is 03/15/2012 - 04/01/2012
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("10001");
		
		LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary("testUser", ce);
		Assert.assertTrue("There ytd dates String should be 'March 1 - March 14 2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("March 1 - March 14 2012"));
		Assert.assertTrue("There pending dates String should be 'March 15 - March 31 2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("March 15 - March 31 2012"));
		
		List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
		Assert.assertTrue("There should be 1 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 1);
		LeaveSummaryRow aRow = rows.get(0);
		Assert.assertTrue("Accrual cateogry for Row should be 'testAC', not " + aRow.getAccrualCategory(), aRow.getAccrualCategory().equals("testAC"));
		Assert.assertTrue("Carry over for Row should be '0', not " + aRow.getCarryOver(), aRow.getCarryOver().compareTo(BigDecimal.ZERO) == 0);
		Assert.assertTrue("YTD accrualed balance for Row should be '5', not " + aRow.getYtdAccruedBalance(), aRow.getYtdAccruedBalance().compareTo(new BigDecimal(5)) == 0);
		Assert.assertTrue("YTD approved usage for Row should be '2', not " + aRow.getYtdApprovedUsage(), aRow.getYtdApprovedUsage().compareTo(new BigDecimal(2)) == 0);
		Assert.assertTrue("Leave Balance for Row should be '3', not " + aRow.getLeaveBalance(), aRow.getLeaveBalance().compareTo(new BigDecimal(3)) == 0);
		Assert.assertTrue("Pending Leave Accrual for Row should be '10', not " + aRow.getPendingLeaveAccrual(), aRow.getPendingLeaveAccrual().compareTo(new BigDecimal(10)) == 0);
		Assert.assertTrue("Pending Leave requests for Row should be '0', not " + aRow.getPendingLeaveRequests(), aRow.getPendingLeaveRequests().compareTo(new BigDecimal(0)) == 0);
		//Assert.assertTrue("Pending Leave Balance for Row should be '3', not " + aRow.getPendingLeaveBalance(), aRow.getPendingLeaveBalance().equals(new BigDecimal(3)));
		//Assert.assertTrue("Pending Available usage for Row should be '198', not " + aRow.getPendingAvailableUsage(), aRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
		Assert.assertTrue("Usage Limit for Row should be '200', not " + aRow.getUsageLimit(), aRow.getUsageLimit().compareTo(new BigDecimal(200)) == 0);
		Assert.assertTrue("FMLA usage for Row should be '2', not " + aRow.getFmlaUsage(), aRow.getFmlaUsage().compareTo(new BigDecimal(2)) == 0);
		
		// selected calendar entry is 04/01/2012 - 04/30/2012
		ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("10002");
		ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary("testUser", ce);
		Assert.assertTrue("There ytd dates String should be 'March 1 - March 14 2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("March 1 - March 14 2012"));
		Assert.assertTrue("There pending dates String should be 'April 1 - April 30 2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("April 1 - April 30 2012"));
		
		rows = ls.getLeaveSummaryRows();
		Assert.assertTrue("There should be 1 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 1);
		aRow = rows.get(0);
		Assert.assertTrue("Accrual cateogry for Row should be 'testAC', not " + aRow.getAccrualCategory(), aRow.getAccrualCategory().equals("testAC"));
		Assert.assertTrue("Carry over for Row should be '0', not " + aRow.getCarryOver(), aRow.getCarryOver().compareTo(BigDecimal.ZERO)==0);
		Assert.assertTrue("YTD accrualed balance for Row should be '5', not " + aRow.getYtdAccruedBalance(), aRow.getYtdAccruedBalance().compareTo(new BigDecimal(5))==0);
		Assert.assertTrue("YTD approved usage for Row should be '2', not " + aRow.getYtdApprovedUsage(), aRow.getYtdApprovedUsage().compareTo(new BigDecimal(2))==0);
		Assert.assertTrue("Leave Balance for Row should be '3', not " + aRow.getLeaveBalance(), aRow.getLeaveBalance().compareTo(new BigDecimal(3))==0);
		Assert.assertTrue("Pending Leave Accrual for Row should be '0', not " + aRow.getPendingLeaveAccrual(), aRow.getPendingLeaveAccrual().compareTo(BigDecimal.ZERO)==0);
		Assert.assertTrue("Pending Leave requests for Row should be '0', not " + aRow.getPendingLeaveRequests(), aRow.getPendingLeaveRequests().compareTo(new BigDecimal(0))==0);
		//Assert.assertTrue("Pending Leave Balance for Row should be '13', not " + aRow.getPendingLeaveBalance(), aRow.getPendingLeaveBalance().equals(new BigDecimal(13)));
		//Assert.assertTrue("Pending Available usage for Row should be '198', not " + aRow.getPendingAvailableUsage(), aRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
		Assert.assertTrue("Usage Limit for Row should be '200', not " + aRow.getUsageLimit(), aRow.getUsageLimit().compareTo(new BigDecimal(200)) == 0);
		Assert.assertTrue("FMLA usage for Row should be '2', not " + aRow.getFmlaUsage(), aRow.getFmlaUsage().compareTo(new BigDecimal(2)) == 0);
		
		// selected calendar entry is 05/01/2012 - 05/31/2012
		ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("10003");
		ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary("testUser", ce);
		Assert.assertTrue("There ytd dates String should be 'March 1 - March 14 2012', not " + ls.getYtdDatesString(), ls.getYtdDatesString().equals("March 1 - March 14 2012"));
		Assert.assertTrue("There pending dates String should be 'May 1 - May 31 2012', not " + ls.getPendingDatesString(), ls.getPendingDatesString().equals("May 1 - May 31 2012"));
		
		rows = ls.getLeaveSummaryRows();
		Assert.assertTrue("There should be 2 leave summary rows for emplyee 'testUser', not " + rows.size(), rows.size()== 2);
		for(LeaveSummaryRow lsRow : rows ) {
			if(lsRow.getAccrualCategory().equals("testAC")) {
				Assert.assertTrue("Carry over for Row should be '0', not " + lsRow.getCarryOver(), lsRow.getCarryOver().compareTo(BigDecimal.ZERO) == 0);
				Assert.assertTrue("YTD accrualed balance for Row should be '5', not " + lsRow.getYtdAccruedBalance(), lsRow.getYtdAccruedBalance().compareTo(new BigDecimal(5)) == 0);
				Assert.assertTrue("YTD approved usage for Row should be '2', not " + lsRow.getYtdApprovedUsage(), lsRow.getYtdApprovedUsage().compareTo(new BigDecimal(2)) == 0);
				Assert.assertTrue("Leave Balance for Row should be '3', not " + lsRow.getLeaveBalance(), lsRow.getLeaveBalance().compareTo(new BigDecimal(3)) == 0);
				Assert.assertTrue("Pending Leave Accrual for Row should be '0', not " + lsRow.getPendingLeaveAccrual(), lsRow.getPendingLeaveAccrual().compareTo(BigDecimal.ZERO) == 0);
				Assert.assertTrue("Pending Leave requests for Row should be '0', not " + lsRow.getPendingLeaveRequests(), lsRow.getPendingLeaveRequests().compareTo(new BigDecimal(0)) == 0);
				//Assert.assertTrue("Pending Leave Balance for Row should be '13', not " + lsRow.getPendingLeaveBalance(), lsRow.getPendingLeaveBalance().equals(new BigDecimal(13)));
				//Assert.assertTrue("Pending Available usage for Row should be '198', not " + lsRow.getPendingAvailableUsage(), lsRow.getPendingAvailableUsage().equals(new BigDecimal(198)));
				Assert.assertTrue("Usage Limit for Row should be '200', not " + lsRow.getUsageLimit(), lsRow.getUsageLimit().compareTo(new BigDecimal(200)) == 0);
				Assert.assertTrue("FMLA usage for Row should be '2', not " + lsRow.getFmlaUsage(), lsRow.getFmlaUsage().compareTo(new BigDecimal(2)) == 0);
			} else if(lsRow.getAccrualCategory().equals("testAC1")) {
				Assert.assertTrue("Carry over for Row should be '0', not " + lsRow.getCarryOver(), lsRow.getCarryOver().compareTo(BigDecimal.ZERO) == 0);
				Assert.assertTrue("YTD accrualed balance for Row should be '0', not " + lsRow.getYtdAccruedBalance(), lsRow.getYtdAccruedBalance().compareTo(new BigDecimal(0)) == 0);
				Assert.assertTrue("YTD approved usage for Row should be '0', not " + lsRow.getYtdApprovedUsage(), lsRow.getYtdApprovedUsage().compareTo(new BigDecimal(0)) == 0);
				Assert.assertTrue("Leave Balance for Row should be '0', not " + lsRow.getLeaveBalance(), lsRow.getLeaveBalance().compareTo(new BigDecimal(0)) == 0);
				Assert.assertTrue("Pending Leave Accrual for Row should be '0', not " + lsRow.getPendingLeaveAccrual(), lsRow.getPendingLeaveAccrual().compareTo(BigDecimal.ZERO) == 0);
				Assert.assertTrue("Pending Leave requests for Row should be '0', not " + lsRow.getPendingLeaveRequests(), lsRow.getPendingLeaveRequests().compareTo(new BigDecimal(0)) == 0);
				//Assert.assertTrue("Pending Leave Balance for Row should be '8', not " + lsRow.getPendingLeaveBalance(), lsRow.getPendingLeaveBalance().equals(new BigDecimal(8)));
				//Assert.assertTrue("Pending Available usage for Row should be '300', not " + lsRow.getPendingAvailableUsage(), lsRow.getPendingAvailableUsage().equals(new BigDecimal(300)));
				Assert.assertTrue("Usage Limit for Row should be '300', not " + lsRow.getUsageLimit(), lsRow.getUsageLimit().compareTo(new BigDecimal(300)) == 0);
				Assert.assertTrue("FMLA usage for Row should be '0', not " + lsRow.getFmlaUsage(), lsRow.getFmlaUsage().compareTo(new BigDecimal(0)) == 0);
			} else {
				Assert.fail("Accrual category for Row should either be 'testAC' or 'testAC1', not " + lsRow.getAccrualCategory());
			}
		}
	}
	
	@Test
	public void testGetHeaderForSummary() throws Exception {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("10001");
		List<String> headers = TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(ce);
		
		Assert.assertTrue("The size of the headers should be 17, not " + headers.size(), headers.size()== 17);
		
	}
}
