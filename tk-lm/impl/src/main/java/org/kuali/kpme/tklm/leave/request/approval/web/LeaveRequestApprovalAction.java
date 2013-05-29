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
package org.kuali.kpme.tklm.leave.request.approval.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.ApprovalFormAction;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionItem;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public class LeaveRequestApprovalAction extends ApprovalFormAction {
	
    public static final String DOC_SEPARATOR = "----";	// separator for documents
    public static final String ID_SEPARATOR = "____";	// separator for documentId and reason string
    public static final String DOC_NOT_FOUND = "Leave request document not found with id ";
    public static final String LEAVE_BLOCK_NOT_FOUND = "Leave Block not found for Leave request document ";
    
    @Override
	protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
		if (!HrContext.isReviewer() && !HrContext.isAnyApprover() && !HrContext.isSystemAdmin() && !TkContext.isLocationAdmin() 
				&& !HrContext.isGlobalViewOnly() && !TkContext.isDepartmentViewOnly() && !TkContext.isDepartmentAdmin()) {
			throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "ApprovalFormAction", "");
		}
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
		
        LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
        
	    List<ActionItem> items = getActionItems(lraaForm.getSelectedPayCalendarGroup(), lraaForm.getSelectedDept(), getWorkAreas(lraaForm));
		lraaForm.setEmployeeRows(getEmployeeRows(items));
		
		return forward;
	}
	
	@Override
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward actionForward = super.selectNewDept(mapping, form, request, response);
		
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
        
    	List<ActionItem> items = getActionItems(lraaForm.getSelectedPayCalendarGroup(), lraaForm.getSelectedDept(), getWorkAreas(lraaForm));
		lraaForm.setEmployeeRows(getEmployeeRows(items));	
 	
		return actionForward;
	}
	
	@Override
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward actionForward = super.selectNewWorkArea(mapping, form, request, response);
		    
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
        
    	List<ActionItem> items = getActionItems(lraaForm.getSelectedPayCalendarGroup(), lraaForm.getSelectedDept(), getWorkAreas(lraaForm));
		lraaForm.setEmployeeRows(getEmployeeRows(items));	
        
		return actionForward;
	}
	
	private List<ActionItem> getActionItems(String calGroup, String dept, List<String> workAreaList) {
		String principalId = HrContext.getTargetPrincipalId();
		List<ActionItem> actionList = KewApiServiceLocator.getActionListService().getActionItemsForPrincipal(principalId);
		List<ActionItem> resultsList = new ArrayList<ActionItem>();

		LocalDate currentDate = LocalDate.now();
		List<String> principalIds = LmServiceLocator.getLeaveApprovalService()
 			.getLeavePrincipalIdsWithSearchCriteria(workAreaList, calGroup, currentDate, currentDate, currentDate);    
		
		if(CollectionUtils.isNotEmpty(principalIds)) {
			for(ActionItem anAction : actionList) {
				String docId = anAction.getDocumentId();
				if(anAction.getDocName().equals(LeaveRequestDocument.LEAVE_REQUEST_DOCUMENT_TYPE)) {
					LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
					if(lrd != null) {
						LeaveBlock lb = lrd.getLeaveBlock();
						if(lb != null) {
							if(principalIds.contains(lb.getPrincipalId())) {
								resultsList.add(anAction);
							}
						}
					}
				}
			}
		}
        
        return resultsList;
	}
	
	public ActionForward takeActionOnEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		
		if(StringUtils.isNotEmpty(lraaForm.getApproveList())) {
			String[] approveList = lraaForm.getApproveList().split(DOC_SEPARATOR);
			for(String eachAction : approveList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString = fields.length > 1 ? fields[1] : ""; 	// approve reason text, could be empty
				LmServiceLocator.getLeaveRequestDocumentService().approveLeave(docId, HrContext.getPrincipalId(), reasonString);
				// leave block's status is changed to "approved" in postProcessor of LeaveRequestDocument
			}
		}
		if(StringUtils.isNotEmpty(lraaForm.getDisapproveList())) {
			String[] disapproveList = lraaForm.getDisapproveList().split(DOC_SEPARATOR);
			for(String eachAction : disapproveList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString = fields.length > 1 ? fields[1] : ""; 	// disapprove reason
				LmServiceLocator.getLeaveRequestDocumentService().disapproveLeave(docId, HrContext.getPrincipalId(), reasonString);
				// leave block's status is changed to "disapproved" in postProcessor of LeaveRequestDocument	
			}
		}
		if(StringUtils.isNotEmpty(lraaForm.getDeferList())) {
			String[] deferList = lraaForm.getDeferList().split(DOC_SEPARATOR);
			for(String eachAction : deferList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString =  fields.length > 1 ? fields[1] : ""; 	// defer reason
				LmServiceLocator.getLeaveRequestDocumentService().deferLeave(docId, HrContext.getPrincipalId(), reasonString);
				// leave block's status is changed to "deferred" in postProcessor of LeaveRequestDocument	
			}
		}
		return mapping.findForward("basic");
	}

	private List<LeaveRequestApprovalEmployeeRow> getEmployeeRows(List<ActionItem> actionList) {
		List<LeaveRequestApprovalEmployeeRow> empRowList = new ArrayList<LeaveRequestApprovalEmployeeRow>();
		Map<String, List<LeaveRequestDocument>> docMap = new HashMap<String, List<LeaveRequestDocument>>();
		for(ActionItem action : actionList) {
			if(action.getDocName().equals(LeaveRequestDocument.LEAVE_REQUEST_DOCUMENT_TYPE)) {
				String docId = action.getDocumentId();
				LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
				if(lrd != null) {
					LeaveBlock lb = lrd.getLeaveBlock();
					if(lb != null) {
						String lbPrincipalId = lb.getPrincipalId();
						List<LeaveRequestDocument> docList = docMap.get(lbPrincipalId) == null ? new ArrayList<LeaveRequestDocument>() : docMap.get(lbPrincipalId);
						docList.add(lrd);
						docMap.put(lbPrincipalId, docList);
					}
				}
				
			}
		}
		for (Map.Entry<String, List<LeaveRequestDocument>> entry : docMap.entrySet()) {
			LeaveRequestApprovalEmployeeRow aRow = this.getAnEmployeeRow(entry.getKey(), entry.getValue());
			if(aRow != null) {
				empRowList.add(aRow);
			}
		}
		// sort list by employee name
		Collections.sort(empRowList, new Comparator<LeaveRequestApprovalEmployeeRow>() {
			@Override
			public int compare(LeaveRequestApprovalEmployeeRow row1, LeaveRequestApprovalEmployeeRow row2) {
				return ObjectUtils.compare(row1.getEmployeeName(), row2.getEmployeeName());
			}
    	});		
		
		return empRowList;
	}
	
	private LeaveRequestApprovalEmployeeRow getAnEmployeeRow(String principalId, List<LeaveRequestDocument> docList) {
		if(CollectionUtils.isEmpty(docList) || StringUtils.isEmpty(principalId)) {
			return null;
		}
        EntityNamePrincipalName entityNamePrincipalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
		if(entityNamePrincipalName == null) {
			return null;
		}
		LeaveRequestApprovalEmployeeRow empRow = new LeaveRequestApprovalEmployeeRow();
		empRow.setPrincipalId(principalId);
		empRow.setEmployeeName(entityNamePrincipalName.getDefaultName() == null ? StringUtils.EMPTY : entityNamePrincipalName.getDefaultName().getCompositeName());
		List<LeaveRequestApprovalRow> rowList = new ArrayList<LeaveRequestApprovalRow>();
		for(LeaveRequestDocument lrd : docList) {
			if(lrd == null) {
				return null;
			}
			LeaveBlock lb = lrd.getLeaveBlock();
			if(lb == null) {
				return null;
			}
			LeaveRequestApprovalRow aRow = new LeaveRequestApprovalRow();
			aRow.setLeaveRequestDocId(lrd.getDocumentNumber());
			aRow.setLeaveCode(lb.getEarnCode());
			aRow.setRequestedDate(TKUtils.formatDate(lb.getLeaveLocalDate()));
			aRow.setRequestedHours(lb.getLeaveAmount().toString());
			aRow.setDescription(lrd.getDescription());
			DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
			aRow.setSubmittedTime(formatter.print(lrd.getDocumentHeader().getWorkflowDocument().getDateCreated()));
			rowList.add(aRow);
		}
		// sort list by date
		if(CollectionUtils.isNotEmpty(rowList)) {
			Collections.sort(rowList, new Comparator<LeaveRequestApprovalRow>() {
				@Override
				public int compare(LeaveRequestApprovalRow row1, LeaveRequestApprovalRow row2) {
					return ObjectUtils.compare(row1.getRequestedDate(), row2.getRequestedDate());
				}
	    	});
			empRow.setLeaveRequestList(rowList);
		}
		return empRow;
	}

	@SuppressWarnings("unchecked")
	public ActionForward validateActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		JSONArray errorMsgList = new JSONArray();
        List<String> errors = new ArrayList<String>();
        if(StringUtils.isEmpty(lraaForm.getApproveList()) 
	        	&& StringUtils.isEmpty(lraaForm.getDisapproveList())
	        	&& StringUtils.isEmpty(lraaForm.getDeferList())) {
        	errors.add("No Actions selected. Please try again.");
        } else {
			if(StringUtils.isNotEmpty(lraaForm.getApproveList())) {
				String[] approveList = lraaForm.getApproveList().split(DOC_SEPARATOR);
				for(String eachAction : approveList){
					String[] fields = eachAction.split(ID_SEPARATOR);
					String docId = fields[0];	// leave request document id
					LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
					if(lrd == null) {
						errors.add(DOC_NOT_FOUND + docId);
						break;
					} else {
						LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
						if(lb == null) {
							errors.add(LEAVE_BLOCK_NOT_FOUND + docId);
							break;
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(lraaForm.getDisapproveList())) {
				String[] disapproveList = lraaForm.getDisapproveList().split(DOC_SEPARATOR);
				for(String eachAction : disapproveList){
					String[] fields = eachAction.split(ID_SEPARATOR);
					String docId = fields[0];	// leave request document id
					String reasonString = fields.length > 1 ? fields[1] : ""; 	// disapprove reason
					if(StringUtils.isEmpty(reasonString)) {
						errors.add("Reason is required for Disapprove action");
						break;
					} else {
						LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
						if(lrd == null) {
							errors.add(DOC_NOT_FOUND + docId);
							break;
						}else {
							LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
							if(lb == null) {
								errors.add(LEAVE_BLOCK_NOT_FOUND + docId);
								break;
							}
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(lraaForm.getDeferList())) {
				String[] deferList = lraaForm.getDeferList().split(DOC_SEPARATOR);
				for(String eachAction : deferList){
					String[] fields = eachAction.split(ID_SEPARATOR);
					String docId = fields[0];	// leave request document id
					String reasonString =  fields.length > 1 ? fields[1] : ""; 	// defer reason
					if(StringUtils.isEmpty(reasonString)) {
						errors.add("Reason is required for Defer action");
						break;
					} else {
						LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
						if(lrd == null) {
							errors.add(DOC_NOT_FOUND + docId);
							break;
						}else {
							LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
							if(lb == null) {
								errors.add(LEAVE_BLOCK_NOT_FOUND + docId);
								break;
							}
						}
					}
				}
			}
        }
        errorMsgList.addAll(errors);
        lraaForm.setOutputString(JSONValue.toJSONString(errorMsgList));
        return mapping.findForward("ws");
    }	
}
