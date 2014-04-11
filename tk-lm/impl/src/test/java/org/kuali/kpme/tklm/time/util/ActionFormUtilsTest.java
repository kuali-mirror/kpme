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
package org.kuali.kpme.tklm.time.util;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.detail.web.TimeDetailActionFormBase;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.utils.TkTestUtils;

@IntegrationTest
public class ActionFormUtilsTest extends TKLMIntegrationTestCase {
	
	private CalendarEntry createdCalendarEntry = null;
	private CalendarEntry createdCalendarEntry2 = null;
	private CalendarEntry createdCalendarEntry3 = null;
	private List<String> warningMessages = new ArrayList<String>();
	
	//TimeDetailActionFormBase tdaf = new TimeDetailActionFormBase(); 
	DateTime aDate = new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	
	@Before
	public void setUp() throws Exception {
	super.setUp();
		CalendarEntry.Builder pce = CalendarEntry.Builder.create();
		pce.setHrCalendarEntryId("105");
		pce.setHrCalendarId("2");
		pce.setCalendarName("BWS-CAL");
		pce.setBeginPeriodFullDateTime(new DateTime(2014, 04, 01, 0, 0, 0, 0));
		pce.setEndPeriodFullDateTime(new DateTime(2014, 04, 15, 0, 0, 0, 0));
		pce.setBatchEmployeeApprovalFullDateTime(null);
		pce.setBatchInitiateFullDateTime(null);
		pce.setBatchEndPayPeriodFullDateTime(null);
		pce.setBatchEmployeeApprovalFullDateTime(null);
		pce.setBatchSupervisorApprovalFullDateTime(null);
		pce.setBatchPayrollApprovalFullDateTime(null);
		pce.setVersionNumber(new Long(1));
		createdCalendarEntry = pce.build();	
		
		CalendarEntry.Builder pce2 = CalendarEntry.Builder.create();
		pce2.setHrCalendarEntryId("104");
		pce2.setHrCalendarId("2");
		pce2.setCalendarName("BWS-CAL");
		pce2.setBeginPeriodFullDateTime(new DateTime(2014, 03, 15, 0, 0, 0, 0));
		pce2.setEndPeriodFullDateTime(new DateTime(2014, 04, 01, 0, 0, 0, 0));
		pce2.setBatchEmployeeApprovalFullDateTime(null);
		pce2.setBatchInitiateFullDateTime(null);
		pce2.setBatchEndPayPeriodFullDateTime(null);
		pce2.setBatchEmployeeApprovalFullDateTime(null);
		pce2.setBatchSupervisorApprovalFullDateTime(null);
		pce2.setBatchPayrollApprovalFullDateTime(null);
		pce2.setVersionNumber(new Long(1));
		createdCalendarEntry2 = pce2.build();
		
		CalendarEntry.Builder pce3 = CalendarEntry.Builder.create();
		pce3.setHrCalendarEntryId("123");
		pce3.setHrCalendarId("2");
		pce3.setCalendarName("BWS-CAL");
		pce3.setBeginPeriodFullDateTime(new DateTime(2015, 01, 01, 0, 0, 0, 0));
		pce3.setEndPeriodFullDateTime(new DateTime(2015, 01, 15, 0, 0, 0, 0));
		pce3.setBatchEmployeeApprovalFullDateTime(null);
		pce3.setBatchInitiateFullDateTime(null);
		pce3.setBatchEndPayPeriodFullDateTime(null);
		pce3.setBatchEmployeeApprovalFullDateTime(null);
		pce3.setBatchSupervisorApprovalFullDateTime(null);
		pce3.setBatchPayrollApprovalFullDateTime(null);
		pce3.setVersionNumber(new Long(1));
		createdCalendarEntry3 = pce3.build();
		
		String warningMessage1 = "warningMessage1";
		String warningMessage2 = "warningMessage2";
		warningMessages.add(warningMessage1);
		warningMessages.add(warningMessage2);
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testBuildAssignmentStyleClassMap() {
		DateTime aDate = new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(aDate, "admin");
		
		Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(doc.getTimeBlocks());
		Assert.assertEquals("Wrong number of classes in style class map", 1, aMap.size());
		Assert.assertEquals("Wrong key for class assignment0", "assignment0", aMap.get("1_1234_1"));
	}
	
	@Test
	public void testGetUnitOfTimeForEarnCode() throws Exception {
		// earn code with an existing Accrual category
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCodeById("5000");
		String unitOfTime = ActionFormUtils.getUnitOfTimeForEarnCode(earnCode);
		Assert.assertTrue("Unit of Time should be 'H', not " + unitOfTime, unitOfTime.equals("H"));
		// earn code without an existing accrual category
		earnCode = HrServiceLocator.getEarnCodeService().getEarnCodeById("5002");
		unitOfTime = ActionFormUtils.getUnitOfTimeForEarnCode(earnCode);
		Assert.assertTrue("Unit of Time should be 'H', not " + unitOfTime, unitOfTime.equals("H"));	
	}
	
	@Test
	public void testGetLeaveBlocksJson() {
		List<LeaveBlock> lbList = new ArrayList<LeaveBlock>();
		LeaveBlockBo lb = new LeaveBlockBo();
		lb.setAssignmentTitle("testAssignment");
		lb.setAssignmentKey("0-123-0");
		lb.setEarnCode("EarnCode");
		lb.setLmLeaveBlockId("1111");
		lb.setLeaveAmount(new BigDecimal(3));
		lb.setLeaveLocalDate(new DateTime(2012, 2, 20, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).toLocalDate());
		lb.setAccrualGenerated(false);
		lbList.add(LeaveBlockBo.to(lb));
		
		String jsonString = ActionFormUtils.getLeaveBlocksJson(lbList);
		String expectedString = "[{\"title\":\"\",\"assignment\":\"0-123-0\",\"earnCode\":\"EarnCode\",\"lmLeaveBlockId\":\"1111\",\"leaveAmount\":\"3\",\"leaveDate\":\"02\\/20\\/2012\",\"id\":\"1111\",\"canTransfer\":false,\"startDate\":\"02\\/20\\/2012\",\"endDate\":\"02\\/20\\/2012\"}]";
		Assert.assertTrue("Leave Block Json should include assignment", jsonString.equals(expectedString));
	}
	
	//following tests are changes of KPME-3367
	@Test
	public void testIsOnCurrentPeriodFlag(){
		boolean gotOne = ActionFormUtils.isOnCurrentPeriodFlag(createdCalendarEntry);
		Assert.assertTrue("Is on Current Period", gotOne);
		
		boolean notGotOne = ActionFormUtils.isOnCurrentPeriodFlag(createdCalendarEntry2);
		Assert.assertTrue("Not on Current Period", !notGotOne);
	}
	
	@Test
	public void testGetAllCalendarEntriesForYear() {
		List<CalendarEntry> calendarEntries = new ArrayList<CalendarEntry>();
		calendarEntries.add(createdCalendarEntry);
		calendarEntries.add(createdCalendarEntry2);
		calendarEntries.add(createdCalendarEntry3); // this one does not belong to year 2014
		
		List<CalendarEntry> allCalendarEntriesForYear = ActionFormUtils.getAllCalendarEntriesForYear(calendarEntries, "2014");
		
		Assert.assertTrue(allCalendarEntriesForYear.size() == 2);
	}
	
	@Test
	public void testGetPlanningMonthsForEmployee(){
		String principleId = "testPrincial";
		int plannningMonths = 0;
		plannningMonths = ActionFormUtils.getPlanningMonthsForEmployee(principleId);
		
		Assert.assertTrue(plannningMonths == 12);
	}	
	
	@Test
	public void testAddUniqueWarningsToForm(){
		TimeDetailActionFormBase tdaf = new TimeDetailActionFormBase(); 
		DateTime aDate = new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		TimesheetDocument timesheetDocument = TkTestUtils.populateTimesheetDocument(aDate, "admin");
		
		tdaf.setTkTimeBlockId("5000");
		tdaf.setWarningMessages(warningMessages);
		tdaf.setTimesheetDocument(timesheetDocument);
		
		ActionFormUtils.addUniqueWarningsToForm(tdaf, warningMessages);
		
		Assert.assertTrue("warningMessage2 should show up.", tdaf.getWarningMessages().get(0).equalsIgnoreCase("warningMessage2"));
		Assert.assertTrue("warningMessage1 should show up.", tdaf.getWarningMessages().get(1).equalsIgnoreCase("warningMessage1"));
	}
	
	@Test
	public void testBuildAssignmentStyleClassMap2(){
		DateTime aDate = new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(aDate, "admin");
		
		List<LeaveBlock> lbList = new ArrayList<LeaveBlock>();
		LeaveBlockBo lb = new LeaveBlockBo();
		lb.setAssignmentTitle("testAssignment");
		lb.setAssignmentKey("0-123-0");
		lb.setEarnCode("EarnCode");
		lb.setLmLeaveBlockId("1111");
		lb.setLeaveAmount(new BigDecimal(3));
		lb.setLeaveLocalDate(new DateTime(2012, 2, 20, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).toLocalDate());
		lb.setAccrualGenerated(false);
		lbList.add(LeaveBlockBo.to(lb));
		
		Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(doc.getTimeBlocks(), lbList);
		Assert.assertEquals("Wrong number of classes in style class map", 2, aMap.size());
		Assert.assertEquals("Wrong key for class assignment0", "assignment1", aMap.get("1_1234_1"));
		Assert.assertEquals("Wrong key for class assignment0", "assignment0", aMap.get("0-123-0"));
	}
	
	@Test
	public void testGetTimeBlocksJson() {
		List<TimeBlock> timeBlockList = new ArrayList<TimeBlock>();
		TimeBlockBo timeBlockBo = new TimeBlockBo();
		
		timeBlockBo.setAssignmentValue("testAssignment");
		timeBlockBo.setAssignmentKey("0-123-0");
		timeBlockBo.setEarnCode("SDR");
		timeBlockBo.setTkTimeBlockId("1111");
		timeBlockBo.setWorkArea(new Long(30));
		timeBlockBo.setBeginDateTime(new DateTime(2014, 4, 10, 0, 0, 0, 0));
		timeBlockBo.setEndDateTime(new DateTime(2014, 4, 10, 0, 0, 0, 0));
		timeBlockBo.setJobNumber(new Long(30));
		timeBlockBo.setAmount(new BigDecimal(10));
		
		timeBlockList.add(TimeBlockBo.to(timeBlockBo));
		
		String jsonString = ActionFormUtils.getTimeBlocksJson(timeBlockList);
		// expected jsonString:
		// [{"isApprover":true,"isSynchronousUser":null,"canEditTb":false,"canEditTBOvt":true,"canEditTBAll":false,"canEditTBAssgOnly":true,"documentId":null,"title":"SDR1 Work Area","earnCode":"SDR","earnCodeDesc":"SHIFT DIFF","earnCodeType":null,"start":"2014-04-10T00:00:00-04:00","end":"2014-04-10T00:00:00-04:00","startDate":"04\/10\/2014","endDate":"04\/10\/2014","startNoTz":"2014-04-10T00:00:00","endNoTz":"2014-04-10T00:00:00","startTimeHourMinute":"12:00 AM","endTimeHourMinute":"12:00 AM","startTime":"0:00","endTime":"0:00","id":"1111","hours":0.00,"amount":10.00,"timezone":"America\/New_York","assignment":"30_30_0","tkTimeBlockId":"1111","lunchDeleted":false,"timeHourDetails":"[]"}]
		Assert.assertTrue("Leave Block Json should include assignment", jsonString.contains("\"earnCode\":\"SDR\""));
		Assert.assertTrue("Leave Block Json should include assignment", jsonString.contains("\"title\":\"SDR1 Work Area\""));
		Assert.assertTrue("Leave Block Json should include assignment", jsonString.contains("\"tkTimeBlockId\":\"1111\""));
		Assert.assertTrue("Leave Block Json should include assignment", jsonString.contains("\"amount\":10.00"));
		Assert.assertTrue("Leave Block Json should include assignment", jsonString.contains("\"assignment\":\"30_30_0\""));
	}
	
	@Test
	public void testGetPayPeriodsMap(){
		List<CalendarEntry> calendarEntries = new ArrayList<CalendarEntry>();
		calendarEntries.add(createdCalendarEntry);
		calendarEntries.add(createdCalendarEntry2);
		
		Map<String, String> pMap = ActionFormUtils.getPayPeriodsMap(calendarEntries, "admin");
		
		Set<Entry<String, String>> mapValues = pMap.entrySet();
	    int maplength = mapValues.size();
	    Entry<String,String>[] test = new Entry[maplength];
	    mapValues.toArray(test);
	    
	    // pMap should have:
	 	// {105=04/01/2014 - 04/14/2014, 104=03/15/2014 - 03/31/2014}
	    Assert.assertTrue("Should return two pay periods", pMap.size() == 2);
	    
        Assert.assertTrue("Key of the first pay periods", test[0].getKey().equals("105"));
        Assert.assertTrue("Value of the first pay periods", test[0].getValue().equals("04/01/2014 - 04/14/2014"));
        
        Assert.assertTrue("Key of the second pay periods", test[1].getKey().equals("104"));
        Assert.assertTrue("Value of the second pay periods", test[1].getValue().equals("03/15/2014 - 03/31/2014"));
	}
	
}
