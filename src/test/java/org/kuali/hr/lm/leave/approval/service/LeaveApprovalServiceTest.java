package org.kuali.hr.lm.leave.approval.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
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
		
	}

}
