/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.leave.web;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leaveblock.LeaveStatusHistory;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

public class LeaveRequestAction extends TkAction {

	  @Override
	  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        ActionForward forward = super.execute(mapping, form, request, response);
	        LeaveRequestForm leaveForm = (LeaveRequestForm) form;
	        String principalId = TKUser.getCurrentTargetPerson().getPrincipalId();
	        Date currentDate = TKUtils.getTimelessDate(null);
	        // Planned Leaves
	        List<LeaveBlock> plannedLeaves  = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, LMConstants.REQUEST_STATUS.PLANNED, currentDate);
	        leaveForm.setPlannedLeaves(plannedLeaves);
	        // Pending Leaves
	        List<LeaveBlock> pendingLeaves  = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, LMConstants.REQUEST_STATUS.REQUESTED, currentDate);
	        if(pendingLeaves != null && !pendingLeaves.isEmpty()) {
	        	for(LeaveBlock pendingLeave : pendingLeaves) {
	        		LeaveStatusHistory leaveStatusHistory = TkServiceLocator.getLeaveStatusHistoryService().getLeaveStatusHistoryByLmLeaveBlockIdAndRequestStatus(pendingLeave.getLmLeaveBlockId(), Arrays.asList(new String[] {LMConstants.REQUEST_STATUS.REQUESTED}));
	        		if(leaveStatusHistory != null) {
	        			pendingLeave.setDateAndTime(leaveStatusHistory.getTimestamp());
	        		}
	        	}
	        }
	        leaveForm.setPendingLeaves(pendingLeaves);
	        // Approved Leaves
	        List<LeaveBlock> approvedLeaves  = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, LMConstants.REQUEST_STATUS.APPROVED, null);
	        if(approvedLeaves != null && !approvedLeaves.isEmpty()) {
	        	for(LeaveBlock approvedLeave : approvedLeaves) {
	        		LeaveStatusHistory leaveStatusHistory = TkServiceLocator.getLeaveStatusHistoryService().getLeaveStatusHistoryByLmLeaveBlockIdAndRequestStatus(approvedLeave.getLmLeaveBlockId(), Arrays.asList(new String[] {LMConstants.REQUEST_STATUS.APPROVED}));
	        		if(leaveStatusHistory != null) {
	        			approvedLeave.setDateAndTime(leaveStatusHistory.getTimestamp());
	        		}
	        	}
	        }
	        leaveForm.setApprovedLeaves(approvedLeaves);
	        // disapproved leaves
	        List<LeaveBlockHistory> disapprovedLeaves = TkServiceLocator.getLeaveBlockHistoryService().getLeaveBlockHistories(principalId, Arrays.asList(new String[] {LMConstants.REQUEST_STATUS.DEFERRED, LMConstants.REQUEST_STATUS.DISAPPROVED}));
	        if(disapprovedLeaves != null && !disapprovedLeaves.isEmpty()) {
	        	for(LeaveBlock leaveBlock : disapprovedLeaves) {
	        		LeaveStatusHistory leaveStatusHistory = TkServiceLocator.getLeaveStatusHistoryService().getLeaveStatusHistoryByLmLeaveBlockIdAndRequestStatus(leaveBlock.getLmLeaveBlockId(), Arrays.asList(new String[] {LMConstants.REQUEST_STATUS.DEFERRED, LMConstants.REQUEST_STATUS.DISAPPROVED}));
	        		if(leaveStatusHistory != null) {
	        			leaveBlock.setReason(leaveStatusHistory.getReason());
	        			leaveBlock.setDateAndTime(leaveStatusHistory.getTimestamp());
	        		}
	        	}
	        }
	        leaveForm.setDisapprovedLeaves(disapprovedLeaves);
	        return forward;
	  }
	  
	  public ActionForward submitForApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		  LeaveRequestForm lf = (LeaveRequestForm) form;
		  for(LeaveBlock leaveBlock : lf.getPlannedLeaves()) {
			  // check if check box is checked
			  System.out.println("Leave block submit is :: >>>>"+leaveBlock.getSubmit());
			  if(leaveBlock.getSubmit()) {
				  // update its status as 'R'
				  leaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
				  TkServiceLocator.getLeaveBlockService().saveLeaveBlock(leaveBlock);
				  // make entry into leave status history...
				  LeaveStatusHistory leaveStatusHistory = new LeaveStatusHistory();
				  leaveStatusHistory.setLmLeaveBlockId(leaveBlock.getLmLeaveBlockId());
				  leaveStatusHistory.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
				  leaveStatusHistory.setPrincipalIdModified(TKUser.getCurrentTargetPerson().getPrincipalId());
				  leaveStatusHistory.setTimestamp(new Timestamp(System.currentTimeMillis()));
				  TkServiceLocator.getLeaveStatusHistoryService().saveLeaveStatusHistory(leaveStatusHistory);
			  }
		  }
		  return mapping.findForward("basic");
	  }
	  
	  
}
