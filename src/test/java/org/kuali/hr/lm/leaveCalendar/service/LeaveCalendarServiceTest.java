package org.kuali.hr.lm.leaveCalendar.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;

public class LeaveCalendarServiceTest extends KPMETestCase {

	@Test
	public void testOpenLeaveCalendarDocument() throws WorkflowException {
		String principalId = "admin";
		CalendarEntries calEntry = TkServiceLocator.getCalendarEntriesService().getCalendarEntries("5000");
		Date beginDate = calEntry.getBeginPeriodDate();
		Date endDate = calEntry.getEndPeriodDate();
		
		List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
		LeaveBlock lb = leaveBlocks.get(0);
		Assert.assertNull("Leave Block should have null as documentId", lb.getDocumentId());
		
		// after leave document created, the existing leave blocks should be assigned with the new document id
		LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(principalId, calEntry);
		Assert.assertNotNull("Leave Calendar document should not be null", lcd);
		
		leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
		lb = leaveBlocks.get(0);
		Assert.assertTrue("Leave Block should have the new leave calendar document's id as document id", lb.getDocumentId().equals(lcd.getDocumentId()));		
	}
}
