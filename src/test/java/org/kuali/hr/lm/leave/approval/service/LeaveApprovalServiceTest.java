package org.kuali.hr.lm.leave.approval.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	
	@Test
	public void testGetLeaveApprovalSummaryRows() {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<String> headers = TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(ce);
		List<String> ids = new ArrayList<String>();
		ids.add("admin");
		List<TKPerson> persons = TkServiceLocator.getPersonService().getPersonCollection(ids);
		
		List<ApprovalLeaveSummaryRow> rows = TkServiceLocator.getLeaveApprovalService().getLeaveApprovalSummaryRows(persons, ce, headers);
		Assert.assertTrue("Rows should not be empty. ", CollectionUtils.isNotEmpty(rows));
		
		ApprovalLeaveSummaryRow aRow = rows.get(0);
		Map<String, Map<String, BigDecimal>> aMap = aRow.getLeaveHoursToPayLabelMap();
		Assert.assertTrue("LeaveHoursToPayLabelMap should have 14 items, not " + aMap.size(), aMap.size() == 14);
	}
	
	@Test
	public void testGetLeaveHoursToPayDayMap() {
		CalendarEntries ce = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		List<String> headers = TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(ce);
		
		List<LeaveBlock> lbList = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", ce.getBeginPeriodDateTime(), ce.getEndPeriodDateTime());
		Assert.assertTrue("Leave Block list should not be empty. ", CollectionUtils.isNotEmpty(lbList));
		Map<String, Map<String, BigDecimal>> aMap = TkServiceLocator.getLeaveApprovalService().getLeaveHoursToPayDayMap(lbList, headers);
		
		Assert.assertTrue("Map should have 14 entries, not " + aMap.size(), aMap.size() == 14);
		Map<String, BigDecimal> dayMap = aMap.get("03/05");
		Assert.assertTrue("Map on day 03/05 should have 1 entries, not " + dayMap.size(), dayMap.size() == 1);
		Assert.assertTrue("testAC on day 03/05 should have 8 hours, not " + dayMap.get("testAC"), dayMap.get("testAC").equals(new BigDecimal(8)));
		
	}

}
