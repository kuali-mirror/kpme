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


import java.util.*;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.block.service.LeaveBlockService;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;

public class LeaveBlockServiceImplTest extends KPMETestCase {
	
	private String TEST_USER = "admin";
	private LeaveBlockService leaveBlockService;

	@Before
	public void setUp() throws Exception{
		super.setUp();
		leaveBlockService = TkServiceLocator.getLeaveBlockService();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetLeaveBlock() {
		LeaveBlock leaveBlock = leaveBlockService.getLeaveBlock("1000");
		Assert.assertNotNull("Leave Block not found ", leaveBlock);
	}

    @Test
    public void testGetLeaveCarryOverDates() {
        Map<String, LeaveBlock> dates = leaveBlockService.getLastCarryOverBlocks("admin", null);
        Assert.assertNotNull("Leave Block not found ", dates);
    }


	
	@Test
	public void testGetLeaveBlocks(){
		// 03/01/2012 to 03/02/2012
		Calendar cal = Calendar.getInstance();
		cal.setTime(LocalDate.now().toDate());
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DATE, 1);
		
		Date beginDate = cal.getTime();
		cal.add(Calendar.DATE,1);
		Date endDate = cal.getTime();
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocks(TEST_USER, LocalDate.fromDateFields(beginDate), LocalDate.fromDateFields(endDate));
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 2 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 2);
	}
	
	@Test
	public void testGetLeaveBlocksForDocumentId() {
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForDocumentId("12546");
		Assert.assertNotNull("Leave Blocks not foudn for document", leaveBlocks);
	}
	
	@Test
	public void testGetLeaveBlocksByLeaveRequestStatus(){
		String requestStatus = LMConstants.REQUEST_STATUS.PLANNED;
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocks(TEST_USER, LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, requestStatus, LocalDate.now());
		Assert.assertNotNull("Leave Blocks not found of Request status", leaveBlocks);
	}
	@Test
	public void testGetLeaveBlocksForLeaveDate(){
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForDate(TEST_USER, LocalDate.now());
		Assert.assertNotNull("Leave Blocks not found of Request status", leaveBlocks);
	}
	
	@Test
	public void testGetLeaveBlocksForTimeCalendar() {
		// 03/01/2012 to 03/06/2012
		Calendar cal = Calendar.getInstance();
		cal.setTime(LocalDate.now().toDate());
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DATE, 1);
		
		Date beginDate = cal.getTime();
		cal.add(Calendar.DATE,5);
		Date endDate = cal.getTime();
		List<String> assignmentKeys = new ArrayList<String>();
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForTimeCalendar(TEST_USER, LocalDate.fromDateFields(beginDate), LocalDate.fromDateFields(endDate), assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 6 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 6);
		
		assignmentKeys.add("0_12345_0");
		leaveBlocks = leaveBlockService.getLeaveBlocksForTimeCalendar(TEST_USER, LocalDate.fromDateFields(beginDate), LocalDate.fromDateFields(endDate), assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 2 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 2);
		
		LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock("1001");
		lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		TkServiceLocator.getLeaveBlockService().saveLeaveBlock(lb, TEST_USER);
		leaveBlocks = leaveBlockService.getLeaveBlocksForTimeCalendar(TEST_USER, LocalDate.fromDateFields(beginDate), LocalDate.fromDateFields(endDate), assignmentKeys);
		Assert.assertTrue("There should be 3 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 3);
		Assert.assertTrue("Approved leave block should be in the list", leaveBlocks.contains(lb));
	}
	
	@Test
	public void testGetLeaveBlocksForLeaveCalendar() {
		// 03/01/2012 to 03/06/2012
		Calendar cal = Calendar.getInstance();
		cal.setTime(LocalDate.now().toDate());
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DATE, 1);
		
		Date beginDate = cal.getTime();
		cal.add(Calendar.DATE,5);
		Date endDate = cal.getTime();
		List<String> assignmentKeys = new ArrayList<String>();
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForLeaveCalendar(TEST_USER, LocalDate.fromDateFields(beginDate), LocalDate.fromDateFields(endDate), assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 6 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 6);
		
		assignmentKeys.add("0_12345_0");
		leaveBlocks = leaveBlockService.getLeaveBlocksForLeaveCalendar(TEST_USER, LocalDate.fromDateFields(beginDate), LocalDate.fromDateFields(endDate), assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 5 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 5);
		
		assignmentKeys.add("1_12345_0");
		leaveBlocks = leaveBlockService.getLeaveBlocksForLeaveCalendar(TEST_USER, LocalDate.fromDateFields(beginDate), LocalDate.fromDateFields(endDate), assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 6 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 6);
	}


}
