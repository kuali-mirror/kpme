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
package org.kuali.kpme.tklm.leave.calendar.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.leave.request.approval.web.LeaveRequestApprovalRow;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;


@IntegrationTest
public class LeaveActionFormUtilsTest extends TKLMIntegrationTestCase {
	
	@Test
	public void testGetLeaveBlocksJson()  {
		
		LeaveBlockBo lb = new LeaveBlockBo();
		
		lb.setAssignmentKey("0-123-0");
		lb.setEarnCode("testEarnCode");
		lb.setLmLeaveBlockId("1111");
		lb.setLeaveAmount(new BigDecimal(3));
		lb.setLeaveLocalDate(new DateTime(2012, 2, 20, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()).toLocalDate());
		lb.setAccrualGenerated(false);
		lb.setDescription("testLeaveBlock");
		lb.setLeaveBlockType("testLeaveBlockType");
		lb.setRequestStatus("S");
		lb.setDocumentId("2");
		lb.setJobNumber(new Long(30));
		lb.setWorkArea(new Long(30));
		lb.setTask(new Long(30));
		
		List<LeaveBlock> lbList = new ArrayList<LeaveBlock>();
		lbList.add(LeaveBlockBo.to(lb));
		
		String jsonString = LeaveActionFormUtils.getLeaveBlocksJson(lbList);
		// expected jsonString:
		//  [{"isApprover":true,"documentId":"2","title":"SDR1 Work Area","leaveDate":"02\/20\/2012","id":"1111","timezone":"America\/New_York","assignment":"_30_30_30","lmLeaveBlockId":"1111","earnCode":"testEarnCode","leaveAmount":3,"description":"testLeaveBlock","leaveBlockType":"testLeaveBlockType","editable":false,"requestStatus":"S","canTransfer":false}]
		Assert.assertTrue("Leave Block Json should include documentId", jsonString.contains("\"documentId\":\"2\""));
		Assert.assertTrue("Leave Block Json should include title", jsonString.contains("\"title\":\"SDR1 Work Area\""));
		Assert.assertTrue("Leave Block Json should include lmLeaveBlockId", jsonString.contains("\"id\":\"1111\""));
		Assert.assertTrue("Leave Block Json should include earnCode", jsonString.contains("\"earnCode\":\"testEarnCode\""));
		//Assert.assertTrue("Leave Block Json should include assignment", jsonString.contains("\"assignment\":\"_30_30_30\""));
		Assert.assertTrue("Leave Block Json should include leaveAmount", jsonString.contains("\"leaveAmount\":3"));
		Assert.assertTrue("Leave Block Json should include description", jsonString.contains("\"description\":\"testLeaveBlock\""));
		Assert.assertTrue("Leave Block Json should include leaveBlockType", jsonString.contains("\"leaveBlockType\":\"testLeaveBlockType\""));
		Assert.assertTrue("Leave Block Json should include requestStatus", jsonString.contains("\"requestStatus\":\"S\""));

	}
	
	@Test
	public void testGetLeaveRequestsJson(){
		
		LeaveRequestApprovalRow leaveRequestApprovalRow = new LeaveRequestApprovalRow();
		leaveRequestApprovalRow.setEmployeeName("Employee Name");
		leaveRequestApprovalRow.setPrincipalId("admin");
		leaveRequestApprovalRow.setLeaveRequestDocId("5000");
		leaveRequestApprovalRow.setRequestedDate("2014-04-12");
		leaveRequestApprovalRow.setRequestedHours("8");
		leaveRequestApprovalRow.setDescription("Unit Test");
		leaveRequestApprovalRow.setLeaveCode("testLeaveCode");
		leaveRequestApprovalRow.setSubmittedTime("2014-04-12 10:25:13");
		leaveRequestApprovalRow.setRequestStatus("A");
		leaveRequestApprovalRow.setSelected(true);
		leaveRequestApprovalRow.setAssignmentTitle("test Assignment Title");
		
		List<LeaveRequestApprovalRow> reqRows = new ArrayList<LeaveRequestApprovalRow>();
		reqRows.add(leaveRequestApprovalRow);
		
		String jsonString = LeaveActionFormUtils.getLeaveRequestsJson(reqRows);
		
		// expected jsonString:
		// [{"id":"5000","documentId":"5000","leaveDate":"2014-04-12","assignmentTitle":"test Assignment Title","leaveHours":"8","principalId":"admin","principalName":"Employee Name","leaveCode":"testLeaveCode","description":"Unit Test"}]
		Assert.assertTrue("Leave Request Json should include documentId", jsonString.contains("\"documentId\":\"5000\""));
		Assert.assertTrue("Leave Request Json should include leaveDate", jsonString.contains("\"leaveDate\":\"2014-04-12\""));
		Assert.assertTrue("Leave Request Json should include assignmentTitle", jsonString.contains("\"assignmentTitle\":\"test Assignment Title\""));
		Assert.assertTrue("Leave Request Json should include leaveHours", jsonString.contains("\"leaveHours\":\"8\""));
		Assert.assertTrue("Leave Request Json should include principalId", jsonString.contains("\"principalId\":\"admin\""));
		Assert.assertTrue("Leave Request Json should include principalName", jsonString.contains("\"principalName\":\"Employee Name\""));
		Assert.assertTrue("Leave Request Json should include description", jsonString.contains("\"description\":\"Unit Test\""));
		Assert.assertTrue("Leave Request Json should include leaveCode", jsonString.contains("\"leaveCode\":\"testLeaveCode\""));
		
	}
}
