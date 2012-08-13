package org.kuali.hr.lm.leaveblock.service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

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
		LeaveBlock leaveBlock = leaveBlockService.getLeaveBlock(1000L);
		Assert.assertNotNull("Leave Block not found ", leaveBlock);
	}
	
	@Test
	public void testGetLeaveBlocks(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(TKUtils.getTimelessDate(null));
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DATE, 1);
		
		Date beginDate = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date endDate = cal.getTime();
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocks(TEST_USER, beginDate, endDate);
		Assert.assertNotNull("Leave blocks not found for user ", leaveBlocks);
	}
	
	@Test
	public void testGetLeaveBlocksForDocumentId() {
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForDocumentId("12546");
		Assert.assertNotNull("Leave Blocks not foudn for document", leaveBlocks);
	}
	
	@Test
	public void testGetLeaveBlocksByLeaveRequestStatus(){
		String requestStatus = LMConstants.REQUEST_STATUS.PLANNED;
		Date currentDate = TKUtils.getTimelessDate(new Date());
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocks(TEST_USER, requestStatus, currentDate);
		Assert.assertNotNull("Leave Blocks not found of Request status", leaveBlocks);
	}
	@Test
	public void testGetLeaveBlocksForLeaveDate(){
		Date leaveDate = TKUtils.getTimelessDate(new Date());
		List<LeaveBlock> leaveBlocks = leaveBlockService.getLeaveBlocksForDate(TEST_USER, leaveDate);
		Assert.assertNotNull("Leave Blocks not found of Request status", leaveBlocks);
	}
	
	@Test
	public void testCalculateAccrualBalance() {
		Calendar leaveDate = Calendar.getInstance();
		leaveDate.setTime(TKUtils.getTimelessDate(null));
		leaveDate.set(Calendar.MONTH, 2);
		leaveDate.set(Calendar.DATE, 2);
		Double sum = leaveBlockService.calculateAccrualbalance(leaveDate.getTime(), "3000", "admin");
	}
	

}
