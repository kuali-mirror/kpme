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
package org.kuali.kpme.tklm.leave.request.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.request.service.LeaveRequestDocumentService;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;

public class LeaveRequestAction extends KPMEAction {
    LeaveRequestDocumentService leaveRequestDocumentService;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		LeaveRequestForm leaveForm = (LeaveRequestForm) form;
		String principalId = HrContext.getTargetPrincipalId();
		DateTime currentDate = LocalDate.now().toDateTimeAtStartOfDay();

        if (leaveForm.getNavString() == null) {
            leaveForm.setYear(LocalDate.now().getYear());
        } else if(leaveForm.getNavString().equals("NEXT")) {
            leaveForm.setYear(leaveForm.getYear() + 1);
        } else if(leaveForm.getNavString().equals("PREV")) {
            leaveForm.setYear(leaveForm.getYear() - 1);
        }
        LocalDate beginDate = new LocalDate(leaveForm.getYear(), 1, 1);
        LocalDate endDate = new LocalDate(leaveForm.getYear(), 12, 31);

//        CalendarEntry calendarEntry = HrServiceLocator.getCalendarService().getCurrentCalendarDatesForLeaveCalendar(principalId, currentDate);
        CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarDatesForLeaveCalendar(principalId, beginDate.toDateTimeAtStartOfDay(), endDate.toDateTimeAtStartOfDay());

        //  If the current pay period ends before the current leave calendar ends, then we need to include any planned leave blocks that occur
        //  in this window between the current pay end and the beginning of the leave planning calendar (the next future leave period).
        //  The most common scenario occurs when a non-monthly pay period ends before the current leave calendar ends.

        CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates(principalId, beginDate.toDateTimeAtStartOfDay(), endDate.toDateTimeAtStartOfDay());
        Boolean checkLeaveEligible = true;
        Boolean nonExemptLeaveEligible = LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, HrConstants.FLSA_STATUS_NON_EXEMPT,checkLeaveEligible);
        if(nonExemptLeaveEligible && calendarEntry != null && payCalendarEntry != null) {
            if ( payCalendarEntry.getEndPeriodDate().before(calendarEntry.getEndPeriodDate()) ) {
                calendarEntry = payCalendarEntry;
            }
        }

		if(calendarEntry != null) {
			if(calendarEntry.getEndPeriodLocalDateTime().getMillisOfDay() == 0) {
				// if the time of the end date is the beginning of a day, subtract one day from the end date
				currentDate = calendarEntry.getEndPeriodFullDateTime().minusDays(1);
			} else {
				currentDate = calendarEntry.getEndPeriodFullDateTime();	// only show leave requests from planning calendars on leave request page
			}
		}
        List<LeaveBlock> plannedLeaves = getLeaveBlocksWithRequestStatus(principalId, beginDate, endDate, HrConstants.REQUEST_STATUS.PLANNED);
        plannedLeaves.addAll(getLeaveBlocksWithRequestStatus(principalId, beginDate, endDate, HrConstants.REQUEST_STATUS.DEFERRED));
		leaveForm.setPlannedLeaves(plannedLeaves);
		leaveForm.setPendingLeaves(getLeaveBlocksWithRequestStatus(principalId, beginDate, endDate, HrConstants.REQUEST_STATUS.REQUESTED));
		leaveForm.setApprovedLeaves(getLeaveBlocksWithRequestStatus(principalId, beginDate, endDate, HrConstants.REQUEST_STATUS.APPROVED));
		leaveForm.setDisapprovedLeaves(getDisapprovedLeaveBlockHistory(principalId, currentDate.toLocalDate()));

        leaveForm.setDocuments(getLeaveRequestDocuments(leaveForm));
		return forward;
	}


    private List<LeaveBlock> getLeaveBlocksWithRequestStatus(String principalId, LocalDate beginDate, LocalDate endDate, String requestStatus) {
        List<LeaveBlock> plannedLeaves = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, requestStatus, beginDate, endDate);

        Collections.sort(plannedLeaves, new Comparator<LeaveBlock>() {
            @Override
            public int compare(LeaveBlock leaveBlock1, LeaveBlock leaveBlock2) {
                return ObjectUtils.compare(leaveBlock1.getLeaveDate(), leaveBlock2.getLeaveDate());
            }
        });

        return plannedLeaves;
    }
    
    private List<LeaveBlockHistory> getDisapprovedLeaveBlockHistory(String principalId, LocalDate currentDate) {
        List<LeaveBlockHistory> historyList = LmServiceLocator.getLeaveBlockHistoryService()
        	.getLeaveBlockHistories(principalId, HrConstants.REQUEST_STATUS.DISAPPROVED, HrConstants.ACTION.DELETE, currentDate);

        Collections.sort(historyList, new Comparator<LeaveBlockHistory>() {
            @Override
            public int compare(LeaveBlockHistory lbh1, LeaveBlockHistory lbh2) {
                return ObjectUtils.compare(lbh1.getLeaveDate(), lbh2.getLeaveDate());
            }
        });

        return historyList;
    }

	  
	public ActionForward submitForApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestForm lf = (LeaveRequestForm) form;
		for(LeaveBlock leaveBlock : lf.getPlannedLeaves()) {
			// check if check box is checked
			if(leaveBlock.getSubmit()) {
                LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().createLeaveRequestDocument(leaveBlock.getLmLeaveBlockId());
                LmServiceLocator.getLeaveRequestDocumentService().requestLeave(lrd.getDocumentNumber());
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
        	if(leaveBlock.getLeaveRequestDocumentId() != null && !leaveBlock.getLeaveRequestDocumentId().isEmpty()){
        		docs.put(leaveBlock.getLmLeaveBlockId(), getLeaveRequestDocumentService().getLeaveRequestDocument(leaveBlock.getLeaveRequestDocumentId()));	
        	}
        }
        for (LeaveBlock leaveBlock : form.getApprovedLeaves()) {        	
        	if(leaveBlock.getLeaveRequestDocumentId() != null && !leaveBlock.getLeaveRequestDocumentId().isEmpty()){
            docs.put(leaveBlock.getLmLeaveBlockId(), getLeaveRequestDocumentService().getLeaveRequestDocument(leaveBlock.getLeaveRequestDocumentId()));
        	}
        }
        for (LeaveBlockHistory lbh : form.getDisapprovedLeaves()) {
        	List<LeaveRequestDocument> docList = getLeaveRequestDocumentService().getLeaveRequestDocumentsByLeaveBlockId(lbh.getLmLeaveBlockId());
        	for(LeaveRequestDocument lrd : docList) {
        		if(lrd.getDocumentNumber() != null && !lrd.getDocumentNumber().isEmpty()){
	        		DocumentStatus status = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(lrd.getDocumentNumber());
					if(status != null && DocumentStatus.DISAPPROVED.getCode().equals(status.getCode())) {
	    				//KPME-2214
						//*getLeaveRequestDocumentService().getLeaveRequestDocument(lrd.getDocumentNumber())* is same as lrd within docList fethced . No need to retrieve again
	    				//docs.put(lbh.getLmLeaveBlockId(), getLeaveRequestDocumentService().getLeaveRequestDocument(lrd.getDocumentNumber()));						 
						docs.put(lbh.getLmLeaveBlockId(), lrd);
						break;
					}			 
        		}
        	}
           
        }
        return docs;
    }

    private LeaveRequestDocumentService getLeaveRequestDocumentService() {
        if (leaveRequestDocumentService == null) {
            leaveRequestDocumentService = LmServiceLocator.getLeaveRequestDocumentService();
        }
        return leaveRequestDocumentService;
    }
	  
}
