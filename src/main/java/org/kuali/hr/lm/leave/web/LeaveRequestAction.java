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

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leaverequest.service.LeaveRequestDocumentService;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.*;

public class LeaveRequestAction extends TkAction {
    LeaveRequestDocumentService leaveRequestDocumentService;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		LeaveRequestForm leaveForm = (LeaveRequestForm) form;
		String principalId = TKUser.getCurrentTargetPerson().getPrincipalId();
		Date currentDate = TKUtils.getTimelessDate(null);

        List<LeaveBlock> plannedLeaves = getLeaveBlocksWithRequestStatus(principalId, currentDate, LMConstants.REQUEST_STATUS.PLANNED);
        plannedLeaves.addAll(getLeaveBlocksWithRequestStatus(principalId, currentDate, LMConstants.REQUEST_STATUS.DEFERRED));
		leaveForm.setPlannedLeaves(plannedLeaves);
		leaveForm.setPendingLeaves(getLeaveBlocksWithRequestStatus(principalId, currentDate, LMConstants.REQUEST_STATUS.REQUESTED));
		leaveForm.setApprovedLeaves(getLeaveBlocksWithRequestStatus(principalId, currentDate, LMConstants.REQUEST_STATUS.APPROVED));
		leaveForm.setDisapprovedLeaves(getLeaveBlocksWithRequestStatus(principalId, currentDate, LMConstants.REQUEST_STATUS.DISAPPROVED));

        leaveForm.setDocuments(getLeaveRequestDocuments(leaveForm));
		return forward;
	}


    private List<LeaveBlock> getLeaveBlocksWithRequestStatus(String principalId, Date currentDate, String requestStatus) {
        List<LeaveBlock> plannedLeaves = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, requestStatus, currentDate);

        Collections.sort(plannedLeaves, new Comparator<LeaveBlock>() {
            @Override
            public int compare(LeaveBlock leaveBlock1, LeaveBlock leaveBlock2) {
                return ObjectUtils.compare(leaveBlock1.getLeaveDate(), leaveBlock2.getLeaveDate());
            }
        });

        return plannedLeaves;
    }

	  
	public ActionForward submitForApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestForm lf = (LeaveRequestForm) form;
		for(LeaveBlock leaveBlock : lf.getPlannedLeaves()) {
			// check if check box is checked
			//System.out.println("Leave block submit is :: >>>>"+leaveBlock.getSubmit());
			if(leaveBlock.getSubmit()) {
                LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().createLeaveRequestDocument(leaveBlock.getLmLeaveBlockId());
                TkServiceLocator.getLeaveRequestDocumentService().requestLeave(lrd.getDocumentNumber());
		    }
		}
	    return mapping.findForward("basic");
	}

    private Map<String, LeaveRequestDocument> getLeaveRequestDocuments(LeaveRequestForm form) {
        Map<String, LeaveRequestDocument> docs = new HashMap<String, LeaveRequestDocument>();

        if (form == null) {
            return docs;
        }

        for (LeaveBlock leaveBlock : form.getPendingLeaves()) {
            docs.put(leaveBlock.getLmLeaveBlockId(), getLeaveRequestDocumentService().getLeaveRequestDocument(leaveBlock.getLeaveRequestDocumentId()));
        }
        for (LeaveBlock leaveBlock : form.getApprovedLeaves()) {
            docs.put(leaveBlock.getLmLeaveBlockId(), getLeaveRequestDocumentService().getLeaveRequestDocument(leaveBlock.getLeaveRequestDocumentId()));
        }
        for (LeaveBlock leaveBlock : form.getDisapprovedLeaves()) {
            docs.put(leaveBlock.getLmLeaveBlockId(), getLeaveRequestDocumentService().getLeaveRequestDocument(leaveBlock.getLeaveRequestDocumentId()));
        }
        return docs;
    }

    private LeaveRequestDocumentService getLeaveRequestDocumentService() {
        if (leaveRequestDocumentService == null) {
            leaveRequestDocumentService = TkServiceLocator.getLeaveRequestDocumentService();
        }
        return leaveRequestDocumentService;
    }
	  
}
