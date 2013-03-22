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
package org.kuali.hr.lm.leaveCalendar.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;

public class LeaveCalendarServiceTest extends KPMETestCase {
	
	@Test
	public void testOpenLeaveCalendarDocument() throws WorkflowException {
		CalendarEntry calEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry("5000");
		Date beginDate = calEntry.getBeginPeriodDate();
		Date endDate = calEntry.getEndPeriodDate();
		
		List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", beginDate, endDate);
		LeaveBlock lb = leaveBlocks.get(0);
		Assert.assertNull("Leave Block should have null as documentId", lb.getDocumentId());
		
		// after leave document created, the existing leave blocks should be assigned with the new document id
		LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument("admin", calEntry);
		Assert.assertNotNull("Leave Calendar document should not be null", lcd);
		
		leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", beginDate, endDate);
		lb = leaveBlocks.get(0);
		Assert.assertTrue("Leave Block should have the new leave calendar document's id as document id", lb.getDocumentId().equals(lcd.getDocumentId()));		
	}
	
	@Test
	public void testShouldCreateLeaveDocument(){
		// no jobs found for assignment of testUser1 with flsa_status = exempt and leave_eligible = yes
		CalendarEntry calEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry("5001");
		boolean flag = TkServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument("testUser1", calEntry);
		Assert.assertFalse("Should NOT create leave document for 'testUser1'", flag);
		
		flag = TkServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument("testUser2", calEntry);
		Assert.assertTrue("Should create leave document for 'testUser2'", flag);
		
	}
}
