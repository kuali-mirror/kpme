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
package org.kuali.hr.lm.leaveblock.service;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlockHistory;
import org.kuali.hr.tklm.leave.block.service.LeaveBlockHistoryService;
import org.kuali.hr.tklm.leave.service.base.LmServiceLocator;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;

public class LeaveBlockHistoryServiceImplTest extends KPMETestCase {

	private LeaveBlockHistoryService leaveBlockHistoryService;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		leaveBlockHistoryService = LmServiceLocator.getLeaveBlockHistoryService();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetLeaveBlockHistoryByLmLeaveBlockId() {
		List<LeaveBlockHistory> leaveBlockHistories = leaveBlockHistoryService.getLeaveBlockHistoryByLmLeaveBlockId("1000");
		Assert.assertNotNull("Leave Block histories  not found ", leaveBlockHistories);
	}
	
	@Test
	public void testGetLeaveBlockHistories() {
		List<LeaveBlockHistory> leaveBlockHistories = leaveBlockHistoryService.getLeaveBlockHistories("admin", null);
		Assert.assertNotNull("Leave Block histories  not found ", leaveBlockHistories);
	}
	
	@Test
	public void testGetLeaveBlockHistoriesForLeaveDisplay() {
		Calendar currCal = Calendar.getInstance();
		currCal.set(2012, 0, 1);
		LocalDate beginDate = LocalDate.fromCalendarFields(currCal);
		currCal.set(2012, 11, 31);
		LocalDate endDate = LocalDate.fromCalendarFields(currCal);
        List<LeaveBlockHistory> leaveBlockHistories= leaveBlockHistoryService.getLeaveBlockHistoriesForLeaveDisplay("admin", beginDate, endDate, Boolean.TRUE);
        Assert.assertNotNull("Leave Block histories for leavedisplay  not found ", leaveBlockHistories);
	}
	@Test
	public void testGetLeaveBlockHistoriesWithStatusAndAction() {
		DateTime currentDate = new DateTime(2012, 3, 10, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		List<LeaveBlockHistory> leaveBlockHistories= leaveBlockHistoryService.getLeaveBlockHistories("admin", LMConstants.REQUEST_STATUS.DISAPPROVED, LMConstants.ACTION.DELETE, currentDate.toLocalDate());
		Assert.assertNotNull("Leave Block histories for leavedisplay  not found ", leaveBlockHistories);
		Assert.assertTrue("There should be 1 leave block history found, not " + leaveBlockHistories.size() , leaveBlockHistories.size() == 1);
	}
	

}
