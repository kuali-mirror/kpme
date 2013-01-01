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
package org.kuali.hr.lm.leave.approval.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class LeaveApprovalServiceTest extends KPMETestCase {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	
	@Test
	public void testGetLeaveApprovalSummaryRows() {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<Date> leaveSummaryDates = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(ce);
		List<String> ids = new ArrayList<String>();
		ids.add("admin");
		List<TKPerson> persons = TkServiceLocator.getPersonService().getPersonCollection(ids);
		
		List<ApprovalLeaveSummaryRow> rows = TkServiceLocator.getLeaveApprovalService().getLeaveApprovalSummaryRows(persons, ce, leaveSummaryDates);
		Assert.assertTrue("Rows should not be empty. ", CollectionUtils.isNotEmpty(rows));
		
		ApprovalLeaveSummaryRow aRow = rows.get(0);
		Map<Date, Map<String, BigDecimal>> aMap = aRow.getEarnCodeLeaveHours();
		Assert.assertTrue("Leave Approval Summary Rows should have 14 items, not " + aMap.size(), aMap.size() == 14);
	}
	
	@Test
	public void testGetEarnCodeLeaveHours() throws Exception {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<Date> leaveSummaryDates = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(ce);
		
		List<LeaveBlock> lbList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", ce.getBeginPeriodDateTime(), ce.getEndPeriodDateTime());
		Assert.assertTrue("Leave Block list should not be empty. ", CollectionUtils.isNotEmpty(lbList));
		Map<Date, Map<String, BigDecimal>> aMap = TkServiceLocator.getLeaveApprovalService().getEarnCodeLeaveHours(lbList, leaveSummaryDates);
		
		Assert.assertTrue("Map should have 14 entries, not " + aMap.size(), aMap.size() == 14);
		Map<String, BigDecimal> dayMap = aMap.get(DATE_FORMAT.parse("03/05/2012"));
		Assert.assertTrue("Map on day 03/05 should have 1 entries, not " + dayMap.size(), dayMap.size() == 1);
		Assert.assertTrue("EC on day 03/05 should have 8 hours, not " + dayMap.get("EC6"), dayMap.get("EC6").equals(new BigDecimal(8)));
	}
	
	@Test
	public void testGetAccrualCategoryLeaveHours() throws Exception {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<Date> leaveSummaryDates = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(ce);
		
		List<LeaveBlock> lbList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", ce.getBeginPeriodDateTime(), ce.getEndPeriodDateTime());
		Assert.assertTrue("Leave Block list should not be empty. ", CollectionUtils.isNotEmpty(lbList));
		Map<Date, Map<String, BigDecimal>> aMap = TkServiceLocator.getLeaveApprovalService().getAccrualCategoryLeaveHours(lbList, leaveSummaryDates);
		
		Assert.assertTrue("Map should have 14 entries, not " + aMap.size(), aMap.size() == 14);
		Map<String, BigDecimal> dayMap = aMap.get(DATE_FORMAT.parse("03/05/2012"));
		Assert.assertTrue("Map on day 03/05 should have 1 entries, not " + dayMap.size(), dayMap.size() == 1);
		Assert.assertTrue("testAC on day 03/05 should have 8 hours, not " + dayMap.get("testAC"), dayMap.get("testAC").equals(new BigDecimal(8)));
	}

}
