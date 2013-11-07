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
package org.kuali.kpme.tklm.leave.calendar;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kew.api.exception.WorkflowException;

@IntegrationTest
public class LeaveCalendarServiceTest extends TKLMIntegrationTestCase {
	
	@Test
	public void testOpenLeaveCalendarDocument() throws WorkflowException {
		CalendarEntry calEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry("5000");
		DateTime beginDate = calEntry.getBeginPeriodFullDateTime();
		DateTime endDate = calEntry.getEndPeriodFullDateTime();
		
		List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", beginDate.toLocalDate(), endDate.toLocalDate());
		LeaveBlock lb = leaveBlocks.get(0);
		Assert.assertNull("Leave Block should have null as documentId", lb.getDocumentId());
		
		// after leave document created, the existing leave blocks should be assigned with the new document id
		LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument("admin", calEntry);
		Assert.assertNotNull("Leave Calendar document should not be null", lcd);
		
		leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks("admin", beginDate.toLocalDate(), endDate.toLocalDate());
		lb = leaveBlocks.get(0);
		Assert.assertTrue("Leave Block should have the new leave calendar document's id as document id", lb.getDocumentId().equals(lcd.getDocumentId()));		
	}
	
	@Test
	public void testShouldCreateLeaveDocument(){
		// no jobs found for assignment of testUser1 with flsa_status = exempt and leave_eligible = yes
		CalendarEntry calEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry("5001");
		boolean flag = LmServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument("testUser1", calEntry);
		Assert.assertFalse("Should NOT create leave document for 'testUser1'", flag);
		
		flag = LmServiceLocator.getLeaveCalendarService().shouldCreateLeaveDocument("testUser2", calEntry);
		Assert.assertTrue("Should create leave document for 'testUser2'", flag);
		
	}
}
