/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.leave.block;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockService;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;

@IntegrationTest
public class LeaveBlockServiceImplTest extends TKLMIntegrationTestCase {
	
	private String TEST_USER = "admin";
	private LeaveBlockService leaveBlockService;

	@Before
	public void setUp() throws Exception{
		super.setUp();
		leaveBlockService = LmServiceLocator.getLeaveBlockService();
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
        LocalDate beginDate = LocalDate.parse("2012-03-01");
        LocalDate endDate = beginDate.plusDays(1);

		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocks(TEST_USER, beginDate, endDate);
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
		String requestStatus = HrConstants.REQUEST_STATUS.PLANNED;
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
        LocalDate beginDate = LocalDate.parse("2012-03-01");
        LocalDate endDate = LocalDate.parse("2012-03-06");
		List<String> assignmentKeys = new ArrayList<String>();
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForTimeCalendar(TEST_USER, beginDate, endDate, assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 6 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 6);
		
		assignmentKeys.add("IU-BL_0_12345_0");
		leaveBlocks = leaveBlockService.getLeaveBlocksForTimeCalendar(TEST_USER, beginDate, endDate, assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 2 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 2);
		
		LeaveBlock.Builder lb = LeaveBlock.Builder.create(LmServiceLocator.getLeaveBlockService().getLeaveBlock("1001"));
		lb.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
		LmServiceLocator.getLeaveBlockService().saveLeaveBlock(lb.build(), TEST_USER);
		leaveBlocks = leaveBlockService.getLeaveBlocksForTimeCalendar(TEST_USER, beginDate, endDate, assignmentKeys);
		Assert.assertTrue("There should be 3 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 3);
		Assert.assertTrue("Approved leave block should be in the list", leaveBlocks.contains(lb.build()));
	}
	
	@Test
	public void testGetLeaveBlocksForLeaveCalendar() {
		// 03/01/2012 to 03/06/2012
        LocalDate beginDate = LocalDate.parse("2012-03-01");
        LocalDate endDate = LocalDate.parse("2012-03-06");
		List<String> assignmentKeys = new ArrayList<String>();
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForLeaveCalendar(TEST_USER, beginDate, endDate, assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 6 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 6);
		
		assignmentKeys.add("IU-BL_0_12345_0");
		leaveBlocks = leaveBlockService.getLeaveBlocksForLeaveCalendar(TEST_USER, beginDate, endDate, assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 5 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 5);
		
		assignmentKeys.add("IU-BL_1_12345_0");
		leaveBlocks = leaveBlockService.getLeaveBlocksForLeaveCalendar(TEST_USER, beginDate, endDate, assignmentKeys);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
		Assert.assertTrue("There should be 6 leave blocks, not " + leaveBlocks.size(), leaveBlocks.size()== 6);
	}


}
