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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.leave.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.hr.tklm.time.person.TKPerson;

public class LeaveApprovalServiceTest extends KPMETestCase {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	
	@Test
	public void testGetLeaveApprovalSummaryRows() {
		CalendarEntry ce = HrServiceLocator.getCalendarEntryService().getCalendarEntry("55");
		List<Date> leaveSummaryDates = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(ce);
		List<String> ids = new ArrayList<String>();
		ids.add("admin");
		List<TKPerson> persons = HrServiceLocator.getPersonService().getPersonCollection(ids);
		
		List<ApprovalLeaveSummaryRow> rows = LmServiceLocator.getLeaveApprovalService().getLeaveApprovalSummaryRows(persons, ce, leaveSummaryDates);
		Assert.assertTrue("Rows should not be empty. ", CollectionUtils.isNotEmpty(rows));
		
		ApprovalLeaveSummaryRow aRow = rows.get(0);
		Map<Date, Map<String, BigDecimal>> aMap = aRow.getEarnCodeLeaveHours();
		Assert.assertTrue("Leave Approval Summary Rows should have 14 items, not " + aMap.size(), aMap.size() == 14);
	}
	
	@Test
	public void testGetEarnCodeLeaveHours() throws Exception {
		CalendarEntry ce = HrServiceLocator.getCalendarEntryService().getCalendarEntry("55");
		List<Date> leaveSummaryDates = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(ce);
		
		List<LeaveBlock> lbList = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", ce.getBeginPeriodFullDateTime().toLocalDate(), ce.getEndPeriodFullDateTime().toLocalDate());
		Assert.assertTrue("Leave Block list should not be empty. ", CollectionUtils.isNotEmpty(lbList));
		Map<Date, Map<String, BigDecimal>> aMap = LmServiceLocator.getLeaveApprovalService().getEarnCodeLeaveHours(lbList, leaveSummaryDates);
		
		Assert.assertTrue("Map should have 14 entries, not " + aMap.size(), aMap.size() == 14);
		Map<String, BigDecimal> dayMap = aMap.get(DATE_FORMAT.parse("03/05/2012"));
		Assert.assertTrue("Map on day 03/05 should have 1 entries, not " + dayMap.size(), dayMap.size() == 1);
		Assert.assertTrue("EC on day 03/05 should have 8 hours, not " + dayMap.get("EC6|P|AS"), dayMap.get("EC6|P|AS").equals(new BigDecimal(8)));
	}
	
	@Test
	public void testGetAccrualCategoryLeaveHours() throws Exception {
		CalendarEntry ce = HrServiceLocator.getCalendarEntryService().getCalendarEntry("55");
		List<Date> leaveSummaryDates = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(ce);
		
		List<LeaveBlock> lbList = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", ce.getBeginPeriodFullDateTime().toLocalDate(), ce.getEndPeriodFullDateTime().toLocalDate());
		Assert.assertTrue("Leave Block list should not be empty. ", CollectionUtils.isNotEmpty(lbList));
		Map<Date, Map<String, BigDecimal>> aMap = LmServiceLocator.getLeaveApprovalService().getAccrualCategoryLeaveHours(lbList, leaveSummaryDates);
		
		Assert.assertTrue("Map should have 14 entries, not " + aMap.size(), aMap.size() == 14);
		Map<String, BigDecimal> dayMap = aMap.get(DATE_FORMAT.parse("03/05/2012"));
		Assert.assertTrue("Map on day 03/05 should have 1 entries, not " + dayMap.size(), dayMap.size() == 1);
		Assert.assertTrue("testAC on day 03/05 should have 8 hours, not " + dayMap.get("testAC"), dayMap.get("testAC").equals(new BigDecimal(8)));
	}
	
	@Test
	public void testGetLeavePrincipalIdsWithSearchCriteria() throws ParseException {
		List<String> workAreaList = new ArrayList<String>();
		String calendarGroup = "leaveCal";
		LocalDate beginDate = LocalDate.fromDateFields(DATE_FORMAT.parse("03/01/2012"));
		LocalDate endDate = LocalDate.fromDateFields(DATE_FORMAT.parse("03/30/2012"));
		
		List<String> idList = LmServiceLocator.getLeaveApprovalService()
			.getLeavePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);		
		Assert.assertTrue("There should be 0 principal ids when searching with empty workarea list, not " + idList.size(), idList.isEmpty());
		
		workAreaList.add("1111");
		workAreaList.add("2222");
		idList = LmServiceLocator.getLeaveApprovalService()
			.getLeavePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);		
		Assert.assertTrue("There should be 2 principal ids when searching with both workareas, not " + idList.size(), idList.size() == 2);
		// there's an principal id '1033' in setup that is not eligible for leave, so it should not be in the search results
		for(String anId : idList) {
			if(!(anId.equals("1011") || anId.equals("1022"))) {
				Assert.fail("PrincipalIds searched with both workareas should be either '1011' or '1022', not " + anId);
			}
		}
		
		workAreaList = new ArrayList<String>();
		workAreaList.add("1111");
		idList = LmServiceLocator.getLeaveApprovalService()
			.getLeavePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);		
		Assert.assertTrue("There should be 1 principal ids for workArea '1111', not " + idList.size(), idList.size() == 1);
		Assert.assertTrue("Principal id for workArea '1111' should be principalA, not " + idList.get(0), idList.get(0).equals("1011"));
		
		workAreaList = new ArrayList<String>();
		workAreaList.add("2222");
		idList = LmServiceLocator.getLeaveApprovalService()
			.getLeavePrincipalIdsWithSearchCriteria(workAreaList, calendarGroup, endDate, beginDate, endDate);		
		Assert.assertTrue("There should be 1 principal ids for workArea '2222', not " + idList.size(), idList.size() == 1);
		Assert.assertTrue("Principal id for workArea '2222' should be principalB, not " + idList.get(0), idList.get(0).equals("1022"));
	}

}
